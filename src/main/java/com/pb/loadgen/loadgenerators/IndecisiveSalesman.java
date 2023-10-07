//! Implementation of changeable CPU load generator
package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndecisiveSalesman extends Salesman {

    //! Load parameter - CPU load change step
    public int loadPercentageChangeStep;
    //! Load parameter - CPU load change frequency
    public int loadPercentageChangeFrequencyS;
    //! Load parameter - max CPU load
    public int loadPercentageHigh;

    //! Constructor
    public IndecisiveSalesman (int loadPercentage, int loadPercentageHigh, int loadPercentageChangeStep, int loadPercentageChangeFrequencyS) {
        super (Math.min(loadPercentage,loadPercentageHigh));
        this.loadPercentageHigh = Math.max(loadPercentage, loadPercentageHigh);
        this.loadPercentageChangeStep = loadPercentageChangeStep;
        this.loadPercentageChangeFrequencyS=loadPercentageChangeFrequencyS;
    }

    //! Run generator in thread
    /*!
      1) Creating X% of cpu load is done by creating loop working for X% of time, 
      and sleeping for 100%-X% of time
      2) Load value change is based on users' parameters
    */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int cycleCounter = 0;
        int currentPercentage = loadPercentage;
        int loadCycleLengthInTenthOfSecond = 1;
        int counterValueForLoadChange = loadPercentageChangeFrequencyS / loadCycleLengthInTenthOfSecond * 10;
        log.info("Indecisive cycle! Load low: " + loadPercentage + "; Load high: " + loadPercentageHigh + "; Step: " + loadPercentageChangeStep + "; Cycle length: " + loadPercentageChangeFrequencyS);
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= loadCycleLengthInTenthOfSecond*currentPercentage) {
                try {
                    Thread.sleep(loadCycleLengthInTenthOfSecond*(100L - currentPercentage));
                    cycleCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (cycleCounter >= counterValueForLoadChange) {
                    currentPercentage += loadPercentageChangeStep;
                    if (currentPercentage > loadPercentageHigh) {
                        loadPercentageChangeStep *= -1;
                        log.info("Load above max value. Reversing");
                        currentPercentage = loadPercentageHigh;
                    }
                    if (currentPercentage < loadPercentage) {
                        loadPercentageChangeStep *= -1;
                        log.info("Load below min value. Reversing");
                        currentPercentage = loadPercentage;
                    }
                    cycleCounter = 0;
                    log.info("Using: " + currentPercentage + "% of CPU core");
                }
                startTime = System.currentTimeMillis();
            } 
        }
    }

}
