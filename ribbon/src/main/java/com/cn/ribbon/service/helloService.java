package com.cn.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class helloService {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<String> hiService(String name){
        return restTemplate.getForEntity("http://service-client/hi?name="+name,String.class);
    }

    public String hiError(String name){
        return "hi"+name+",sorry";
    }
}
