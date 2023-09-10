package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Salesman implements Runnable{

    protected volatile boolean keepRunning = true;
    protected int loadPercentage;

    public Salesman(int percentage) {
        this.loadPercentage = percentage;
    }

    public void doStop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
        log.info("Finishing load cycle");
    }
}
