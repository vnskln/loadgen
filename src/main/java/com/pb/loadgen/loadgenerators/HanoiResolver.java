//! Hanoi load generator
/*!
  Creating list of commands to resolve hanoi tower problem with given difficulty
*/
package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.loadcontrollers.HanoiLoadController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiResolver implements Runnable {
    
    //! Number of discs in tower to move
    protected int discNumber;
    //! HanoiLoadController instance
    protected HanoiLoadController hanoiLoadController;

    //! Constructor
    public HanoiResolver(int discNumber, HanoiLoadController hanoiLoadController) {
        this.discNumber = discNumber;
        this.hanoiLoadController = hanoiLoadController;
        
    }
    
    //! Generator - recursive implementation
    private void hanoiTower (int n, String source, String dest, String helper) {
        log.debug("Started hanoi for n = " + n);
        if (n == 1) {
            log.debug("Take disk 1 from " + source + " to " + dest);
            return;
        }
        hanoiTower(n-1, source, helper, dest);
        log.debug("Take disk " + n + " from " + source + " to " + dest);
        hanoiTower(n-1, helper, dest, source);
    }

    //! Run generator in thread
    @Override
    public void run() {
        hanoiTower(discNumber, "A", "C", "B");
        hanoiLoadController.generatorFinished();
    }
    
}
