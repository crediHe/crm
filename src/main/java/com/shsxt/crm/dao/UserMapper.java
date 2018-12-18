package com.shsxt.crm.dao;

import com.shsxt.crm.po.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/12/18.
 */
@Repository
public interface UserMapper {

    /*用户名查询id*/
    public User queryUserByName(String userName);
}
