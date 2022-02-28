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

    LoadInput loadInput;
    Hoarder hoarder;
    Thread worker;
    DockerSpy dockerSpy;

    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        try {
            log.info("Starting memory load generator");
            dockerSpy = new DockerSpy();
            log.info("Total memory: " + dockerSpy.getTotalMemoryMB());
            log.info("Used memory: " + dockerSpy.getUsedMemoryMB());
            log.info("Available memory: " + dockerSpy.getFreeMemoryMB());
            log.info("Max available memory: " + dockerSpy.getMaxMemoryMB());
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
            worker.start();
            log.info("Memory load generator started");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Memory load generator failed");
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
