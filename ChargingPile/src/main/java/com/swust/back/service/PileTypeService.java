package com.swust.back.service;

import com.alibaba.fastjson.JSONObject;


/**
 * 电桩类型代码
 */
public interface PileTypeService{

    /**
     * 电桩类型列表
     * @param jsonObject
     * @return
     */
    JSONObject listPileType(JSONObject jsonObject);

    /**
     * 增加电桩类型
     * @param requestJson
     * @return
     */
    JSONObject addPileType(JSONObject requestJson);

    /**
     * 删除某个电桩类型
     * @param requestJson
     * @return
     */
    JSONObject deletePileType(JSONObject requestJson);

    /**
     * 编辑电桩类型
     * @param requestJson
     * @return
     */
    JSONObject updatePileType(JSONObject requestJson);
}
