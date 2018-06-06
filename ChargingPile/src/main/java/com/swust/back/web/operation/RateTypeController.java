package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.RateTypeService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/RateType")
public class RateTypeController {
    @Resource
    private RateTypeService rateTypeService;

    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listRateType(HttpServletRequest request) {
        return rateTypeService.list(CommonUtil.request2Json(request));
    }

    @PostMapping("/changeStatus")
    public JSONObject changeStatus(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return rateTypeService.changeStatus(requestJson);
    }
}
