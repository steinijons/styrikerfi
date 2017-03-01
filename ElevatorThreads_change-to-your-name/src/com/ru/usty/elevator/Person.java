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
			System.out.println("Manneskja fer inn� lyftu");
			
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
			System.out.println("Manneskja kominn � lyftu");
			
			ElevatorScene.elevatorWaitMutex.acquire();
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);

			System.out.println("number of people: " + ElevatorScene.scene.personCount);
			/*//b�ti vi� manneskju � lyftu
			
			//*** VANTAR *** B�ta vi� manneskju � fara�th��. .scene.incrementFara�tH��(destinationFloor)
			
			//Minnka bi�r�� � �essari h��
			
					
			ElevatorScene.elevatorGoOut.acquire();
			//Henda �t �r lyftu
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);
			//*** Vantar *** minnka �eir manneskjur sem eru a� fara � �essari h��.
			
			System.out.println("Farinn �r lyftu");*/
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Person Thread realeased");
	}

}
