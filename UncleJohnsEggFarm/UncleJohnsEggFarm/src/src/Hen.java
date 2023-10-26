package src;

import java.util.Random;
// This method simulates the behavior of a hen

public class Hen extends Thread {
	public Boolean isDead;
	private Farm _farm;
	private int _id;
	private Coordinates _position;
	
	private int dx[] = {0, 0, 1, -1};
    private int dy[] = {1, -1, 0, 0};
	
    //The hen must have a (@param) position and a (@param) id and must know which farm belongs to

	public Hen(Coordinates position, Farm farm, int id)
	{
		_position = position;
		_farm = farm;
		isDead = false;
		_id = id;
	}
	
	//While the hen is not retired he will continue to run and try to move in order to lay eggs
	public void run()
	{
		while(!isDead)
		{
			TryToMove();
		}
		
	}
	
	private void TryToMove()
	{	
		boolean henMoved = false;

        _farm.PrintMessageFromFarm("Hen " + _id + " wants to move");
        for (int i = 0; i < 4; ++i) 
        {
            Coordinates nextPosition = new Coordinates(_position.getX() + dx[i], _position.getY() + dy[i]);

            henMoved = _farm.TryMoveHen(_id, nextPosition, _position);
            if (henMoved)
            {
                break;
            }
        }

        if (!henMoved) 
        {
            _farm.PrintMessageFromFarm("Hen " + _id + " failed to move");
            try 
            {
                sleep(new Random().nextInt(41) + 10);
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            layEgg();
            try 
            {
                sleep(30);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
		}
	}
		
    private void layEgg() 
    {
        Egg egg = new Egg();
        _farm.PrintMessageFromFarm("Hen " + _id + " created gift " + egg.getId());
        _farm.GetEggFromHen(egg, _id);
    }
    
    //In order to lay eggs, the hens needs to move in a (@param) newPosition
    public void GetNewPosition(Coordinates newPosition) 
    {
        _farm.PrintMessageFromFarm("Moved hen " + _id + " to " + newPosition.getX() + ", " + newPosition.getY() +
                " from " + _position.getX() + ", " + _position.getY());
        _position.setX(newPosition.getX());
        _position.setY(newPosition.getY());
    }
    
    /**This method return the current position*/
    public Coordinates GetCurrentPosition()
    {
    	return _position;
    }
    
    /**This method will retire a hen */
    public void Retire()
    {
    	isDead = true;
    	System.out.println("Hen " + _id + " was retired");
    }

}