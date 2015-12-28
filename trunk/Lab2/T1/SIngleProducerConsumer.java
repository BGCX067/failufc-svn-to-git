//package src;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class SIngleProducerConsumer {
	static final int TAM = 3;

	public static void main(String[] args) {
		List<Integer> array = Collections.synchronizedList(new LinkedList<Integer>());
		Semaphore e = new Semaphore(0);
		Semaphore f = new Semaphore(TAM);
	
		new Thread(new Producer(array, e, f)).start();
		new Thread(new Producer(array, e, f)).start();
		new Thread(new Consumer(array, e, f)).start();
		new Thread(new Consumer(array, e, f)).start();

	}

}
