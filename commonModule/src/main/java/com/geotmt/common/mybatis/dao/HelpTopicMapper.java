package com.geotmt.common.mybatis.dao;

import com.geotmt.common.mybatis.model.HelpTopic;
import com.geotmt.common.mybatis.model.HelpTopicExample;
import com.geotmt.common.mybatis.model.HelpTopicWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HelpTopicMapper {
    int countByExample(HelpTopicExample example);

    int deleteByExample(HelpTopicExample example);

    int deleteByPrimaryKey(Integer helpTopicId);

    int insert(HelpTopicWithBLOBs record);

    int insertSelective(HelpTopicWithBLOBs record);

    List<HelpTopicWithBLOBs> selectByExampleWithBLOBs(HelpTopicExample example);

    List<HelpTopic> selectByExample(HelpTopicExample example);

    HelpTopicWithBLOBs selectByPrimaryKey(Integer helpTopicId);

    int updateByExampleSelective(@Param("record") HelpTopicWithBLOBs record, @Param("example") HelpTopicExample example);

    int updateByExampleWithBLOBs(@Param("record") HelpTopicWithBLOBs record, @Param("example") HelpTopicExample example);

    int updateByExample(@Param("record") HelpTopic record, @Param("example") HelpTopicExample example);

    int updateByPrimaryKeySelective(HelpTopicWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HelpTopicWithBLOBs record);

    int updateByPrimaryKey(HelpTopic record);

    @Select("select * from help_topic")
    List<HelpTopic> findHelpTopics();
}