package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.CusdevPlanMapper;
import com.shsxt.crm.po.CusdevPlan;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by xlf on 2018/9/20.
 */
@Service
public class CusdevPlanService extends BaseService<CusdevPlan> {

    @Autowired
    private CusdevPlanMapper cusdevPlanMapper;

    /**
     * 保存或添加 营销计划
     * @param cusdevPlan
     */
    public void saveOrUpdate(CusdevPlan cusdevPlan,Integer sid){
        /***
         * 1. 校验参数
         * 2. 判断添加或者修改
         * 3. 补全参数
         * 4. 执行操作
         * */
        checkCusdevPlanParams(cusdevPlan.getPlanDate(),cusdevPlan.getPlanItem(),cusdevPlan.getExeAffect());
        Integer id = cusdevPlan.getId();

        cusdevPlan.setUpdateDate(new Date());
        if(null==id){
            cusdevPlan.setSaleChanceId(sid);
            cusdevPlan.setCreateDate(new Date());
            cusdevPlan.setIsValid(1);

            AssertUtil.isTrue(cusdevPlanMapper.save(cusdevPlan)<1,"添加失败");

        }else{
            AssertUtil.isTrue(cusdevPlanMapper.update(cusdevPlan)<1,"更新失败");
        }
    }

    private void checkCusdevPlanParams(Date planData,String planItem,String exeAffect) {
        AssertUtil.isTrue(null==planData,"计划时间为空");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"计划内容为空");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect),"执行效果为空");
    }


}
