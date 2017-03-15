package com.ru.usty.scheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
//import java.util.PriorityQueue;
import java.util.Queue;

import com.ru.usty.scheduling.process.ProcessExecution;
//import com.ru.usty.scheduling.process.ProcessHandler;
//import com.ru.usty.scheduling.process.ProcessInfo;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	boolean isProcessinExec;
	int cheat;

	/**
	 * Add any objects and variables here (if needed)
	 */
	Queue<ProcessData> processQueue;
	List<ProcessData> processList;
	long whatTimeisNow;
	long currProcessTime;
	int currProcessID;
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
		cheat = 1000;
				
		processQueue = new LinkedList<ProcessData>();
		processList = new ArrayList<ProcessData>();
		
		//processes = new ArrayList<Integer>();
		
		

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
				processList.add(new ProcessData(processID, 0));
			}
			else
			{
				System.out.println("ekki empty");
				processList.add(new ProcessData(processID, 0));
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
		if(policy.equals(Policy.SPN))
		{
			if(isProcessinExec == false)
			{
				System.out.println("isempty");
				//kveiki á fyrsta process, þegar hann klárar rönnar processFinished
				processExecution.switchToProcess(processID);
				isProcessinExec = true;
			}
			else
			{
				
				System.out.println("ekki empty");
				processList.add(new ProcessData(processID, processExecution.getProcessInfo(processID).totalServiceTime));
			}
		}
		if(policy.equals(Policy.SRT))
		{
			if(isProcessinExec == false)
			{
				System.out.println("isempty");
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
				
				System.out.println("ekki empty");
				processList.add(new ProcessData(processID, processExecution.getProcessInfo(processID).totalServiceTime));
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
		
		System.out.println("KEYRSLU LOKIÐ!");


	}
	
	/*
	 * Round Robin
	 * Með öðrum orðum time-slicing
	 * Búum til nýtt interupt byggð á timer, þannig í hvert skipti sem dispatcherinn 
	 * keyrir þá býr hann líka til timer sem bíður í einhvern ákveðinn tíma og interuptar og þá er distpatcher keyrður.
	 * Dispatcher velur þann sem er fremstur á queueinni sambærilegt og í FCFS
	 */
	
	private void RR(int processID) {
		
		processQueue.remove(processID);
		if(!processQueue.isEmpty())
		{
			Thread t = new Thread(){
				public void run() {
					while(true) {
						try {
							
							if(!processQueue.isEmpty())
							{
								int currProcessor = processQueue.remove().processID;
								processExecution.switchToProcess(currProcessor);	
								processQueue.add(new ProcessData(currProcessor, 0));
								Thread.sleep(quantum);
							}

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
			t.start();	
		}
	}

	/*
	 * 	First-Come-First-Served (FCFS)
	 * 	Einfaldlega nonpreemtive queue
	 * 	Simplest scheduling policy
	 * 	FCFS = FIFO
	 * 	Performs much better for long process than short ones
	 * 	Tends to favor processor-bound processes over I/O-bound processes
	 */
	
	public void FCFR()
	{	
		processQueue.remove();
		if(!processQueue.isEmpty())
		{
			processExecution.switchToProcess(processQueue.peek().processID);
		}
	}
	
	private void SPN(int processID) {
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
			System.out.println("efst á queue " + id);
			processList.remove(id);
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
	
}
