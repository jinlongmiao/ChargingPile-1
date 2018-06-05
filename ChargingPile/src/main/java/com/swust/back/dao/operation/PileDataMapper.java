package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PileDataMapper {
    List<JSONObject> pileDataList(JSONObject jsonObject);

    int pileDataCount();
}
