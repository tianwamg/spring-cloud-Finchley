package com.config.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cc")
public class HelloController {

    @Value("${foo}")
    public String foo;

    @RequestMapping("/hello")
    public String hello(){
        return foo;
    }
}
