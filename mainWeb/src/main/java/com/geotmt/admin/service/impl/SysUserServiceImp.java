package com.geotmt.admin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.geotmt.admin.dao.SysTokenRepository;
import com.geotmt.admin.dao.SysUserRepository;
import com.geotmt.admin.model.jpa.SysMenu;
import com.geotmt.admin.model.jpa.SysToken;
import com.geotmt.admin.model.jpa.SysUser;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.common.utils.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImp implements SysUserService {
	private final Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private SysTokenRepository sysTokenRepository;
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

	public List<SysMenu> getMenu(Long userId){
		List<Map<String,Object>> menuListResult =  this.sysUserRepository.getMenu(userId);
		List<SysMenu> menuList = new ArrayList<>();
		for (Map<String,Object> map :menuListResult) {
			SysMenu sysMenu = new SysMenu();
			try {
				BeanUtils.populate(sysMenu,map);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			menuList.add(sysMenu);
		}

		// 最后的结果
		List<SysMenu> menuList2 = new ArrayList<>();
		// 先找到所有的一级菜单
		for (int i = 0; i < menuList.size(); i++) {
			// 一级菜单没有parentId
			if (StringUtils.isBlank(menuList.get(i).getParentId())) {
				menuList2.add(menuList.get(i));
			}
		}
		// 为一级菜单设置子菜单，getChild是递归调用的
		for (SysMenu menu : menuList2) {
			menu.setChildren(getChild(menu.getMenuId(), menuList));
		}

		return menuList2;
	}

	/**
	 * 递归查找子菜单
	 *
	 * @param id
	 *            当前菜单id
	 * @param rootMenu
	 *            要查找的列表
	 * @return
	 */
	private List<SysMenu> getChild(String id, List<SysMenu> rootMenu) {
		// 子菜单
		List<SysMenu> childList = new ArrayList<>();
		for (SysMenu menu : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtils.isNotBlank(menu.getParentId())) {
				if (menu.getParentId().equals(id)) {
					childList.add(menu);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (SysMenu menu : childList) {// 没有url子菜单还有子菜单
//            if (StringUtils.isBlank(menu.getUrl())) {
			// 递归
			menu.setChildren(getChild(menu.getMenuId(), rootMenu));
//            }
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	/**
	 * 保存token
	 * @param userId 用户ID
	 * @param username 账号
	 * @param password 密码
	 * @param accessToken  accessToken
	 * @return SysToken
	 */
	@Override
	public SysToken saveToken(Long userId, String username, String password, String accessToken){
		SysToken sysToken = new SysToken() ;
		sysToken.setUserId(userId);
		sysToken.setUserCode(username);
		sysToken.setPassword(password);
		sysToken.setInsertTime(DateUtil.getCurrentDate(Long.class));
		sysToken.setInvalidTime(DateUtil.getCurrentDate(Long.class));
		sysToken.setTokenId(accessToken);
		// TODO 先删除userid对应的token
		return this.sysTokenRepository.save(sysToken);
	}

	/**
	 * 获取token信息
	 * @param  accessToken accessToken
	 * @return SysToken
	 */
	@Override
	public SysToken getTokenById(String accessToken){
		logger.info("获取token信息:[{}]",accessToken);
		return this.sysTokenRepository.findById(accessToken).get();
	}
}
