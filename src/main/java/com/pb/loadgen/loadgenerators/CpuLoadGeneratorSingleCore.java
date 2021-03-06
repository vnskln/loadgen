package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.services.RandomDivider;

public class CpuLoadGeneratorSingleCore implements LoadGenerator {

    int percentage;
    RandomDivider randomDivider;
    Thread worker;

    public CpuLoadGeneratorSingleCore (int percentage) {
        this.percentage = percentage;
    }

    @Override
    public void generate() {
        System.out.println("Starting load generator");
        randomDivider = new RandomDivider(percentage);
        worker = new Thread(randomDivider);
        worker.start();
        System.out.println("Load generator started");
    }

    @Override
    public void generateStop() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        randomDivider.doStop();
    }
}
