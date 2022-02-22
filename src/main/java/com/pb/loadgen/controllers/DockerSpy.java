package com.pb.loadgen.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DockerSpy {
    
    boolean isThisWindows  = System.getProperty("os.name").toLowerCase().startsWith("windows");
    
    Runtime runtime = Runtime.getRuntime();
    
    public String getContainerName () throws InterruptedException, IOException {
        
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
    
    public long getTotalMemoryMB () {
        return runtime.totalMemory()/1024/1024;
    }
    
    public long getFreeMemoryMB () {
        return runtime.freeMemory()/1024/1024;
    }
    
    public long getUsedMemoryMB () {
        return (this.getTotalMemoryMB() - this.getFreeMemoryMB());
    }
}
