package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.ChargeQueryMapper;
import com.swust.back.service.operation.ChargeQueryService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ChargeQueryServiceImpl implements ChargeQueryService {
    @Resource
    private ChargeQueryMapper chargeQueryMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = chargeQueryMapper.allCount(jsonObject);
        List<JSONObject> list = chargeQueryMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
