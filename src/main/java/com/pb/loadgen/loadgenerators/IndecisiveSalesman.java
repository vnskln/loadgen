package com.pb.loadgen.loadgenerators;

public class IndecisiveSalesman extends Salesman {

    public int indecisiveness;

    public IndecisiveSalesman (int percentage, int indecisiveness) {
        super (percentage);
        this.indecisiveness = indecisiveness;
    }

    @Override
    public void run() {
        System.out.println("Init percentage: " + percentage);
        System.out.println("Indeciveness: " + indecisiveness);
        long startTime = System.currentTimeMillis();
        while(keepRunning) {
            long timeSinceSleep = System.currentTimeMillis() - startTime;
            System.out.println("Time: " + timeSinceSleep);
            System.out.println("Percentage: " + percentage);
            if (timeSinceSleep >= percentage) {
                try {
                    System.out.println("Sleeping for " + (100L - percentage));
                    Thread.sleep(100L - percentage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                percentage+=indecisiveness;
                if (percentage > 100) {
                    indecisiveness*=-1;
                    percentage = 100;
                }
                if (percentage < 0) {
                    indecisiveness*=-1;
                    percentage = 0;
                }
                startTime = System.currentTimeMillis();
            }

        }
    }

}
