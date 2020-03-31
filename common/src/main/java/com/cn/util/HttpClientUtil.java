package com.cn.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 单例模式下http请求工具
 */
public class HttpClientUtil {

    private static volatile HttpClientUtil httpClientUtil = null;

    private static CloseableHttpClient closeableHttpClient;

    private HttpClientUtil(){
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(100);//设置最大并发数
        manager.setDefaultMaxPerRoute(20);//单个路由最大并发数
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        manager.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000)
                .setConnectTimeout(60000)//读取与链接超时时间
                .setConnectionRequestTimeout(60000)//读取实例超时时间
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)//获取链接的最大等待时间，0将阻塞当前线程，直到有可用的链接为止
                .build();
        closeableHttpClient = HttpClients.custom().setConnectionManager(manager)
                .setDefaultRequestConfig(requestConfig).build();

    }

    /**
     * 双重锁校验创建实例对象
     * @return
     */
    public static HttpClientUtil getInstance(){
        if(httpClientUtil == null){
            synchronized (HttpClientUtil.class){
                if(httpClientUtil == null){
                    httpClientUtil = new HttpClientUtil();
                }
            }
        }
        return httpClientUtil;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public String invokeGet(String url){
        CloseableHttpResponse response = null;
        String result = "";
        try{
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type","application/json;charset=utf-8");
            httpGet.setHeader("Accept","application/json");
            response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, HTTP.UTF_8);
            EntityUtils.consume(entity);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * JSON格式post请求
     * @param url
     * @param params
     * @return
     */
    public String invokePost(String url,String params){
        //TODO
        CloseableHttpResponse response = null;
        String result = null;
        try{
            HttpPost httpPost = new HttpPost(params);
            httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            httpPost.setHeader("Accept","application/json");
            response = closeableHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,"UTF-8");
            EntityUtils.consume(entity);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


}
