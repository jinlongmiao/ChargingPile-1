package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.RateTypeMapper;
import com.swust.back.dao.operation.RateTypeMapper;
import com.swust.back.service.operation.RateTypeService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RateTypeServiceImpl implements RateTypeService{
    @Resource
    private RateTypeMapper rateTypeMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        CustomUtils.show(jsonObject);
        int count = rateTypeMapper.allCount(jsonObject);
        List<JSONObject> list = rateTypeMapper.RateTypeList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject changeStatus(JSONObject requestJson) {
        CustomUtils.show(requestJson);
        rateTypeMapper.changeStatus(requestJson);
        return CommonUtil.successJson();
    }
}
