//package src;
import java.util.LinkedList;
import java.util.Queue;

public class Semaphore{
	private int k;
	private Object A = new Object();
	private Thread t = null;
	private Thread tt = new Thread();
	private Queue<Thread> queue = new LinkedList<Thread>();
	private boolean w;
	private boolean continua, continua2;

	protected Semaphore(int num){
		k = num;
		w = false;
		continua = continua2 = true;
		System.out.println("Oi");
		new Thread() {
			public void run() {
				boolean continuaAux = true;
				synchronized (A) {
					while ((t == null || (t != tt && !queue.contains(t))) && continua){
						try{
							A.wait();
						}catch(java.lang.InterruptedException e){}
					}
					continuaAux = continua;
				}
				while (continuaAux) {
					synchronized (t) {
						while (!w){
							try{
								t.wait();
							}catch(java.lang.InterruptedException e){}
						}
						w = false;
					}

					synchronized (A) {
						t = null;
						A.notify();

						while ((t == null || (t != tt && !queue.contains(t))) && continua){
							try{
								A.wait();
							}catch(java.lang.InterruptedException e){}
						}
						if(!continua){
							for(int i = 0; i < queue.size(); i++){
								queue.peek().interrupt();
								queue.remove();
							}
						}
						continuaAux = continua;
					}
				}
				System.out.println("Semaforo encerrando...");
			}
		}.start();		
	}

	public void waitS() throws java.lang.InterruptedException{
		synchronized (A) {
			while (t != null && continua){
				A.wait();
			}
			if(!continua){
				A.notifyAll();
				throw new java.lang.InterruptedException();
			}
			if (k > 0) {
				k--;
				t = tt;
			}
			else {
				t = Thread.currentThread();
				queue.add(t);
			}
			A.notifyAll();
		}

		synchronized (t) {
			w = true;
			t.notify();
			if (t != tt)
				t.wait();
		}
	}

	public void signalS() throws java.lang.InterruptedException{
		Thread p = null;
		synchronized (A) {
			while (t != null && continua){
					A.wait();
			}
			if(!continua){
				A.notifyAll();
				throw new java.lang.InterruptedException();
			}
			if (queue.isEmpty())
				k++;
			else
				p = queue.remove();

			A.notify();
		}

		if (p != null)
			synchronized (p){
				p.notify();
			}
	}

	public void stop(){
		synchronized(A){
			continua = false;
			A.notify();
		}
		if(t != null) {
			synchronized(t){
				t.interrupt();
			}
		}

	}
		
}
