package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dao.RoleMapper;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xlf on 2018/9/27.
 */
@Service
public class PermissionService extends BaseService<Permission> {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 角色授权
     *
     * @param roleId
     * @param moduleIds
     */
    public void doGrant(Integer roleId, Integer[] moduleIds) {
        AssertUtil.isTrue(null == roleId || null == roleMapper.queryById(roleId), "角色不存在");
        /***
         * 授权操作: 先删除该角色所有的模块权限, 然后再批量添加模块权限
         * 1) 删除模块权限之前,先查询是否有模块权限,有就删除! 没有就不用删
         *
         * */
        Integer total = permissionMapper.queryPermissionByRoleId(roleId);
        if (total > 0) {
            // 批量删除操作
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId) < total, "模块权限删除失败");
        }

        if (null != moduleIds && moduleIds.length > 0) {
            // 批量添加操作
            List<Permission> list = new ArrayList<>();

            for (Integer moduleId : moduleIds) {
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(moduleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                list.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.saveBatch(list)<list.size(), "模块权限添加失败");
        }
    }
}
