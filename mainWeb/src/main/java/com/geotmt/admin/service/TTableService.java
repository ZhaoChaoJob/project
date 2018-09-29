package com.geotmt.admin.service;



import com.geotmt.admin.model.jpa.TTable;

import java.util.List;
import java.util.Map;

public interface TTableService {

	public List<TTable> findTTable();
	public Map<String, Object> listHelpTopics(int pageNo, int pageSize);
}
