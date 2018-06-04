package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.ChargingpileService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ChargingPile")
public class ChargingpileController {

    @Resource
    private ChargingpileService chargingpileService;

    /**
     * 增加充电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:add")
    @PostMapping("/addChargingPile")
    public JSONObject addChargingPile(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "gateId,pileId");
        return chargingpileService.addChargingPile(requestJson);
    }

    /**
     * 删除某个充电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:delete")
    @PostMapping("/deleteChargingPile")
    public JSONObject deleteChargingPile(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return chargingpileService.deleteChargingPile(requestJson);
    }

    /**
     * 编辑充电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:edit")
    @PostMapping("/updateChargingPile")
    public JSONObject updateChargingPile(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return chargingpileService.updateChargingPile(requestJson);
    }

    /**
     * 充电桩状态列表
     * @param request
     * @return
     */
    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listChargingPile(HttpServletRequest request) {
        return chargingpileService.listChargingPile(CommonUtil.request2Json(request));
    }

    /**
     * 所有的充电站位置
     * @return
     */
    @GetMapping("/getAllPileType")
    public JSONObject getAllPileType() {
        return chargingpileService.getAllPileType();
    }

    /**
     * 所有的状态码信息
     * @return
     */
    @GetMapping("/getStationInfo")
    public JSONObject getStationInfo(){
        return chargingpileService.getStationInfo();
    }
}
