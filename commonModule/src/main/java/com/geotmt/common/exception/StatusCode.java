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
    public static StatusCode LOCKERR = new StatusCode("-1000", "锁争抢异常") ;
    public static StatusCode NOSALES = new StatusCode("-998", "不是销售账号不允许登陆") ;

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
