package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.AppEmptycardsService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/AppEmptycards")
public class AppEmptycardsController {
    @Resource
    private AppEmptycardsService appEmptycardsService;

    @RequiresPermissions("Public:add")
    @PostMapping("/add")
    public JSONObject addAppEmptycards(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "chargeCardId,createUser");
        return appEmptycardsService.add(requestJson);
    }

    @RequiresPermissions("Public:delete")
    @PostMapping("/delete")
    public JSONObject deleteAppEmptycards(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return appEmptycardsService.delete(requestJson);
    }

    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listAppEmptycards(HttpServletRequest request) {
        return appEmptycardsService.list(CommonUtil.request2Json(request));
    }
}
