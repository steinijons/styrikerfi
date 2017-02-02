package com.ru.usty.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadAssignment01Main {
	
    private static final int NUMBER_OF_PROBLEMS = 30;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Solutions:");

        long startTime = System.currentTimeMillis();

        int POOL_SIZE = 5;
        
        Thread[] threads = new Thread[NUMBER_OF_PROBLEMS];
        
        ExecutorService threadPool = Executors.newFixedThreadPool(POOL_SIZE);
        
        for(int i = 0; i < NUMBER_OF_PROBLEMS; i++){
        	final int number = i+1;
        	threadPool.submit(makeNewRunnable(i));
        }
        	//threads[i] = new Thread(new Runnable()  {
		
        try {
        	threadPool.shutdown();
        	threadPool.awaitTermination(4, TimeUnit.MINUTES);
        }catch (InterruptedException e) {
        	e.printStackTrace();
        }
        		
        System.out.println("All done");

        System.out.println("Total time: " + (System.currentTimeMillis() - startTime) + " ms");
    }
    public static Runnable makeNewRunnable(final int number) {
    	return new Runnable() {
	    	@Override
			public void run() {
				Solver.findAndPrintSolution(Problematic.nextProblem());
				System.out.println("þráður " + number + " starting");	
	    	}
    	};
    }
}
