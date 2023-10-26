package src;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
//This class creates multiple employees and give them a workshop to work on
public class FarmEmployeeGenerator extends Thread {
	
	private Headquarter _headquarter;
	private List<Farm> _farms;
	long _uniqueId;
	
	InetAddress serverAddress;
	int serverPort = 800;
	
	
	public FarmEmployeeGenerator(Headquarter headquarter, List<Farm> farms)
	{
		_uniqueId = 0;
		_headquarter = headquarter;
		_farms = farms;
		try {
			serverAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//This class creates the number of employees required by the headquarter
	public void run()
	{
		Random rand = new Random();
		int nrEmployee = rand.nextInt(13) + 8;
		
		for(int i = 0; i < nrEmployee; ++i)
		{
			GenerateEmployee();
		}
	}
	
	private void GenerateEmployee()
	{
		_uniqueId++;
		System.out.println("Headquarter: Employee " + _uniqueId + " has spawned");
		_headquarter.AddEmployee(new FarmEmployee(_farms, _uniqueId,  serverAddress,  serverPort));
	}
}
