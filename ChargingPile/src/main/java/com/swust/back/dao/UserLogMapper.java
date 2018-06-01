package com.swust.back.dao;


import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.UserLog;

import java.util.List;

public interface UserLogMapper  {
    /**
     * 增加日志
     * @param userLog
     */
    void addLog(UserLog userLog);

    /**
     * 统计日志数量
      * @param jsonObject
     * @return
     */
    int countLogs(JSONObject jsonObject);

    /**
     * 日志列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listLogs(JSONObject jsonObject);
}