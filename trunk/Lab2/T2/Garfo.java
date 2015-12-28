import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.LinkedList;

public class Garfo{

	int position;
	Queue<Integer> PhilosophersList;
	
	public Garfo(int pos){
		this.position = pos;
		this.PhilosophersList = new LinkedList<Integer>();
		
	}

	public void add(int philosopher){

		PhilosophersList.add(philosopher);
	}

	public void print(String SourceFile){

		try{
			PrintWriter saida = new PrintWriter(new BufferedWriter(new FileWriter(SourceFile,false)), true);
			
			saida.println("=====================Relatorio Garfo Nº"+ position + " =====================");
			saida.println("===============================================================");
			while(!this.PhilosophersList.isEmpty()){
				
				saida.println(this.PhilosophersList.remove());
			}
		}catch(Exception e){}

	}
}
