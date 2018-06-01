package com.swust.back.web;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.UserService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import com.swust.back.util.MD5;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 关于user的controller
 */
@RestController
@RequestMapping("/User")
public class UserController {


    @Resource
    private UserService userService;


    /**
     * 增加用户
     * @param requestJson
     * @return
     */
    @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username, password, nickname,  roleId");
        requestJson.put("password", MD5.MD5(requestJson.getString("password")));
        return userService.addUser(requestJson);
    }

    /**
     * 删除用户
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Role:delRoleUser")
    @PostMapping("/deleteUser")
    public JSONObject deleteUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return userService.deleteUser(requestJson);
    }

    /**
     * 更新用户
     * @param requestJson
     * @return
     */
    @PostMapping("/updateUser")
    public JSONObject updateUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "nickname,roleId, userId");
        return userService.updateUser(requestJson);
    }

    /**
     * 用户列表
     * @param request
     * @return
     */
    @GetMapping("/list")
    public JSONObject listUser(HttpServletRequest request) {
        return userService.listUser(CommonUtil.request2Json(request));
    }

    /**
     * 获取所有角色
     * @return
     */
    @GetMapping("/getAllRoles")
    public JSONObject getAllRoles() {
        return userService.getAllRoles();
    }

}
