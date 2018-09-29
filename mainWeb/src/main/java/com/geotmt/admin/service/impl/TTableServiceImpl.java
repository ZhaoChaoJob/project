package com.geotmt.admin.service.impl;

import com.geotmt.admin.dao.TTableDao;
import com.geotmt.admin.model.jpa.TTable;
import com.geotmt.admin.service.TTableService;
import com.geotmt.db.mybatis.dao.TTableMapper;
import com.geotmt.db.mybatis.model.TTableExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TTableServiceImpl implements TTableService {
	@Autowired
	private TTableDao userFillDao;

	@Override
	public List<TTable> findTTable() {
		return userFillDao.findFillAndFlow();
	}

	@Autowired
	private TTableMapper tTableMapper;

	public Map<String, Object> listHelpTopics(int pageNo, int pageSize){
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(pageNo, pageSize, true);
		List<com.geotmt.db.mybatis.model.TTable> list = tTableMapper.selectByExample(new TTableExample());
		//List<HelpTopic> list = helpTopicMapper.findHelpTopics();
		PageInfo<com.geotmt.db.mybatis.model.TTable> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		map.put("list", list);
		map.put("count", total);
		return map;
	}
}
