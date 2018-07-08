package com.swust.chargingmobileback.service.user;

import com.alibaba.fastjson.JSONObject; /**
 * @author 73559
 * @Date 2018/7/8 13:19
 * @Description 充电查询相关
 */
public interface CostService {
    JSONObject findCost(String phone, int count);
}
