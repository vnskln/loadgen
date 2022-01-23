package com.pb.loadgen.controllers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.domains.LoadType;
import com.pb.loadgen.loadcontrollers.CpuLoadController;
import com.pb.loadgen.loadcontrollers.LoadController;
import com.pb.loadgen.loadcontrollers.MemLoadController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    LoadController loadController;

    public HomeController() throws Exception {
    }

    @GetMapping("/")
    public String home (Model model) {
        model.addAttribute("loadInput", new LoadInput());
        return "home";
    }

    @PostMapping("/start")
    public String start (LoadInput loadInput) throws InterruptedException {
        if (loadInput.getLoadType() == LoadType.MEM_COLLECTOR) {
            loadController = new MemLoadController(loadInput);
        } else {
            loadController = new CpuLoadController(loadInput);
        }
        loadController.generate();
        return "start";
    }

    @GetMapping("/stop")
    public String stop () {
        loadController.stopGenerating();
        System.out.println("Load generator stopped");
        return "stop";
    }
}
