package com.bjyada.rps.dbservice.model.mapper;

import com.bjyada.rps.dbservice.model.ExcuteLogInfo;
import com.mysql.jdbc.ResultSet;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
<#assign className = table.className> 
<#assign sqlName = table.sqlName> 

/**
 * 数据库字段和实体类的映射
 *
 * Created by chao.zhao on 2015/12/18.
 */
@Component("${className}Mapper")
public class ${className}Mapper implements RowMapper<${className}> {
	
	 /**
     * 转换方法
     * @param rs
     * @param row
     * @return
     * @throws SQLException
     */
    public ${className} mapRow(ResultSet rs, int row) throws SQLException {
    	
    	${className} ${className?uncap_first} = new ${className}() ;
        <#list table.columns as column>
        ${className?uncap_first}.set${column.columnName}(rs.getString("${column.constantName}")) ;
		</#list>
        return ${className?uncap_first} ;
    }
    
    /**
     * 任务调度执行情况日志信息
     *
     * @param ${className?uncap_first}
     * @return
     */
    public Map<String, Object> mapRow(${className} ${className?uncap_first}){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        <#list table.columns as column>
        paramMap.put("${column.constantName}", ${className?uncap_first}.get${column.columnName}());
        </#list>
        return paramMap ;
    }
}
