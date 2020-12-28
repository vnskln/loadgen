package com.pb.loadgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class LoadgenApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadgenApplication.class, args);
    }

}
