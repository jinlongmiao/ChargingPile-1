package com.swust.back.service.operation;

import com.alibaba.fastjson.JSONObject;

public interface AlipaytradeService {

    JSONObject listAlipaytrade(JSONObject jsonObject);



    JSONObject getMoney(JSONObject requestJson);
}
