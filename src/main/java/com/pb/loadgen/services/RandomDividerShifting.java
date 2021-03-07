package com.pb.loadgen.services;

public class RandomDividerShifting extends RandomDivider{

    public RandomDividerShifting (int percentage) {
        super(percentage);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long shiftTime = startTime;
        while(keepRunning) {
            if (System.currentTimeMillis() - startTime >= percentage) {
                try {
                    Thread.sleep(100L - percentage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startTime = System.currentTimeMillis();
            }
            double number = random.nextDouble();
            double secondNumber = random.nextDouble();
            double result = Math.atan(Math.pow(Math.tan(secondNumber), Math.tan(number)));
            if (System.currentTimeMillis() - shiftTime >= 1000) {
                if (++percentage > 100)
                    percentage -= 100;
                shiftTime = System.currentTimeMillis();
            }
        }
    }

}
