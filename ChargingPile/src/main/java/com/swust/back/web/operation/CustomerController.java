package com.swust.back.web.operation;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.operation.CustomerService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @RequiresPermissions("Public:add")
    @PostMapping("/add")
    public JSONObject addCustomer(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "name,phone,chargeCardId");
        return customerService.add(requestJson);
    }

    @RequiresPermissions("Public:delete")
    @PostMapping("/delete")
    public JSONObject deleteCustomer(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return customerService.delete(requestJson);
    }

    @RequiresPermissions("Public:edit")
    @PostMapping("/update")
    public JSONObject updateCustomer(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "id");
        return customerService.update(requestJson);
    }

    @RequiresPermissions("Public:read")
    @GetMapping("/list")
    public JSONObject listCustomer(HttpServletRequest request) {
        return customerService.list(CommonUtil.request2Json(request));
    }

    @PostMapping("/lockCustomer")
    public JSONObject lockCustomer(@RequestBody JSONObject jsonObject){
        return customerService.lockCustomer(jsonObject);
    }

    @PostMapping("/chargeNum")
    public JSONObject chargeNum(@RequestBody JSONObject jsonObject){
        return customerService.chargeNum(jsonObject);
    }


    @GetMapping("/depositList")
    public JSONObject listDeposit(HttpServletRequest request) {
        return customerService.listDeposit(CommonUtil.request2Json(request));
    }
}
