package com.shsxt.crm.interceptors;

import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xlf on 2018/9/18.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /***
         * 登陆拦截判断:
         * 1. 通过Request获取cookie中的id
         * 2. 通过id查询用户,看是否存在
         * */
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isLogin(null==userId||null==userService.queryById(userId),"用户未登录或者不存在");
        return true;//放行
    }
}
