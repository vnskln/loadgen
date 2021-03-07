package com.pb.loadgen.services;

import java.util.Random;

public class RandomDivider implements  Runnable {

    protected volatile boolean keepRunning = true;
    protected Random random = new Random();
    protected int percentage;

    public RandomDivider (int percentage) {
        this.percentage = percentage;
    }

    public RandomDivider () {}

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= percentage) {
                try {
                    Thread.sleep(100L - percentage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
            double number = random.nextDouble();
            double secondNumber = random.nextDouble();
            double result = Math.atan(Math.pow(Math.tan(secondNumber), Math.tan(number)));
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
