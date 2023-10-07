//! Endpoints controller
/*!
  Class connecting http endpoints with thymeleaf views and application logic
*/
package com.pb.loadgen.controllers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.domains.LoadType;
import com.pb.loadgen.loadcontrollers.CpuLoadController;
import com.pb.loadgen.loadcontrollers.HanoiLoadController;
import com.pb.loadgen.loadcontrollers.MemLoadController;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@SessionAttributes("loadInput")
@ComponentScan
public class HomeController {

    //! DockerSpy class instance
    /*!
      Gathering informations about application running in container
    */
    @Autowired
    private DockerSpy dockerSpy;
    //! LoadSpy class instance
    /*!
      Gathering informations about load running in application
    */
    @Autowired
    private LoadSpy loadSpy;
    //! Variable for storing name of the app container
    private String containerName;
    
    //! LoadInput generator
    @ModelAttribute
    public LoadInput loadInput () {
        return new LoadInput();
    }

    //! Root endpoint - application homepage
    /*!
      1) Add LoadInput to Model
      2) User chooses load type
    */
    @RequestMapping("/")
    public String home (@ModelAttribute LoadInput loadInput, Model model) {
        model.addAttribute(new LoadInput());
        log.info("Front - load picker");
        return "home";
    }
    
    //! User sets load parameters
    /*!
      1) Add informations from Docker Spy to model
      2) Set initial parameters values
      3) Show informations to user
    */
    @PostMapping("/loadDetails")
    public String loadDetails (@ModelAttribute LoadInput loadInput, Model model) {
        model.addAttribute("initHeap",dockerSpy.getInitHeapMemory());
        model.addAttribute("maxHeap", dockerSpy.getMaxHeapMemory());
        model.addAttribute("usedHeap",dockerSpy.getUsageHeapMemory());
        model.addAttribute("logicalProcessorNumber", dockerSpy.getLogicalProcessorNumber());
        loadInput.setThreadNumber(1);
        loadInput.setLoadPercentageHigh(1);
        loadInput.setLoadPercentageChangeStep(1);
        loadInput.setLoadPercentageChangeFrequencyS(1);
        loadInput.setMemoryLoadSizeMiBHigh(1);
        loadInput.setMemoryLoadSizeMiBChangeStep(1);
        loadInput.setMemoryLoadChangeFrequencyS(1);
        loadInput.setHanoiSize(1);
        loadInput.setHanoiForeground(false);
        log.info("Front - load details");
        return "load_details";
    }

    //! Start load generator
    /*!
      1) Add informations from Docker Spy to model
      2) Prepare and start load controller for load type chosen by user
      3) Add load to Load Spy map
    */
    @PostMapping("/start")
    public String start (@ModelAttribute LoadInput loadInput, Model model) throws InterruptedException, IOException {
        if (containerName == null) {
            containerName = dockerSpy.getContainerName();
            log.info("Found container name: " + containerName);
        }
        model.addAttribute("containerName", containerName);
        String uniqueID = UUID.randomUUID().toString();
        loadInput.setUniqueID(uniqueID);
        if (loadInput.getLoadType() == LoadType.MEM_STUBBORN_HOARDER || loadInput.getLoadType() == LoadType.MEM_INDECISIVE_HOARDER) {
            log.info("Front - preparing memory load");
            loadSpy.getLoad().put(uniqueID, new MemLoadController(loadInput));
        } else if (loadInput.getLoadType() == LoadType.CPU_STUBBORN_SALESMAN || loadInput.getLoadType() == LoadType.CPU_INDECISIVE_SALESMAN){
            log.info("Front - preparing CPU load");
            loadSpy.getLoad().put(uniqueID, new CpuLoadController(loadInput));
        } else {
            log.info("Front - preparing hanoi load");
            loadSpy.getLoad().put(uniqueID, new HanoiLoadController(loadInput));
        }
        log.info("Load " + uniqueID + " created");
        loadSpy.getLoad().get(uniqueID).generate();
        if (loadInput.getLoadType() == LoadType.HANOI_RESOLVER && loadInput.isHanoiForeground()) {
            model.addAttribute("elapsedTime", loadSpy.getLoad().get(uniqueID).getElapsedTime());
            log.info("Front - hanoi load stop");
            return "stop";
        } else
        return "start";
    }

    //! Stop load generator and remove load from LoadSpy map
    @GetMapping("/stop")
    public String stop (@ModelAttribute LoadInput loadInput, Model model) {
        log.info("Front - load generator stop:" + loadInput.getUniqueID());
        loadSpy.getLoad().get(loadInput.getUniqueID()).stopGenerating();
        loadSpy.getLoad().remove(loadInput.getUniqueID());
        return "stop";
    }
    
    //! Summary page of running load
    @GetMapping("/summary")
    public String summary (Model model) {
        log.info("Front - load summary");
        model.addAttribute("loadSummary", loadSpy.getLoad());
        model.addAttribute("threadsRunning", loadSpy.getThreads());
        model.addAttribute("threadsCount", loadSpy.countThreads());
        return "summary";
    }
    
    //! Delete running load and remove load from LoadSpy map
    @PostMapping("/delete")
    public String deleteLoad (@RequestParam String deleteid, Model model){
        log.info("Front - delete for " + deleteid);
        if (loadSpy.getLoad().get(deleteid) != null) {
            loadSpy.getLoad().get(deleteid).stopGenerating();
            loadSpy.getLoad().remove(deleteid);
            model.addAttribute("deleteid", deleteid);
        }
        return "delete";
    }
}
