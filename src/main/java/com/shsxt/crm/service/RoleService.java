package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.dao.RoleMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/22.
 */
@Service
public class RoleService extends BaseService<Role> {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;
    public List<Map> queryAllRoles(){
        return roleMapper.queryAllRoles();
    }

    public List<ModuleDto> queryPermissionByRoleId(Integer roleId){
        return roleMapper.queryPermissionByRoleId(roleId);
    }

    /**
     * 添加或者更新用户角色
     * @param role
     */
    public void saveOrUpdate(Role role){
        /***
         * 1. 检查参数
         * 2. 补全参数
         * 3. 通过id区分添加和更新
         * 4. 执行操作
         * */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称为空");

        Integer id = role.getId();

        role.setUpdateDate(new Date());
        if (null == id) {
            AssertUtil.isTrue(null!=roleMapper.queryRoleByName(role.getRoleName()),"角色名称已存在");
            role.setCreateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleMapper.save(role)<1,"角色添加失败");
        }else{
            /***
             * 如果用户更新角色名字就做唯一校验,
             * 如果用户不更新名字不用做唯一校验
             * */
            if(!roleMapper.queryById(id).getRoleName().equals(role.getRoleName())){
                AssertUtil.isTrue(null!=roleMapper.queryRoleByName(role.getRoleName()),"角色名称已存在");
            }
            AssertUtil.isTrue(roleMapper.update(role)<1, "角色更新失败");
        }

    }

    public void deleteRole(Integer[] ids){
        if(null!=ids && ids.length>0){

            for(Integer roleId:ids){
                /***
                 * 1. 删除角色
                 * 2. 删除用户角色中间表
                 * 3. 删除角色模块权限
                 * */
                AssertUtil.isTrue(roleMapper.delete(roleId)<1,"角色删除失败");//1

                Integer total1 = userRoleMapper.queryUserRolesByRoleId(roleId);//3
                if(total1>0){
                    //执行删除用户角色
                    AssertUtil.isTrue(userRoleMapper.deleteRolesByUserId(roleId)<total1,"用户角色删除失败");//2
                }

                Integer total2 = permissionMapper.queryPermissionByRoleId(roleId);
                if(total2>0){
                    //执行删除角色模块权限
                    AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId)<total2,"角色模块删除失败");
                }
            }
        }
    }
}
