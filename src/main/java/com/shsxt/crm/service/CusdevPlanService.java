package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.CusdevPlanMapper;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.po.CusdevPlan;
import com.shsxt.crm.po.SaleChance;
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
    @Autowired
    private SaleChanceMapper saleChanceMapper;

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
            /***
             * 修改客户营销状态 devResult = 1
             * 如果在添加开发计划的时候，是未开发的状态，设置为开发中
             * 如果devResult=0 改1, 如果是1的,就不动
             *
             * */
            SaleChance saleChance = saleChanceMapper.queryById(sid);
            if(saleChance.getDevResult()==0){
                saleChance.setDevResult(1);//设置开发状态为开发中
                AssertUtil.isTrue(saleChanceMapper.update(saleChance)<1,"客户开发状态修改失败");
            }

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
