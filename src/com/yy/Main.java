package com.yy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int coreCount = Runtime.getRuntime().availableProcessors();

        final double[] time = {0,1};
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(coreCount);

        singleThreadExecutor.execute(() -> {
            double startTime = System.currentTimeMillis();
            int count = 0;
            for (int i = 0; i < 100; i++) {
                int[] randomNo = new int[1000];
                for (int j = 0; j < 1000; j++) {
                    randomNo[j] = (int) (Math.random() * (999 - 100 + 1));
                    System.out.print(randomNo[j] + " ");
                }
            }
            double stime = (System.currentTimeMillis() - startTime)/1000;
            time[0] = stime;
            System.out.println();
        });

        singleThreadExecutor.shutdown();
        singleThreadExecutor.awaitTermination(5, TimeUnit.SECONDS);

        fixedThreadPool.execute(() -> {
            double startTime = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                int[] randomNo = new int[1000];
                for (int j = 0; j < 1000; j++) {
                    randomNo[j] = (int) (Math.random() * (999 - 100 + 1));
                }
            }
            double ftime = (System.currentTimeMillis() - startTime)/1000;
            time[1] = ftime;
            System.out.println();
            System.out.println("_____________________________________________________________________________________________________________________________");
            System.out.println();
        });

        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Total execution time (in seconds) for 100 tasks where each task will display 1000 random numbers between 100 and 999:");
        System.out.println("1. singleThreadExecutor: " + time[0] + " seconds");
        System.out.println("2. fixedThreadPool: " + time[1] + " seconds");
    }
}
