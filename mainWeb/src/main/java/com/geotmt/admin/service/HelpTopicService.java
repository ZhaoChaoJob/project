package com.geotmt.admin.service;


import com.geotmt.common.mybatis.dao.HelpTopicMapper;
import com.geotmt.common.mybatis.model.HelpTopic;
import com.geotmt.common.mybatis.model.HelpTopicExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HelpTopicService {
    @Autowired
    private HelpTopicMapper helpTopicMapper;

    public Map<String, Object> listHelpTopics(int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(pageNo, pageSize, true);
        List<HelpTopic> list = helpTopicMapper.selectByExample(new HelpTopicExample());
        //List<HelpTopic> list = helpTopicMapper.findHelpTopics();
        PageInfo<HelpTopic> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        map.put("list", list);
        map.put("count", total);
        return map;
    }
}
