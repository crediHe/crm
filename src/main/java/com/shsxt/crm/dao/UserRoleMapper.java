package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper extends BaseDao<UserRole> {
//    通过id查询用户是否存在
    public Integer queryRolesByUserId(Integer userId);
//    删除角色
    public Integer deleteRolesByUserId(Integer userId);
}