
public class Countdown {
	private int num;
	
	public Countdown(int number){
		num = number;
	}
	
	public void count(){
		num --;
	}
	
	public boolean isFinished(){
		return (num == 0);
	}
}
