package com.pb.loadgen.loadgenerators;

import com.pb.loadgen.loadgenerators.LoadGenerator;
import com.pb.loadgen.services.TimeCheck;

public class TestLoadGenerator implements LoadGenerator {

    TimeCheck timeCheck = new TimeCheck();
    Thread thread = new Thread(timeCheck);

    @Override
    public void generate() {
        thread.start();
    }

    @Override
    public void generateStop() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timeCheck.doStop();
    }
}
