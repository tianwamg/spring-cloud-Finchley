package com.cn.encrypt;

import sun.misc.BASE64Encoder;

public class Base64 {

    /**
     * base64生成token
     * @return
     */
    public static String generatorBase64(String data){
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            String token = encoder.encode(data.getBytes()).replaceAll("\\r","").replaceAll("\\n","");
            return token;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
