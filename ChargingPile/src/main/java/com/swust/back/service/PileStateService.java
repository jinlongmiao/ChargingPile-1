package com.swust.back.service;

import com.alibaba.fastjson.JSONObject;

public interface PileStateService {
    /**
     * 增加电桩状态
     * @param requestJson
     * @return
     */
    JSONObject addPileState(JSONObject requestJson);

    JSONObject deletePileState(JSONObject requestJson);

    JSONObject updatePileState(JSONObject requestJson);

    JSONObject listPileState(JSONObject jsonObject);
}
