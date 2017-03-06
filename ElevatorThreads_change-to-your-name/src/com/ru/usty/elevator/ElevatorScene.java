package com.ru.usty.elevator;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * The base function definitions of this class must stay the same
 * for the test suite and graphics to use.
 * You can add functions and/or change the functionality
 * of the operations at will.
 *
 */

public class ElevatorScene {
	
	//Allar semaphoreur
	public static Semaphore personCountMutex;
	public static Semaphore personGoOutMutex;
	
	public static Semaphore elevatorCountMutex;
	public static Semaphore peopleInElevevatorMutext;
	public static ElevatorScene scene;
	
	public static boolean elevatorMayDie;
	
	//TO SPEED THINGS UP WHEN TESTING,
	//feel free to change this.  It will be changed during grading
	public static final int VISUALIZATION_WAIT_TIME = 50;  //milliseconds

	private int numberOfFloors;
	private int numberOfElevators;
	
	private Thread elevatorThread;
	
	ArrayList<Semaphore> inSemaphore;
	ArrayList<Semaphore> outSemaphore;
	ArrayList<Semaphore> elevatorWaitMutex;

	ArrayList<Integer> personInElevatorlist;
	ArrayList<Integer> personCountGoingOut;

	ArrayList<Integer> personCount; //use if you want but
									//throw away and
									//implement differently
									//if it suits you
	ArrayList<Integer> exitedCount = null;
	ArrayList<Integer> elevatorNumber;
	
	public static Semaphore exitedCountMutex;

	//Base function: definition must not change
	//Necessary to add your code in this one
	public void restartScene(int numberOfFloors, int numberOfElevators) {
		
		elevatorMayDie = true; //Byrja á að drepa allar lyftur
		
		if(elevatorThread != null)
		{
			if(elevatorThread.isAlive())
			{
				try {
					elevatorThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		elevatorMayDie = false;
		
		scene = this;
		
		personGoOutMutex = new Semaphore(1);
		personCountMutex = new Semaphore(1);
		elevatorCountMutex = new Semaphore(1);
		peopleInElevevatorMutext = new Semaphore(1);

		this.numberOfFloors = numberOfFloors;
		this.numberOfElevators = numberOfElevators;

		inSemaphore = new ArrayList<Semaphore>();
		outSemaphore = new ArrayList<Semaphore>();
		elevatorWaitMutex = new ArrayList<Semaphore>();
		
		for(int i = 0; i < numberOfFloors; i++)
		{
			this.inSemaphore.add(new Semaphore(0));
			this.outSemaphore.add(new Semaphore(0));
			this.elevatorWaitMutex.add(new Semaphore(1));
		}
		
		
		personCount = new ArrayList<Integer>();
		elevatorNumber = new ArrayList<Integer>();
		personCountGoingOut = new ArrayList<Integer>();
		for(int i = 0; i < numberOfFloors; i++) {
			this.personCount.add(0);
			this.elevatorNumber.add(i);
			this.personCountGoingOut.add(0);
		}
		
		personInElevatorlist = new ArrayList<Integer>();
		for(int i = 0; i < numberOfElevators; i++)
		{
			this.personInElevatorlist.add(0);
		}
		
		if(exitedCount == null) {
			exitedCount = new ArrayList<Integer>();
		}
		else {
			exitedCount.clear();
		}
		for(int i = 0; i < getNumberOfFloors(); i++) {
			this.exitedCount.add(0);
		}
		exitedCountMutex = new Semaphore(1);
		for(int i = 0; i < numberOfElevators; i++)
		{
			elevatorThread = new Thread(new Elevator(i));
			elevatorThread.start();
		}
	}

	
	//Base function: definition must not change
	//Necessary to add your code in this one
	public Thread addPerson(int sourceFloor, int destinationFloor) {

		Thread thread = new Thread(new Person(sourceFloor, destinationFloor));
		thread.start();
		
		incrementNumberOfPeopleWaitingAtFloor(sourceFloor);
		return thread;  //this means that the testSuite will not wait for the threads to finish
	}


	//Base function: definition must not change, but add your code
	public int getCurrentFloorForElevator(int elevator) {

		//dumb code, replace it!
		return elevatorNumber.get(elevator);
	}

	public void setCurrentFloorForElevator(int floor,int elevator){
			try {
				elevatorCountMutex.acquire();
					elevatorNumber.set(elevator, floor);
				elevatorCountMutex.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleInElevator(int elevator) {
		
		//dumb code, replace it!
		/*switch(elevator) {
		case 1: return 1;
		case 2: return 4;
		default: return 3;
		}*/
		return personInElevatorlist.get(elevator);
	}

	//Base function: definition must not change, but add your code
	public int getNumberOfPeopleWaitingAtFloor(int floor) {

		return personCount.get(floor);
	}
	
	public void incrementNumberOfPeopleGoOutThisFloor(int floor)
	{
		try {
			personGoOutMutex.acquire();
				personCountGoingOut.set(floor, (personCountGoingOut.get(floor) + 1));
			personGoOutMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void decrementNumberOfPeopleGoOutThisFloor(int floor)
	{
		try {
			personGoOutMutex.acquire();
				personCountGoingOut.set(floor, (personCountGoingOut.get(floor) - 1));
			personGoOutMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decrementNumberOfPeopleInElevator(int elevator) {
		try {
			
			peopleInElevevatorMutext.acquire();
				personInElevatorlist.set(elevator, (personInElevatorlist.get(elevator) - 1));
			peopleInElevevatorMutext.release();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void incrementNumberOfPeopleInElevator(int elevator) {
		
		try {			
			peopleInElevevatorMutext.acquire();
				personInElevatorlist.set(elevator, (personInElevatorlist.get(elevator) + 1));
			peopleInElevevatorMutext.release();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decrementNumberOfPeopleWaitingAtFloor(int floor) {
		try {
			
			personCountMutex.acquire();
				personCount.set(floor, (personCount.get(floor) - 1));
			personCountMutex.release();	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	public void incrementNumberOfPeopleWaitingAtFloor(int floor) {
		
		try {
			
			personCountMutex.acquire();
				personCount.set(floor, personCount.get(floor) + 1);
			personCountMutex.release();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	//Base function: definition must not change, but add your code if needed
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	//Base function: definition must not change, but add your code if needed
	public int getNumberOfElevators() {
		return numberOfElevators;
	}

	//Base function: definition must not change, but add your code if needed
	public void setNumberOfElevators(int numberOfElevators) {
		this.numberOfElevators = numberOfElevators;
	}

	//Base function: no need to change unless you choose
	//				 not to "open the doors" sometimes
	//				 even though there are people there
	public boolean isElevatorOpen(int elevator) {

		return isButtonPushedAtFloor(getCurrentFloorForElevator(elevator));
	}
	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public boolean isButtonPushedAtFloor(int floor) {

		return (getNumberOfPeopleWaitingAtFloor(floor) > 0);
	}

	//Person threads must call this function to
	//let the system know that they have exited.
	//Person calls it after being let off elevator
	//but before it finishes its run.
	public void personExitsAtFloor(int floor) {
		try {
			
			exitedCountMutex.acquire();
				exitedCount.set(floor, (exitedCount.get(floor) + 1));
			exitedCountMutex.release();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Base function: no need to change, just for visualization
	//Feel free to use it though, if it helps
	public int getExitedCountAtFloor(int floor) {
		if(floor < getNumberOfFloors()) {
			return exitedCount.get(floor);
		}
		else {
			return 0;
		}
	}

	public int getNumberOfPeopleGoOutThisFloor(int floor) {
		
		return personCountGoingOut.get(floor);
	}
	
	
}