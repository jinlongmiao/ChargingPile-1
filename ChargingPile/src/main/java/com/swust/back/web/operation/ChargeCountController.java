package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.ChargeCountService;
import com.swust.back.util.CommonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ChargeCount")
public class ChargeCountController {
    @Resource
    private ChargeCountService chargingCountService;

    @GetMapping("/list")
    public JSONObject getCountOff(HttpServletRequest request) {
        return chargingCountService.list(CommonUtil.request2Json(request));
    }
}
