import java.io.Serializable;

public class Location implements Serializable{
	String IP;
	int port;
	private static final long serialVersionUID = 7526471155622776147L;

	public Location(String ipadress, int portnumber){
		IP = ipadress;
		port = portnumber;
	}
	
	public String getIPAdress(){
		return(IP);
	}
	
	public int getPortNumber(){
		return(port);
	}
}
