package com.swust.back.dao.socketData;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PileTransMapper {
    int allCount(JSONObject jsonObject);

    List<JSONObject> list(JSONObject jsonObject);
}
