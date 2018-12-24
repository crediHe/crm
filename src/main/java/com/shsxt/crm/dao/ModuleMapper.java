package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.Module;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ModuleMapper extends BaseDao<Module>{
    public List<Map> queryModuleByGrade(Integer grade);
}