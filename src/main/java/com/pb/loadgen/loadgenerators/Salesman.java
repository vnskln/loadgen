package com.pb.loadgen.loadgenerators;

public abstract class Salesman implements Runnable{

    protected volatile boolean keepRunning = true;
    protected int percentage;

    public Salesman(int percentage) {
        this.percentage = percentage;
    }

    public abstract void run();

    public void doStop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
    }
}
