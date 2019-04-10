package com.example.demoloadtestingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    private Environment env;

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/port")
    public ResponseEntity<?> getPort() {
        return ResponseEntity.ok("The running port for " + env.getRequiredProperty("spring.application.name") + " is " + env.getRequiredProperty("server.port"));
    }
}
