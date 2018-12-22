package com.shsxt.crm.dao;

import com.shsxt.crm.po.CustomerServe;

public interface CustomerServeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerServe record);

    int insertSelective(CustomerServe record);

    CustomerServe selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerServe record);

    int updateByPrimaryKey(CustomerServe record);
}