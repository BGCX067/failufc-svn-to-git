import java.util.ArrayList;
public class Philosopher extends Thread{
	private int position;
	Countdown final_countdown;
	ArrayList<Semaphore> garfos;
	ArrayList<Garfo> forks;
	Semaphore quarto;
	private int tamanho;
	Semaphore fim;

	public Philosopher(int pos, Semaphore room ,ArrayList<Semaphore> gar, int tam,ArrayList<Garfo> forks, Countdown count, Semaphore end){
		position = pos;
		this.final_countdown = count;
		garfos = gar;
		quarto = room;
		this.forks = forks; 
		tamanho = tam;
		this.fim = end;

	}

	public void run(){
		for(int i=0; i<1000; i++){
			try{
				
				quarto.waitS();
				garfos.get(position % tamanho).waitS();
				garfos.get((position + 1) % tamanho).waitS();
				
				forks.get(position % tamanho).add(position);
				forks.get((position + 1) % tamanho).add(position);
				
				garfos.get(position % tamanho).signalS();
				garfos.get((position + 1) % tamanho).signalS();
				quarto.signalS();
			}
			catch(Exception e){}
			
		}
		try{
			fim.waitS();
			final_countdown.count();
			fim.signalS();
		}catch(Exception e){}

	}

}
