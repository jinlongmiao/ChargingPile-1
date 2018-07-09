package com.swust.chargingmobileback.web.home;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.service.home.ScanService;
import com.swust.chargingmobileback.service.home.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileReader;

/**
 * @author 73559
 * @Date 2018/7/9 10:28
 * @Description 充电进度查询页面功能实现
 */

@RestController
@RequestMapping("/ChargingPile")
public class ScheduleController {

    static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Resource
    private ScheduleService scheduleService;

    /**
     * 停止充电
     * @param requestJson
     * @return
     */
    @PostMapping(value = "/stopCharging")
    public JSONObject stopCharging(@RequestBody JSONObject requestJson) {
        logger.info("", 3);
        logger.info("充电进度过来的关闭充电开始------------------------");
        logger.info("app端传递过来的值=" + requestJson.toString());
        String phone = requestJson.getString("phone");
        return scheduleService.stopCharging(phone);

    }


}
