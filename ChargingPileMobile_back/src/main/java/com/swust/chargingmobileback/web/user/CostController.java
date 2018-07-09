package com.swust.chargingmobileback.web.user;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.user.CostService;
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
 * @Date 2018/7/8 13:06
 * @Description 充电记录相关
 */

@RestController
@RequestMapping("/ChargingPile")
public class CostController {

    static Logger logger = LoggerFactory.getLogger(CostController.class);

    @Resource
    private CostService costService;

    @PostMapping(value = "/findCost")
    public JSONObject findCost(@RequestBody JSONObject requestJson) {
        logger.info("--------------充电记录--------------");
        String phone = requestJson.getString("phone");
        int count = requestJson.getInteger("count");
        return costService.findCost(phone,count);
    }

}
