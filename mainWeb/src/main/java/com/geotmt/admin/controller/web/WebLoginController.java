package com.geotmt.admin.controller.web;

import com.geotmt.admin.controller.BaseController;
import com.geotmt.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by geo on 2018/10/11. */
@Controller
@RequestMapping(value="/web/o")
public class WebLoginController extends BaseController {
    @Autowired
    private LoginService loginService ;

}
