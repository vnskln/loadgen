package com.pb.loadgen.controllers;

import com.pb.loadgen.loadgenerators.CpuLoadGeneratorSingleCore;
import com.pb.loadgen.loadgenerators.LoadGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    LoadGenerator loadGenerator = new CpuLoadGeneratorSingleCore(65);

    public HomeController() throws Exception {
    }

    @GetMapping("/")
    public String home () {
        return "home";
    }

    @GetMapping("/start")
    public String start () throws InterruptedException {
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
