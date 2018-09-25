package com.geotmt.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.geotmt.admin.dao.SysUserRepository;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImp implements SysUserService {

	@Autowired
	private SysUserRepository sysUserRepository;

	@Override
	public SysUser getSysUserByName(String name, String password) {
		return sysUserRepository.findByNameAndPsw(name,password);
	}

	@Override
	public Page<SysUser> findUserNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "uid");
		return sysUserRepository.findAll(pageable);
	}

	@Override
	public Page<SysUser> findUserCriteria(Integer page, Integer size, final SysUser user) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "uid");
		Page<SysUser> userPage = sysUserRepository.findAll(new Specification<SysUser>() {

			@Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();

				if(null != user.getNickname() &&!"".equals(user.getNickname())){
					list.add(criteriaBuilder.equal(root.get("nickname").as(String.class), user.getNickname()));
				}
				// 多条件

				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		return userPage;
	}

}
