package com.swust.back.web;
import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.UserLogService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* Created by CodeGenerator on 2018/05/07.
*/
@RestController
@RequestMapping("/UserLog")
public class UserLogController {
    @Resource
    private UserLogService userLogService;


    @GetMapping("/List")
    public JSONObject listUserLog(HttpServletRequest request){
        CustomUtils.show(CommonUtil.request2Json(request));
        return userLogService.listUserLog(CommonUtil.request2Json(request));
    }

}
