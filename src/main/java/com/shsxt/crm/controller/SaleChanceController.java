package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/12/20.
 */
@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {
    @Autowired
    private SaleChanceController saleChanceController;

    @RequestMapping("index")
    public String index(){
        return "sale_chance";
    }
}
