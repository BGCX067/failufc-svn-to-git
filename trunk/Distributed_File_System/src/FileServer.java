import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;


public class FileServer extends UnicastRemoteObject implements FileInterface{
	
	protected FileServer() throws RemoteException, AlreadyBoundException, NotBoundException {
		LocateRegistry.createRegistry(1992).rebind("File", this);
		System.err.println("File ready");
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1990);
		CallerInterface stub2 = (CallerInterface) registry.lookup("Caller");
		stub2.registerService("File", 1992, "localhost");   
	}
	
	public String Teste(){
		return("File Server working.....");
	}
	
	public String read(String capability, int off, int length) throws PermisionDenied{
		int dash = capability.lastIndexOf("/");
		String ufid = capability.substring(0, dash);
		
		String permissions = capability.substring(dash+1, capability.length());
		if(permissions.startsWith("r")) {
			
			char[] result = new char[length+1];
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(ufid));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				reader.read(result, off, length);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			return (new String(result));
		}
		throw new PermisionDenied();
	}
	
	public void write(String capability, String content, int position) throws PermisionDenied{
		int dash = capability.lastIndexOf("/");
		String ufid = capability.substring(0, dash);
		
		String permissions = capability.substring(dash+1, capability.length());
		if(permissions.endsWith("w")) {
	
			FileWriter file = null;
			try {
				file = new FileWriter(ufid, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				
				file.write(content, position, content.length());
				file.close();
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new PermisionDenied();
		
	}
	
	public void create(String ufid){
		 File file = new File(ufid);
		
		 try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void delete(LinkedList<String> l){
		for(int i = 0; i < l.size(); i++) {
			File file = new File(l.get(i));
			file.delete();
		}
	}
	
	public static void main(String args[]){
		
		try{
			FileServer fs = new FileServer();
			         
		}
		catch(Exception e){
			System.err.println("Directory exception: " + e.toString());
            e.printStackTrace();
		}
	}

}
