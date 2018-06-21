package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.VChargingProcMapper;
import com.swust.back.service.operation.VChargingProcService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class VChargingProcServiceImpl implements VChargingProcService {

    @Resource
    private VChargingProcMapper vChargingProcMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = vChargingProcMapper.allCount(jsonObject);
        List<JSONObject> list = vChargingProcMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
