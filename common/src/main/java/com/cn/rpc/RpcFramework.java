package com.cn.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcFramework {

    public static void export(Object service,Class interfaceClass,int port)throws Exception{
        if(service == null){
            throw new IllegalArgumentException("service instance == null");
        }
        if(port<=0 || port>65535){
            throw new IllegalAccessException("invalid port "+port);
        }
        System.out.print("Export service "+service.getClass().getName()+" on port "+port);
        ServerSocket serverSocket = new ServerSocket(port);
        for (;;){
            final Socket socket = serverSocket.accept();
            try{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            try{
                                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                                try{
                                    //String interfaceName = inputStream.readUTF();
                                    String methodName = inputStream.readUTF();
                                    Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();
                                    Object[] arguments = (Object[]) inputStream.readObject();
                                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                                    try{
                                        /*if(!interfaceName.equals(interfaceClass.getName())){
                                            throw new IllegalAccessException("Interface wrong,export:"+interfaceClass
                                            +"refer :"+interfaceName);
                                        }*/
                                        Method method = service.getClass().getMethod(methodName,parameterTypes);
                                        Object result = method.invoke(service,arguments);
                                        outputStream.writeObject(result);
                                    }catch (Throwable t){
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

    public static <T> T refer(Class<T> interfaceClass,String host,int port)throws Exception{
        if (interfaceClass == null) {
            throw new IllegalAccessException("Interface class == null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalAccessException(interfaceClass.getName() + " must be interface");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalAccessException("host == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalAccessException("Invalid port " + port);
        }
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // TODO Auto-generated method stub
                        Socket socket = new Socket(host, port);
                        try {
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                            try {
                                output.writeUTF(method.getName());
                                output.writeObject(method.getParameterTypes());
                                output.writeObject(args);

                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                                try {
                                    Object result = input.readObject();
                                    if (result instanceof Throwable) {
                                        throw (Throwable) result;
                                    }
                                    return result;
                                } finally {
                                    input.close();
                                }

                            } finally {
                                output.close();
                            }

                        } finally {
                            socket.close();
                        }
                    }
                });
    }
}
