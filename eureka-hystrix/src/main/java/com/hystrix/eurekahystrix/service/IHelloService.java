package com.hystrix.eurekahystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IHelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String hello(){
        return restTemplate.getForObject("http://EUREKA-PROVIDER/hc/hello",String.class);
    }

    public String error(){
        return "eureke-provider error!";
    }
}
