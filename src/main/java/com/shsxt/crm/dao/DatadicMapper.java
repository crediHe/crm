package com.shsxt.crm.dao;

import com.shsxt.crm.po.Datadic;

public interface DatadicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Datadic record);

    int insertSelective(Datadic record);

    Datadic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Datadic record);

    int updateByPrimaryKey(Datadic record);
}