import java.util.ArrayList;
public class SpecialPhilosopher extends Thread{
	private int position;
	ArrayList<Semaphore> garfos1;
	ArrayList<Semaphore> garfos2;
	Semaphore quarto1;
	Semaphore quarto2;
	private int tamanho;

	public SpecialPhilosopher(int pos, Semaphore room1, Semaphore room2,ArrayList<Semaphore> garfos1, ArrayList<Semaphore> garfos2, int tam){
		position = pos;
		this.garfos1 = garfos1;
		this.garfos2 = garfos2;
		quarto1 = room1;
		quarto2 = room2;
		tamanho = tam;

	}

	public void run(){
		while(true){
			try{
				System.out.print("O filosofo [" + position + "] está pensando\n");
				quarto1.waitS();
				quarto2.waitS();
				garfos1.get(position % tamanho).waitS();
				garfos1.get((position + 1) % tamanho).waitS();
				garfos2.get(position % tamanho).waitS();
				garfos2.get((position + 1) % tamanho).waitS();
				System.out.print("O filosofo [" + position + "] está comendo\n");
				garfos1.get(position % tamanho).signalS();
				garfos1.get((position + 1) % tamanho).signalS();
				garfos2.get(position % tamanho).signalS();
				garfos2.get((position + 1) % tamanho).signalS();
				quarto1.signalS();
				quarto2.signalS();
			}
			catch(Exception e){}
		}

	}

}
