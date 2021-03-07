package com.pb.loadgen.controllers;

import com.pb.loadgen.domains.LoadInput;
import com.pb.loadgen.loadgenerators.CpuLoadGeneratorSingleCore;
import com.pb.loadgen.loadgenerators.LoadGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    LoadGenerator loadGenerator;

    public HomeController() throws Exception {
    }

    @GetMapping("/")
    public String home (Model model) {
        model.addAttribute("loadInput", new LoadInput());
        return "home";
    }

    @PostMapping("/start")
    public String start (LoadInput loadInput) throws InterruptedException {
        loadGenerator = new CpuLoadGeneratorSingleCore(loadInput);
        loadGenerator.generate();
        return "start";
    }

    @GetMapping("/stop")
    public String stop () {
        loadGenerator.generateStop();
        System.out.println("Load generator stopped");
        return "stop";
    }
}
