//package src;
import java.util.List;

public class Consumer extends Thread {
	
	List<Integer> array;
	Semaphore notEmpty;
	Semaphore notFull;
	
	public Consumer(List<Integer> array2, Semaphore e, Semaphore f){
		this.array = array2;
		notEmpty = e;
		notFull = f;
	}
	
	public void run(){
		int num;
		while(true){
			try{
				notEmpty.waitS();
			}
			catch(java.lang.InterruptedException e){
				System.out.println("Consumer Encerrando....");
				break;
			}
			num = array.remove(0);
			System.out.print("Consumer "+ Thread.currentThread() +": "+  num +"\n");
			try{
				notFull.signalS();
			}
			catch(java.lang.InterruptedException e){
				System.out.println("Consumer Encerrando....");
				break;
			}
			
			if(num == 0){
				notEmpty.stop();
				notFull.stop();
				System.out.print("Consumer encerrando...\n");
				break;
			}
		}//Fim de while(true)
		
	}//Fim do metodo run
	
}//Fim da classe Consumer
