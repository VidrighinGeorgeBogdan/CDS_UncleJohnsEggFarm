package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
//This class creates the following entities: Hen spawner, Employee spawner, farms and retire hen class

public class Headquarter extends Thread{
	int _nrFarms;
	int _nrEmployees;
	
	HenGenerator _henGenerator;
	FarmEmployeeGenerator _employeeGenerator;
	
	public List<Farm> farms;
	List<FarmEmployee> _employees;
	
	RetireHen _retireHens;
	
	//Using the number of farms (nrFarms) and the number of employees (nrEmployees) , the headquarter will create:
	// hen generator, employee generator, farms and retire hen class
	public Headquarter(int nrFarms, int nrEmployees)
	{
		_nrFarms = nrFarms;
		_nrEmployees = nrEmployees;
		
		_henGenerator = new HenGenerator(this, _nrFarms);
		
		farms = new ArrayList<Farm>();
		_employees = new ArrayList<FarmEmployee>();
		
		_retireHens = new RetireHen(this);
	}
	
	public void run()
	{
		CreateFarms();
		StartFarms();
		_henGenerator.start();
		_employeeGenerator = new FarmEmployeeGenerator(this, farms);
		_employeeGenerator.start();
		
		_retireHens.start();
	}
	
	//This method creates the farms with a random size and put them in a list
	private void CreateFarms()
	{
		Random rand = new Random();
		
		for(int i = 0; i < _nrFarms; ++ i)
		{	
			int dimension = rand.nextInt(400) + 100;
			farms.add(new Farm(dimension, rand.nextInt(dimension / 2), i));
		}
	}

	private void StartFarms()
	{
		for(Farm f : farms)
		{
			f.start();
		}
	}

	public void AddHenToFarm(int farmId)
	{
		farms.get(farmId).AddHen();
	}

	public void AddEmployee(FarmEmployee employee)
	{
		_employees.add(employee);
		employee.start();
	}
	
	//The hen with the id equal to (@param) henId will be removed from the farm indicated by (@param) farmId
	public void RetireHen(int farmId, int henId)
	{
		farms.get(farmId).RemoveHen(henId);
	}
}
 
