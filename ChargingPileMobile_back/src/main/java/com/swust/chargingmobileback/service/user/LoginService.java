package com.swust.chargingmobileback.service.user;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 73559
 * @Date 2018/7/7 9:37
 */
public interface LoginService {

    JSONObject auth(JSONObject requestObj, HttpServletRequest request);

}
