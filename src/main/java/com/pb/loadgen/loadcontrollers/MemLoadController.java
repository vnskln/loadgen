//! Memory load controller
/*!
  Class for creating and controlling memory load
*/
package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.controllers.DockerSpy;
import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Hoarder;
import com.pb.loadgen.loadgenerators.IndecisiveHoarder;
import com.pb.loadgen.loadgenerators.StubbornHoarder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemLoadController implements LoadController {

    //! LoadInput class instance
    /*!
      Gathering load parameters given by user
    */
    private final LoadInput loadInput;
    //! Memory load generator instance
    private Hoarder hoarder;
    //! Memory load generator thread
    private Thread worker;
    //! DockerSpy instance for gathering informations
    private DockerSpy dockerSpy;
    //! Time counter variable
    private long startTime = 0;
    //! Time counter variable
    private long stopTime = 0;
    //! Time counter variable
    private long elapsedTime = 0;

    //! Constructor
    public MemLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
    }

    //! Start generator
    /*!
      1) Choose memory load generator implementation
      2) Prepare and start thread with load generator
      3) Count operation time
    */
    @Override
    public void generate() {
        try {
            log.info("Starting memory load generator: " + loadInput.getUniqueID());
            startTime = System.currentTimeMillis();
            switch (loadInput.getLoadType()) {
                case MEM_STUBBORN_HOARDER:
                    hoarder = new StubbornHoarder(loadInput.getMemoryLoadSizeMiB());
                    break;
                case MEM_INDECISIVE_HOARDER:
                    hoarder = new IndecisiveHoarder(loadInput.getMemoryLoadSizeMiB(), loadInput.getMemoryLoadSizeMiBHigh(),
                    loadInput.getMemoryLoadSizeMiBChangeStep(), loadInput.getMemoryLoadChangeFrequencyS());
                    break;
            }
            worker = new Thread(hoarder);
            worker.setName("loadgt//" + loadInput.getUniqueID() + "//0");
            worker.start();
            log.info("Memory load generator started: " + loadInput.getUniqueID());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Memory load generator failed: " + loadInput.getUniqueID());
        }
    }

    //! Stop generator
    @Override
    public void stopGenerating() {
        try {
            worker.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hoarder.doStop();
        stopTime = System.currentTimeMillis();
        elapsedTime = (stopTime - startTime)/1000;
        log.info("Memory load generator stopped: " + loadInput.getUniqueID());
    }
    
    //! Get load details
    @Override
    public String getDetails() {
        String details = loadInput.getLoadType().toString() + ", " + loadInput.getMemoryLoadSizeMiB();
        if (loadInput.getLoadType().toString().equals("MEM_INDECISIVE_HOARDER")) {
            details += " - " + loadInput.getMemoryLoadSizeMiBHigh();
            details += ", step " + loadInput.getMemoryLoadSizeMiBChangeStep();
            details += ", freq " + loadInput.getMemoryLoadChangeFrequencyS();
        }
        return details;
    }
    
    //! Get load generation elapsed time 
    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }
}
