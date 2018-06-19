package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ChargingpileMapper {
    void addChargingpile(JSONObject requestJson);

    void deleteChargingpileById(JSONObject requestJson);

    void updateChargingpileById(JSONObject requestJson);

    int pileCount();

    List<JSONObject> chargingpileList(JSONObject jsonObject);

    List<JSONObject> getAllPileType();

    List<JSONObject> getStationInfo();

    List<JSONObject> getPileOff(JSONObject jsonObject);

    int pileOffCount(JSONObject jsonObject);
}
