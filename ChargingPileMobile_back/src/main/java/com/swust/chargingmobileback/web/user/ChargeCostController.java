package com.swust.chargingmobileback.web.user;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.user.ChargeCostService;
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
 * @Date 2018/7/8 11:06
 * @Description 充值记录
 */

@RestController
@RequestMapping("/ChargingPile")
public class ChargeCostController {

    static Logger logger = LoggerFactory.getLogger(ChargeCostController.class);

    @Resource
    private ChargeCostService chargeCostService;

    @PostMapping(value = "/findChargeCost")
    public JSONObject findChargeCost(@RequestBody JSONObject requestJson) {
        logger.info("-------------充值记录查询-------------");
        String phone = requestJson.getString("phone");
        return chargeCostService.findChargeCost(phone);
    }

}
