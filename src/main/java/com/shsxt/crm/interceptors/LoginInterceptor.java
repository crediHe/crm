package com.shsxt.crm.interceptors;

import com.shsxt.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/12/20.
 */
public class LoginInterceptor {
    @Autowired
    private UserService userService;

   /* public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        *//***
         * 登陆拦截判断:
         * 1. 通过Request获取cookie中的id
         * 2. 通过id查询用户,看是否存在
         * *//*
    }*/
}
