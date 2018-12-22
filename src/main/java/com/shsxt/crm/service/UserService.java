package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.crm.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     * 删除用户
     * @param ids
     */
    public void deleteUser(Integer[] ids){
        /***
         * 1. 执行用户删除
         * 2. 删除用户角色
         * */
        for(Integer userId : ids){
            AssertUtil.isTrue(userMapper.delete(userId)<1,"用户删除失败");
            // 删除之前先查询用户是否有角色, 如果有删除,如果没有不删除
            Integer num1 = userRoleMapper.queryRolesByUserId(userId);
            if(null!=num1&&num1>0){
                AssertUtil.isTrue(userRoleMapper.deleteRolesByUserId(userId)<1,"用户角色删除失败");
            }
        }
    }
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
            /***
             * 校验id 用户名
             * */
            User user1 = userMapper.queryById(id);
            //用户要修改用户名才执行用户名查询操作
            if(!user1.getUserName().equals(user.getUserName())){
                AssertUtil.isTrue(null!=userMapper.queryUserByName(user.getUserName()),"用户名已经被注册");
            }

            AssertUtil.isTrue(userMapper.update(user)<1,"用户更新失败");

            /***
             * 方案: 先全部删除原有所有角色, 在批量添加新角色
             * */
            // 1. 删除之前先查询用户是否有角色, 如果有删除,如果没有不删除
            Integer num1 = userRoleMapper.queryRolesByUserId(id);//106
            if(null!=num1 && num1>0){
                AssertUtil.isTrue(userRoleMapper.deleteRolesByUserId(id)<num1,"用户角色删除失败");
            }
        }
        /***
         * 角色添加
         * 1. 拿到roleId 的数组
         * 2. 查询出新用户的ID
         * 3. 变量数组生成所有的UserRole对象
         * 4. 批量一次性添加到数据库
         * */
        if(null!=roleIds && roleIds.length>0){
            ArrayList<UserRole> list = new ArrayList<>();

            Integer userId = userMapper.queryUserByName(user.getUserName()).getId();
            //角色id
            for(Integer roleId:roleIds){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                list.add(userRole);
            }

            AssertUtil.isTrue(userRoleMapper.saveBatch(list)<list.size(),"用户角色添加失败");
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

    public Map<String,Object> queryForPage(UserQuery baseQuery) throws DataAccessException {
        PageHelper.startPage(baseQuery.getPageNum(),baseQuery.getPageSize());
        List<UserDto> entities=userMapper.queryByParams(baseQuery);
        PageInfo<UserDto> pageInfo=new PageInfo<UserDto>(entities);
        Map<String,Object> map=new HashMap<String,Object>();

        /* 获取roleId 字符串 */
        // 2,3,4,5,6  -> [2,3,4,5,6]
        List<UserDto> userDtoList = pageInfo.getList();
        for(UserDto userDto : userDtoList){
            String roleIdsStr = userDto.getRoleIdsStr();// 2,3,4,5
            if(!StringUtils.isBlank(roleIdsStr)){
                String[] roleIdsArr = roleIdsStr.split(",");
                List<Integer> list = new ArrayList<>();
                for (int i=0; i<roleIdsArr.length; i++){
                    list.add(Integer.valueOf(roleIdsArr[i]));
                }
                userDto.setRoleIds(list);
            }
        }
        /* 把字符串变成 数字的集合 */

        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

}
