package com.swust.back.dao.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CustomerMapper {
    void add(JSONObject requestJson);

    void deleteCustomerById(JSONObject requestJson);

    void updateCustomerById(JSONObject requestJson);

    int allCount(JSONObject jsonObject);

    List<JSONObject> CustomerList(JSONObject jsonObject);

    float getSumMoneyByCondition(JSONObject jsonObject);

    void lockCustomer(JSONObject jsonObject);

    void chargeNum(JSONObject jsonObject);

    int allDepositCount(JSONObject jsonObject);

    List<JSONObject> depositList(JSONObject jsonObject);
}
