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
			
			ElevatorScene.elevatorWaitMutex.acquire();
			ElevatorScene.semaphore1.acquire(); //semwait
			ElevatorScene.elevatorWaitMutex.release();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Person is through barrier
		ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);
		
		System.out.println("Person Thread realeased");
	}

}
