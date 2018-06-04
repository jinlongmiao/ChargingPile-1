package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.AlipaytradeService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Alipaytrade")
public class AlipaytradeController {
    @Resource
    private AlipaytradeService alipaytradeService;

    /**
     * 列表
     * @param request
     * @return
     */
    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listAlipaytrade(HttpServletRequest request) {
        return alipaytradeService.listAlipaytrade(CommonUtil.request2Json(request));
    }


    @GetMapping("/getMoney")
    public JSONObject getMoney(HttpServletRequest request) {
        return alipaytradeService.getMoney(CommonUtil.request2Json(request));
    }
}
