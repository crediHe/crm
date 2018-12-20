package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/12/17.
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping("index")
    public String index(HttpServletRequest request){
        return "index";
    }

}
