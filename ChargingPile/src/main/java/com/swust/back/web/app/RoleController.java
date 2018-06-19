package com.swust.back.web.app;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.app.UserService;
import com.swust.back.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Role")
public class RoleController {

    @Resource
    private UserService userService;

    /**
     * 新增角色
     * @return
     */
    @RequiresPermissions("Role:addRoleUser")
    @PostMapping("/addRoleUser")
    public JSONObject addRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
        return userService.addRole(requestJson);
    }

    /**
     * 删除角色
     * @param requestJson
     * @return
     */
    @RequiresPermissions("Role:delRoleUser")
    @PostMapping("/deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId");
        return userService.deleteRole(requestJson);
    }


    /**
     * 修改角色
     * @return
     */
    @RequiresPermissions("Role:saveRoleUser")
    @PostMapping("/saveRoleUser")
    public JSONObject updateRole(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "roleId,permissions");
        userService.updateRole(requestJson);
        return CommonUtil.successJson();
    }

    /**
     * 获取所有角色
     * @return
     */
    @GetMapping("/GetAllRoles")
    public JSONObject getAllRoles() {
        return userService.getAllRoles();
    }

    /**
     * 角色列表
     * @return
     */
    @RequiresPermissions("Role:user")
    @GetMapping("/listRole")
    public JSONObject listRole() {
        return userService.listRole();
    }


    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    @RequiresPermissions("Role:user")
    @GetMapping("/listAllPermission")
    public JSONObject listAllPermission() {
        return userService.listAllPermission();
    }





}
