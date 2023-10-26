package src;
import java.util.List;
import java.util.Random;
//This class will retire a hen

public class RetireHen extends Thread {
	private Headquarter _headquarter;
	
	public RetireHen(Headquarter headquarter)
	{
		_headquarter = headquarter;
	}
	
	//A hen from a random farm from headquarter is picked and retired
	public void run()
	{
		Random rand = new Random();
		while(true)
		{
			try
			{
				sleep(rand.nextInt(501)+ 501);
				Farm f = _headquarter.farms.get(_headquarter.farms.size() - 1);
				System.out.println("Try to stop a hen from a farm");
				int farmId = f.getFarmID();
				System.out.println("One hen from farm " + farmId + " will be retired");
				if(f.getNrOfHens() > 0)
				{
					int henId = rand.nextInt(f.getNrOfHens());
					_headquarter.RetireHen(farmId, henId); 
				}
			}
			catch(InterruptedException e)
			{
			e.printStackTrace();
			}
		}
	}
}
