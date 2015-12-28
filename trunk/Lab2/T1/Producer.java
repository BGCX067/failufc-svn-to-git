//package src;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Producer extends Thread{
	
	List<Integer> array;
	Semaphore notEmpty;
	Semaphore notFull;

	public Producer(List<Integer> a, Semaphore e, Semaphore f){
		array = a;
		notEmpty = e;
		notFull = f;
	}
	
	public void run(){
		int count;
		//Scanner input = new Scanner(System.in);
		Random r = new Random();
		count = r.nextInt(100) + 1;
		System.out.print("Producer Count" + Thread.currentThread() + ": " + count +"\n");
		while(count > 0){
			int num = r.nextInt();
			try{
				notFull.waitS();
			}
			catch(java.lang.InterruptedException e){
				System.out.println("Producer encerrando...");
				break;
			}
			System.out.print("Producer " + Thread.currentThread() + ": " + num +"\n");
			array.add(new Integer(num));
			try{
				notEmpty.signalS();
			}
			catch(java.lang.InterruptedException e){
				System.out.println("Producer encerrando...");
				break;
			}


			if(count == 1){
				try{
					notFull.waitS();
				}
				catch(java.lang.InterruptedException e){
					System.out.println("Producer encerrando...");
					break;
				}
				System.out.print("Producer " + Thread.currentThread() + ": 0\n");
				array.add(0);
				System.out.println("Producer encerrando...");
				try{
					notEmpty.signalS();
				}
				catch(java.lang.InterruptedException e){
					System.out.println("Producer encerrando...");
					break;
				}	
			}
			count--;

		}//Fim de while(true)

	}//Fim do metodo run()

}//Fim da classe Producer
