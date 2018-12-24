package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.po.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/9/27.
 */
@Service
public class ModuleService extends BaseService<Module> {

    @Autowired
    private ModuleMapper moduleMapper;

    public List<Map> queryModuleByGrade(Integer grade){
        return moduleMapper.queryModuleByGrade(grade);
    }
}
