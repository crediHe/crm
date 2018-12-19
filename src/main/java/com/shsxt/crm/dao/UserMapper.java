package com.shsxt.crm.dao;

import com.shsxt.crm.base.BaseDao;
import com.shsxt.crm.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/12/18.
 */
@Repository
public interface UserMapper extends BaseDao<User> {

    /*用户名查询id*/
    public User queryUserByName(String userName);
    /*修改密码*/
    public Integer updateUserPwd(@Param("id") Integer id, @Param("userPwd") String userPwd);
}
