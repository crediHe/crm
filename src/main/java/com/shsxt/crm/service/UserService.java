package com.shsxt.crm.service;

import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/18.
 */
@Service
public class UserService extends BaseService<User>{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 添加和更新用户
     * @param user
     * @param roleIds
     */
    public void saveOrUpdate(User user, Integer[] roleIds){
        /***
         * 1. 校验参数
         * 2. 判断添加或者更新
         * 3. 补全参数
         * 4. 执行操作
         * */
        checkParams(user.getUserName(),user.getTrueName(),user.getEmail(),user.getPhone());
        Integer id = user.getId();

        user.setUpdateDate(new Date());
        if(null==id){
            //添加
            /***
             * 1. 用户名唯一,不重复
             * 2. 密码需要进行初始化
             *      初始密码: 123456, 注意存存加密之后密码
             * */
            AssertUtil.isTrue(null!=userMapper.queryUserByName(user.getUserName()),"用户名已被注册");

            user.setUserPwd(Md5Util.encode("123456"));
            user.setIsValid(1);
            user.setCreateDate(new Date());
            AssertUtil.isTrue(userMapper.save(user)<1, "用户添加失败");

            /***
             * 角色添加
             * 1. 拿到roleId 的数组
             * 2. 查询出新用户的ID
             * 3. 变量数组生成所有的UserRole对象
             * 4. 批量一次性添加到数据库
             * */
            if(null!=roleIds && roleIds.length>0){
                List<UserRole> list = new ArrayList<>();

                Integer userId = userMapper.queryUserByName(user.getUserName()).getId();

                for (Integer roleId:roleIds){
                    UserRole userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(userId);
                    userRole.setCreateDate(new Date());
                    userRole.setUpdateDate(new Date());
                    list.add(userRole);
                }

                AssertUtil.isTrue(userRoleMapper.saveBatch(list)<list.size(),"用户角色添加失败");
            }

        }else{
            //更新
        }

    }

    private void checkParams(String userName, String trueName, String email, String phone) {

        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName),"真实姓名为空");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"电话为空");
    }
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
