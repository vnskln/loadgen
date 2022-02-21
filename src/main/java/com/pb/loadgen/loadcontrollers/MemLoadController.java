package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Hoarder;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import com.pb.loadgen.loadgenerators.StubbornHoarder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemLoadController implements LoadController {

    LoadInput loadInput;
    Hoarder hoarder;
    Thread worker;

    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        try {
            log.info("Starting memory load generator: " + loadInput.getMemoryLoadSizeMegaBytes() + " megabytes");
            switch (loadInput.getLoadType()) {
                case MEM_COLLECTOR:
                    hoarder = new StubbornHoarder(loadInput.getMemoryLoadSizeMegaBytes());
                    break;
            }
            worker = new Thread(hoarder);
            worker.start();
            log.info("Memory load generator started");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Memory load generator failed - not enough memory");
        }
    }

    @Override
    public void stopGenerating() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hoarder.doStop();
        log.info("Memory load generator stopped");
    }
}
