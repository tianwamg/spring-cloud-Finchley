package com.cn.ribbon.controller;

import com.cn.ribbon.service.helloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @Autowired
    helloService helloService;

    @RequestMapping(value = "/hello")
    public ResponseEntity<String> hello(String name){
        return helloService.hiService(name);
    }
}
