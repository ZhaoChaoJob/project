package com.geotmt.admin.service;

import com.geotmt.admin.model.jpa.SysMenu;
import com.geotmt.admin.model.jpa.SysToken;
import com.geotmt.admin.model.jpa.SysUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysUserService {

	/**根据用户名密码查询用户*/
	SysUser getSysUserByName(String name, String password);
	
	//List<SysUser> getSysUserByName(String name,String password);
	
	/**不带查询条件的分页查询*/
	Page<SysUser> findUserNoCriteria(Integer page, Integer size);
	
	/**带查询条件的分页查询*/
	Page<SysUser> findUserCriteria(Integer page, Integer size, SysUser user);

	/**获取菜单*/
	List<SysMenu> getMenu(Long userId);

	/**
	 * 保存token
	 * @param userId 用户ID
	 * @param username 账号
	 * @param password 密码
	 * @param accessToken  accessToken
	 * @param openId  openId
	 * @return SysToken
	 */
	public SysToken saveToken(Long userId, String username, String password, String accessToken,String openId);

	/**
	 * 获取token信息
	 * @param  accessToken accessToken
	 * @return SysToken
	 */
	public SysToken getTokenById(String accessToken) ;
}
