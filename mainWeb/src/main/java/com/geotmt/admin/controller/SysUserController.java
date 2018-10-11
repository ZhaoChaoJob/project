package com.geotmt.admin.controller;


import com.geotmt.admin.service.SysUserService;
import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.exception.StatusCode;
import com.geotmt.commons.entity.UsernamePasswordExtToken;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SysUserController {

	@Autowired
    private SysUserService sysUserService;

	/**
	 * ajax登录请求接口方式登陆
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/ajaxLogin")
	@ResponseBody
	public Map<String,Object> submitLogin(String username, String password, Model model) {
		Map<String, Object> resultMap = new LinkedHashMap<>();
		try {

			UsernamePasswordExtToken token = new UsernamePasswordExtToken(username, password);
			SecurityUtils.getSubject().login(token);
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");

		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "listUser",method = RequestMethod.GET)
	public String getListUser(){
		return "demo/table2";
	}

	/**
	 * 获取菜单
	 *
	 * @param userId 用户ID
	 * @return ResultBean
	 */
	@RequestMapping(value="/getMenu")
	@ResponseBody
	public ResultBean getMenu(@RequestParam("userId") Long userId){
		ResultBean resultBean = new ResultBean();
		resultBean.setData(this.sysUserService.getMenu(userId));
		resultBean.setStatusCode(StatusCode.OK);
		return resultBean;
	}
}
