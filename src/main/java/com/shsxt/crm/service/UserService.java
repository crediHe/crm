package com.shsxt.crm.service;

import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/12/18.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登陆
     * @param userName
     * @param userPwd
     */
    public void login(String userName,String userPwd){
        //信息校验
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空");

        //用户名验证
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user==null,"用户名不存在或已注销");

        //密码验证,数据库中的密码是加密的
        AssertUtil.isTrue(Md5Util.encode(user.getUserPwd()).equals(userPwd),"用户密码不正确");

    }
}
