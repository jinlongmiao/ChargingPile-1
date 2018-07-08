package com.swust.chargingmobileback.dao.user;

import com.alibaba.fastjson.JSONArray;
import com.swust.chargingmobileback.model.AppPay;

public interface AppPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppPay record);

    int insertSelective(AppPay record);

    AppPay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppPay record);

    int updateByPrimaryKey(AppPay record);

    JSONArray findPay(String phone);
}