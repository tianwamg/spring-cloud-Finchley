package com.cn.client.jwt;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * jwt token
 */
public class JwtToken {

    /**
     * generate token
     * @param jsonObject
     * @return
     */
    public static String getToken(JSONObject jsonObject){
        String token ="";
        token = JWT.create()
                .withAudience(jsonObject.getString("id"))
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256(jsonObject.getString("pwd")));
        return token;
    }

}
