package com.swust.back.web.socketData;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.socketData.SocketDtlService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/SocketDtl")
public class SocketDtlController {
    @Resource
    private SocketDtlService socketDtlService;


    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listSocketDtl(HttpServletRequest request) {
        return socketDtlService.list(CommonUtil.request2Json(request));
    }
}
