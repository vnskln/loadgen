package com.pb.loadgen.loadgenerators;

public class IndecisiveSalesman extends Salesman {

    public int indecisiveness;
    public int speed;

    public IndecisiveSalesman (int percentage, int indecisiveness, int speed) {
        super (percentage);
        this.indecisiveness = indecisiveness;
        this.speed=speed;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int speedCounter = 0;
        int currentPercentage = percentage;
        int loadCycleLengthInTenthOfSecond = 1;
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= loadCycleLengthInTenthOfSecond*currentPercentage) {
                try {
                    Thread.sleep(loadCycleLengthInTenthOfSecond*(100L - currentPercentage));
                    speedCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (speedCounter >= 10*speed) {
                    currentPercentage += indecisiveness;
                    if (currentPercentage > 100) {
                        indecisiveness *= -1;
                        currentPercentage = 100;
                    }
                    if (currentPercentage < percentage) {
                        indecisiveness *= -1;
                        currentPercentage = percentage;
                    }
                    speedCounter = 0;
                }
                startTime = System.currentTimeMillis();
            }

        }
    }

}
