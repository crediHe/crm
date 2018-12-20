package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.SaleChanceMapper;
import com.shsxt.crm.po.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/12/20.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

}