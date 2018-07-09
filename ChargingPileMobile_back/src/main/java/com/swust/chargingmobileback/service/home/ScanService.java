package com.swust.chargingmobileback.service.home;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/8 15:21
 * @Description 扫码充电
 */
public interface ScanService {
    List<String> getCardAndMoney(String phone);

    JSONObject findPileState(JSONObject jsonObject, List<String> list);
}
