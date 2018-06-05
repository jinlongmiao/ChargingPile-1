package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.ChargingStationMapper;
import com.swust.back.service.operation.ChargingStationService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ChargingStationServiceImpl implements ChargingStationService {

    @Resource
    private ChargingStationMapper chargingStationMapper;

    @Override
    public JSONObject add(JSONObject requestJson) {
        chargingStationMapper.add(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject delete(JSONObject requestJson) {
        chargingStationMapper.deleteChargingStationById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject update(JSONObject requestJson) {
        chargingStationMapper.updateChargingStationById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        CustomUtils.show(jsonObject);
        int count = chargingStationMapper.allCount();
        List<JSONObject> list = chargingStationMapper.chargingStationList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
