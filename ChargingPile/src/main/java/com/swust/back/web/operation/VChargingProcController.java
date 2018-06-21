package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.VChargingProcService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 实时充电数据监测
 */
@RestController
@RequestMapping("/VChargingProc")
public class VChargingProcController {


    @Resource
    private VChargingProcService vChargingProcService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listVChargingProc(HttpServletRequest request) {
        return vChargingProcService.list(CommonUtil.request2Json(request));
    }
}
