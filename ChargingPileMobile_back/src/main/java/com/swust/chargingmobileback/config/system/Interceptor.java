package com.swust.chargingmobileback.config.system;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.web.user.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

/**
 * @author 73559
 * 自定义拦截器
 * 拦截时机 Filter pre -> service -> dispatcher -> preHandle ->controller
 *  ->postHandle - > afterCompletion -> FilterAfter
 */

public class Interceptor implements HandlerInterceptor {
    static Logger logger = LoggerFactory.getLogger(Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
//        logger.info("----------拦截器preHandle--------------");
//        JSONObject requestObj = new JSONObject();
//
//        requestObj = JSONObject.parseObject(httpServletRequest.getParameter("jsonstring"));
//        // logger.info(httpServletRequest.getParameter("jsonstring"));
//        httpServletRequest.setAttribute("requsetObj", requestObj);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }

}
