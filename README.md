# 1.1	Architectural overview
*	Coordinates = represents the coordinates
*	Hen = simulates the behavior of a hen
-	every hen must have the parameters position and id, and must know to which farm it belongs to
-	the hen will continue to run and try to move in order to create eggs while it is not retired
-	in order to create eggs, the hens needs to move in a parameter newPosition
*	HenGenerator = creates multiple hens and gives them a farm to lay eggs on
-	it needs the data from Headquarters
-	Hens are spawning randomly in each farm at a random place in the farm (random time between 500-1000 milliseconds)
*	Farm = represents a farm that has some hens. Every time a hen is
moving it creates an egg that will be taken by a farm employee in order to send them to the Headquarters
-	the Farm needs @param farmSize, @param nrHens and @param id in order to start working
-	when the farm starts to run, also ObserverHens will start
-	a hen will be added if the maximum number of hens was not reached
-	when a @param egg is created, it will be added in the list
-	a FarmEmployee will try to take an egg from list
*	Egg = represents an egg
-	an egg has a random name
*	MainClass = simulates the activities of Uncle John and his staff
*	ObserverHens = used by every farm in order to see where the hens are
*	FarmEmployee = simulates the behavior of a farm employee
-	Farm employees have access to all farms and send eggs to the address indicated by serverAddress, serverPort
-	the farm’s employee goal is to take eggs from farms
-	if an egg was taken, the farm employee sends it to the Headquarters
*	FarmEmployeeGenerator = creates multiple farm employees and gives them a farm to work on.
1.1	Architectural overview
*	Coordinates = represents the coordinates
*	Hen = simulates the behavior of a hen
-	every hen must have the parameters position and id, and must know to which farm it belongs to
-	the hen will continue to run and try to move in order to create eggs while it is not retired
-	in order to create eggs, the hens needs to move in a parameter newPosition
*	HenGenerator = creates multiple hens and gives them a farm to lay eggs on
-	it needs the data from Headquarters
-	Hens are spawning randomly in each farm at a random place in the farm (random time between 500-1000 milliseconds)
*	Farm = represents a farm that has some hens. Every time a hen is
moving it creates an egg that will be taken by a farm employee in order to send them to the Headquarters
-	the Farm needs @param farmSize, @param nrHens and @param id in order to start working
-	when the farm starts to run, also ObserverHens will start
-	a hen will be added if the maximum number of hens was not reached
-	when a @param egg is created, it will be added in the list
-	a FarmEmployee will try to take an egg from list
*	Egg = represents an egg
-	an egg has a random name
*	MainClass = simulates the activities of Uncle John and his staff
*	ObserverHens = used by every farm in order to see where the hens are
*	FarmEmployee = simulates the behavior of a farm employee
-	Farm employees have access to all farms and send eggs to the address indicated by serverAddress, serverPort
-	the farm’s employee goal is to take eggs from farms
-	if an egg was taken, the farm employee sends it to the Headquarters
*	FarmEmployeeGenerator = creates multiple farm employees and gives them a farm to work on.
