package com.cn.enumeration;

public enum LoginEnum {

    Error("10001","登录信息已失效，请重新登录"),
    Fail("10002","用户不存在，请重新输入");

    private String code;
    private String msg;

    LoginEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
