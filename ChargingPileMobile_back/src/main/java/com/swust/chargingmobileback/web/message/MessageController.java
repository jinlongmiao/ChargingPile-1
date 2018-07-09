package com.swust.chargingmobileback.web.message;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ChargingPile")
public class MessageController {

    static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageService messageService;

    @PostMapping(value = "/message")
    public JSONObject findAllAppMessage(){

        logger.info("系统消息查询模块开始----------------------");
        JSONObject jsonObject = messageService.findAllAppMessage();
        return jsonObject;
    }
}
