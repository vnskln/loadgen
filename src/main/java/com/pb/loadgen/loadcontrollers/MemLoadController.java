package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;

public class MemLoadController implements LoadController {

    LoadInput loadInput;
    byte[] loadArray;

    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    @Override
    public void generate() {
        System.out.println("Starting load generator");
        loadArray = new byte [1000*1000*loadInput.getLoadPercentage()];
        System.out.println("Load generator started");
    }

    @Override
    public void stopGenerating() {
        loadArray = null;
        System.gc();
        System.runFinalization();
        System.out.println("Load generator stopped");
    }
}
