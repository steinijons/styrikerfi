package com.ru.usty.elevator;

public class Elevator implements Runnable {
	int numberOfPeopleInElevator, currFloor, elevator, peopleGoOutThisFloor;
	final int ELEVATORMAX = 6;
	boolean goingUp;
	
	public Elevator(int elevator)
	{
		this.elevator = elevator;
		//ElevatorScene.scene.setCurrentFloorForElevator(0,elevator);
		currFloor = 0;
		numberOfPeopleInElevator = 0;
		peopleGoOutThisFloor = 0;
		goingUp = true;
	}
	
	public void run() {
		while(true)
		{
			if(ElevatorScene.elevatorMayDie)
			{
				return;
			}
			
			System.out.println("Current floor: " + currFloor);
			// If we have reached top floor, switch elevator direction to down and to up if we reach bottom floor.
			if (currFloor == ElevatorScene.scene.getNumberOfFloors() - 1) {
				goingUp = false;
			}
			else if (currFloor == 0) {
				goingUp = true;
			}
			
			// Let people out of elevator for currFloor (if any)
			peopleGoOutThisFloor = ElevatorScene.scene.getNumberOfPeopleGoOutThisFloor(currFloor);
			for(int j = 0; j < peopleGoOutThisFloor; j++) {
				//System.out.println("Persona: " + (j + 1) + " fer ur lyftu a h�d " + ElevatorScene.scene.getCurrentFloorForElevator(currFloor));				
				
				ElevatorScene.scene.outSemaphore.get(currFloor).release();
				sleepNow();
			}
			
			// Let people waiting on currFloor fill the elevator (if there is room)
			numberOfPeopleInElevator = ElevatorScene.scene.getNumberOfPeopleInElevator(elevator);
			for (int i = 0; i < (ELEVATORMAX - numberOfPeopleInElevator); i++) {
				if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(currFloor) > 0)
				{
					ElevatorScene.scene.inSemaphore.get(currFloor).release();
				}
				sleepNow();
			}
			
			// Move elevator between floors according to direction
			if (goingUp) {
				currFloor += 1;	
			}
			else {
				currFloor -= 1;	
			}
			ElevatorScene.scene.setCurrentFloorForElevator(currFloor, elevator);
			sleepNow();
			
			
			//NÆR EKKI AÐ KLÁRA AÐ TÆMA LYFTU !! ÞARF AÐ LAGA!!!!
			/*if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(0) == 0)
			{
				break;
			}*/
			
		}
	}
	
//			System.out.println("NUMBER OF FLOORS: " + ElevatorScene.scene.getNumberOfFloors());
//			for(int i = 0; i < ElevatorScene.scene.getNumberOfFloors(); i++)
//			{
//				ElevatorScene.scene.setCurrentFloorForElevator(i);
//				System.out.println("Floor: " + i);
//		
//				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
//				for(int j = 0; j < (6 - numberOfPeople); j++)
//				{
//					System.out.println("Persona ma " + (j + 1) + " fara inn");
//					ElevatorScene.scene.inSemaphore.get(i).release();
//					sleepNow();
//				}
//				
//				//ElevatorScene.scene.elevatorWaitMutex.get(i).release();
//				System.out.println("Lyfta full");
//				
//	
//				currFloor = i + 1;
//				
//				if(currFloor < ElevatorScene.scene.getNumberOfFloors())
//				{
//					ElevatorScene.scene.setCurrentFloorForElevator(currFloor);
//					
//					peopleGoOutThisFloor = ElevatorScene.scene.getNumberOfPeopleGoOutThisFloor(currFloor);
//					System.out.println("PEOPLE GOING OUT ON THIS FLOOR: " + peopleGoOutThisFloor);
//					
//					for(int j = 0; j < peopleGoOutThisFloor; j++)
//					{
//						System.out.println("Persona: " + (j + 1) + " fer ur lyftu a h�d " + ElevatorScene.scene.getCurrentFloorForElevator(currFloor));				
//						ElevatorScene.scene.outSemaphore.get(currFloor).release();
//			
//						sleepNow();
//					}
//					System.out.println("Allir farnir ur lyftu!!!!");
//				}
//				else
//				{
//					break;
//				}
//				
//				
//			}
//			for(int i = ElevatorScene.scene.getNumberOfFloors()-1; i > 0 ; i--)
//			{
//				ElevatorScene.scene.setCurrentFloorForElevator(i);
//				System.out.println("Haed: " + i);
//		
//				
//				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
//				for(int j = 0; j < 6/*(6 - numberOfPeople)*/; j++)
//				{
//					System.out.println("Persona ma fara inn");
//					ElevatorScene.scene.inSemaphore.get(i).release();
//					sleepNow();
//				}
//				
//				currFloor = i - 1;
//				//peopleGoingOut = ElevatorScene.scene.getNumberOfPeopleGoingOutAtFloor(currFloor);
//				if(currFloor >= 0)
//				{
//					ElevatorScene.scene.setCurrentFloorForElevator(currFloor);
//					System.out.println("FOLK I LYFTU: " + ElevatorScene.scene.getNumberOfPeopleInElevator(1));
//					
//					peopleGoOutThisFloor = ElevatorScene.scene.getNumberOfPeopleGoOutThisFloor(currFloor);
//					
//					System.out.println("PEOPLE GOING OUT ON THIS FLOOR: " + peopleGoOutThisFloor);
//					
//					for(int j = 0; j < peopleGoOutThisFloor; j++)
//					{
//						System.out.println("Floor: " + ElevatorScene.scene.getCurrentFloorForElevator(currFloor));
//						System.out.println("Persona: " + j + " fer ur lyftu");				
//						ElevatorScene.scene.outSemaphore.get(currFloor).release();
//			
//						sleepNow();
//					}
//					System.out.println("Allir farnir úr lyftu!!!!");
//				}
//				else
//				{
//					break;
//				}
//			
//			if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(0) == 0)
//			{
//				System.out.println("KEYRSLA BUIN!!!!!");
//				break;
//				
//			}
//		  }
//		}
//	}
	
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