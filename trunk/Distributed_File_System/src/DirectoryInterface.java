import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface DirectoryInterface extends Remote{
	
	String getKey() throws RemoteException;
	String verifyUser(String name, String password) throws RemoteException;
	
	String Lookup(String name,String userName, String password) throws RemoteException, FileNotFound;//->(ufid) - 	EXCEPTION (Inexistente)
	String AddName(String fileName, int type, String userName, String password) throws RemoteException, LocationNotFound; //–  			EXCEPTION (NomeExistente)
	void UnName(String name, String userName, String password) throws RemoteException;//	EXCEPTION (Inexistente)
	public void Rename(String capability, String fileName, String newName)throws RemoteException;
	LinkedList<String> Delete(String name, String userName, String password) throws RemoteException, FileNotFound;
	/*GetNames (DirName, expressao) -> nomes*/
	String Teste() throws RemoteException;
}
