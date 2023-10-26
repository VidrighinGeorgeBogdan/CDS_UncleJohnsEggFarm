package src;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
/**This application simulates the activities of Uncle John and his staff
 */
public class MainClass {
	public static void main(String args[])
	{
		Random rand = new Random();
		
		/**Uncle John can have a maximum of 5 farms and 50 employees
		 * It is mandatory that Uncle John should have at least 8 employees and one farm */
		int nrEmployees = rand.nextInt(43) + 8;
		int nrFarms = rand.nextInt(4) + 2;
		
		try {
			/** Uncle John is waiting for the eggs */
			UncleJohn uncle = new UncleJohn(InetAddress.getLocalHost().getHostAddress().toString(), 800);
			uncle.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HeadquarterSingleton headQuarterSingleton = new HeadquarterSingleton(nrFarms, nrEmployees);
	}
}
