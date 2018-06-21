package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.PileServiceDataService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/PileServiceData")
public class PileServiceDataController {
    @Resource
    private PileServiceDataService pileServiceDataService;

    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listPileServiceData(HttpServletRequest request) {
        return pileServiceDataService.list(CommonUtil.request2Json(request));
    }


    @GetMapping("/getPiles")
    public JSONObject getPiles(HttpServletRequest request) {
        return pileServiceDataService.getPiles(CommonUtil.request2Json(request));
    }
}
