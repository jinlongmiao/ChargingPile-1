package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface ChargingStationService {
    JSONObject add(JSONObject requestJson);

    JSONObject delete(JSONObject requestJson);

    JSONObject update(JSONObject requestJson);

    JSONObject list(JSONObject jsonObject);
}
