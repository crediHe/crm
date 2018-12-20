package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/12/17.
 */
@Controller
public class MainController extends BaseController{
    @Autowired
    private UserService userService;
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.queryById(id);
        request.setAttribute("user",user);
        return "main";
    }

}
