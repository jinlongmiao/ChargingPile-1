package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.ChargeQueryService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ChargeQuery")
public class ChargeQueryController {
    @Resource
    private ChargeQueryService chargeQueryService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listChargeQuery(HttpServletRequest request) {
        return chargeQueryService.list(CommonUtil.request2Json(request));
    }
}
