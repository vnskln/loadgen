package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Hoarder;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import com.pb.loadgen.loadgenerators.StubbornHoarder;

public class MemLoadController implements LoadController {

    LoadInput loadInput;
    Hoarder hoarder;
    Thread worker;           

    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        System.out.println("Starting hoard procedure");
        switch (loadInput.getLoadType()) {
            case MEM_COLLECTOR:
                hoarder = new StubbornHoarder(loadInput.getMemoryLoadSizeMegaBytes());
                break;
        }
        worker = new Thread(hoarder);
        worker.start();
        System.out.println("Memory load generator started");
    }

    @Override
    public void stopGenerating() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hoarder.doStop();
        System.out.println("Memory generator stopped");
    }
}
