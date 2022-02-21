package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornHoarder extends Hoarder {
    
    public StubbornHoarder(int loadSizeMegaBytes) {
        super(loadSizeMegaBytes);
    }

    @Override
    public void run() {
        try {
            loadArray = new byte [1048576*loadSizeMegaBytes];
        } catch (Exception e) {
            log.info("Hoarding memory failed");
            e.printStackTrace();
            keepRunning = false;
        }
        log.info("Stubborn load array! Size: " + loadSizeMegaBytes + " MB");
        while(keepRunning) {      
            try {
                    Thread.sleep(10000);
                    log.info("Hoarding: " + + loadSizeMegaBytes + " MB");
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }
}
