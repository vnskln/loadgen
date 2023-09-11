package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.HanoiResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiLoadController implements LoadController {

    private HanoiResolver hanoiResolver;
    private final int hanoiSize;
    private final String uniqueID;
    private Thread worker;
    private final boolean inForeground;
    private boolean finished;
    private long elapsedTime = 0;
    
    public HanoiLoadController (LoadInput loadInput) {
        this.hanoiSize = loadInput.getHanoiSize();
        this.hanoiResolver = new HanoiResolver(hanoiSize, this);
        this.uniqueID = loadInput.getUniqueID();
        this.inForeground = loadInput.isHanoiForeground();
    }
    
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
    
    public void generatorFinished () {
        this.finished = true;
        log.info("Hanoi load generator finished operations");
    }
    
    public long getElapsedTime () {
        return elapsedTime;
    }
    
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
