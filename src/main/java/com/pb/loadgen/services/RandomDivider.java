package com.pb.loadgen.services;

import java.util.Random;

public class RandomDivider implements  Runnable {

    private volatile boolean keepRunning = true;
    private Random random = new Random();

    public synchronized void doStop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
    }

    @Override
    public void run() {
        while(keepRunning) {
            double number = random.nextDouble();
            double secondNumber = random.nextDouble();
            System.out.println(Math.atan(Math.pow(Math.tan(secondNumber), Math.tan(number))));
        }
    }
}
