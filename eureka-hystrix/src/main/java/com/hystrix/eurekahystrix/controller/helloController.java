package com.hystrix.eurekahystrix.controller;

import com.hystrix.eurekahystrix.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ribbon-h")
public class helloController {


    @Autowired
    IHelloService helloService;

    @GetMapping("/hello")
    public String hello(){
        return helloService.hello();
    }
}
