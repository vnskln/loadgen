package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.controllers.DockerSpy;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Hoarder implements Runnable{
    
    protected volatile boolean keepRunning = true;
    protected int memoryLoadSizeMiB;
    byte [][] loadArray;
    protected DockerSpy dockerSpy = new DockerSpy();
    Random randomGenerator = new Random();

    public Hoarder(int memoryLoadSizeMiB) {
        this.memoryLoadSizeMiB = memoryLoadSizeMiB;
    }
    
    protected void clearLoadArray () {
        log.info("Clearing load array");
        for (int i=0; i<loadArray.length;i++) {
            loadArray[i]=null;
        }
    }
    
    protected void randomizeLoadArray () {
        log.info("Randomizing load array");
        for (int i=0; i<loadArray.length;i++) {
            randomGenerator.nextBytes(loadArray[i]);
        }
    }
    
    public abstract void run();
 
    public void doStop() {
        clearLoadArray();
        loadArray = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
        log.info("Finishing load cycle");
    }
}
