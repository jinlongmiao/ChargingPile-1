package com.swust.back.service.datadict.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.datadict.PileMapper;
import com.swust.back.service.datadict.PileStateService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PileStateServiceImpl implements PileStateService {
    @Resource
    private PileMapper tPileTypeMapper;

    @Override
    public JSONObject addPileState(JSONObject requestJson) {
        tPileTypeMapper.addPileState(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deletePileState(JSONObject requestJson) {
        tPileTypeMapper.deletePileStateById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updatePileState(JSONObject requestJson) {
        tPileTypeMapper.updatePileStateById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listPileState(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = tPileTypeMapper.stateCount();
        List<JSONObject> list = tPileTypeMapper.stateList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
