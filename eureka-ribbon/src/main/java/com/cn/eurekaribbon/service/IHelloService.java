package com.cn.eurekaribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IHelloService {

    @Autowired
    RestTemplate restTemplate;

    public String hello(){
        return restTemplate.getForObject("http://EUREKA-PROVIDER/hc/hello",String.class);
    }
}
