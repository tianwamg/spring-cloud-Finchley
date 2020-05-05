package com.cn.rpcFramework;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class RpcServices implements Runnable {

    private Socket socket;
    private Map<String,Object> services;

    public RpcServices(Socket client,Map<String,Object> services){
        super();
        this.socket =client;
        this.services = services;
    }

    @Override
    public void run() {
        InputStream in = null;
        ObjectInputStream oin = null;
        OutputStream out = null;
        ObjectOutputStream oout = null;
        RpcResponse response = new RpcResponse();
        try{
            //获取流
            in =  socket.getInputStream();
            oin = new ObjectInputStream(in);
            out = socket.getOutputStream();
            oout = new ObjectOutputStream(out);
            //获取请求参数，强转参数类型
            Object param = oin.readObject();
            RpcRequest request = null;
            if(!(param instanceof RpcRequest)){
                response.setError(new Exception("参数错误"));
                oout.writeObject(response);
                oout.flush();
                return;
            }else {
                request = (RpcRequest) param;
            }
            //查找并执行服务方法
            Object service = services.get(request.getClass());
            Class<?> clazz = service.getClass();
            Method method = clazz.getMethod(request.getMethodName(),request .getParamTypes());
            Object result = method.invoke(service,request);
            response.setResult(result);
            oout.writeObject(response);
            oout.flush();
            //得到结果并返回
        }catch (Exception e){
            try {	//异常处理
                if(oout != null){
                    response.setError(e);
                    oout.writeObject(response);
                    oout.flush();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return;
        }finally {
            try {	// 关闭流
                if(in != null) in.close();
                if(oin != null) oin.close();
                if(out != null) out.close();
                if(oout != null) oout.close();
                if(socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
