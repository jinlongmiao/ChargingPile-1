package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ChargingpileService {
    JSONObject addChargingPile(JSONObject requestJson);

    JSONObject deleteChargingPile(JSONObject requestJson);

    JSONObject updateChargingPile(JSONObject requestJson);

    JSONObject listChargingPile(JSONObject jsonObject);

    JSONObject getAllPileType();

    JSONObject getStationInfo();

    JSONObject pileOff(JSONObject jsonObject);
}
