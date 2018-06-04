package com.swust.back.web.datadict;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.datadict.PileTypeService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* 电桩类型代码
*/
@RestController
@RequestMapping("/PileType")
public class PileTypeController {
    @Resource
    private PileTypeService pileTypeService;

    /**
     * 增加电桩类型
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:add")
    @PostMapping("/addPileType")
    public JSONObject addPileType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "typeCode,typeName,wi,port,type");
        return pileTypeService.addPileType(requestJson);
    }

    /**
     * 删除某个电桩类型
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:delete")
    @PostMapping("/deletePileType")
    public JSONObject deletePileType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return pileTypeService.deletePileType(requestJson);
    }

    /**
     * 编辑电桩类型
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Public:edit")
    @PostMapping("/updatePileType")
    public JSONObject updatePileType(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return pileTypeService.updatePileType(requestJson);
    }

    /**
     * 电桩类型列表
     * @param request
     * @return
     */
    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listPileType(HttpServletRequest request) {
        return pileTypeService.listPileType(CommonUtil.request2Json(request));
    }
}
