package com.swust.chargingmobileback.web.message;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.message.UserAdviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ChargingPile")
public class UserAdviceController {

    static Logger logger = LoggerFactory.getLogger(UserAdviceController.class);

    @Resource
    private UserAdviceService userAdviceService;

    @PostMapping(value = "userAdvice")
    public JSONObject insertUserAdvice(@RequestBody JSONObject requestJson){

        logger.info("用户建议提交模块开始----------------------");
        JSONObject jsonObject = userAdviceService.insertUserAdvice(requestJson);
        return  jsonObject;
    }
}
