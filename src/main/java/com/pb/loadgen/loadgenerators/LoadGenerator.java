package com.pb.loadgen.loadgenerators;

public interface LoadGenerator {
    void generate() throws InterruptedException;
    void generateStop();
}
