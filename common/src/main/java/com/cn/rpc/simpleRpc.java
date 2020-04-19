package com.cn.rpc;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class simpleRpc {

    public static void export(final Object service,int port)throws Exception{
        if(service == null){
            throw new IllegalArgumentException("service install null");
        }
        if(port <=0 || port >65535){
            throw new IllegalArgumentException("port invail :"+port);
        }
        System.out.println("Export service "+service.getClass().getName()+" on port "+port);
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            try{
                final Socket socket = serverSocket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            try {
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                                try{
                                    String methodName = inputStream.readUTF();//接口方法名
                                    Class<?> parameterType = (Class<?>) inputStream.readObject();//接口类型与参数
                                    Object[] arguments = (Object[]) inputStream.readObject();//具体参数
                                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());//获取回传output
                                    try{
                                      //通过Java反射，动态执行实现类service的方法
                                        Method method = service.getClass().getMethod(methodName,parameterType);
                                        Object result = method.invoke(service,arguments);
                                        //将调用结果回传给调用方
                                        outputStream.writeObject(result);
                                    } catch (Throwable t){
                                        outputStream.writeObject(t);
                                    }finally {
                                        outputStream.close();
                                    }
                                }finally {
                                    inputStream.close();
                                }
                            }finally {
                                socket.close();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static <T> T refer(final Class<T> interfaceClass,final String host ,final int port)throws Exception{
        if(interfaceClass == null){
            throw new IllegalArgumentException("interfaceClass class == null");
        }
        if(!interfaceClass.isInterface()){
            throw new IllegalArgumentException("The "+interfaceClass.getName()+"must be a interface");
        }
        if(host == null || host.length() == 0){
            throw new IllegalArgumentException("host == null");
        }
        if(port <=0 || port >65535){
            throw new IllegalArgumentException("Invalid port "+port);
        }
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket(host,port);
                        try{
                            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                            try{
                                outputStream.writeUTF(method.getName());
                                outputStream.writeObject(method.getParameterTypes());
                                outputStream.writeObject(args);
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                                try{
                                    Object result = inputStream.readObject();
                                    if(result instanceof Throwable){
                                        throw (Throwable)result;
                                    }
                                    return result;
                                }finally {
                                    inputStream.close();
                                }
                            }finally {
                                outputStream.close();
                            }
                        }finally {
                            socket.close();
                        }
                    }
                });
    }
}
