package src;

import java.util.Random;
//This class creates multiple hens and give them a farm to work on
public class HenGenerator extends Thread {
	
	private Headquarter _headquarter;
	private int _nrFarms;
	
	//HenGenerator needs the data from headquarter
	public HenGenerator(Headquarter headquarter, int nrFarms)
	{
		_headquarter = headquarter;
		_nrFarms = nrFarms;
	}

	//At a random time (between 500-1000 milliseconds) hens are spawning randomly in each farm at a random place
	public void run()
	{
		Random rand = new Random();
		while(true)
		{
			try
			{
				sleep(rand.nextInt(501)+ 501);
				GenerateHen(rand.nextInt(_nrFarms));
			}
			catch(InterruptedException e)
			{
			e.printStackTrace();
			}
		}
	}
	
	private void GenerateHen(int farmId)
	{
		_headquarter.AddHenToFarm(farmId);
	}
}
