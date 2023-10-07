//! Class for gathering container informations
/*!
  Class returns the name of app container and java memory parameters
*/

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
    
    //! Windows operating system flag
    /*! Cgroups are available only in Linux. We can't easily get container name in Windows. */
    private final boolean isThisWindows  = System.getProperty("os.name").toLowerCase().startsWith("windows");
    //! Java object for accessing jvm memory parameters
    private final MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
    //! Java object used to get number of logical processors
    private final Runtime runtime = Runtime.getRuntime();
    
    //! Get container name from Linux cgroups
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
    
    //! Get jvm init heap size
    public long getInitHeapMemory () {
        return memBean.getHeapMemoryUsage().getInit()/1024/1024;
    }
    
    //! Get jvm max heap size
    public long getMaxHeapMemory () {
        return memBean.getHeapMemoryUsage().getMax()/1024/1024;
    }
    
    //! Get jvm used heap size
    public long getUsageHeapMemory () {
        return memBean.getHeapMemoryUsage().getUsed()/1024/1024;
    }
    
    //! Get number of logical processors
    public int getLogicalProcessorNumber() {
        return runtime.availableProcessors();
    }
}