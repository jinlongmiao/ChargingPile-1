package com.swust.chargingmobileback.util.constants;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 73559
 * @Date 2018/7/8 14:59
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 */
public class CommonJson {

    public static JSONObject getchargingConfigJson() {
        JSONObject chargingConfigJson = new JSONObject();
        chargingConfigJson.put("ip","47.95.210.136");
        chargingConfigJson.put("port","8089");
        return chargingConfigJson;
    }
}
