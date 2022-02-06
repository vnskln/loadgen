package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornHoarder extends Hoarder {
    
    public StubbornHoarder(int loadSizeMegaBytes) {
        super(loadSizeMegaBytes);
    }

    @Override
    public void run() {
        loadArray = new byte [1048576*loadSizeMegaBytes];
        log.info("Stubborn load array! Size: " + loadSizeMegaBytes + " MB");
        while(keepRunning) {      
            try {
                    Thread.sleep(10000);
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }
}
