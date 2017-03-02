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
			System.out.println("Peróna: " + " til: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			ElevatorScene.scene.elevatorWaitMutex.get(sourceFloor).acquire();
			ElevatorScene.scene.inSemaphore.get(sourceFloor).acquire();
			ElevatorScene.scene.elevatorWaitMutex.get(sourceFloor).release();
			
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			
			ElevatorScene.scene.outSemaphore.get(destinationFloor).acquire();
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);
			ElevatorScene.scene.incrementNumberOfPeopleWaitingAtFloor(destinationFloor);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Person Thread realeased");
	}

}