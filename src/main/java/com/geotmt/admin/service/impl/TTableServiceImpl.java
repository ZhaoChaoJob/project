package com.geotmt.admin.service.impl;

import com.geotmt.admin.dao.TTableDao;
import com.geotmt.admin.model.jpa.TTable;
import com.geotmt.admin.service.TTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TTableServiceImpl implements TTableService {
	@Autowired
	private TTableDao userFillDao;

	@Override
	public List<TTable> findTTable() {
		return userFillDao.findFillAndFlow();
	}
}
