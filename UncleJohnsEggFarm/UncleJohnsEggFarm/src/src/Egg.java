package src;

import java.io.Serializable;
import java.util.Random;

public class Egg implements Serializable {
	private String _id;
	
	public Egg()
	{
		_id = generateEggId();
	}
	
	//This method return the name of the egg
	public String getId()
	{
		return _id;
	}
	
	//This method generates a random name
	private String generateEggId()
	{
		Random rand = new Random();
		int idLength = rand.nextInt(15);
		
		StringBuilder sb = new StringBuilder(idLength);
		
		for(int i = 0; i < idLength; ++i)
		{
			sb.append(rand.nextInt('z' - 'a' + 1) + 'a');
		}
		
		return sb.toString();
	}
	
	public String toString()
	{
		return _id;
	}
}