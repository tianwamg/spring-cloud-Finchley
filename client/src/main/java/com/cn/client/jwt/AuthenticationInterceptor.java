package com.cn.client.jwt;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.cn.client.annotation.PassToken;
import com.cn.client.bean.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


public class AuthenticationInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object)throws Exception{
        String token = request.getHeader("token");//从http请求头中获取token
        if(!(object instanceof HandlerMethod)){//如果不是映射方法直接通过
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(PassToken.class)){//检查是否有passtoken注释，有则跳过验证
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //开始验证

        if(token == null){
            this.LoginRequired(request,response);
            return false;
            //throw new RuntimeException("无token或token已失效,请重新登录");
        }
        String userid="";
        try{
            userid = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            this.LoginRequired(request,response);
            return false;
            //throw new RuntimeException("无效的token值，请重新登录");
        }

        //TODO 将token放入redis中并设置生存时间，key:ACCESS_TOKEN:USER token;value:当前登录时间，只要在toke生存期内登录，token生存时间自动更新
        //TODO 暂时设置为只允许一个客户端登录
        //FIXME 频繁请求调整为放入redis中

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("")).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            this.LoginRequired(request,response);
            return false;
            //throw new RuntimeException("无效的token值，请重新登录");
        }
        LoginUser u = new LoginUser();
        u.setRoleId(0);
        u.setId(1);
        u.setUsername("");
        request.setAttribute("user",u);


        return true;
    }

    public void LoginRequired(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(null));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)throws Exception{

    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object object,Exception e)throws Exception{

    }
}
