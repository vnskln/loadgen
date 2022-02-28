package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornSalesman extends Salesman {

    public StubbornSalesman (int loadPercentage) {
        super (loadPercentage);
    }

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
