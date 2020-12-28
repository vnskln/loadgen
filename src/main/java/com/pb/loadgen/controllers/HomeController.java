package com.pb.loadgen.controllers;

import com.pb.loadgen.loadgenerators.CpuLoadGeneratorSingleCore;
import com.pb.loadgen.loadgenerators.LoadGenerator;
import com.pb.loadgen.loadgenerators.TestLoadGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    LoadGenerator loadGenerator = new CpuLoadGeneratorSingleCore(40);

    public HomeController() throws Exception {
    }

    @GetMapping("/")
    public String home () {
        return "home";
    }

    @GetMapping("/start")
    public String start () throws InterruptedException {
        System.out.println("Starting load generator");
        loadGenerator.start();
        System.out.println("Load generator started");
        return "start";
    }

    @GetMapping("/stop")
    public String stop () {
        loadGenerator.stop();
        System.out.println("Load generator stopped");
        return "stop";
    }
}
