package com.pb.loadgen.loadcontrollers;

public interface LoadController {
    
    void generate() throws InterruptedException;
    void stopGenerating();
    long getElapsedTime();
    
}
