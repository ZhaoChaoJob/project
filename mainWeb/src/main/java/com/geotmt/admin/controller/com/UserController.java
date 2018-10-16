package com.geotmt.admin.controller.com;

import com.geotmt.admin.controller.BaseController;
import com.geotmt.admin.service.LoginService;
import com.geotmt.admin.service.SysUserService;
import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用用户Controller
 */
@Controller
@RequestMapping(value="/com/u")
public class UserController extends BaseController {
    @Autowired
    private LoginService loginService ;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取菜单
     *
     * @return ResultBean
     */
    @RequestMapping(value="/getMenu")
    @ResponseBody
    public ResultBean getMenu(){
        ResultBean resultBean = new ResultBean();
        Long userId = getSysUserId();
        resultBean.setData(this.sysUserService.getMenu(userId));
        resultBean.setStatusCode(StatusCode.OK);
        return resultBean;
    }
}
