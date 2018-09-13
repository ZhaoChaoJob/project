package com.geotmt.admin.service;


import com.geotmt.admin.model.jpa.SysPermission;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysPermissionService {

	/**查询所有权限*/
	List<SysPermission> getPermisAll();
	
	/**不带查询条件的分页查询*/
	Page<SysPermission> findPermisNoCriteria(Integer page, Integer size);
	
	/**带查询条件的分页查询*/
	Page<SysPermission> findPermisCriteria(Integer page, Integer size, SysPermission permis);
}
