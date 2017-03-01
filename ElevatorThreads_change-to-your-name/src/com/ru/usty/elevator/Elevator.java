package com.ru.usty.elevator;

public class Elevator implements Runnable {
	int numberOfPeople;
	public void run() {
		while(true)
		{
			numberOfPeople = 0;
			if(ElevatorScene.elevatorMayDie)
			{
				return;
			}
			
			numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
			
			for(int i = 0; i < (6 - numberOfPeople); i++){
				
				ElevatorScene.semaphore1.release();
				System.out.println("býr til pláss í lyftu " + (i + 1));
			}
			System.out.println();
			
			try {
				ElevatorScene.elevatorWaitMutex.acquire();
				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
				if(numberOfPeople == 6){
					ElevatorScene.elevatorWaitMutex.release();
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
