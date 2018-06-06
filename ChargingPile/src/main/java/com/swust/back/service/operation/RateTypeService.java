package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface RateTypeService {
    JSONObject list(JSONObject jsonObject);

    JSONObject changeStatus(JSONObject requestJson);
}
