package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CusdevPlan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.query.CusdevPlanQuery;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.CusdevPlanService;
import com.shsxt.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.util.Map;

/**
 * Created by xlf on 2018/9/19.
 */
@Controller
@RequestMapping("cusDevPlan")
public class CusDevPlanController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CusdevPlanService cusdevPlanService;

    @RequestMapping("index")
    public String index(Integer sid, Model model){
        SaleChance saleChance = saleChanceService.queryById(sid);
        model.addAttribute(saleChance);
        return "cus_dev_plan_detail";
    }


    @RequestMapping("queryCusDevPlansByParams")
    @ResponseBody
    public Map<String, Object> queryCusDevPlansByParams(CusdevPlanQuery query,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer rows){
        query.setPageNum(page);
        query.setPageSize(rows);
        return cusdevPlanService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateCusDevPlan")
    @ResponseBody
    public ResultInfo saveOrUpdateCusDevPlan(CusdevPlan cusdevPlan, Integer sid){
        cusdevPlanService.saveOrUpdate(cusdevPlan, sid);
        return success("操作成功");
    }

    @RequestMapping("deleteBatchCusDevPlan")
    @ResponseBody
    public ResultInfo deleteBatchCusDevPlan(Integer[] ids){
        cusdevPlanService.deleteBatch(ids);
        return success("操作成功");
    }

}
