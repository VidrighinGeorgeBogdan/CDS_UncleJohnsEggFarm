package src;

//This class creates the headquarter
public class HeadquarterSingleton {

	int _nrFarms;
	int _nrEmployees;
	Headquarter _headquarter;
	
	public HeadquarterSingleton(int nrFarms, int nrEmployees)
	{
		_nrFarms = nrFarms; 
		_nrEmployees = nrEmployees; 
	
		CreateHeadquarter();
	}
	
	private void CreateHeadquarter()
	{   
		_headquarter = new Headquarter(_nrFarms, _nrEmployees);
		_headquarter.start();
	}

	
	
}
