import java.io.*;

public class Verify {
	public static void main(String args[]) throws Exception{

		File teste = new File("/home/0286717/Lab2/T1/FAIL");
		FileReader fr = new FileReader(teste);
	   BufferedReader d = new BufferedReader(fr);  

		int count = 0;
		int linha = 1;
		String line = null;
		while((line = d.readLine())!=null) {
			if (line.startsWith("Producer")) count++;
			else count--;
			if (count < 0 || count > 3) System.out.println(linha);
			linha++;
		}
	}
}
