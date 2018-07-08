package com.swust.chargingmobileback.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.user.AppPayMapper;
import com.swust.chargingmobileback.service.user.ChargeCostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 73559
 * @Date 2018/7/8 11:15
 */

@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ChargeCostServiceImpl implements ChargeCostService {

    @Resource
    private AppPayMapper appPayMapper;

    @Override
    public JSONObject findChargeCost(String phone) {
        JSONObject returnObj = new JSONObject();
        // 查询用户充值记录
        JSONArray array = appPayMapper.findPay(phone);
        if (array != null) {
            // 有充值记录
            returnObj.put("flag", true);
            returnObj.put("trade", array);
        } else {
            // 没有充值记录
            returnObj.put("flag", false);
        }
        System.out.println("\n" + returnObj.toJSONString() + "\n");
        return returnObj;
    }
}
