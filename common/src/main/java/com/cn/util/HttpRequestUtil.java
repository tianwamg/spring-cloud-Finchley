package com.cn.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpRequestUtil {

    public static String httpGet(){
        //TODO
        return "";
    }

    /**
     * post请求formdata格式
     * @param url
     * @param params
     * @return
     */
    public static String httpPostFormdata(String url, String jsonStr){
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setConnectionRequestTimeout(60000).setSocketTimeout(60000).build();
            httpPost.setConfig(requestConfig);
            //设置header
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            //组织请求参数
            /*List<NameValuePair> list = new ArrayList<>();
            if(params != null && params.size() >0){
                Set<String> keySet = params.keySet();
                for(String key:keySet){
                    list.add(new BasicNameValuePair(key,params.get(key)));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));*/
            httpPost.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
            response = closeableHttpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder stringBuilder = new StringBuilder("");
            String line="";
            String NL = System.getProperty("line.separator");
            while ((line=in.readLine()) != null){
                stringBuilder.append(line+NL);
            }
            in.close();
            result = stringBuilder.toString();
        }catch (Exception e){
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
}