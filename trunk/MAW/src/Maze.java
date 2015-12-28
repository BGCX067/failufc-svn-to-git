import java.util.LinkedList;

public class Maze {
	LinkedList<Coalition> coalitions;
	Agent[][] rooms;
	int goldposx;
	int goldposy;
	
	public Maze(int size, int gposx, int gposy, LinkedList<Coalition> coali){
		coalitions = coali;
		rooms = new Agent[size][size];
		goldposx = gposx;
		goldposy = gposy;
	}
	
	public boolean isThereAnyGold(int posx, int posy){
		if( (goldposx == posx) &&(goldposy==posy) ){
			return(true);
		}
		return(false);
	}
	
	public LinkedList<Agent> isThereSomeoneNear(int posx, int posy){
		LinkedList<Agent> list = new LinkedList<Agent>();
		//Há alguém acima
		if(posy-1>0){
			if (rooms[posy-1][posx]!= null) {
				list.add(rooms[posy-1][posx]);
			}
		}
		//Há alguém à direita
		if(posx+1<rooms.length){
			if(rooms[posy][posx+1]!=null){
				list.add(rooms[posy][posx+1]);
			}
		}
		//Há alguém abaixo
		if(posy+1<rooms.length){
			if(rooms[posy+1][posx]!=null){
				list.add(rooms[posy+1][posx]);
			}
		}
		//Há alguém à esquerda
		if(posx-1>0){
			if(rooms[posy][posx-1]!=null){
				list.add(rooms[posy][posx-1]);
			}
		}
		
		return(list);
	}
	
	public int mergeCoalitions(int id1, int id2){
		Coalition coalition1 = null;
		Coalition coalition2 = null;
		
		for(int i = 0; i< coalitions.size(); i++){
			coalition1 = coalitions.get(i);
			if(coalition1.belongsToCoalition(id1)){
				break;
			}
		}
		
		if(coalition1.belongsToCoalition(id2)){
			return (0);
		}
		
		for(int i = 0; i< coalitions.size(); i++){
			coalition2 = coalitions.get(i);
			if(coalition2.belongsToCoalition(id2)){
				break;
			}
		}
		
		if( !(coalition1.existSmart && coalition2.existSmart) ){
			coalitions.remove(coalition1);
			coalitions.remove(coalition2);
			coalition1.existSmart = (coalition1.existSmart || coalition2.existSmart);
			coalition1.agents.addAll(coalition2.agents);
			coalitions.addFirst(coalition1);
		}
		
		return(0);
	}
	
	public void printRooms(){
		for(int i=0; i<rooms.length; i++){
			for(int j=0; j<rooms.length; j++){
				if(rooms[i][j]!=null){
					System.out.print("|_o_|");
				}
				else if((i==goldposy) && (j==goldposx)){
					System.err.print("|$$$|");
				}
				else{
					System.out.print("|   |");
				}
			}
			System.out.println("");
		}
	}
	
	public void printCoalitions(){
		for(int i=0; i<coalitions.size(); i++){
			System.err.println("Coalizão "+i+":");
			coalitions.get(i).print();
		}
	}
	
	public Coalition getCoalition(int id){
		for(int i = 0; i< coalitions.size(); i++){
			if(coalitions.get(i).belongsToCoalition(id)){
				return(coalitions.get(i));
			}
		}
		return(null);
	}
	
}
