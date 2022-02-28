package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuLoadController implements LoadController {

    LoadInput loadInput;
    Salesman salesman;
    Thread worker;

    public CpuLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        log.info("Starting CPU load generator");
        switch (loadInput.getLoadType()) {
            case CPU_STUBBORN_SALESMAN:
                salesman = new StubbornSalesman(loadInput.getLoadPercentage());
                break;
            case CPU_INDECISIVE_SALESMAN:
                salesman = new IndecisiveSalesman(loadInput.getLoadPercentage(), loadInput.getLoadPercentageHigh(), 
                        loadInput.getLoadPercentageChangeStep(), loadInput.getLoadPercentageChangeFrequencyS());
                break;
        }
        worker = new Thread(salesman);
        worker.start();
        log.info("CPU load generator started");
    }

    @Override
    public void stopGenerating() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        salesman.doStop();
        log.info("CPU load generator stopped");
    }
}
