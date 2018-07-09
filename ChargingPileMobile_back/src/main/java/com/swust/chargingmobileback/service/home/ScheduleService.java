package com.swust.chargingmobileback.service.home;

import com.alibaba.fastjson.JSONObject; /**
 * @author 73559
 * @Date 2018/7/9 10:32
 */
public interface ScheduleService {
    JSONObject stopCharging(String phone);
}
