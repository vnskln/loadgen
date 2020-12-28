package com.pb.loadgen.loadgenerators;

public interface LoadGenerator {
    public void start() throws InterruptedException;
    public void stop();
}
