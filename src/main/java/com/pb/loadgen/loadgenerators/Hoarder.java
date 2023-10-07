//! Abstract class for memory load generator
package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.controllers.DockerSpy;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Hoarder implements Runnable{
    
    //! Thread stop parameter
    protected volatile boolean keepRunning = true;
    //! Load parameter - used memory size in MiB
    protected int memoryLoadSizeMiB;
    //! Load array kept in RAM
    byte [][] loadArray;
    //! DockerSpy instance
    protected DockerSpy dockerSpy = new DockerSpy();
    Random randomGenerator = new Random();

    //! Constructor
    public Hoarder(int memoryLoadSizeMiB) {
        this.memoryLoadSizeMiB = memoryLoadSizeMiB;
    }
    
    //! Clear all objects in load array
    protected void clearLoadArray () {
        log.info("Clearing load array");
        for (int i=0; i<loadArray.length;i++) {
            loadArray[i]=null;
        }
    }
    
    //! Put random bytes in load array
    protected void randomizeLoadArray () {
        log.info("Randomizing load array");
        for (int i=0; i<loadArray.length;i++) {
            randomGenerator.nextBytes(loadArray[i]);
        }
    }
    
    //! Abstract run method
    public abstract void run();
 
    //! Method for stopping load generator running in thread
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
