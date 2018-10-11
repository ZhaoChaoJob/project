package com.geotmt.common.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果集map
 */
public class ResultMap {
    private Map<String,Object> map ;

    public ResultMap(String key,Object obj){
        this.map =new HashMap<>();
        this.map.put(key, obj);
    }
    public ResultMap append(String key,Object obj){
        this.map.put(key, obj);
        return this;
    }

    public Map<String,Object> apply(){
        return this.map;
    }
//    public static void main(String[] args) {
//        String json = JSONObject.toJSONString( new ResultMap("33","33").append("a","1").append("b","2").append("c","3").append("d","4").apply());
//        System.out.println(json);
//    }
}
