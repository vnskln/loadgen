package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndecisiveSalesman extends Salesman {

    public int indecisiveness;
    public int loadPercentageChangeFrequencyInSeconds;
    public int percentageHigh;

    public IndecisiveSalesman (int percentage, int percentageHigh, int loadPercentageChangeStep, int loadPercentageChangeFrequencyInSeconds) {
        super (percentage);
        this.percentageHigh = percentageHigh;
        this.indecisiveness = loadPercentageChangeStep;
        this.loadPercentageChangeFrequencyInSeconds=loadPercentageChangeFrequencyInSeconds;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int cycleCounter = 0;
        int currentPercentage = percentage;
        int loadCycleLengthInTenthOfSecond = 1;
        int counterValueForLoadChange = loadPercentageChangeFrequencyInSeconds / loadCycleLengthInTenthOfSecond * 10;
        log.info("Indecisive cycle! Load low: " + percentage + "; Load high: " + percentageHigh + "; Step: " + indecisiveness + "; Cycle length: " + loadPercentageChangeFrequencyInSeconds);
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= loadCycleLengthInTenthOfSecond*currentPercentage) {
                try {
                    Thread.sleep(loadCycleLengthInTenthOfSecond*(100L - currentPercentage));
                    cycleCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (cycleCounter >= counterValueForLoadChange) {
                    currentPercentage += indecisiveness;
                    if (currentPercentage > percentageHigh) {
                        indecisiveness *= -1;
                        currentPercentage = percentageHigh;
                    }
                    if (currentPercentage < percentage) {
                        indecisiveness *= -1;
                        currentPercentage = percentage;
                    }
                    cycleCounter = 0;
                }
                startTime = System.currentTimeMillis();
            }

        }
    }

}
