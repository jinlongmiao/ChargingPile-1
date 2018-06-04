package com.swust.back.web.datadict;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.datadict.BatteryTypeService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/BatteryType")
public class BatteryTypeController {
    @Resource
    private BatteryTypeService batteryTypeService;

    /**
     * 增加电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:add")
    @PostMapping("/addBatteryType")
    public JSONObject addBatteryType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "typeCode,typeName");
        return batteryTypeService.addBatteryType(requestJson);
    }

    /**
     * 删除某个电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:delete")
    @PostMapping("/deleteBatteryType")
    public JSONObject deleteBatteryType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return batteryTypeService.deleteBatteryType(requestJson);
    }

    /**
     * 编辑电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:edit")
    @PostMapping("/updateBatteryType")
    public JSONObject updateBatteryType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return batteryTypeService.updateBatteryType(requestJson);
    }

    /**
     * 电桩状态列表
     * @param request
     * @return
     */
    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listBatteryType(HttpServletRequest request) {
        return batteryTypeService.listBatteryType(CommonUtil.request2Json(request));
    }
}
