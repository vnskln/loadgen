package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornSalesman extends Salesman {

    public StubbornSalesman (int percentage) {
        super (percentage);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int loadCycleLengthInTenthOfSecond = 1;
        log.info("Stubborn cycle! Load: " + percentage);
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= loadCycleLengthInTenthOfSecond*percentage) {
                try {
                    Thread.sleep(loadCycleLengthInTenthOfSecond*(100L - percentage));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
        }
    }
}
