package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface PileServiceDataService {
    JSONObject list(JSONObject jsonObject);

    JSONObject getPiles(JSONObject jsonObject);
}
