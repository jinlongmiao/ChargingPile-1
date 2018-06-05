package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.ChargingStationService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ChargingStation")
public class ChargingStationController {
    @Resource
    private ChargingStationService chargingStationService;

    @RequiresPermissions("Public:add")
    @PostMapping("/add")
    public JSONObject addChargingStation(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "stationId,stationName,gateId,address,lat,lng,openDate");
        return chargingStationService.add(requestJson);
    }

    @RequiresPermissions("Public:delete")
    @PostMapping("/delete")
    public JSONObject deleteChargingStation(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return chargingStationService.delete(requestJson);
    }

    @RequiresPermissions("Public:edit")
    @PostMapping("/update")
    public JSONObject updateChargingStation(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return chargingStationService.update(requestJson);
    }

    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listChargingStation(HttpServletRequest request) {
        return chargingStationService.list(CommonUtil.request2Json(request));
    }
}
