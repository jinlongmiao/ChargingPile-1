package com.swust.chargingmobileback.service.user;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 73559
 * @Date 2018/7/8 11:15
 * @Description 充值记录
 */
public interface ChargeCostService {

    JSONObject findChargeCost(String phone);
}
