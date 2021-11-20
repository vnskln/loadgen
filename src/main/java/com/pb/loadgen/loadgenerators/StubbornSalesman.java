package com.pb.loadgen.loadgenerators;

import java.util.Random;

public class StubbornSalesman extends Salesman {

    public StubbornSalesman (int percentage) {
        super (percentage);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= percentage) {
                try {
                    Thread.sleep(100L - percentage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
        }
    }
}
