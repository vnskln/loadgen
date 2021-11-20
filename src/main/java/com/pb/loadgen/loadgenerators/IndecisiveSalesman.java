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
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= percentage) {
                try {
                    System.out.println("Sleeping for " + (100L - percentage));
                    Thread.sleep(100L - percentage);
                    speedCounter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (speedCounter >= speed) {
                    percentage += indecisiveness;
                    if (percentage > 100) {
                        indecisiveness *= -1;
                        percentage = 100;
                    }
                    if (percentage < 0) {
                        indecisiveness *= -1;
                        percentage = 0;
                    }
                    speedCounter = 0;
                }
                startTime = System.currentTimeMillis();
            }

        }
    }

}
