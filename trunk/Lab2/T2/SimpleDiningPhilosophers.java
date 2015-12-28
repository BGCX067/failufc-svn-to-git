/*import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;*/
import java.util.ArrayList;

public class SimpleDiningPhilosophers{

	public static long dine(int k){

		final int TAM = k;
		long begin;
		long end;
		Countdown final_countdown = new Countdown(TAM);
		ArrayList<Semaphore> semaphores = new ArrayList<Semaphore>();
		ArrayList<Garfo> garfos = new ArrayList<Garfo>();
	    ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
	    
		for(int i = 0; i < TAM; i++){

			semaphores.add(i, new Semaphore(1));
			garfos.add(i, new Garfo(i));
		}
		
		Semaphore room = new Semaphore(TAM -1);
		Semaphore fim = new Semaphore(1);
		
		for(int i = 0; i<TAM; i++){

			philosophers.add(i ,new Philosopher(i, room, semaphores, TAM, garfos, final_countdown, fim));
		}
		
		begin = System.currentTimeMillis();
		
		for(int i=0; i<TAM; i++){
			philosophers.get(i).start();
		}
		try{
		fim.waitS();
			while(!final_countdown.isFinished()){
				
					fim.signalS();
					fim.waitS();
			}
		fim.signalS();
		}catch(Exception e){}
		
		end = System.currentTimeMillis();
		
		room.stop();
		fim.stop();
		
		for(int i = 0; i < TAM; i++){

			semaphores.get(i).stop();
		}
		
		for(int i = 0; i < TAM; i++){

			garfos.get(i).print("Relatorio_"+i+".txt");
		}
		
		return end-begin;
	}

}
