package com.pb.loadgen.loadgenerators;

public class StubbornHoarder extends Hoarder {

    byte [] loadArray;
    
    public StubbornHoarder(int loadSizeBytese) {
        super(loadSizeBytese);
    }

    @Override
    public void run() {
        loadArray = new byte [1048576*loadSizeMegaBytes];
        System.out.println("Load array created");
    }

    @Override
    public void doStop() {
        loadArray = null;
        System.gc();
        System.runFinalization();
        closeThread();
    }
}
