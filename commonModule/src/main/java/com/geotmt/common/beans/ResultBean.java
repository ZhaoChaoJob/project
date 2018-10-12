package com.geotmt.common.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.geotmt.common.exception.StatusCode;
import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by geo on 2018/10/11. */
@JsonInclude(JsonInclude.Include.NON_NULL) // 这个注解用来控制json控制不输出
public class ResultBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object data ; //数据信息
    private String code ;
    private String msg ;

    public void setStatusCode(StatusCode statusCode){
        this.code = statusCode.code();
        this.msg = statusCode.value();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if(data instanceof Page<?>){
            Page<?> page = (Page<?>)data;
            Map<String,Object> map = new HashMap<String,Object>() ;
            map.put("page", page.getResult()) ; //内容
            map.put("pageNum", page.getPageNum()) ; //当前页
            map.put("pageSize", page.getPageSize()) ; //页长
            map.put("startRow", page.getStartRow()) ; //开始行从0开始(包括)
            map.put("endRow", page.getEndRow()) ; //结束行(不包括)
            map.put("total", page.getTotal()) ; //总行数
            map.put("pages", page.getPages()) ;//总页数
            this.data = map ;
        }else if(data == null){
            this.data = new Object();
        }
        else{
            this.data = data;
        }
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
