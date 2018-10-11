package com.geotmt.admin.controller.api;

import com.geotmt.admin.controller.BaseController;
import com.geotmt.admin.service.LoginService;
import com.geotmt.common.beans.ResultBean;
import com.geotmt.common.beans.ResultMap;
import com.geotmt.common.exception.StatusCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * api 登录
 */
@Controller
@RequestMapping(value="/api/o")
public class ApiLoginController extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private LoginService loginService ;

    /**
     * ajax登录请求接口方式登陆
     * @param username 账号
     * @param password 密码
     * @return
     *
     * http://127.0.0.1:8080/api/o/apiLogin?username=admin&password=123
     */
    @RequestMapping(value="/apiLogin")
    @ResponseBody
    public ResultBean apiLogin(String username,String password) {
        ResultBean resultBean = new ResultBean();
        try {

            String accessToken = this.loginService.login(username,password,null);
            resultBean.setStatusCode(StatusCode.OK);
            resultBean.setData(new ResultMap("accessToken",accessToken).apply());
        } catch (Exception e) {
            resultBean.setStatusCode(StatusCode.ERROR);
        }
        return resultBean;
    }
}
