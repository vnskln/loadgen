package com.pb.loadgen.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DockerSpy {
    
    private final boolean isThisWindows  = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
    private final Runtime runtime = Runtime.getRuntime();
    
    protected String getContainerName () throws InterruptedException, IOException {
        
        String output = "";
        if (!isThisWindows) {
            String [] cmd = {
                "/bin/sh",
                "-c",
                "cat /proc/self/cgroup | grep name | cut -d '/' -f3"
            };
            Process pr = runtime.exec(cmd);
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line=buf.readLine())!=null) {
                System.out.println(line);
                output+=line;
            }
        }
        return output;
    }
    
    public long getInitHeapMemory () {
        return memBean.getHeapMemoryUsage().getInit()/1024/1024;
    }
    
    public long getMaxHeapMemory () {
        return memBean.getHeapMemoryUsage().getMax()/1024/1024;
    }
    
    public long getUsageHeapMemory () {
        return memBean.getHeapMemoryUsage().getUsed()/1024/1024;
    }
    
    public int getLogicalProcessorNumber() {
        return runtime.availableProcessors();
    }
}