package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.AppEmptycardsMapper;
import com.swust.back.service.operation.AppEmptycardsService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class AppEmptycardsServiceImpl implements AppEmptycardsService{
    @Resource
    private AppEmptycardsMapper appEmptycardsMapper;

    @Override
    public JSONObject add(JSONObject requestJson) {
        appEmptycardsMapper.add(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject delete(JSONObject requestJson) {
        appEmptycardsMapper.deleteAppEmptycardsById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        CustomUtils.show(jsonObject);
        int count = appEmptycardsMapper.allCount(jsonObject);
        List<JSONObject> list = appEmptycardsMapper.AppEmptycardsList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
