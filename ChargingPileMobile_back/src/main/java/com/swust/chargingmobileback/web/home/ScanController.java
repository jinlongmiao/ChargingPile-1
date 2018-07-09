package com.swust.chargingmobileback.web.home;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.config.exception.MoneyNotEnoughException;
import com.swust.chargingmobileback.config.exception.NullJsonObjectException;
import com.swust.chargingmobileback.service.home.ScanService;
import com.swust.chargingmobileback.util.constants.CommonJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/8 14:47
 * @Description 扫码充电
 */

@RestController
@RequestMapping("/ChargingPile")
public class ScanController {

    static Logger logger = LoggerFactory.getLogger(ScanController.class);

    @Resource
    private ScanService scanService;

    /**
     * 扫码后点击开始充电的响应
     * @param requestJson
     * @return
     */
    @PostMapping(value = "/scan")
    public JSONObject findScan(@RequestBody JSONObject requestJson) {
        logger.info("-------------扫码充电-------------");
        logger.info(requestJson.toString());
        // 电话号码
        String phone = requestJson.getString("phone");
        JSONObject jsonObject = new JSONObject();
        List<String> list = null;
        try{
            // 查询电桩运行状态
            list = scanService.getCardAndMoney(phone);
            jsonObject = scanService.findPileState(requestJson,list);
        }catch (NullJsonObjectException njoe){
            // 尚未绑定账户
            logger.info( njoe.getMessage() );
            jsonObject.put("code", "3");
        }catch (MoneyNotEnoughException mnee){
            logger.info("余额不足");
            jsonObject.put("code", "31");
        }
        logger.info( "开启充电===>" +jsonObject.toString() );
        return jsonObject;
    }
}
