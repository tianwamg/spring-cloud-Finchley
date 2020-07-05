package com.hystrix.hystrixdashboard.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hb")
public class HelloController {

    @Value("${server.port}")
    public Integer port;

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/hello")
    public String hello(){
        return "hystrix-dashboard: "+port;
    }

    public String error(){
        return "error";
    }
}
