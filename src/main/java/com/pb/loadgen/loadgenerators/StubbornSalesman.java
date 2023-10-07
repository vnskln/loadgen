//! Implementation of constant CPU load generator
package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornSalesman extends Salesman {

    //! Constructor
    public StubbornSalesman (int loadPercentage) {
        super (loadPercentage);
    }

    //! Run generator in thread
    /*!
      1) Creating X% of cpu load is done by creating loop working for X% of time, 
      and sleeping for 100%-X% of time
    */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int loadCycleLengthInTenthOfSecond = 1;
        log.info("Stubborn cycle! Load: " + loadPercentage);
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= loadCycleLengthInTenthOfSecond*loadPercentage) {
                try {
                    Thread.sleep(loadCycleLengthInTenthOfSecond*(100L - loadPercentage));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
        }
    }
}
