import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class Caller implements CallerInterface{
	HashMap<String, Location> hash = new HashMap<String, Location>();
	
	public void registerService(String serviceName, int portNumber, String IPAdress){
		hash.put(serviceName, new Location(IPAdress, portNumber));
	}
	
	public Location lookUpService(String serviceName) throws ServiceNotFound{
		if( ! hash.containsKey(serviceName) ){
			throw new ServiceNotFound();
		}
		Location location = hash.get(serviceName);
		return(location);
	}
	
	public static void main(String[] args){
		try{
			CallerInterface stub = (CallerInterface) UnicastRemoteObject.exportObject( new Caller(), 0);
			LocateRegistry.createRegistry(1990).rebind("Caller", stub);
			System.err.println("Caller ready");
		}
		catch(Exception e){
			System.err.println("Caller exception: " + e.toString());
            e.printStackTrace();
		}
	}
}
