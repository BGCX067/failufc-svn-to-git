import java.lang.Exception;

public class FileNotFound extends Exception{

	public String toString(){
		return("Arquivo não encontrado");
	}
}
