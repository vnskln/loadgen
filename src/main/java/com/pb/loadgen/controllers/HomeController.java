package com.pb.loadgen.controllers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.domains.LoadType;
import com.pb.loadgen.loadcontrollers.CpuLoadController;
import com.pb.loadgen.loadcontrollers.HanoiLoadController;
import com.pb.loadgen.loadcontrollers.LoadController;
import com.pb.loadgen.loadcontrollers.MemLoadController;
import java.io.IOException;
import java.util.HashMap;
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

    HashMap<String, LoadController> load = new HashMap<String, LoadController>();
    @Autowired
    DockerSpy dockerSpy;
    @Autowired
    ThreadSpy threadSpy;
    String containerName;
    
    @ModelAttribute
    public LoadInput loadInput () {
        return new LoadInput();
    }

    public HomeController() throws Exception {
        
    }

    @RequestMapping("/")
    public String home (@ModelAttribute LoadInput loadInput, Model model) {
        model.addAttribute(new LoadInput());
        log.info("Front - load picker");
        return "home";
    }
    
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
            load.put(uniqueID, new MemLoadController(loadInput));
        } else if (loadInput.getLoadType() == LoadType.CPU_STUBBORN_SALESMAN || loadInput.getLoadType() == LoadType.CPU_INDECISIVE_SALESMAN){
            log.info("Front - preparing CPU load");
            load.put(uniqueID, new CpuLoadController(loadInput));
        } else {
            log.info("Front - preparing hanoi load");
            load.put(uniqueID, new HanoiLoadController(loadInput, load));
        }
        log.info("Load " + uniqueID + " created");
        load.get(uniqueID).generate();
        if (loadInput.getLoadType() == LoadType.HANOI_RESOLVER && loadInput.isHanoiForeground()) {
            model.addAttribute("elapsedTime", load.get(uniqueID).getElapsedTime());
            log.info("Front - hanoi load stop");
            return "stop";
        } else
        return "start";
    }

    @GetMapping("/stop")
    public String stop (@ModelAttribute LoadInput loadInput, Model model) {
        log.info("Front - load generator stop:" + loadInput.getUniqueID());
        load.get(loadInput.getUniqueID()).stopGenerating();
        load.remove(loadInput.getUniqueID());
        return "stop";
    }
    
    @GetMapping("/summary")
    public String summary (Model model) {
        log.info("Front - load summary");
        model.addAttribute("loadSummary", load);
        model.addAttribute("threadsRunning", threadSpy.getThreads());
        model.addAttribute("threadsCount", threadSpy.countThreads());
        return "summary";
    }
    
    @PostMapping("/delete")
    public String deleteLoad (@RequestParam String deleteid, Model model){
        log.info("Front - delete for " + deleteid);
        load.get(deleteid).stopGenerating();
        load.remove(deleteid);
        model.addAttribute("deleteid", deleteid);
        return "delete";
    }
}
