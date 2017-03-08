package com.ru.usty.scheduling;

import java.util.LinkedList;
import java.util.Queue;

import com.ru.usty.scheduling.process.ProcessExecution;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;

	/**
	 * Add any objects and variables here (if needed)
	 */
	Queue<ProcessData> processQueue;
	long whatTimeisNow ;
	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public Scheduler(ProcessExecution processExecution) {
		this.processExecution = processExecution;
		/**
		 * Add general initialization code here (if needed)
		 */
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void startScheduling(Policy policy, int quantum) {

		this.policy = policy;
		this.quantum = quantum;
		
		processQueue = new LinkedList<ProcessData>();
		
		

		/**
		 * Add general initialization code here (if needed)
		 */

		switch(policy) {
		case FCFS:	//First-come-first-served
			System.out.println("Starting new scheduling task: First-come-first-served");
			policy = Policy.FCFS;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			policy = Policy.RR;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			policy = Policy.SPN;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			policy = Policy.SRT;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			policy = Policy.HRRN;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case FB:	//Feedback
			System.out.println("Starting new scheduling task: Feedback, quantum = " + quantum);
			policy = Policy.FB;
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		}

		/**
		 * Add general scheduling or initialization code here (if needed)
		 */

	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processAdded(int processID) {
/*		
		• 25% - First Come First Served (FCFS) KOMIÐ!
		• 20% - Round Robin (RR)
		• 10% - Shortest Process Next (SPN)
		• 10% - Shortest Remaining Time (SRT)
		• 10% - Highest Response Ratio Next (HRRN)
		• 20% - Feedback (FB)
*/
		
		if(policy.equals(Policy.FCFS))
		{
			if(processQueue.isEmpty())
			{
				System.out.println("isempty");
				processExecution.switchToProcess(processID);
				processQueue.add(new ProcessData(processID, 0));
			}
			else
			{
				System.out.println("ekki empty");
				processQueue.add(new ProcessData(processID, 0));
			}
		}
		else if(policy.equals(Policy.RR))
		{
			
			if(processQueue.isEmpty())
			{
				processExecution.switchToProcess(processID);
				processQueue.add(new ProcessData(processID, 0));
			}
			else
			{
				processQueue.add(new ProcessData(processID, 0));
			}
		}

		
		/**
		 * Add scheduling code here
		 */

	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
		/*		
		• 25% - First Come First Served (FCFS) KOMIÐ!
		• 20% - Round Robin (RR)
		• 10% - Shortest Process Next (SPN)
		• 10% - Shortest Remaining Time (SRT)
		• 10% - Highest Response Ratio Next (HRRN)
		• 20% - Feedback (FB)
*/
		/**
		 * Add scheduling code here
		 */
		if(policy.equals(Policy.FCFS))
		{
			FCFR();
		}
		else if(policy.equals(Policy.RR))
		{
			RR(processID);
		}
		
		System.out.println("KEYRSLU LOKIÐ!");


	}
	
	private void RR(int processID) {
		processQueue.remove(processID);
		if(!processQueue.isEmpty())
		{
			int bla = processQueue.remove().processID;
			processExecution.switchToProcess(bla);
			processQueue.add(new ProcessData(bla, 0));
		}
		
	}

	public void FCFR()
	{	
		processQueue.remove();
		if(!processQueue.isEmpty())
		{
			processExecution.switchToProcess(processQueue.peek().processID);
		}
	}
	
}
