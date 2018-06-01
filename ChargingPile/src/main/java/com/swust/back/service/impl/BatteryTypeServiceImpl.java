package com.swust.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.BatteryMapper;
import com.swust.back.dao.PileMapper;
import com.swust.back.service.BatteryTypeService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BatteryTypeServiceImpl implements BatteryTypeService{
    @Resource
    private BatteryMapper batteryMapper;

    @Override
    public JSONObject addBatteryType(JSONObject requestJson) {
        batteryMapper.addBatteryType(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteBatteryType(JSONObject requestJson) {
        batteryMapper.deleteBatteryTypeById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateBatteryType(JSONObject requestJson) {
        batteryMapper.updateBatteryTypeById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listBatteryType(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = batteryMapper.typeCount();
        List<JSONObject> list = batteryMapper.batteryTypeList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
