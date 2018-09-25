package com.geotmt.admin.service.impl;


import com.geotmt.admin.dao.SysPermissionRepository;
import com.geotmt.admin.model.jpa.SysPermission;
import com.geotmt.admin.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class SysPermissionServiceImp implements SysPermissionService {

	@Autowired
    private SysPermissionRepository sysPermissionRepository;

	@Override
	public List<SysPermission> getPermisAll() {
		return sysPermissionRepository.findAll();
	}

	@Override
	public Page<SysPermission> findPermisNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "permissionId");
		return sysPermissionRepository.findAll(pageable);
	}

	@Override
	public Page<SysPermission> findPermisCriteria(Integer page, Integer size, final SysPermission permis) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "uid");
		Page<SysPermission> userPage = sysPermissionRepository.findAll(new Specification<SysPermission>() {
			
			@Override
			public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				if(null != permis.getName() &&!"".equals(permis.getName())){
					list.add(criteriaBuilder.equal(root.get("nickname").as(String.class), permis.getName()));
				}
				// 多条件
				
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		return userPage;
	}

}
