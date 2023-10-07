//! CPU load controller
/*!
  Class for creating and controlling cpu load
*/
package com.pb.loadgen.loadcontrollers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.Salesman;
import com.pb.loadgen.loadgenerators.StubbornSalesman;
import com.pb.loadgen.loadgenerators.IndecisiveSalesman;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CpuLoadController implements LoadController {

    //! LoadInput class instance
    /*!
      Gathering load parameters given by user
    */
    private final LoadInput loadInput;
    //! CPU load generator instance
    private Salesman salesman;
    //! CPU load generator thread
    private final Thread [] workers;
    //! Time counter variable
    private long startTime = 0;
    //! Time counter variable
    private long stopTime = 0;
    //! Time counter variable
    private long elapsedTime = 0;

    //! Constructor
    public CpuLoadController(LoadInput loadInput) {
        this.loadInput = loadInput;
        this.workers = new Thread [loadInput.getThreadNumber()];
    }
    
    //! Start generator
    /*!
      1) Choose CPU load generator implementation
      2) Prepare and start thread with load generator
      3) Count operation time
    */
    @Override
    public void generate() {
        log.info("Starting CPU load generator: " + loadInput.getUniqueID());
        startTime = System.currentTimeMillis();
        switch (loadInput.getLoadType()) {
            case CPU_STUBBORN_SALESMAN:
                salesman = new StubbornSalesman(loadInput.getLoadPercentage());
                break;
            case CPU_INDECISIVE_SALESMAN:
                salesman = new IndecisiveSalesman(loadInput.getLoadPercentage(), loadInput.getLoadPercentageHigh(), 
                        loadInput.getLoadPercentageChangeStep(), loadInput.getLoadPercentageChangeFrequencyS());
                break;
        }
        for (int i=0; i<loadInput.getThreadNumber(); i++) {
            log.info("CPU load thread " + i + " starting: " + loadInput.getUniqueID());
            workers[i] = new Thread(salesman);
            workers[i].setName("loadgt//" + loadInput.getUniqueID() + "//" + i);
            workers[i].start();
            log.info("CPU load thread " + i + " started: " + loadInput.getUniqueID());
        }
        
        log.info("CPU load generator started: " + loadInput.getUniqueID());
    }

    //! Stop generator
    @Override
    public void stopGenerating() {
        for (int i=0; i<loadInput.getThreadNumber(); i++) {
            try {
                workers[i].sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            salesman.doStop();
            stopTime = System.currentTimeMillis();
            elapsedTime = (stopTime - startTime)/1000;
            log.info("CPU load generator stopped: " + loadInput.getUniqueID());
        }
    }

    //! Get load details
    @Override
    public String getDetails() {
        String details = loadInput.getLoadType().toString() + ", " + loadInput.getLoadPercentage();
        if (loadInput.getLoadType().toString().equals("CPU_INDECISIVE_SALESMAN")) {
            details += " - " + loadInput.getLoadPercentageHigh();
            details += ", step " + loadInput.getLoadPercentageChangeStep();
            details += ", freq " + loadInput.getLoadPercentageChangeFrequencyS();
        }
        details += ", threads " + loadInput.getThreadNumber();
        
        return details;
    }

    //! Get load generation elapsed time 
    @Override
    public long getElapsedTime() {
        return elapsedTime;
    }
}
