package com.cn.rpcFramework;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务端，发布服务，接收客户端请求
 */
public class RpcServer {

    /**
     * 启动rpc服务
     * @param port
     * @param clazz
     */
    public void start(int port,String clazz){
        ServerSocket server = null;
        try{
            //创建socket连接
            server  = new ServerSocket(port);
            //获取所有的rpc服务类，即发布服务
            Map<String,Object> services = getServices(clazz);
            //创建线程池
            Executor executor = new ThreadPoolExecutor(5,10,10,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
            while (true) {
                //获取客户端连接
                Socket client = server.accept();
                //查找并执行服务
                RpcServices rpcServices = new RpcServices(client,services);
                executor.execute(rpcServices);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server != null){
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 实例化所有的rpc服务类
     * @param clazz
     * @return
     */
    public Map<String,Object> getServices(String clazz){
        try {
            Map<String, Object> services = new HashMap<>();
            //获取所有的服务类
            String[] clazzes = clazz.split(",");
            List<Class<?>> classes = new ArrayList<>();
            for (String s : clazzes) {
                List<Class<?>> classList = getClasses(s);
                classes.addAll(classList);
            }
            //循环实例化
            for(Class<?> cla :classes){
                Object obj = cla.newInstance();
                services.put(cla.getAnnotation(com.cn.rpcFramework.RpcService.class).value().getName(),obj);
            }
            return services;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取包下所有@RpcService注解的类
     * @param packageName
     * @return
     */
    public List<Class<?>> getClasses(String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = null;
        try{
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if(cld == null){
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = packageName.replace(',','/');
            URL resource = cld.getResource(path);
            if(resource == null){
                throw new ClassNotFoundException("No resource for "+path);
            }
            directory = new File(resource.getFile());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(directory.exists()){
            String[] files = directory.list();
            File[] fileList = directory.listFiles();
            for(int i =0;fileList != null && i <fileList.length;i++){
                File file = fileList[i];
                //判断是否是Class文件
                if(file.isFile() && file.getName().endsWith(".class")){
                    Class<?> clazz = Class.forName(packageName+"."+files[i].substring(0,files[i].length()-6));
                    if(clazz.getAnnotation(com.cn.rpcFramework.RpcService.class) !=null){
                        classes.add(clazz);
                    }
                }else if(file.isDirectory()){//如果是目录，递归查找
                    List<Class<?>> result = getClasses(packageName+"."+file.getName());
                    if(result != null && result.size()>0){
                        classes.addAll(result);
                    }
                }
            }
        }else {
            throw new ClassNotFoundException(packageName+"does not appear to be a valid package");
        }
        return classes;
    }
}
