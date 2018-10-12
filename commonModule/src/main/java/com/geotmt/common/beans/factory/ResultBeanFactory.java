package com.geotmt.common.beans.factory;

import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.exception.StatusCode;

/**
 * 请求结果封装类工厂
 */
public class ResultBeanFactory {

    /**
     * 获取一个结果
     * @param statusCode 系统状态
     * @return 结果Bean
     */
    public static ResultBean create(StatusCode statusCode){
        ResultBean resultBean = new ResultBean() ;
        resultBean.setStatusCode(statusCode);
        return resultBean ;
    }

    /**
     * 获取一个结果
     * @param statusCode 系统状态
     * @param data 数据
     * @return 结果Bean
     */
    public static ResultBean create(StatusCode statusCode,Object data){
        ResultBean resultBean = new ResultBean() ;
        resultBean.setStatusCode(statusCode);
        resultBean.setData(data);
        return resultBean ;
    }
}
