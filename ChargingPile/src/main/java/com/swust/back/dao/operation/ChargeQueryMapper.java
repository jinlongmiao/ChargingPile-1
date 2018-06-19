package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ChargeQueryMapper {
    int allCount(JSONObject jsonObject);

    List<JSONObject> list(JSONObject jsonObject);
}
