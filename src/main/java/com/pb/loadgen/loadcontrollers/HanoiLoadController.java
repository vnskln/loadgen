package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.HanoiResolver;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiLoadController implements LoadController {

    private HanoiResolver hanoiResolver;
    private int hanoiSize;
    private String uniqueID;
    private Thread worker;
    private boolean inForeground;
    private HashMap<String, LoadController> load;
    private long elapsedTime = 0;
    
    public HanoiLoadController (LoadInput loadInput, HashMap<String, LoadController> load) {
        this.hanoiSize = loadInput.getHanoiSize();
        this.hanoiResolver = new HanoiResolver(hanoiSize, this);
        this.uniqueID = loadInput.getUniqueID();
        this.inForeground = loadInput.isHanoiForeground();
        this.load = load;
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
    
    public long getElapsedTime () {
        return elapsedTime;
    }
    
    public void generatorFinished () {
        load.remove(uniqueID);
        log.info("Hanoi load generator finished operations");
    }
    
    @Override
    public String getDetails() {
        String details = "HANOI_RESOLVER, size: " + hanoiSize;
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
