package com.swust.back.dao.datadict;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BatteryMapper {
    void addBatteryType(JSONObject requestJson);

    void deleteBatteryTypeById(JSONObject requestJson);

    void updateBatteryTypeById(JSONObject requestJson);

    int typeCount();

    List<JSONObject> batteryTypeList(JSONObject jsonObject);

}
