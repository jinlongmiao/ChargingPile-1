package com.swust.back.web.datadict;


import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.datadict.PileStateService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 电桩状态代码
 */
@RestController
@RequestMapping("/PileState")
public class PileStateController {

    @Resource
    private PileStateService pileStateService;

    /**
     * 增加电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:add")
    @PostMapping("/addPileState")
    public JSONObject addPileState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "stateCode,stateName");
        return pileStateService.addPileState(requestJson);
    }

    /**
     * 删除某个电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:delete")
    @PostMapping("/deletePileState")
    public JSONObject deletePileState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return pileStateService.deletePileState(requestJson);
    }

    /**
     * 编辑电桩状态
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:edit")
    @PostMapping("/updatePileState")
    public JSONObject updatePileState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return pileStateService.updatePileState(requestJson);
    }

    /**
     * 电桩状态列表
     * @param request
     * @return
     */
    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listPileState(HttpServletRequest request) {
        return pileStateService.listPileState(CommonUtil.request2Json(request));
    }
}
