package com.geotmt.common.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统状态码
 */
public class StatusCode implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final Map<String, StatusCode> lookup = new HashMap<>();
    public static StatusCode OK = new StatusCode("200", "请求成功") ;
    public static StatusCode ERROR = new StatusCode("500", "请求失败") ;
    public static StatusCode R_ACC_NO_LOGIN = new StatusCode("0201001000", "未登录") ;
    public static StatusCode E_ACC_NULL_USERNAME = new StatusCode("1202001000", "账号不能为空") ;
    public static StatusCode E_ACC_NULL_PASSWORD = new StatusCode("1202002000", "密码不能为空") ;
    public static StatusCode E_ACC_ERR_USERORPASS = new StatusCode("1202011000", "用户名或密码错误") ;
    public static StatusCode R_ACC_AUTH_NO_ENOUGH = new StatusCode("0204001000", "权限不足") ;

    private String code; // 状态码
    private String value; // 状态码值

    public StatusCode setValue(String value) {
        this.value = value;
        return this ;
    }

    public StatusCode(String code, String value) {
        this.code = code;
        this.value = value;
        StatusCode statusCode = lookup.get(code);
        if(statusCode!=null){
            String errStr = "warning~~~~~code:"+code+" already exist previous "+statusCode.toString() ;
            System.err.println(errStr);
        }
        lookup.put(code, this) ;
    }

    public String code() {
        return code;
    }
    public String value() {
        return value;
    }
	
    public static StatusCode get(String code) {
        return lookup.get(code);
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
