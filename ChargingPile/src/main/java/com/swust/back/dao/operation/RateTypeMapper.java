package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface RateTypeMapper {
    int allCount(JSONObject jsonObject);

    List<JSONObject> RateTypeList(JSONObject jsonObject);

    void changeStatus(JSONObject requestJson);
}
