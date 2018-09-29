package com.geotmt.common.exception;


/**
 * 业务状态码
 */
public class BizStatusCode extends StatusCode{
    private static final long serialVersionUID = 1L;

    // 系统问题区间
    public static BizStatusCode E_SYS_NO_RESPONSE  = new BizStatusCode("1301001000", "系统无响应") ;
    public static BizStatusCode E_SYS_BUSY  = new BizStatusCode("1301002000", "系统繁忙") ;

    // 数据库区间
    public static BizStatusCode R_DB_NO_RECORD  = new BizStatusCode("0401001000", "未查到结果") ;
    // 中间件区间

    // 业务相关区间

    protected BizStatusCode(String code, String value) {
        super(code, value);
    }
    public static StatusCode get(String code) {
        return lookup.get(code);
    }
}

