package com.swust.back.web.datadict;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.datadict.AreaService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Area")
public class AreaController {

    @Resource
    private AreaService areaService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listArea(HttpServletRequest request) {
        return areaService.list(CommonUtil.request2Json(request));
    }
}
