package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.AlipaytradeMapper;
import com.swust.back.service.operation.AlipaytradeService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AlipaytradeServiceImpl implements AlipaytradeService {

    @Resource
    private AlipaytradeMapper alipaytradeMapper;

    @Override
    public JSONObject listAlipaytrade(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = alipaytradeMapper.typeCount(jsonObject);
        System.out.println("count is " + count);
        CustomUtils.show(jsonObject);
        List<JSONObject> list = alipaytradeMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }


    @Override
    public JSONObject getMoney(JSONObject requestJson) {
        return CommonUtil.successJson(alipaytradeMapper.getSumMoneyByCondition(requestJson));
    }
}
