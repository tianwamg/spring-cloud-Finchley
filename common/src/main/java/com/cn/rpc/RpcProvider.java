package com.cn.rpc;

public class RpcProvider {

    public static void main(String args[]) throws Exception {
        helloService service = new helloServiceImpl();
        RpcFramework.export(service,helloService.class,9000);
    }
}
