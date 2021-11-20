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
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= 3*currentPercentage) {
                try {

                    Thread.sleep(3*(100L - currentPercentage));
                    speedCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (speedCounter >= speed) {
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
