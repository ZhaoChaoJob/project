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
    // 基本状态码
    public static StatusCode OK = new StatusCode("200", "请求成功") ;
    public static StatusCode ERROR = new StatusCode("500", "请求失败") ;
    public static StatusCode E_SYS_BUSY = new StatusCode("100", "系统繁忙") ;

    // 参数状态码区间
    public static StatusCode E_PARAM_LOST = new StatusCode("1101001000", "缺少参数错误") ;
    public static StatusCode E_PARAM_ERR_TYPE = new StatusCode("1101002000", "参数类型错误") ;
    public static StatusCode E_PARAM_NULL = new StatusCode("1101003000", "参数为空错误") ;
    public static StatusCode E_PARAM_ERR_FORMAT = new StatusCode("1101004000", "参数格式错误") ;

    // 登录失败部分
    public static StatusCode R_ACC_NO_LOGIN = new StatusCode("0201000001", "未登录") ;
    public static StatusCode R_ACC_OUT_LOGIN = new StatusCode("0201000002", "登录已过期") ;
    public static StatusCode R_ACC_DROP = new StatusCode("0201000003", "用户登录被挤掉") ;
    public static StatusCode R_ACC_OTHER_LOGIN = new StatusCode("0201000004", "其他地方已登录") ;

    // 登录验证-空值处理
    public static StatusCode E_ACC_NULL_USERNAME= new StatusCode("1202001001", "请输入账号") ;
    public static StatusCode E_ACC_NULL_PASSWORD= new StatusCode("1202001002", "请输入密码") ;
    public static StatusCode E_ACC_NULL_ACC_PASS= new StatusCode("1202001003", "请输入账号和密码") ;
    public static StatusCode E_ACC_NULL_MSGCODE= new StatusCode("1202001004", "请输入短信验证码") ;
    public static StatusCode E_ACC_NULL_IMAGECODE= new StatusCode("1202001005", "请输入图片验证码") ;
    public static StatusCode E_ACC_NULL_OPEN_ID= new StatusCode("1202001006", "请输入openId") ;
    public static StatusCode E_ACC_NO_SEND_MSGCODE= new StatusCode("1202001007", "请发送短信验证码") ;

    // 登录验证-参数有效性
    public static StatusCode E_ACC_TIMEOUT_LOGIN= new StatusCode("1202002001", "登录超时") ;
    public static StatusCode E_ACC_DATED_MSGCODE= new StatusCode("1202002002", "短信验证码过期") ;
    public static StatusCode E_ACC_USED_MSGCODE= new StatusCode("1202002003", "短信验证码已被使用") ;
    public static StatusCode E_ACC_ERR_MSGCODE= new StatusCode("1202002004", "短信验证码错误") ;
    public static StatusCode E_ACC_ERR_IMAGECODE= new StatusCode("1202002005", "图片验证码错误") ;
    public static StatusCode E_ACC_NO_USERNAME= new StatusCode("1202002006", "用户不存在") ;
    public static StatusCode E_ACC_ERR_USERNAME= new StatusCode("1202002007", "账号错误") ;
    public static StatusCode E_ACC_ERR_PASSWORD= new StatusCode("1202002008", "密码错误") ;
    public static StatusCode E_ACC_ERR_ACC_PASS= new StatusCode("1202002009", "账号或密码错误") ;
    public static StatusCode R_ACC_ALREADY_LOGIN= new StatusCode("1202002010", "用户已登录") ;

    // 登录限制
    public static StatusCode E_ACC_KILL_ACCOUNT = new StatusCode("1202003001", "账户被冻结") ;
    public static StatusCode E_ACC_KILL_IP = new StatusCode("1202003002", "IP临时封杀") ;
    public static StatusCode E_ACC_BLACK_IP = new StatusCode("1202003003", "IP触碰黑名单") ;
    public static StatusCode E_ACC_LIMIT_PASSWORD = new StatusCode("1202003004", "密码输入错误过多") ;
    public static StatusCode E_ACC_LIMIT_MAGCODE = new StatusCode("1202003005", "短信验证码发送超限") ;
    public static StatusCode E_ACC_LIMIT_MAGCODE_DAY = new StatusCode("1202003006", "当日验证码获取已达到上限") ;
    public static StatusCode E_ACC_UNACTIVATE_USERNAME = new StatusCode("1202003007", "账户未激活") ;
    public static StatusCode E_ACC_INVALID_USERNAME = new StatusCode("1202003008", "账户已失效") ;
    public static StatusCode E_ACC_DEL_USERNAME = new StatusCode("1202003009", "账户已被删除") ;
    public static StatusCode E_ACC_NO_MAIL = new StatusCode("1202003010", "邮箱未绑定") ;
    public static StatusCode E_ACC_UNACTIVATE_MAIL = new StatusCode("1202003011", "邮箱绑定未激活") ;

    // 注册限制
    public static StatusCode E_ACC_USED_USERNAME = new StatusCode("1202004001", "用户名已被占用") ;
    public static StatusCode E_ACC_USED_PHONE = new StatusCode("1202004002", "手机号已被占用") ;
    public static StatusCode E_ACC_ERR_PHONE = new StatusCode("1202004003", "手机号错误") ;
    public static StatusCode E_ACC_UN_PHONERANGE = new StatusCode("1202004004", "不支持手机号段") ;
    public static StatusCode E_ACC_USED_MAIL = new StatusCode("1202004005", "邮箱已被绑定") ;
    public static StatusCode E_ACC_TIMEOUT_MIAL = new StatusCode("1202004006", "绑定邮箱已过期") ;

    // 权限限制
    public static StatusCode R_ACC_AUTH_NO_ENOUGH = new StatusCode("1202005001", "权限不足") ;
    public static StatusCode E_ACC_NO_SUPPORT = new StatusCode("1202005002", "不支该类用户") ;
    public static StatusCode E_ACC_NO_SUPPORT_LOANUSER = new StatusCode("1202005003", "不支持贷款类型用户") ;
    public static StatusCode E_ACC_NO_SUPPORT_ORGUSER = new StatusCode("1202005004", "不支持机构类型用户") ;
    public static StatusCode E_CHANNEL_NO_SUPPORT = new StatusCode("1202005005", "不支持渠道") ;

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
