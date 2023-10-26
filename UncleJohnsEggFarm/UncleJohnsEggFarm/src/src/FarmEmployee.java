package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// This method simulates the behavior of an employee

public class FarmEmployee extends Thread {
	List<Farm> _farms = new ArrayList<Farm>();
	long _id;
	Random rand = new Random();
	Socket _socket;
	
	//The Farm employees have access to all farms and send eggs to the address indicated by serverAddress, serverPort
	public FarmEmployee(List<Farm> farms, long id, InetAddress serverAddress, int serverPort)
	{
		_id = id;
		_farms = farms;
		
		try {
			serverPort = 800;
			_socket = new Socket(serverAddress.getHostAddress(), serverPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//The employee's goal is to take eggs from farms
	public void run()
	{
		while(true)
		{
			GetEggFromFarm();
		}
		
	}
	
	private void GetEggFromFarm()
	{
		for(Farm f: _farms)
		{
			Egg egg = f.GetEggFromFarm(_id);
			if(egg != null)
			{
				SendEggToUncleJohn(egg);
			}
			
			try {
				this.sleep(rand.nextInt(900) + 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//If an egg was taken, the employee sends it to Uncle John
	private void SendEggToUncleJohn(Egg egg)
	{
		try {
			System.out.println("Employee " + _id + " sends egg " + egg.getId() + " to Uncle John");
			PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
			out.println(egg.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}