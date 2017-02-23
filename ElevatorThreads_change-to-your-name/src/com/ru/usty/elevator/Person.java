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
			System.out.println("Fer � lyftu");	
			
			//b�ti vi� manneskju � lyftu
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(1);
			//*** VANTAR *** B�ta vi� manneskju � fara�th��. .scene.incrementFara�tH��(destinationFloor)
			
			//Minnka bi�r�� � �essari h��
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
					
			ElevatorScene.elevatorGoOut.acquire();
			//Henda �t �r lyftu
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(1);
			//*** Vantar *** minnka �eir manneskjur sem eru a� fara � �essari h��.
			
			System.out.println("Farinn �r lyftu");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Person is through barrier
		
		
		System.out.println("Person Thread realeased");
	}

}
