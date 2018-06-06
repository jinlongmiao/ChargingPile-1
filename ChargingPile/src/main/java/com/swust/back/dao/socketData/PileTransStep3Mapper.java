package com.swust.back.dao.socketData;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PileTransStep3Mapper {
    int allCount(JSONObject jsonObject);

    List<JSONObject> list(JSONObject jsonObject);
}
