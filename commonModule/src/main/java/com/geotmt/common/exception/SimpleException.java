package com.geotmt.common.exception;

/**
 * 简单异常
 */
public class SimpleException extends RuntimeException {
    private String msg ;
    private StatusCode statusCode ;

    public SimpleException(StatusCode statusCode) {
        super(statusCode.toString()) ;
        this.msg = statusCode.toString() ;
        this.statusCode = statusCode ;
    }

    public SimpleException(StatusCode statusCode, String msg) {
        super(msg) ;
        this.msg = msg ;
        this.statusCode = statusCode ;
    }

    public SimpleException(StatusCode statusCode, String msg, Throwable e) {
        super(msg, e) ;
        this.msg = msg ;
        this.statusCode = statusCode ;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "{" +
                "msg='" + msg + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}