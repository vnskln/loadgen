package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.HanoiResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HanoiLoadController implements LoadController {

    private HanoiResolver hanoiResolver;
    private long elapsedTime = 0;
    
    public HanoiLoadController (LoadInput loadInput) {
        this.hanoiResolver = new HanoiResolver(loadInput.getHanoiSize());
    }
    
    @Override
    public void generate() throws InterruptedException {
        log.info("Hanoi resolver starting");
        long startTime = System.currentTimeMillis();
        hanoiResolver.run();
        log.info("Hanoi resolver finished");
        long stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime)/1000;
        log.info("Time elapsed: " + elapsedTime + " seconds");
        stopGenerating();
    }
    
    public long getElapsedTime () {
        return elapsedTime;
    }

    @Override
    public void stopGenerating() {
        log.info("Clearing memory");
        System.gc();
        System.runFinalization();
    }
    
}
