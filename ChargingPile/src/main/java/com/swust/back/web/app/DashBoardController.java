package com.swust.back.web.app;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.app.DashBoardService;
import com.swust.back.service.operation.PileServiceDataService;
import com.swust.back.util.CommonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/Board")
public class DashBoardController {

    @Resource
    private DashBoardService dashBoardService;

    @GetMapping("/getPiles")
    public JSONObject getPiles(HttpServletRequest request) {
        return dashBoardService.getPiles(CommonUtil.request2Json(request));
    }
}
