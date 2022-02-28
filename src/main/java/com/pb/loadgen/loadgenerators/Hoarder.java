package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.controllers.DockerSpy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
public abstract class Hoarder implements Runnable{
    
    protected volatile boolean keepRunning = true;
    protected int memoryLoadSizeMiB;
    byte [] loadArray;
    protected DockerSpy dockerSpy = new DockerSpy();

    public Hoarder(int memoryLoadSizeMiB) {
        this.memoryLoadSizeMiB = memoryLoadSizeMiB;
    }

    public abstract void run();
    
    public void doStop() {
        loadArray = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.keepRunning = false;
        log.info("Finising load cycle");
    }
}
