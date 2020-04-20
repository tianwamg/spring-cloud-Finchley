package com.cn.rpc;

public class helloServiceImpl implements helloService {

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public String hello(String name) {
        return "hello "+name;
    }
}
