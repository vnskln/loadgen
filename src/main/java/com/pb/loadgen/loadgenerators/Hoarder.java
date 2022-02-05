package com.pb.loadgen.loadgenerators;

public abstract class Hoarder implements Runnable{
    
    protected volatile boolean keepRunning = true;
    protected int loadSizeMegaBytes;

    public Hoarder(int loadSizeBytese) {
        this.loadSizeMegaBytes = loadSizeMegaBytes;
    }

    public abstract void run();

    public abstract void doStop();
    
    public void closeThread() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
    }
}
