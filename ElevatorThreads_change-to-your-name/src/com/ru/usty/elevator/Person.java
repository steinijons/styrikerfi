package com.ru.usty.elevator;

import javax.sound.midi.SoundbankResource;

public class Person implements Runnable {
	
	int sourceFloor, destinationFloor;
	
	public Person(int sourceFloor, int destinationFloor)
	{
		
		this.sourceFloor = sourceFloor;
		this.destinationFloor = destinationFloor;
		
	}
	@Override
	public void run() {
		ElevatorScene.scene.incrementNumberOfPeopleWaitingAtFloor(sourceFloor);
		try {
			
			System.out.println("sourceFloor: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			ElevatorScene.elevatorWaitMutex.acquire();
			//ElevatorScene.semaphore1.acquire(); //semwait
			System.out.println("Fer í lyftu");	
			
			//bæti við manneskju í lyftu
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			//*** VANTAR *** Bæta við manneskju á faraúthæð. .scene.incrementFaraÚtHæð(destinationFloor)
			
			//Minnka biðröð á þessari hæð
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
					
			ElevatorScene.elevatorGoOut.acquire();
			//Henda út úr lyftu
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);
			//*** Vantar *** minnka þeir manneskjur sem eru að fara á þessari hæð.
			
			System.out.println("Farinn úr lyftu");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Person is through barrier
		
		
		System.out.println("Person Thread realeased");
	}

}
