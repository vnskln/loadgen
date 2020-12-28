package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.services.RandomDivider;
import com.pb.loadgen.services.SingleThreadManager;

public class CpuLoadGeneratorSingleCore implements LoadGenerator {

    private Thread thread;
    SingleThreadManager manager;

    public CpuLoadGeneratorSingleCore (int percentage) throws Exception {
        RandomDivider randomDivider = new RandomDivider();
        manager = new SingleThreadManager(randomDivider, percentage);
        thread = new Thread(manager);
    }

    @Override
    public void start() throws InterruptedException {
        thread.start();
    }

    @Override
    public void stop() {
        manager.doStop();
    }
}
