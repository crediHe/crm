package com.shsxt.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/12/17.
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());//当前项目路径
        return "index";
    }

}
