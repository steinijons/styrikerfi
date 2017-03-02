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
			System.out.println("Per�na: " + " til: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			//ElevatorScene.semaphore1.acquire();
			ElevatorScene.scene.inSemaphore.get(sourceFloor).acquire();
			System.out.println("Manneskja fer inn� lyftu");
			
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			System.out.println("Manneskja kominn � lyftu");
			System.out.println("number of people: " + ElevatorScene.scene.personCount + "fyrir elevatorGoOut semaphore");
			
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