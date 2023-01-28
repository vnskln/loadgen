package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuLoadController implements LoadController {

    private LoadInput loadInput;
    private Salesman salesman;
    private Thread [] workers;
    private long startTime = 0;
    private long stopTime = 0;
    private long elapsedTime = 0;

    public CpuLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
        this.workers = new Thread [loadInput.getThreadNumber()];
    }

    @Override
    public void generate() {
        log.info("Starting CPU load generator");
        startTime = System.currentTimeMillis();
        switch (loadInput.getLoadType()) {
            case CPU_STUBBORN_SALESMAN:
                salesman = new StubbornSalesman(loadInput.getLoadPercentage());
                break;
            case CPU_INDECISIVE_SALESMAN:
                salesman = new IndecisiveSalesman(loadInput.getLoadPercentage(), loadInput.getLoadPercentageHigh(), 
                        loadInput.getLoadPercentageChangeStep(), loadInput.getLoadPercentageChangeFrequencyS());
                break;
        }
        for (int i=0; i<loadInput.getThreadNumber(); i++) {
            log.info("CPU load thread " + i + " starting");
            workers [i] = new Thread(salesman);
            workers[i].start();
            log.info("CPU load thread " + i + " started");
        }
        
        log.info("CPU load generator started");
    }

    @Override
    public void stopGenerating() {
        for (int i=0; i<loadInput.getThreadNumber(); i++) {
            try {
                workers[i].sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            salesman.doStop();
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime)/1000;
        log.info("CPU load generator stopped");
        }
        
    }

    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }
}
