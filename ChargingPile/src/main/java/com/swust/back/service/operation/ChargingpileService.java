package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface ChargingpileService {
    JSONObject addChargingPile(JSONObject requestJson);

    JSONObject deleteChargingPile(JSONObject requestJson);

    JSONObject updateChargingPile(JSONObject requestJson);

    JSONObject listChargingPile(JSONObject jsonObject);

    JSONObject getAllPileType();

    JSONObject getStationInfo();
}
