package com.geotmt.rong360;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geo on 2018/9/25.
 */
@Controller
public class Rong360Controller {
    /**
     * ajax登录请求接口方式登陆
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value="/rong360")
    @ResponseBody
    public String rong360(String username, String password, Model model) {
        return "这是融360的接口";
    }
}
