//! Implementation of constant memory load generator
package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornHoarder extends Hoarder {
    
    //! Constructor
    public StubbornHoarder(int memoryLoadSizeMiB) {
        super(memoryLoadSizeMiB);
    }

    //! Run generator in thread
    /*!
      1) Gather jvm memory parameters
      2) Create random input for memory load array
    */
    @Override
    public void run() {
        log.info("Current heap usage: " + dockerSpy.getUsageHeapMemory());
        log.info("Free heap: " + (dockerSpy.getMaxHeapMemory() - dockerSpy.getUsageHeapMemory()));
        loadArray = new byte [memoryLoadSizeMiB][1048576];
        randomizeLoadArray();
        log.info("Stubborn load array! Size: " + memoryLoadSizeMiB + " MB");
        while(keepRunning) {      
            try {
                Thread.sleep(10000);
                log.info("Hoarding: " + memoryLoadSizeMiB + " MB");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
