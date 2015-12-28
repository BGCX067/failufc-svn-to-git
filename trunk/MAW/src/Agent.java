import java.util.LinkedList;
import java.util.Random;

public abstract class Agent{
	int myid;
	double cash;
	int posx;
	int posy;
	
	public Agent(int i, int x, int y){
		myid = i;
		posx = x;
		posy =y;
		cash =0;
	}
	
	public abstract boolean interestedInCoalition();
	
	public boolean yourTurn(Maze maze){
		Random rand = new Random();
		/*Escolher algum lugar pra ir.
		 * 0: cima
		 * 1: direita
		 * 2: baixo
		 * 3: esquerda*/
		int direction = rand.nextInt(4);
		
		/*Ir para a nova posição
		 * caso não possa ir ele se mantém onde está*/
		if( (direction==0) && (posy-1>=0) ){
			if(maze.rooms[posy-1][posx]==null){
				maze.rooms[posy-1][posx] = maze.rooms[posy][posx];
				maze.rooms[posy][posx] = null;
				posy = posy-1;
			}
		}
		
		if( (direction==1) && (posx+1<maze.rooms.length) ){
			if(maze.rooms[posy][posx+1]==null){
				maze.rooms[posy][posx+1] = maze.rooms[posy][posx];
				maze.rooms[posy][posx] = null;
				posx = posx+1;
			}
		}
		
		if( (direction==2) && (posy+1<maze.rooms.length) ){
			if(maze.rooms[posy+1][posx]==null){
				maze.rooms[posy+1][posx] = maze.rooms[posy][posx];
				maze.rooms[posy][posx] = null;
				posy = posy+1;
			}
		}
		
		if( (direction==3) && (posx-1>=0) ){
			if(maze.rooms[posy][posx-1]==null){
				maze.rooms[posy][posx-1] = maze.rooms[posy][posx];
				maze.rooms[posy][posx] = null;
				posx = posx-1;
			}
		}
		
		/*Verificar se há ouro na sala
		 * Se sim termina o turno e retorna true
		 * Caso Contrário 
		 * 	Verificar se há alguem próximo
		 * 	Se houver propõe coalizão*/
		
		if(maze.isThereAnyGold(posx, posy)){
			return(true);
		}
		
		LinkedList<Agent> list = maze.isThereSomeoneNear(posx, posy);
		if(list.size()!=0){
			for(int i=0; i<list.size(); i++){
				Agent near = list.get(i);
				if( (near.interestedInCoalition()) && this.interestedInCoalition()){
					maze.mergeCoalitions(this.myid, near.myid);
				}
			}
		}
		return(false);
	}
	
	public void newPosition(int px, int py){
		posx = px;
		posy = py;
	}
	
	public void incrementCash(double money){
		cash = cash + money;
	}
	
	public void print(){
		System.out.println("My name is Agent_"+myid+" and I have "+cash+" of money!");
	}
}
