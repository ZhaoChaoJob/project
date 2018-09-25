package com.geotmt.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * MybatisDao
 */
@Mapper
public interface TTableMyBatisDao {
    @Select("select * from t_table;")
    public List<Map<String,Object>> find();
}
