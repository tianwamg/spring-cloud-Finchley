package com.cn.eurekaprovider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hc")
public class helloController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/hello")
    public String hello(){
        return "provider: "+port;
    }
}
