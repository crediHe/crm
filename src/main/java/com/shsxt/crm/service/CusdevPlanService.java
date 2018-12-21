package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.CusdevPlanMapper;
import com.shsxt.crm.po.CusdevPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xlf on 2018/9/20.
 */
@Service
public class CusdevPlanService extends BaseService<CusdevPlan> {

    @Autowired
    private CusdevPlanMapper cusdevPlanMapper;

}
