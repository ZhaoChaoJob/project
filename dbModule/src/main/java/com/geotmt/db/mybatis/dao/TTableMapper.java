package com.geotmt.db.mybatis.dao;

import com.geotmt.db.mybatis.model.TTable;
import com.geotmt.db.mybatis.model.TTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TTableMapper {
    int countByExample(TTableExample example);

    int deleteByExample(TTableExample example);

    int deleteByPrimaryKey(Long tId);

    int insert(TTable record);

    int insertSelective(TTable record);

    List<TTable> selectByExample(TTableExample example);

    TTable selectByPrimaryKey(Long tId);

    int updateByExampleSelective(@Param("record") TTable record, @Param("example") TTableExample example);

    int updateByExample(@Param("record") TTable record, @Param("example") TTableExample example);

    int updateByPrimaryKeySelective(TTable record);

    int updateByPrimaryKey(TTable record);
}