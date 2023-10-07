//! Hanoi load controller
/*!
  Class for creating and controlling hanoi load
*/
package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.HanoiResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiLoadController implements LoadController {

    //! Hanoi load generator instance
    private HanoiResolver hanoiResolver;
    //! Hanoi load generator parameter
    private final int hanoiSize;
    //! Hanoi load generator id
    private final String uniqueID;
    //! Hanoi load generator thread
    private Thread worker;
    //! Should load generator run in foreground?
    private final boolean inForeground;
    //! Did hanoi calculations are finished?
    private boolean finished;
    //! Time counter variable
    private long elapsedTime = 0;
    
    //! Constructor
    public HanoiLoadController (LoadInput loadInput) {
        this.hanoiSize = loadInput.getHanoiSize();
        this.hanoiResolver = new HanoiResolver(hanoiSize, this);
        this.uniqueID = loadInput.getUniqueID();
        this.inForeground = loadInput.isHanoiForeground();
    }
    
    //! Start generator
    /*!
      1) Prepare and start thread with load generator
      2) Count operation time
    */
    @Override
    public void generate() throws InterruptedException {
        worker = new Thread(hanoiResolver);
        worker.setName("loadgt//" + uniqueID + "//0");
        log.info("Hanoi resolver starting: " + uniqueID);
        long startTime = System.currentTimeMillis();
        worker.start();
        if (inForeground) {
            worker.join();
        }
        log.info("Hanoi resolver finished: " + uniqueID);
        long stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime)/1000;
        log.info("Hanoi resolver: " + uniqueID + " - time elapsed " + elapsedTime + " seconds");
        if (inForeground) {
            stopGenerating();
        }
    }
    
    //! Did generator finished calculations?
    public void generatorFinished () {
        this.finished = true;
        log.info("Hanoi load generator finished operations");
    }
    
    //! Getter
    @Override
    public long getElapsedTime () {
        return elapsedTime;
    }
    
    //! Get load details
    @Override
    public String getDetails() {
        String details = "HANOI_RESOLVER, size " + hanoiSize + ", finished " + finished;
        return details;
    }

    @Override
    public void stopGenerating() {
        try {
            worker.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            worker.stop();
        } catch (Exception e) {
            log.info("Hanoi load generator stopped: " + uniqueID);
        }
        System.gc();
        System.runFinalization();
    }
    
}
