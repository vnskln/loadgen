package com.pb.loadgen.loadgenerators;

public class StubbornHoarder extends Hoarder {
    
    public StubbornHoarder(int loadSizeMegaBytes) {
        super(loadSizeMegaBytes);
    }

    @Override
    public void run() {
        loadArray = new byte [1048576*loadSizeMegaBytes];
        while(keepRunning) {      
            try {
                    Thread.sleep(10000);
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }
}
