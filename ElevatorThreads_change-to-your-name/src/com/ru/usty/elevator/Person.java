package com.ru.usty.elevator;

public class Person implements Runnable {
	
	int sourceFloor, destinationFloor;
	public Person(int sourceFloor, int destinationFloor)
	{
		this.sourceFloor = sourceFloor;
		this.destinationFloor = destinationFloor;
		
	}
	@Override
	public void run() {
		
		try {
			
			
			//System.out.println("Persóna: " + " til: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			//ElevatorScene.semaphore1.acquire();
			int currElevator = findElevator();
			ElevatorScene.scene.inSemaphore.get(sourceFloor).acquire();
			//System.out.println("Manneskja fer inní lyftu");
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(currElevator);
			ElevatorScene.scene.incrementNumberOfPeopleGoOutThisFloor(destinationFloor);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			//System.out.println("Manneskja kominn í lyftu");
			//System.out.println("number of people: " + ElevatorScene.scene.personCount + "fyrir elevatorGoOut semaphore");

			//System.out.println("Peróna: " + " til: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			//ElevatorScene.scene.elevatorWaitMutex.get(sourceFloor).acquire();
			//ElevatorScene.scene.inSemaphore.get(sourceFloor).acquire();
			//ElevatorScene.scene.elevatorWaitMutex.get(sourceFloor).release();
			
			//ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			//ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			
			ElevatorScene.scene.outSemaphore.get(destinationFloor).acquire();
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(currElevator);
			ElevatorScene.scene.decrementNumberOfPeopleGoOutThisFloor(destinationFloor);
			ElevatorScene.scene.personExitsAtFloor(destinationFloor);
			//ElevatorScene.scene.incrementNumberOfPeopleWaitingAtFloor(destinationFloor);
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Person Thread realeased");
	}
	public int findElevator()
	{
		for(int i = 0; i < ElevatorScene.scene.getNumberOfElevators(); i++){
			
			if(ElevatorScene.scene.getNumberOfPeopleInElevator(i) < 6){
				System.out.println("Elevator Nr : " + ElevatorScene.scene.elevatorNumber.get(i) + " number of people in elevator: " + ElevatorScene.scene.getNumberOfPeopleInElevator(i));
				return i;
			}
		}
		return 0;
	}

}