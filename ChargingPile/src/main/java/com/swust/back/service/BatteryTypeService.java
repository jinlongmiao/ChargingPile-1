package com.swust.back.service;

import com.alibaba.fastjson.JSONObject;

public interface BatteryTypeService {
    JSONObject addBatteryType(JSONObject requestJson);

    JSONObject deleteBatteryType(JSONObject requestJson);

    JSONObject updateBatteryType(JSONObject requestJson);

    JSONObject listBatteryType(JSONObject jsonObject);
}
