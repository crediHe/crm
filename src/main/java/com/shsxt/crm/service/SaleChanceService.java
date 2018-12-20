package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
/**
 * Created by Administrator on 2018/12/20.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加或者更新SaleChance
     * @param saleChance
     */
    public void saveOrUpdateSaleChance(SaleChance saleChance , Integer userId){
        /***
         * 1. 校验参数
         * 2. 通过id区分时添加还是更新
         *      1) 如果有id, 就是更新
         *      2) 如果没有id, 就是添加
         * 3. 补全参数,比如: 创建时间 创建人 更新时间
         * 4. 执行操作
         * */
        System.out.println("6666666666666666666666666666666666");//2
        checkSaleChanceParams(saleChance.getCustomerName(),
                saleChance.getLinkMan(), saleChance.getLinkPhone());

        Integer id = saleChance.getId();
        if(null==id){
            //添加
            User user = userMapper.queryById(userId);
            saleChance.setCreateMan(user.getUserName());//创建人
            saleChance.setState(0);//未分配
            saleChance.setDevResult(0);//未开发
            saleChance.setIsValid(1);//账户有效
            saleChance.setCreateDate(new Date());//创建时间
            saleChance.setUpdateDate(new Date());//更新时间
            AssertUtil.isTrue(saleChanceMapper.save(saleChance)<1,"营销机会添加失败");
            System.out.println("service执行...");
        }else{
            //更新
        }
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人为空");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话为空");
    }

}