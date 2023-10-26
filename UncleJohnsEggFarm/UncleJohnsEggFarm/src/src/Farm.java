package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Farm extends Thread {
	private int _id;
	private int _nrHens; 
	private int _farmSize;
	private int[][] _matrix;
	private int uniqueHenId = 0;
	Random rand = new Random();

	ConcurrentLinkedQueue<Egg> _eggList;	
	
	//This lock is used to ensure the correctness of the hen move method.
	ReentrantLock  oneHenTrytoMove = new ReentrantLock (); 
	
	//This lock is used to determine which operation has priority in this class at a given time.
	// The operations are: a hen try to move, an employee tries to get an egg and the farm try to ask all the hens where they are.
	ReentrantLock  setPriorityOperation = new ReentrantLock();
	
	//A maximum of 10 employees can read from a farm at a time
	Semaphore semaphoreForEmployees;
	
	ConcurrentHashMap<Integer, Hen> _hens;
	
	//This variable helps the farm to ask all its hens about their position
	ObserverHens observerHens;
	
	
	//The factory needs @param farmSize, (@param) nrHens and (@param) id in order to start working
	public Farm(int farmSize, int nrHens, int id)
	{
		_id = id;
		_farmSize = farmSize;
		_matrix = new int[_farmSize][_farmSize];
		_nrHens = nrHens;
		if(_nrHens == 0) _nrHens = 1;
		
		_eggList = new ConcurrentLinkedQueue<Egg>();
		semaphoreForEmployees = new Semaphore(10);
		
		_hens = new ConcurrentHashMap<Integer, Hen>();
		
		observerHens = new ObserverHens(this);
		
		System.out.println("A new farm of size " + _farmSize + " has been created!");
	}
	
	//When farm starts to run, also hens observer will start
	@Override
	public void run()
	{
		observerHens.start();
	}
	
	//An elf will be added if the maximum number of elves was not reached
	public void AddHen()
	{
		if(_hens.size() < _farmSize / 2)
		{
			int x = 0, y = 0;
			Hen newHen = new Hen(GetFirstPosition(x, y, uniqueHenId), this, uniqueHenId);
			_hens.put(uniqueHenId, newHen);
			newHen.start();
			
			 PrintMessageFromFarm("Hen " + uniqueHenId + " has spawned");
	         ++uniqueHenId;
		}
	}

	public void PrintMessageFromFarm(String message)
	{
		System.out.println("Farm " + _id + ": " + message);
	}
	
	//This method returns the first position of a hen on factory matrix
	private Coordinates GetFirstPosition(int x, int y, int idHen)
	{
		x = rand.nextInt(_farmSize);
		y = rand.nextInt(_farmSize);
		
		while(_matrix[x][y] != 0)
		{
			x = rand.nextInt(_farmSize);
			y = rand.nextInt(_farmSize);
		}
		
		_matrix[x][y] = idHen;
		return new Coordinates(x, y);
	}

	private boolean PositionOutOfBounds(Coordinates position)
	{
        return position.getX() < 0 || position.getX() >= _farmSize ||
               position.getY() < 0 || position.getY() >= _farmSize;
    }

    public boolean HenCanMove(int id, Coordinates position)
    {
        return !PositionOutOfBounds(position) && (_matrix[position.getX()][position.getY()] == 0
                                              ||  _matrix[position.getX()][position.getY()] == id);
    }

    public boolean MoveHen(int id, Coordinates position, Coordinates oldPosition) {
        if (oneHenTrytoMove.tryLock()) {
            try {
                if(HenCanMove(id, position))
                {
                    _matrix[position.getX()][position.getY()] = id;
                    _hens.get(id).GetNewPosition(position);
                    return true;
                }
                return false;
            }
            finally 
            {
            	oneHenTrytoMove.unlock();
            }
        }

        return false;
    }
    
    //A hen try to move in another position. The function return true if she can, otherwise false
    public boolean TryMoveHen(int id, Coordinates nextPosition, Coordinates currentPosition)
    {
    	setPriorityOperation.lock();
    	
        if (HenCanMove(id, nextPosition)) 
        {
        	setPriorityOperation.unlock();
            return MoveHen(id, nextPosition, currentPosition);
        }

        setPriorityOperation.unlock();
        return false;
    }    

    //When a @param gift in created, it will be added in the list
    public void GetEggFromHen(Egg egg, int id) {
        _eggList.add(egg);
        PrintMessageFromFarm("Got egg " + egg.getId() + " from hen " + id);
    }
    
    //An employee will try to take a gift from list. If he cannot the method return null.
    public Egg GetEggFromFarm(long idEmployee)
    {
    	setPriorityOperation.lock();
    	PrintMessageFromFarm("Employee " + idEmployee + " try to get a gift");
    	
    	Egg egg = null;
    	try {
			semaphoreForEmployees.acquire();
		  	if(!oneHenTrytoMove.isLocked())
	    	{
		  		egg = _eggList.poll();
	    	}
		} 
    	finally
    	{
    		
    		if(egg == null)
    			PrintMessageFromFarm("Employee " + idEmployee + " cannot get the egg");
    		else
    			PrintMessageFromFarm("Employee " + idEmployee + " got the egg" + egg.getId());
    		semaphoreForEmployees.release();
    		setPriorityOperation.unlock();
    		return egg;
		}
  
    }
    
    //This method checks if the hens are in the positions known by the farm
    public void HelpObserver()
    {
    	setPriorityOperation.lock();
    	
    	PrintMessageFromFarm("Observer starts working");
    	Iterator<Integer> it = _hens.keySet().iterator();

		while(it.hasNext())
		{
			Integer key = it.next();
			Coordinates position = _hens.get(key).GetCurrentPosition();
			if(_matrix[position.getX()][position.getY()] == key)
			{
				PrintMessageFromFarm("Hen " + key + " provided the correct position to the factory");
			}
			else
			{
				PrintMessageFromFarm("Hen " + key + " provided an incorrect position to the factory, id is = " + key);
			}
		}
		
		setPriorityOperation.unlock();
		
    }

    public void RemoveHen(int henId)
    {
    	Hen e = _hens.get(henId);
    	_hens.get(henId).Retire();
    	_hens.remove(e);
    	Iterator<Integer> it = _hens.keySet().iterator();

		while(it.hasNext())
		{
			Integer key = it.next();
			Coordinates position = _hens.get(key).GetCurrentPosition();
			if(_matrix[position.getX()][position.getY()] == key)
			{
				_matrix[position.getX()][position.getY()] = 0;
				break;
			}
		}
		
    }

    public int getFarmID()
    {
    	return _id;
    }

    public int getNrOfHens()
    {
    	return _hens.size();
    }
}
 
