package com.pb.loadgen.loadgenerators;

public abstract class Hoarder implements Runnable{
    
    protected volatile boolean keepRunning = true;
    protected int loadSizeMegaBytes;
    byte [] loadArray;

    public Hoarder(int loadSizeMegaBytese) {
        this.loadSizeMegaBytes = loadSizeMegaBytese;
    }

    public abstract void run();
    
    public void doStop() {
        loadArray = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
    }
}
