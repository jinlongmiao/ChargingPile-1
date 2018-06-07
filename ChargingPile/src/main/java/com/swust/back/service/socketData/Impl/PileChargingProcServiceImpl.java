package com.swust.back.service.socketData.Impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.socketData.PileChargingProcMapper;
import com.swust.back.service.socketData.PileChargingProcService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PileChargingProcServiceImpl implements PileChargingProcService {
    @Resource
    private PileChargingProcMapper pileChargingProcMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = pileChargingProcMapper.allCount(jsonObject);
        List<JSONObject> list = pileChargingProcMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
