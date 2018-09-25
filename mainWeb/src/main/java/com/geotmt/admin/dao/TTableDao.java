package com.geotmt.admin.dao;

import com.geotmt.admin.model.jpa.TTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * 线索平台
 *
 * Created by chao.zhao on 2018/3/13. */
public interface TTableDao extends JpaRepository<TTable,Long> {

	@Query(value = "select f.tId," +
			"f.tDate," +
			"f.tTxt from TTable f")
	List<TTable> findFillAndFlow();


}
