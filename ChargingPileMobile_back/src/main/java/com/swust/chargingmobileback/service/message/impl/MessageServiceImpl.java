package com.swust.chargingmobileback.service.message.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.message.AppMessageMapper;
import com.swust.chargingmobileback.model.AppMessage;
import com.swust.chargingmobileback.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService{

    static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    private AppMessageMapper messageMapper;

    /**
     *
     * @return 返回查询到全部系统消息
     */
    public JSONObject findAllAppMessage(){

        List<AppMessage> messages = messageMapper.findAllAppMessage();

        JSONObject fatherJson = new JSONObject();
        JSONArray array = new JSONArray();
        if (messages.size() > 0) {
            fatherJson.put("flag", true);
            for (int i = 0; i < messages.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("title", messages.get(i).getTitle());
                obj.put("content", messages.get(i).getContent());
                obj.put("time",messages.get(i).getTime());
                array.add(obj);
            }
            fatherJson.put("msg", array);
        } else {
            fatherJson.put("flag", false);
        }

        logger.info(fatherJson.toString());

        return fatherJson;
    }
}
