package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.ChargingpileMapper;
import com.swust.back.service.operation.ChargingpileService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChargingpileServiceImpl implements ChargingpileService{
    @Resource
    private ChargingpileMapper chargingpileMapper;

    @Override
    public JSONObject addChargingPile(JSONObject requestJson) {
        try {
            chargingpileMapper.addChargingpile(requestJson);
            return CommonUtil.successJson();
        }catch (DuplicateKeyException e){
            return CommonUtil.errorMsg(e.getCause().getMessage());
        }
    }

    @Override
    public JSONObject deleteChargingPile(JSONObject requestJson) {
        chargingpileMapper.deleteChargingpileById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateChargingPile(JSONObject requestJson) {
        chargingpileMapper.updateChargingpileById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listChargingPile(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        CustomUtils.show(jsonObject);
        int count = chargingpileMapper.pileCount();
        List<JSONObject> list = chargingpileMapper.chargingpileList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 取类型下拉框的值
     * @return
     */
    @Override
    public JSONObject getAllPileType() {
//        CustomUtils.show(chargingpileMapper.getAllPileType());
        List<JSONObject> iList = chargingpileMapper.getAllPileType();
        // 存放数据
        List<JSONObject> jList = new ArrayList<>();
        for( JSONObject map : iList ){
            JSONObject chargingPileInfo = new JSONObject();
            String msg = (String) map.get("typeCode") + ", " + map.get("typeName") + ", " + map.get("wi")
                    + ", " + map.get("port");
            chargingPileInfo.put("id",map.get("id"));
            chargingPileInfo.put("value",map.get("typeCode"));
            chargingPileInfo.put("label",msg);
            jList.add(chargingPileInfo);
        }
//        CustomUtils.show(jList);
        return CommonUtil.successPage(jList);
    }

    /**
     * 取充电站的值
     * @return
     */
    @Override
    public JSONObject getStationInfo() {
        List<JSONObject> iList = chargingpileMapper.getStationInfo();
        // 存放数据
//        List<JSONObject> jList = new ArrayList<>();
//        for( JSONObject map : iList ){
//            JSONObject stationInfo = new JSONObject();
//            stationInfo.put((String) map.get("stationId"),map.get("stationName"));
//            jList.add(stationInfo);
//        }
//        CustomUtils.show(iList);
        return CommonUtil.successPage(iList);
    }
}
