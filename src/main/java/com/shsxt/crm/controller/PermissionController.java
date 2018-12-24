package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/12/24.
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController{
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("doGrant")
    @ResponseBody
    public ResultInfo doGrant(Integer roleId, Integer[] moduleIds){
        permissionService.doGrant(roleId, moduleIds);
        return success("操作成功");
    }
}
