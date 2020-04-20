package com.cn.rpc;

public class RpcConsumer {

    public static void main(String args[]) throws Exception {
        helloService service = RpcFramework.refer(helloService.class,"127.0.0.1",9000);
        String result = service.hello("ha ha ha");
        System.out.print(result);
    }
}
