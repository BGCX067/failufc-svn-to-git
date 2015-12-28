import java.util.LinkedList;
public class Coalition {
	boolean existSmart = false;
	LinkedList<Agent> agents = new LinkedList<Agent>();
	
	public Coalition(Agent a){
		agents.add(a);
		if(a instanceof SmartAgent){
			existSmart = true;
		}
	}
	
	public boolean belongsToCoalition(int id){
		for(int i=0; i<agents.size(); i++){
			if(agents.get(i).myid == id){
				return(true);
			}
		}
		return(false);
	}
	
	public void print(){
		for(int i=0; i<agents.size(); i++){
			if(agents.get(i) instanceof InnocentAgent){
				System.err.println("Ingênuo: "+ agents.get(i).myid);
			}
			if(agents.get(i) instanceof SmartAgent){
				System.err.println("Esperto: "+ agents.get(i).myid);
			}
			if(agents.get(i) instanceof ForeverAloneAgent){
				System.err.println("Forever Alone: "+ agents.get(i).myid);
			}
		}
	}
	
	public void DistributePrize(double prize){
		if(existSmart){
			for(int i=0; i<agents.size(); i++){
				if(agents.get(i) instanceof SmartAgent){
					if(agents.size()==1){
						agents.get(i).incrementCash(prize);
					}
					else{
						agents.get(i).incrementCash(prize*0.7);
					}
				}
				else{
					agents.get(i).incrementCash( (prize*0.3)/(agents.size()-1) );
				}
			}
		}
		else{
			for(int i=0; i<agents.size(); i++){
				agents.get(i).incrementCash(prize/agents.size());
			}
		}
	}
	
}