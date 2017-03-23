package com.ru.usty.scheduling;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.ru.usty.scheduling.process.ProcessExecution;
//import com.ru.usty.scheduling.process.ProcessHandler;
//import com.ru.usty.scheduling.process.ProcessInfo;
import com.ru.usty.scheduling.process.ProcessHandler;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	boolean isProcessinExec;
	Timer timer;
	TimerTask task;
	ProcessHandler processHandler;

	/**
	 * Add any objects and variables here (if needed)
	 */
	Queue<ProcessData> processQueue;
	List<ProcessData> processList;
	List<ArrayList<ProcessData>> processFeedbackListOfLists;
	long whatTimeisNow;
	long currProcessTime;
	int currProcessID;
	long secPassed;

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
		
		isProcessinExec = false;
		processQueue = new LinkedList<ProcessData>();
		processList = new ArrayList<ProcessData>();
		timer = new Timer();
		processHandler = new ProcessHandler();
		secPassed = 0;
		processFeedbackListOfLists = new ArrayList<ArrayList<ProcessData>>();
		
		
		task = new TimerTask(){
			public void run(){
				secPassed = secPassed + 1000;
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);

		/**
		 * Add general initialization code here (if needed)
		 */

		switch(policy) {
		case FCFS:	//First-come-first-served
			System.out.println("Starting new scheduling task: First-come-first-served");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case FB:	//Feedback
			System.out.println("Starting new scheduling task: Feedback, quantum = " + quantum);
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

		if(policy.equals(Policy.FCFS))
		{
			if(!isProcessinExec)
			{
				processExecution.switchToProcess(processID);
				processQueue.add(new ProcessData(processID, 0));
				isProcessinExec = true;
			}
			else
			{
				processQueue.add(new ProcessData(processID, 0));
			}
		}
		else if(policy.equals(Policy.RR))
		{
			if(!isProcessinExec)
			{
				
				processExecution.switchToProcess(processID);
				processQueue.add(new ProcessData(processID, 0));
				isProcessinExec = true;
			}
			else
			{
				processQueue.add(new ProcessData(processID, 0));
			}
		}
		if(policy.equals(Policy.SPN))
		{
			if(!isProcessinExec)
			{
				//kveiki á fyrsta process, þegar hann klárar rönnar processFinished
				processExecution.switchToProcess(processID);
				isProcessinExec = true;
			}
			else
			{
				processList.add(new ProcessData(processID, processExecution.getProcessInfo(processID).totalServiceTime));
			}
		}
		if(policy.equals(Policy.SRT))
		{
			if(!isProcessinExec)
			{
				//kveiki á fyrsta process, þegar hann klárar rönnar processFinished
				processExecution.switchToProcess(processID);
				currProcessTime = processExecution.getProcessInfo(processID).totalServiceTime;
				currProcessID = processID;
				isProcessinExec = true;
			}
			else if(processExecution.getProcessInfo(processID).totalServiceTime < currProcessTime)
			{
				processList.add(new ProcessData(currProcessID, currProcessTime));
				processExecution.switchToProcess(processID);
				currProcessTime = processExecution.getProcessInfo(processID).totalServiceTime;
				currProcessID = processID;
			}
			else
			{
				processList.add(new ProcessData(processID, processExecution.getProcessInfo(processID).totalServiceTime));
			}
		}
		if(policy.equals(Policy.HRRN))
		{
			if(!isProcessinExec)
			{
				//kveiki á fyrsta process, þegar hann klárar rönnar processFinished
				processExecution.switchToProcess(processID);
				isProcessinExec = true;
			}
			else
			{
				processList.add(new ProcessData(processID, secPassed));
			}
		}
		
		if(policy.equals(Policy.FB))
		{
			if(!isProcessinExec)
			{
				//kveiki á fyrsta process, þegar hann klárar rönnar processFinished
				processExecution.switchToProcess(processID);
				ProcessData pd = new ProcessData(processID, processExecution.getProcessInfo(processID).totalServiceTime);
				ArrayList<ProcessData> list = new ArrayList<ProcessData>();
				list.add(pd);
				processFeedbackListOfLists.add(list);
				isProcessinExec = true;
			}
			else
			{
				processList.add(new ProcessData(processID, secPassed));
			}
		}
		
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {

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
		else if(policy.equals(Policy.SPN))
		{
			SPN(processID);
		}
		else if(policy.equals(Policy.SRT))
		{
			SRT(processID);
		}
		else if(policy.equals(Policy.HRRN))
		{
			HRRN(processID);
		}
		else if(policy.equals(Policy.FB))
		{
			FB(processID);
		}
	}
	
	public void FCFR()
	{	
		processQueue.remove();
		if(!processQueue.isEmpty())
		{
			processExecution.switchToProcess(processQueue.peek().processID);
		}
		else
			isProcessinExec = false;
	}
	
	private void RR(int processID) {
		
		if(!processQueue.isEmpty())
		{
			Thread thread = new Thread(){
				public void run() {
					while(true) {
						try {
							int currProcessor = processQueue.remove().processID;
							processExecution.switchToProcess(currProcessor);	
							processQueue.add(new ProcessData(currProcessor, 0));
							Thread.sleep(quantum);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
					}
				};
				thread.start();	
		}
		isProcessinExec = false;
	}

	
	private void SPN(int processID) {
		if(!processList.isEmpty())
		{
			long somet = 100000;
			int id = 0;
			for(int i = 0; i < processList.size(); i++) // finnur styðsta process
			{
				if(processList.get(i).someTime < somet)
				{
					somet = processList.get(i).someTime;
					id = i;
				}
			}
			processExecution.switchToProcess(processList.get(id).processID); // setur styðsta process í vinnslu

			processList.remove(id); // fjarlægir styðsta process af lista
		}
		else
			isProcessinExec = false;
		
	}
	private void SRT(int processID) {
		if(!processList.isEmpty())
		{
			long somet = 100000;
			int id = 0;
			for(int i = 0; i < processList.size(); i++)
			{
				if(processList.get(i).someTime < somet)
				{
					somet = processList.get(i).someTime;
					id = i;
				}
			}
			processExecution.switchToProcess(processList.get(id).processID);
			currProcessTime = processList.get(id).someTime;
			currProcessID = processList.get(id).processID;
		
			processList.remove(id);
		}
		else
			isProcessinExec = false;
		
	}
	private void HRRN(int processID) {
		if(!processList.isEmpty())
		{
			long sometime = 0;
			int id = 0;
			for(int i = 0; i < processList.size(); i++)
			{
				long lol = processExecution.getProcessInfo(processList.get(i).processID).elapsedWaitingTime;
				//long startTimeMinusArrivalTime = secPassed - processList.get(i).someTime;
				long serviceTime = processExecution.getProcessInfo(processList.get(i).processID).totalServiceTime;
				long priority = ((lol + serviceTime) / serviceTime);
				if(priority > sometime)
				{
					sometime = priority;
					id = i;
				}
				
			}
			processExecution.switchToProcess(processList.get(id).processID);
			processList.remove(id);
		}
		else
			isProcessinExec = false;
		
	}
	
	private void FB(int processID) {
		if(!processList.isEmpty())
		{
			long sometime = 0;
			int id = 0;
			for(int i = 0; i < processList.size(); i++)
			{
				long lol = processExecution.getProcessInfo(processList.get(i).processID).elapsedWaitingTime;
				//long startTimeMinusArrivalTime = secPassed - processList.get(i).someTime;
				long serviceTime = processExecution.getProcessInfo(processList.get(i).processID).totalServiceTime;
				long priority = ((lol + serviceTime) / serviceTime);
				if(priority > sometime)
				{
					sometime = priority;
					id = i;
				}
				
			}
			processExecution.switchToProcess(processList.get(id).processID);
			processList.remove(id);
		}
		else
			isProcessinExec = false;
		
	}
	
}
