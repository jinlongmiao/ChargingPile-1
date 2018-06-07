package com.swust.back.web.socketData;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.socketData.PileChargingProcService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/PileChargingProc")
public class PileChargingProcController {
    @Resource
    private PileChargingProcService pileChargingProcService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listPileChargingProc(HttpServletRequest request) {
        return pileChargingProcService.list(CommonUtil.request2Json(request));
    }
}
