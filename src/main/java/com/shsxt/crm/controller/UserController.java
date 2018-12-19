package com.shsxt.crm.controller;

import com.shsxt.crm.exceptions.ParamsException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserInfo;
import com.shsxt.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/12/18.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd){
        ResultInfo resultInfo = new ResultInfo();
        try {
            UserInfo result = userService.login(userName, userPwd);
            resultInfo.setMsg("登陆成功");
            resultInfo.setResult(result);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg(e.getMsg());
        } catch (Exception e1) {
            e1.printStackTrace();
            resultInfo.setMsg(e1.getMessage());
            resultInfo.setCode(300);
        }
        return resultInfo;
    }
}
