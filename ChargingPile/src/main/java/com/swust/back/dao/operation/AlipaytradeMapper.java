package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AlipaytradeMapper {
    int typeCount(JSONObject jsonObject);

    List<JSONObject> list(JSONObject jsonObject);

    float getSumMoneyByCondition(JSONObject requestJson);
}
