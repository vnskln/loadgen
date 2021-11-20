package com.pb.loadgen.loadgenerators;

public class StubbornSalesman extends Salesman {

    public StubbornSalesman (int percentage) {
        super (percentage);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= 10*percentage) {
                try {
                    Thread.sleep(10*(100L - percentage));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
        }
    }
}
