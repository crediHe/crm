package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/9/20.
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role";
    }

    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map> queryAllRoles(){
        return roleService.queryAllRoles();
    }

    @RequestMapping("queryPermissionByRoleId")
    @ResponseBody
    public List<ModuleDto> queryPermissionByRoleId(Integer roleId){
        return roleService.queryPermissionByRoleId(roleId);
    }

    @RequestMapping("queryRolesByParams")
    @ResponseBody
    public Map<String, Object> queryRolesByParams(RoleQuery query,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer rows){
        query.setPageNum(page);
        query.setPageSize(rows);
        return roleService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateRole")
    @ResponseBody
    public ResultInfo saveOrUpdateRole(Role role){
        roleService.saveOrUpdate(role);
        return success("操作成功");
    }

    @RequestMapping("deleteRole")
    @ResponseBody
    public ResultInfo deleteRole(Integer[] ids){
        roleService.deleteRole(ids);
        return success("操作成功");
    }
}
