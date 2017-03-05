package com.ru.usty.elevator;

public class Elevator implements Runnable {
	int numberOfPeople;
	int currFloor;
	int elevator;
	int peopleGoOutThisFloor;
	
	public Elevator(int elevator)
	{
		this.elevator = elevator;
	}
	
	public void run() {
		while(true)
		{
			System.out.println("NUMBER OF FLOORS: " + ElevatorScene.scene.getNumberOfFloors());
			for(int i = 0; i < ElevatorScene.scene.getNumberOfFloors(); i++)
			{
				ElevatorScene.scene.setCurrentFloorForElevator(i);
				System.out.println("Floor: " + i);
		
				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
				for(int j = 0; j < (6 - numberOfPeople); j++)
				{
					System.out.println("Persona ma " + (j + 1) + " fara inn");
					ElevatorScene.scene.inSemaphore.get(i).release();
					sleepNow();
				}

				/*for(int j = 0; j < (6 - numberOfPeople); j++){
					System.out.println("fyllum lyftu med personu " + j);
					try {
						ElevatorScene.scene.inSemaphore.get(i).acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//tokum semaphorurnar sem ad gefa leyfi fyrir ad fara inni lyftu
					sleepNow();
				}*/
				
				//ElevatorScene.scene.elevatorWaitMutex.get(i).release();
				System.out.println("Lyfta full");
				
	
				currFloor = i + 1;
				
				if(currFloor < ElevatorScene.scene.getNumberOfFloors())
				{
					ElevatorScene.scene.setCurrentFloorForElevator(currFloor);
					
					peopleGoOutThisFloor = ElevatorScene.scene.getNumberOfPeopleGoOutThisFloor(currFloor);
					System.out.println("PEOPLE GOING OUT ON THIS FLOOR: " + peopleGoOutThisFloor);
					
					for(int j = 0; j < peopleGoOutThisFloor; j++)
					{
						System.out.println("Persona: " + (j + 1) + " fer ur lyftu a hæd " + ElevatorScene.scene.getCurrentFloorForElevator(currFloor));				
						ElevatorScene.scene.outSemaphore.get(currFloor).release();
			
						sleepNow();
					}
					System.out.println("Allir farnir ur lyftu!!!!");
				}
				else
				{
					break;
				}
				
				
			}
			for(int i = ElevatorScene.scene.getNumberOfFloors()-1; i > 0 ; i--)
			{
				ElevatorScene.scene.setCurrentFloorForElevator(i);
				System.out.println("Haed: " + i);
		
				
				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
				for(int j = 0; j < 6/*(6 - numberOfPeople)*/; j++)
				{
					System.out.println("Persona ma fara inn");
					ElevatorScene.scene.inSemaphore.get(i).release();
					sleepNow();
				}


				//Filla lyftu
				/*for(int j = 0; j < (6 - numberOfPeople); j++)
				{
					System.out.println("fyllum lyftu meÃ° personu " + j);
					try {
						ElevatorScene.scene.inSemaphore.get(i).acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//tokum semaphorurnar sem ad gefa leyfi fyrir ad fara inni lyftu
					sleepNow();
				}*/
				//ElevatorScene.scene.elevatorWaitMutex.get(i).release();
				
				currFloor = i - 1;
				//peopleGoingOut = ElevatorScene.scene.getNumberOfPeopleGoingOutAtFloor(currFloor);
				if(currFloor >= 0)
				{
					ElevatorScene.scene.setCurrentFloorForElevator(currFloor);
					System.out.println("FOLK I LYFTU: " + ElevatorScene.scene.getNumberOfPeopleInElevator(1));
					
					peopleGoOutThisFloor = ElevatorScene.scene.getNumberOfPeopleGoOutThisFloor(currFloor);
					
					System.out.println("PEOPLE GOING OUT ON THIS FLOOR: " + peopleGoOutThisFloor);
					
					for(int j = 0; j < peopleGoOutThisFloor; j++)
					{
						System.out.println("Floor: " + ElevatorScene.scene.getCurrentFloorForElevator(currFloor));
						System.out.println("Persona: " + j + " fer ur lyftu");				
						ElevatorScene.scene.outSemaphore.get(currFloor).release();
			
						sleepNow();
					}
					System.out.println("Allir farnir Ãºr lyftu!!!!");
				}
				else
				{
					break;
				}
			
			if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(0) == 0)
			{
				System.out.println("KEYRSLA BUIN!!!!!");
				break;
				
			}
		  }
		}
	}
	
		private void sleepNow()
		{
			try {
				Thread.sleep(ElevatorScene.VISUALIZATION_WAIT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
}