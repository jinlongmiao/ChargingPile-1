package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ChargingStationMapper {
    void add(JSONObject requestJson);

    void deleteChargingStationById(JSONObject requestJson);

    void updateChargingStationById(JSONObject requestJson);

    int allCount();

    List<JSONObject> chargingStationList(JSONObject jsonObject);
}
