package com.cn.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-client",fallback = SchedualSerivceImpl.class)
public interface SchedualServiceHi {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String sayHiFromClient(@RequestParam(value = "name")String name);
}
