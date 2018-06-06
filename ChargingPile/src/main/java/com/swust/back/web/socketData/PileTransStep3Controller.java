package com.swust.back.web.socketData;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.socketData.PileTransStep3Service;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/PileTransStep")
public class PileTransStep3Controller {

    @Resource
    private PileTransStep3Service pileTransStepService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listPileTransStep(HttpServletRequest request) {
        return pileTransStepService.list(CommonUtil.request2Json(request));
    }
}
