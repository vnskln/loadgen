package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.loadcontrollers.HanoiLoadController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiResolver implements Runnable {
    
    protected int discNumber;
    protected HanoiLoadController hanoiLoadController;

    public HanoiResolver(int discNumber, HanoiLoadController hanoiLoadController) {
        this.discNumber = discNumber;
        this.hanoiLoadController = hanoiLoadController;
        
    }
    
    private void hanoiTower (int n, String source, String dest, String helper) {
        log.info("Started hanoi for n = " + n);
        if (n == 1) {
            log.info("Take disk 1 from " + source + " to " + dest);
            return;
        }
        hanoiTower(n-1, source, helper, dest);
        log.info("Take disk " + n + " from " + source + " to " + dest);
        hanoiTower(n-1, helper, dest, source);
    }

    public void run() {
        hanoiTower(discNumber, "A", "C", "B");
        hanoiLoadController.generatorFinished();
    }
    
}
