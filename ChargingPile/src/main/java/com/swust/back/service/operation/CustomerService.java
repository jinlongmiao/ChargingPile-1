package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface CustomerService {
    JSONObject add(JSONObject requestJson);

    JSONObject delete(JSONObject requestJson);

    JSONObject update(JSONObject requestJson);

    JSONObject list(JSONObject jsonObject);

    JSONObject lockCustomer(JSONObject jsonObject);

    JSONObject chargeNum(JSONObject jsonObject);

    JSONObject listDeposit(JSONObject jsonObject);
}
