package com.swust.chargingmobileback.config.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * s@author 73559
 * @Date 2018/7/8 15:25
 * @description: 本系统使用的自定义错误类
 * 比如在校验参数时,如果不符合要求,可以抛出此错误类
 * 拦截器可以统一拦截此错误,将其中json返回给前端
 */
public class CommonException extends RuntimeException {
    private JSONObject resultJson;

    private String result;

    /**
     * 调用时可以在任何代码处直接throws这个Exception,
     * 都会统一被拦截,并封装好json返回给前台
     */
    public CommonException(JSONObject resultJson) {
        this.resultJson = resultJson;
    }

    public CommonException(String result) {
        super(result);
    }

    public JSONObject getResultJson() {
        return resultJson;
    }

    public String getResult() {
        return result;
    }
}
