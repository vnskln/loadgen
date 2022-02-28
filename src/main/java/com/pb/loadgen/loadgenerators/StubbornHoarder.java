package com.pb.loadgen.loadgenerators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubbornHoarder extends Hoarder {
    
    public StubbornHoarder(int memoryLoadSizeMiB) {
        super(memoryLoadSizeMiB);
    }

    @Override
    public void run() {
        loadArray = new byte [1048576*memoryLoadSizeMiB];
        log.info("Stubborn load array! Size: " + memoryLoadSizeMiB + " MB");
        while(keepRunning) {      
            try {
                    Thread.sleep(10000);
                    log.info("Hoarding: " + + memoryLoadSizeMiB + " MB");
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    }
}
