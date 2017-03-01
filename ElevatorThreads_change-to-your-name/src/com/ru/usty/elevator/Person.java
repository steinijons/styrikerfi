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
		
		try {
			System.out.println("sourceFloor: " + sourceFloor + " DestinationFloor: " + destinationFloor);
			
			ElevatorScene.semaphore1.acquire();
			System.out.println("Manneskja fer inní lyftu");
			
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			System.out.println("Manneskja kominn í lyftu");
			
			ElevatorScene.elevatorWaitMutex.acquire();
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);

			System.out.println("number of people: " + ElevatorScene.scene.personCount);
			/*//bæti við manneskju í lyftu
			
			//*** VANTAR *** Bæta við manneskju á faraúthæð. .scene.incrementFaraÚtHæð(destinationFloor)
			
			//Minnka biðröð á þessari hæð
			
					
			ElevatorScene.elevatorGoOut.acquire();
			//Henda út úr lyftu
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);
			//*** Vantar *** minnka þeir manneskjur sem eru að fara á þessari hæð.
			
			System.out.println("Farinn úr lyftu");*/
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Person Thread realeased");
	}

}
