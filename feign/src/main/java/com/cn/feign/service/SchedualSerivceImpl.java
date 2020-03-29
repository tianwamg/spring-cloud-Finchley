package com.cn.feign.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualSerivceImpl implements SchedualServiceHi {

    @Override
    public String sayHiFromClient(String name) {
        return "hi"+name+",sorry";
    }
}
