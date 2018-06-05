package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface AppEmptycardsService {
    JSONObject add(JSONObject requestJson);

    JSONObject delete(JSONObject requestJson);

    JSONObject list(JSONObject jsonObject);
}
