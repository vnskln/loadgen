package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.domains.LoadType;
import com.pb.loadgen.services.RandomDivider;
import com.pb.loadgen.services.RandomDividerShifting;

public class CpuLoadGeneratorSingleCore implements LoadGenerator {

    LoadInput loadInput;
    RandomDivider randomDivider;
    Thread worker;

    public CpuLoadGeneratorSingleCore (LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        System.out.println("Starting load generator");
        switch (loadInput.getLoadType()) {
            case RANDOM_DIVIDER:
                randomDivider = new RandomDivider(loadInput.getLoadPercentage());
                break;
            case RANDOM_DIVIDER_SHIFTING:
                randomDivider = new RandomDividerShifting(loadInput.getLoadPercentage());
                break;
        }
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
