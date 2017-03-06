package com.ru.usty.elevator;

public class Elevator implements Runnable {
	int numberOfPeopleInElevator, currFloor, elevator, peopleGoOutThisFloor;
	final int ELEVATORMAX = 6;
	boolean goingUp;
	
	int counter = 0;
	
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
			counter++;
			if(ElevatorScene.elevatorMayDie)
			{
				return;
			}
			
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
				ElevatorScene.scene.outSemaphore.get(currFloor).release();
				sleepNow();
			}
				
			//kill program if there is no person waiting for the elevator
			if(counter != 1)
			{
				if(checkIfToKillProgram()){
					if(currFloor == 0){
						System.out.println("Finished!");
						break;
					}
				}
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
						
		}
	}

		private boolean checkIfToKillProgram() {
		int killCounter = 0;
		for(int i = 0; i < ElevatorScene.scene.getNumberOfFloors(); i++){
			if(ElevatorScene.scene.getNumberOfPeopleWaitingAtFloor(i) == 0){
				killCounter++;
			}
			if(killCounter == ElevatorScene.scene.getNumberOfFloors()){
				return true;
			}
		}
		return false;
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