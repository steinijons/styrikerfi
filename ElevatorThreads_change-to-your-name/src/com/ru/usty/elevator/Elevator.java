package com.ru.usty.elevator;

public class Elevator implements Runnable {
	int numberOfPeople = 0;
	int currFloor;
	int elevator;
	
	public Elevator(int elevator)
	{
		this.elevator = elevator;
	}
	
	public void run() {
		/*while(true)
		{*/	
			sleepNow();
			if(ElevatorScene.elevatorMayDie)
			{
				return;
			}
			System.out.println("NUMBER OF FLOORS: " + ElevatorScene.scene.getNumberOfFloors());
			for(int i = 0; i < ElevatorScene.scene.getNumberOfFloors(); i++)
			{
				ElevatorScene.scene.setCurrentFloorForElevator(i);
				System.out.println("HÆÐ: " + i);
		
				//Ath pláss í lyftu
				numberOfPeople = ElevatorScene.scene.getNumberOfPeopleInElevator(1);
				for(int j = 0; j < (6 - numberOfPeople); j++)
				{
					System.out.println("Persóna ma fara inn");
					ElevatorScene.scene.inSemaphore.get(i).release();
					sleepNow();
				}
				//Filla lyftu
				/*for(int j = 0; j < (6 - numberOfPeople); j++)
				{
					System.out.println("fyllum lyftu með personu " + j);
					try {
						ElevatorScene.scene.inSemaphore.get(i).acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//tokum semaphorurnar sem ad gefa leyfi fyrir ad fara inni lyftu
					sleepNow();
				}*/
				
				
				System.out.println("Lyfta full");
				ElevatorScene.scene.setCurrentFloorForElevator(i+1);
				for(int j = 0; j < 6; j++)
				{
					System.out.println("HÆÐ: " + ElevatorScene.scene.getCurrentFloorForElevator(i+1));
					System.out.println("Persóna: " + j + " fer úr lyftu");				
					ElevatorScene.scene.outSemaphore.get(i+1).release();
		
					sleepNow();
				}
				System.out.println("Allir farnir úr lyftu!!!!");
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
