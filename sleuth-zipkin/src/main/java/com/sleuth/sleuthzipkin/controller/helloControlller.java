package com.sleuth.sleuthzipkin.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin")
public class helloControlller {

    @GetMapping("/hello")
    public String hello(){
        return "hello zipkin";
    }
}
