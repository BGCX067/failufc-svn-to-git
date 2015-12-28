import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DinnerTest {

	public static void main(String[] args) {
		System.out.println("Jantar iniciado, aguarde...");
		
		/*i denota o numero de filósofos em cada jantar*/
		for(int i = 2; i <= 30; i++){ 
			
			long time = 0;
			
			/*Para cada jantar com um numero específico de filósofos, é realizado um numero n de iterações (10, no caso),
			 * é feita a média aritmética do valor resultante da soma do tempo, isto é feito para amenizar discrepâncias
			 * em caso de sobrecarga por causa de chamadas do sistema */
			for(int j = 0; j < 10; j++) { 
				time += SimpleDiningPhilosophers.dine(i);
			}
			
			time /= 10;
			
			System.out.println("Jantar dos " + i +" filósofos finalizado!");
			try{
				PrintWriter saida = new PrintWriter(new BufferedWriter(new FileWriter("Tempo.txt",true)), true);
				
				/*saida.println("================================Relatorio de Tempo de Execução =================================");
				saida.println("================================================================================================");
				saida.println("A execução do programa com " + TAM + " sfilosofos demorou: " + (end-begin)+" miliseconds.");*/
				saida.println(time);

			}catch(Exception e){}
			
		}

	}

}
