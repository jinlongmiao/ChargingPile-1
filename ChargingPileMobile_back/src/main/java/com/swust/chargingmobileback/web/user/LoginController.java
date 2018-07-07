package com.swust.chargingmobileback.web.user;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.service.user.LoginService;
import com.swust.chargingmobileback.util.tools.IpAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * @author 73559
 * 登陆
 */
@RestController
@RequestMapping("/ChargingPile")
public class LoginController {
    static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    @PostMapping(value = "/userLogin")
    public JSONObject login(@RequestBody JSONObject requestJson,HttpServletRequest request) {
        logger.info("用户登陆模块开始----------------------");
        return loginService.auth(requestJson,request);
    }

}
