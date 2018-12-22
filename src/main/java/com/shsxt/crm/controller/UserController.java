package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/18.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "user";
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd){
      UserInfo userInfo = userService.login(userName,userPwd);
      return success("登陆成功",userInfo);
    }

    @RequestMapping("updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(String oldPassword, String newPassword,
                                    String confirmPassword, HttpServletRequest request){
        ResultInfo resultInfo = new ResultInfo();
        //通过用户登录cookie信息中获取id
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPwd(oldPassword,newPassword,confirmPassword,id);
        return success("密码更新成功");

    }

    @RequestMapping("queryCustomerMamagers")
    @ResponseBody
    public List<Map> queryCustomerMamagers(){
        return userService.queryCustomerMamagers();
    }

    @RequestMapping("queryUsersByParams")
    @ResponseBody
    public Map<String, Object> queryUsersByParams(UserQuery query,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer rows){
        query.setPageNum(page);
        query.setPageSize(rows);
        return userService.queryForPage(query);
    }

    @RequestMapping("saveOrUpdateUser")
    @ResponseBody
    public ResultInfo saveOrUpdateUser(User user,Integer[] roleIds){
        userService.saveOrUpdate(user,roleIds);
        return success("操作成功");
    }

    @RequestMapping("deleteUser")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return success("操作成功");
    }
}
