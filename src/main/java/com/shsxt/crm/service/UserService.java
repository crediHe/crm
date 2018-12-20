package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/18.
 */
@Service
public class UserService extends BaseService<User>{
    @Autowired
    private UserMapper userMapper;

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param userId
     */
    public void updateUserPwd(String oldPassword, String newPassword, String confirmPassword, Integer userId){
        /**
         * 1. 校验参数是否为空
         * 2. 校验旧密码是否正确,通过id查询用户进行密码比对
         * 3. 执行更新操作
         * */
        //校验非空
        checkUserParams(oldPassword, newPassword, confirmPassword);

        User user = userMapper.queryById(userId);
        AssertUtil.isTrue(oldPassword.equals(user.getUserPwd()),"用户密码不正确");
        /***
         * 存入的应该是加密后的密码
         * */
        AssertUtil.isTrue(userMapper.updateUserPwd(userId,Md5Util.encode(newPassword))<1,"用户密码更新失败");
    }

    private void checkUserParams(String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"旧密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码为空");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"确认密码为空");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword),"两次密码不一致");
    }

    /**
     * 用户登陆
     * @param userName
     * @param userPwd
     */
    public UserInfo login(String userName, String userPwd){
        UserInfo userInfo=new UserInfo();
        //信息校验
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空");

        //用户名验证
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user==null,"用户名不存在或已注销");

        //密码验证,数据库中的密码是加密的
        AssertUtil.isTrue(Md5Util.encode(user.getUserPwd()).equals(userPwd),"用户密码不正确");

        userInfo.setUserName(user.getUserName());
        userInfo.setRealName(user.getTrueName());
        userInfo.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return userInfo;
    }

    public List<Map> queryCustomerMamagers(){
        return userMapper.queryCustomerMamagers();
    }
}
