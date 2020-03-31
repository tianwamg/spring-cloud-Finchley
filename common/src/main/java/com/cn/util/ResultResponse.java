package com.cn.util;


import com.cn.enumeration.LoginEnum;

import java.io.Serializable;

public class ResultResponse<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public ResultResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResultResponse success(){
        return new ResultResponse("200","成功",null);
    }

    public static ResultResponse success(String msg,Object data){
        return new ResultResponse("200",msg,data);
    }

    public static ResultResponse success(String code,String msg,Object data){
        return new ResultResponse(code,msg,code);
    }

    public static <T> ResultResponse error(){
        return new ResultResponse("500","失败",null);
    }

    public static <T> ResultResponse error(String msg,T data){
        return new ResultResponse("500",msg,data);
    }

    public ResultResponse(LoginEnum loginEnum){
        this.code = loginEnum.getCode();
        this.msg = loginEnum.getMsg();
    }

    public static ResultResponse loginError() {
        return new ResultResponse(LoginEnum.Error);
    }

    public static <T> ResultResponse error(String code,String msg,T data){
        return new ResultResponse(code,msg,data);
    }
}
