package com.swust.chargingmobileback.util.constants;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 73559
 * @Date 2018/7/7 16:26
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 */

public class PullJson {

    private JSONObject pullJson = new JSONObject();

    public JSONObject getPullJson() {
        pullJson.put("accessKeyId","LTAIy81J3Pcu1XEe");
        pullJson.put("accessKeySecret","vFprPvynAFkrnsX8WVEvyDF75T7vlI");
        pullJson.put("iphoneKey","24925453");
        pullJson.put("androidKey","24843478");
        pullJson.put("region","cn-hangzhou");
        pullJson.put("time",0.3);
        return pullJson;
    }
}
