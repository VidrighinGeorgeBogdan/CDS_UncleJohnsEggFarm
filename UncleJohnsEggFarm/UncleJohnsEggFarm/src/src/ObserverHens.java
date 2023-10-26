package src;

import java.util.Iterator;
import java.util.Random;
//This class is used by every farm in order to locate the hens
public class ObserverHens extends Thread {
	private Farm _farm;
	
	public ObserverHens(Farm farm)
	{
		_farm = farm;
	}

	
	public void run()
	{
		while(true)
		{
			System.out.println("Observer try to start");
			_farm.HelpObserver();
			Random rand = new Random();
			try {
				this.sleep(rand.nextInt(401) + 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
