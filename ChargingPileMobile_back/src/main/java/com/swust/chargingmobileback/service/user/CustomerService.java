package com.swust.chargingmobileback.service.user;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 73559
 * @Date 2018/7/8 10:04
 * @Description Custom表的操作相关
 */
public interface CustomerService {
    /**
     * 查询余额
     * @param phone
     * @return
     */
    JSONObject findMoney(String phone);
}
