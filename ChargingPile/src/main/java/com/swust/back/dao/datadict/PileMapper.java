package com.swust.back.dao.datadict;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PileMapper {

    /**
     * 电桩类型列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> typeList(JSONObject jsonObject);

    /**
     * 电桩数量
     * @return
     */
    int typeCount();


    /**
     * 增加电桩类型
     * @param requestJson
     * @return
     */
    void addPileType(JSONObject requestJson);

    /**
     * 删除某个电桩类型
     * @param requestJson
     * @return
     */
    void deletePileTypeById(JSONObject requestJson);

    /**
     * 编辑电桩类型
     * @param requestJson
     * @return
     */
    void updatePileTypeById(JSONObject requestJson);


    /**
     * 增加电桩类型
     * @param requestJson
     * @return
     */
    void addPileState(JSONObject requestJson);

    void deletePileStateById(JSONObject requestJson);

    void updatePileStateById(JSONObject requestJson);

    int stateCount();

    List<JSONObject> stateList(JSONObject jsonObject);
}