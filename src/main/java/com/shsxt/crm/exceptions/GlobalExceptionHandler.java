package com.shsxt.crm.exceptions;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by xlf on 2018/9/18.
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception ex) {

        ModelAndView mv = createDefModelAndView(request);
        mv.addObject("errorMsg", "系统繁忙");

        /***
         * 区分: 页面请求 还是 json请求
         * 依据: 是否含有@ResponseBody注解
         *   (1) 有,代表时json接口
         *   (2) 没有, 页面跳转接口
         * */
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
            if(null==responseBody){
                // 没有注解, 页面请求
                if(ex instanceof ParamsException){
                    ParamsException e = (ParamsException) ex;
                    mv.addObject("errorMsg",e.getMsg());
                }
                if(ex instanceof LoginException){
                    LoginException e = (LoginException) ex;
                    mv.addObject("errorMsg",e.getMsg());
                }
                return mv;
            }else{
                // 有注解, json请求
                ResultInfo resultInfo = new ResultInfo();

                if (ex instanceof ParamsException){
                    ParamsException e = (ParamsException) ex;
                    resultInfo.setCode(e.getCode());
                    resultInfo.setMsg(e.getMsg());
                }

                if (ex instanceof LoginException){
                    LoginException e = (LoginException) ex;
                    resultInfo.setCode(e.getCode());
                    resultInfo.setMsg(e.getMsg());
                }

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");

                PrintWriter pw = null;

                try {
                    pw = response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (null!=pw){
                        pw.close();
                    }
                }
                return null;
            }
        }
        return null;
    }

    private ModelAndView createDefModelAndView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("ctx", request.getContextPath());
        mv.addObject("uri", request.getRequestURI());
        return mv;
    }
}
