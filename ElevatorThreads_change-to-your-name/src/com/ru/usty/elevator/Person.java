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
			int currElevator = findElevator();
			ElevatorScene.scene.inSemaphore.get(sourceFloor).acquire();
			
			
			ElevatorScene.scene.incrementNumberOfPeopleInElevator(currElevator);
			ElevatorScene.scene.incrementNumberOfPeopleGoOutThisFloor(destinationFloor);
			ElevatorScene.scene.decrementNumberOfPeopleWaitingAtFloor(sourceFloor);

			
			ElevatorScene.scene.outSemaphore.get(destinationFloor).acquire();
			ElevatorScene.scene.decrementNumberOfPeopleInElevator(currElevator);
			ElevatorScene.scene.decrementNumberOfPeopleGoOutThisFloor(destinationFloor);
			ElevatorScene.scene.personExitsAtFloor(destinationFloor);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Person Thread realeased");
	}
	public int findElevator()
	{
		for(int i = 0; i < ElevatorScene.scene.getNumberOfElevators(); i++){
			if(ElevatorScene.scene.getNumberOfPeopleInElevator(i) < 6)
			{
				return i;
			}
		}
		return 0;
	}

}