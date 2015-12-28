import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DirectoryServer extends UnicastRemoteObject implements DirectoryInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected DirectoryServer() throws RemoteException, AlreadyBoundException, NotBoundException {

		
		LocateRegistry.createRegistry(1991).rebind("Directory", this);
		System.err.println("Directory ready");
		
		Registry registry = LocateRegistry.getRegistry("localhost", 1990);
		CallerInterface stub2 = (CallerInterface) registry.lookup("Caller");
		stub2.registerService("Directory", 1991, "localhost");       
	}


	public String getKey(){
		return "a";		
	}

	public String Teste(){
		//getUser("s", "s");
		return("Directory Server working....");
	}
	
	int getUser(String name, String password) {
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DATABASE_URL = "jdbc:mysql://127.0.0.1/directory";
			
		Connection connection = null;
		Statement statement = null;
		int id = 0;
		try{
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			
			statement = connection.createStatement();
			//statement.executeUpdate("USE directory;");
			
			ResultSet rs = statement.executeQuery("SELECT id FROM users WHERE name =\"" + name + "\" AND pass=\"" + password + "\";");
		
			
			while(rs.next()){
		        id= rs.getInt("id");
			}
			 connection.close();
		} catch(SQLException sqlE){
				sqlE.printStackTrace();
				System.exit(1);
			}
			catch(ClassNotFoundException classnf){
				classnf.printStackTrace();
				System.exit(1);
			}

		
		return id;

	}
	
	public String verifyUser(String name, String password){
		if (getUser(name, password) == 0) return ("F");
		else return ("T");
	}
	
	public static void main(String args[]){
		try{
			
			  DirectoryServer ds = new DirectoryServer();
		}
		catch(Exception e){
			System.err.println("Directory exception: " + e.toString());
            e.printStackTrace();
		}
	}


	public String Lookup(String fileName, String userName, String password) throws RemoteException, FileNotFound {
		
		int user = getUser(userName, password);
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DATABASE_URL = "jdbc:mysql://127.0.0.1/directory";
			
		Connection connection = null;
		Statement statement = null;
		
		String id = null;
		String permissions = null;
		String capability = null;
		
		try{
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT id,permissions FROM files WHERE name =\"" + fileName + "\";");
		
			
			if(rs.next()){
		        id = rs.getString("id");
		        permissions = rs.getString("permissions");
		        capability = id.concat("/" + permissions);
			}
			else{
				throw new FileNotFound();
			}
			connection.close();
			
		} catch(SQLException sqlE){
				sqlE.printStackTrace();
				System.exit(1);
			}
			catch(ClassNotFoundException classnf){
				classnf.printStackTrace();
				System.exit(1);
			}
		
		return capability;
	}


	public void UnName(String name, String userName, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	public String AddName(String fileName, int type, String userName, String password) throws RemoteException, LocationNotFound {
		int user = getUser(userName, password);
		
		int dash = fileName.lastIndexOf("-");
		
		String path;
		if (dash == 0) path = "-";
		else path = fileName.substring(0, dash);
		//String name = fileName.substring(dash+1, fileName.length());
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DATABASE_URL = "jdbc:mysql://127.0.0.1/directory";
			
		Connection connection = null;
		Statement statement = null;
		String newID = " ";
		try{
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT id, childs FROM files f WHERE f.name =\"" + path + "\" AND f.type = 0 AND f.permissions LIKE \"_w\";");
		
			String id = null;
			int childs = 0;
			if(rs.next()){
		        id = rs.getString("id");
		        childs = rs.getInt("childs");
			}
			else{
				throw new LocationNotFound(path);
			}

			newID = id.concat("." + String.valueOf(childs+1));
			
			statement.executeUpdate("UPDATE files SET files.childs=\"" + String.valueOf(childs+1) + "\" WHERE files.id=\"" + String.valueOf(id) + "\";");
			
			if(type == 0) statement.executeUpdate("INSERT INTO files (id, name, owner, father, childs, permissions, type) VALUES (\"" + newID + "\", \"" + 
					fileName + "\", " + user + ", \"" + path + "\", \"" + String.valueOf(0) + "\", " + "\"rw\"" + ", " + String.valueOf(type) + ");");
			else statement.executeUpdate("INSERT INTO files (id, name, owner, father, permissions, type) VALUES (\"" + newID + "\", \"" + 
					fileName + "\", " + user + ", \"" + path + "\", " + "\"rw\"" + ", " + String.valueOf(type) + ");");
		
			 connection.close();
		} catch(SQLException sqlE){
				sqlE.printStackTrace();
				System.exit(1);
			}
			catch(ClassNotFoundException classnf){
				classnf.printStackTrace();
				System.exit(1);
			}
		
		return newID;
		
	}


	public void Rename(String capability, String fileName, String newName) {
		int dash = capability.lastIndexOf("/");
		String ufid = capability.substring(0, dash);
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DATABASE_URL = "jdbc:mysql://127.0.0.1/directory";
			
		Connection connection = null;
		Statement statement = null;
		Statement statement2 = null;
		String newID = " ";
		try{
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
			
			statement = connection.createStatement();
			statement2 = connection.createStatement();
			
			dash = fileName.lastIndexOf("-");
			String name = fileName.substring(0, dash).concat("-" + newName);
			statement.executeUpdate("UPDATE files SET files.name=\"" + name + "\" WHERE files.id=\"" + ufid + "\";");
			
			ResultSet rs = statement2.executeQuery("SELECT id,name FROM files f WHERE f.name LIKE \"" + fileName + "%\";");
			
			String childName = null;
			while(rs.next()){
				Integer cid = rs.getInt("id");
				childName = rs.getString("name");
				String newchildName = childName.replace(fileName, name);
				dash = newchildName.lastIndexOf("-");
				String father = newchildName.substring(0, dash);
				statement.executeUpdate("UPDATE files SET files.name=\"" + newchildName + "\" ,files.father = \"" + father +"\" WHERE files.name=\"" + childName + "\";");
			}
			
			 connection.close();
		} catch(SQLException sqlE){
				sqlE.printStackTrace();
				System.exit(1);
			}
			catch(ClassNotFoundException classnf){
				classnf.printStackTrace();
				System.exit(1);
			}
		
	}
	
	public LinkedList<String> Delete(String name, String userName, String password) throws RemoteException, FileNotFound {
	int user = getUser(userName, password);
	
	LinkedList<String> l = new LinkedList<String>();
	
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DATABASE_URL = "jdbc:mysql://127.0.0.1/directory";
		
	Connection connection = null;
	Statement statement = null;
	String newID = " ";
	try{
		Class.forName(JDBC_DRIVER);
		
		connection = DriverManager.getConnection(DATABASE_URL, "root", "root");
		
		statement = connection.createStatement();
		
		ResultSet rs = statement.executeQuery("SELECT id FROM files f WHERE f.name =\"" + name + "\" AND f.permissions LIKE \"_w\";");
	
		String id = null;
		if(rs.next()){
	        id = rs.getString("id");
		}
		else{
			throw new FileNotFound();
		}

		ResultSet rs2 = statement.executeQuery("SELECT id FROM files WHERE files.id LIKE \"" + id + "%\";");
		
		
		while(rs2.next()) {
			String s = rs2.getString("id");
			l.add(s);
		}
		
		statement.executeUpdate("DELETE FROM files WHERE files.id LIKE \"" + id + "%\";");
		
		 connection.close();
	} catch(SQLException sqlE){
			sqlE.printStackTrace();
			System.exit(1);
		}
		catch(ClassNotFoundException classnf){
			classnf.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return l;
}
}
