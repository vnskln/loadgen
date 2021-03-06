package com.pb.loadgen.services;

import java.util.Random;

public class RandomDivider implements  Runnable {

    private volatile boolean keepRunning = true;
    private Random random = new Random();
    private int percentage;

    public RandomDivider (int percentage) {
        this.percentage = percentage;
    }

    @Override
    public void run() {
        while(keepRunning) {
            try {
                Thread.sleep(100L - percentage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double number = random.nextDouble();
            double secondNumber = random.nextDouble();
            double result = Math.atan(Math.pow(Math.tan(secondNumber), Math.tan(number)));
            System.out.println(result);
        }
    }

    public synchronized void doStop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
    }
}
