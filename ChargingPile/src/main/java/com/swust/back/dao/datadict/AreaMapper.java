package com.swust.back.dao.datadict;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AreaMapper {
    int allCount(JSONObject jsonObject);

    List<JSONObject> list(JSONObject jsonObject);
}
