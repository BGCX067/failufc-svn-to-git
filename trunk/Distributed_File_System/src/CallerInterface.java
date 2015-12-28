import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallerInterface extends Remote{

	void registerService(String serviceName, int portNumber, String IPadress) throws RemoteException;
	Location lookUpService(String serviceName) throws ServiceNotFound, RemoteException;
	
}
