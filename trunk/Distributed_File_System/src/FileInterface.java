import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface FileInterface extends Remote{

	String read(String ufid, int off, int lenght) throws RemoteException, PermisionDenied; //-> (dados) – EXCEPTION (posicaoInvalida)
	void write(String ufid, String content, int position) throws RemoteException, PermisionDenied; //– EXCEPTION (posicaoInvalida)
	void create(String ufid) throws RemoteException; //-> ufid
	void delete(LinkedList<String> l) throws RemoteException;
	/*Truncate(ufid, l)
	GetAttributes(ufid) -> descritores
	SetAttributes(ufid, descritores) */
	String Teste() throws RemoteException;
}
