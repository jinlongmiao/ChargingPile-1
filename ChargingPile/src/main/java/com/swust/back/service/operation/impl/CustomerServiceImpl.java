package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.operation.CustomerMapper;
import com.swust.back.service.operation.CustomerService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;

    @Override
    public JSONObject add(JSONObject requestJson) {
        customerMapper.add(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject delete(JSONObject requestJson) {
        customerMapper.deleteCustomerById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject update(JSONObject requestJson) {
        customerMapper.updateCustomerById(requestJson);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = customerMapper.allCount(jsonObject);
        List<JSONObject> list = customerMapper.CustomerList(jsonObject);
        float sumMoney = customerMapper.getSumMoneyByCondition(jsonObject);
        CustomUtils.show(count);
        return CommonUtil.successPage(jsonObject, list, count,sumMoney);
    }

    @Override
    public JSONObject lockCustomer(JSONObject jsonObject) {
        customerMapper.lockCustomer(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject chargeNum(JSONObject jsonObject) {
        customerMapper.chargeNum(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject listDeposit(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = customerMapper.allDepositCount(jsonObject);
        List<JSONObject> list = customerMapper.depositList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
