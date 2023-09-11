package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuLoadController implements LoadController {

    private final LoadInput loadInput;
    private Salesman salesman;
    private final Thread [] workers;
    private long startTime = 0;
    private long stopTime = 0;
    private long elapsedTime = 0;

    public CpuLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
        this.workers = new Thread [loadInput.getThreadNumber()];
    }

    @Override
    public void generate() {
        log.info("Starting CPU load generator: " + loadInput.getUniqueID());
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
            log.info("CPU load thread " + i + " starting: " + loadInput.getUniqueID());
            workers[i] = new Thread(salesman);
            workers[i].setName("loadgt//" + loadInput.getUniqueID() + "//" + i);
            workers[i].start();
            log.info("CPU load thread " + i + " started: " + loadInput.getUniqueID());
        }
        
        log.info("CPU load generator started: " + loadInput.getUniqueID());
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
            log.info("CPU load generator stopped: " + loadInput.getUniqueID());
        }
    }

    @Override
    public String getDetails() {
        String details = loadInput.getLoadType().toString() + ", " + loadInput.getLoadPercentage();
        if (loadInput.getLoadType().toString().equals("CPU_INDECISIVE_SALESMAN")) {
            details += " - " + loadInput.getLoadPercentageHigh();
            details += ", step " + loadInput.getLoadPercentageChangeStep();
            details += ", freq " + loadInput.getLoadPercentageChangeFrequencyS();
        }
        details += ", threads " + loadInput.getThreadNumber();
        
        return details;
    }

    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }
}
