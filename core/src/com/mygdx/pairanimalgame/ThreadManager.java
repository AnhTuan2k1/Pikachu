package com.mygdx.pairanimalgame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    public static ExecutorService executor;
    public static void doTask(Runnable task){
        executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                task.run();
                System.out.println("Task executed by " + Thread.currentThread().getName());
            });
        }
        executor.shutdown();
    }
}
