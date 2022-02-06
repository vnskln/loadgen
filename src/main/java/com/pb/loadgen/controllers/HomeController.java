package com.pb.loadgen.controllers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.domains.LoadType;
import com.pb.loadgen.loadcontrollers.CpuLoadController;
import com.pb.loadgen.loadcontrollers.LoadController;
import com.pb.loadgen.loadcontrollers.MemLoadController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@SessionAttributes("loadInput")
public class HomeController {

    LoadController loadController;
    
    @ModelAttribute
    public LoadInput loadInput () {
        return new LoadInput();
    }

    public HomeController() throws Exception {
    }

    @GetMapping("/")
    public String home (@ModelAttribute LoadInput loadInput) {
        log.info("Front - load picker");
        return "home";
    }
    
    @PostMapping("/loadDetails")
    public String loadDetails (@ModelAttribute LoadInput loadInput) {
        log.info("Front - load details");
        return "load_details";
    }

    @PostMapping("/start")
    public String start (@ModelAttribute LoadInput loadInput) throws InterruptedException {
        if (loadInput.getLoadType() == LoadType.MEM_COLLECTOR) {
            log.info("Front - preparing memory load");
            loadController = new MemLoadController(loadInput);
        } else {
            log.info("Front - preparing CPU load");
            loadController = new CpuLoadController(loadInput);
        }
        loadController.generate();
        return "start";
    }

    @GetMapping("/stop")
    public String stop () {
        log.info("Front - load generator stop");
        loadController.stopGenerating();
        return "stop";
    }
}
