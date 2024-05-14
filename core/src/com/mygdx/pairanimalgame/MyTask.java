package com.mygdx.pairanimalgame;

public class MyTask implements Runnable {

    @Override
    public void run() {
        // Code để thực thi trong luồng mới
        System.out.println("This is running in a separate thread");
    }
}
