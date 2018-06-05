package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AppEmptycardsMapper {
    void add(JSONObject requestJson);

    void deleteAppEmptycardsById(JSONObject requestJson);

    int allCount(JSONObject requestJson);

    List<JSONObject> AppEmptycardsList(JSONObject jsonObject);
}
