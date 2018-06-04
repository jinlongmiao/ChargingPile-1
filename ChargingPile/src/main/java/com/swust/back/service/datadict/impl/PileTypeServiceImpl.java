package com.swust.back.service.datadict.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.datadict.PileMapper;
import com.swust.back.service.datadict.PileTypeService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/05/16.
 */
@Service
@Transactional
public class PileTypeServiceImpl implements PileTypeService {
    @Resource
    private PileMapper tPileTypeMapper;

    /**
     * 电桩类型列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listPileType(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = tPileTypeMapper.typeCount();
        List<JSONObject> list = tPileTypeMapper.typeList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 增加电桩类型
     * @param requestJson
     * @return
     */
    @Override
    public JSONObject addPileType(JSONObject requestJson) {
        tPileTypeMapper.addPileType(requestJson);
        return CommonUtil.successJson();
    }

    /**
     * 删除某个电桩类型
     * @param requestJson
     * @return
     */
    @Override
    public JSONObject deletePileType(JSONObject requestJson) {
        tPileTypeMapper.deletePileTypeById(requestJson);
        return CommonUtil.successJson();
    }

    /**
     * 编辑电桩类型
     * @param requestJson
     * @return
     */
    @Override
    public JSONObject updatePileType(JSONObject requestJson) {
        tPileTypeMapper.updatePileTypeById(requestJson);
        return CommonUtil.successJson();
    }
}
