package com.swust.back.service;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.User;

public interface LoginService {

    JSONObject login(User user);



    JSONObject getInfo();

    /**
     * 退出登录
     * @return
     */
    JSONObject logout();

}
