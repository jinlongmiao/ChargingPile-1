package com.swust.chargingmobileback.web.user;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.user.CustomerService;
import com.swust.chargingmobileback.service.user.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 73559
 * @Date 2018/7/8 10:00
 * @Description 余额查询
 */

@RestController
@RequestMapping("/ChargingPile")
public class MoneyController {

    static Logger logger = LoggerFactory.getLogger(MoneyController.class);

    @Resource
    private CustomerService customerService;

    @PostMapping(value = "/findMoney")
    public JSONObject findMoney(@RequestBody JSONObject requestJson) {
        logger.info("--------------查询余额开始----------------");
        String phone = requestJson.getString("phone");
        return customerService.findMoney(phone);
    }
}
