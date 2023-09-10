package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.controllers.DockerSpy;
import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Hoarder;
import com.pb.loadgen.loadgenerators.IndecisiveHoarder;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import com.pb.loadgen.loadgenerators.StubbornHoarder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
public class MemLoadController implements LoadController {

    private LoadInput loadInput;
    private Hoarder hoarder;
    private Thread worker;
    private DockerSpy dockerSpy;
    private long startTime = 0;
    private long stopTime = 0;
    private long elapsedTime = 0;

    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        try {
            log.info("Starting memory load generator: " + loadInput.getUniqueID());
            startTime = System.currentTimeMillis();
            switch (loadInput.getLoadType()) {
                case MEM_STUBBORN_HOARDER:
                    hoarder = new StubbornHoarder(loadInput.getMemoryLoadSizeMiB());
                    break;
                case MEM_INDECISIVE_HOARDER:
                    hoarder = new IndecisiveHoarder(loadInput.getMemoryLoadSizeMiB(), loadInput.getMemoryLoadSizeMiBHigh(),
                    loadInput.getMemoryLoadSizeMiBChangeStep(), loadInput.getMemoryLoadChangeFrequencyS());
                    break;
            }
            worker = new Thread(hoarder);
            worker.setName("loadgt//" + loadInput.getUniqueID() + "//0");
            worker.start();
            log.info("Memory load generator started: " + loadInput.getUniqueID());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Memory load generator failed: " + loadInput.getUniqueID());
        }
    }

    @Override
    public void stopGenerating() {
        try {
            worker.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hoarder.doStop();
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime)/1000;
        log.info("Memory load generator stopped: " + loadInput.getUniqueID());
    }
    
    @Override
    public String getDetails() {
        String details = loadInput.getLoadType().toString() + ", " + loadInput.getMemoryLoadSizeMiB();
        if (loadInput.getLoadType().toString().equals("MEM_INDECISIVE_HOARDER")) {
            details += " - " + loadInput.getMemoryLoadSizeMiBHigh();
            details += ", step " + loadInput.getMemoryLoadSizeMiBChangeStep();
            details += ", freq " + loadInput.getMemoryLoadChangeFrequencyS();
        }
        return details;
    }
    
    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }
}
