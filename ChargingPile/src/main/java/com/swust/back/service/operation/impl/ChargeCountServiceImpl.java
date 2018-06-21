package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.ChargeCountMapper;
import com.swust.back.service.operation.ChargeCountService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ChargeCountServiceImpl implements ChargeCountService {
    @Resource
    private ChargeCountMapper chargeCountMapper;
    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = chargeCountMapper.allCount(jsonObject);
        List<JSONObject> list = chargeCountMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
