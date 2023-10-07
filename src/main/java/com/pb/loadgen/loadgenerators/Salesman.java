//! Abstract class for CPU load generator
package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Salesman implements Runnable{

    //! Thread stop parameter
    protected volatile boolean keepRunning = true;
    //! Load parameter - CPU load percentage
    protected int loadPercentage;

    //! Constructor
    public Salesman(int percentage) {
        this.loadPercentage = percentage;
    }

    //! Method for stopping load generator running in thread
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
