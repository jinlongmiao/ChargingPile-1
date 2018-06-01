package com.swust.back.service;


import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.UserLog;

/**
 * Created by CodeGenerator on 2018/05/07.
 */
public interface UserLogService {

    void addLog(UserLog userLog);

    JSONObject listUserLog(JSONObject jsonObject);

}
