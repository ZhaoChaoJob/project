package com.geotmt.rong360;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 融360接口
 *
 * Created by chao.zhao on 2018/9/25. */
@Controller
public class Rong360Controller {
    /**
     * ajax登录请求接口方式登陆
     */
    @RequestMapping(value="/rong360")
    @ResponseBody
    public String rong360() {
        return "this is rong360 api !!";
    }
}
