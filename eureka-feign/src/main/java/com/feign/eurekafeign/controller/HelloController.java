package com.feign.eurekafeign.controller;

import com.feign.eurekafeign.feignFacade.IHelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign-h")
public class HelloController {

    @Autowired
    IHelloFacade iHelloFacade;

    @GetMapping("/hello")
    public String hello(){
        return iHelloFacade.hello();
    }
}
