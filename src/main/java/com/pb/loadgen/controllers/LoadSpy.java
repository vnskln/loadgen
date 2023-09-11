package com.pb.loadgen.controllers;

import com.pb.loadgen.loadcontrollers.LoadController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoadSpy {
    
    private HashMap<String, LoadController> load = new HashMap<>();

    public HashMap<String, LoadController> getLoad() {
        return load;
    }

    public void setLoad(HashMap<String, LoadController> load) {
        this.load = load;
    }
    
    protected ArrayList<String> getThreads () {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        ArrayList<String> threads = new ArrayList<>(); 
        for(Thread thread : threadSet) {
            if(thread.getName().startsWith("loadgt")){
                threads.add(thread.getName());
            }
        }
        return threads;
    }
    
    protected HashMap<String,Integer> countThreads () {
        HashMap<String,Integer> threadCount = new HashMap<>(); 
        log.info("Getting threads list");
        ArrayList<String> threads = this.getThreads();
        log.info("Counting threads");
        for (String thread : threads) {
            String threadUnique = thread.split("//")[1];
            threadCount.putIfAbsent(threadUnique, 0);
            threadCount.put(threadUnique, threadCount.get(threadUnique)+1);
        }
        return threadCount;
    }    
}
