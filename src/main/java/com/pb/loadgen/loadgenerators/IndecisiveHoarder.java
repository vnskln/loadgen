//! Implementation of indecisive memory load generator
package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndecisiveHoarder extends Hoarder {

    //! Load parameter - max load array size
    public int memoryLoadSizeMiBHigh;
    //! Load parameter - load array change step
    public int memoryLoadSizeMiBChangeStep;
    //! Load parameter - load array change frequency
    public int memoryLoadChangeFrequencyS;

    //! Constructor
    public IndecisiveHoarder(int memoryLoadSizeMiB, int memoryLoadSizeMiBHigh, int memoryLoadSizeMiBChangeStep, int memoryLoadChangeFrequencyS) {
        super(Math.min(memoryLoadSizeMiB,memoryLoadSizeMiBHigh));
        this.memoryLoadSizeMiBHigh = Math.max(memoryLoadSizeMiB, memoryLoadSizeMiBHigh);
        this.memoryLoadSizeMiBChangeStep = memoryLoadSizeMiBChangeStep;
        this.memoryLoadChangeFrequencyS = memoryLoadChangeFrequencyS;
    }

    //! Run generator in thread
    /*!
      1) Gather jvm memory parameters
      2) Create random input for memory load array
      3) Change size of load array
    */
    @Override
    public void run() {
        int currentLoadSizeMiB = memoryLoadSizeMiB;
        log.info("Current heap usage: " + dockerSpy.getUsageHeapMemory());
        log.info("Free heap: " + (dockerSpy.getMaxHeapMemory() - dockerSpy.getUsageHeapMemory()));
        loadArray = new byte [memoryLoadSizeMiB][1048576];
        randomizeLoadArray();
        log.info("Indecisive load array! Size low: " + memoryLoadSizeMiB + "MB; Size high: " + memoryLoadSizeMiBHigh + "MB; Step: " + memoryLoadSizeMiBChangeStep + "MB; Frequency: " + memoryLoadChangeFrequencyS + "s");
        while (keepRunning) {
            try {
                log.info("Waiting " + memoryLoadChangeFrequencyS + " s");
                Thread.sleep(memoryLoadChangeFrequencyS * 1000);
                log.info("Changing load size by " + memoryLoadSizeMiBChangeStep + "MiB");
                currentLoadSizeMiB += memoryLoadSizeMiBChangeStep;
                if (currentLoadSizeMiB > memoryLoadSizeMiBHigh) {
                    memoryLoadSizeMiBChangeStep *= -1;
                    log.info("Load above max value. Reversing");
                    currentLoadSizeMiB = memoryLoadSizeMiBHigh;
                }
                if (currentLoadSizeMiB < memoryLoadSizeMiB) {
                    memoryLoadSizeMiBChangeStep *= -1;
                    log.info("Load below min value. Reversing");
                    currentLoadSizeMiB = memoryLoadSizeMiB;
                }
                clearLoadArray();
                loadArray = null;
                System.gc();
                System.runFinalization();
                loadArray = new byte [currentLoadSizeMiB][1048576];
                randomizeLoadArray();
                log.info("Hoarding: " + currentLoadSizeMiB + " MB");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
