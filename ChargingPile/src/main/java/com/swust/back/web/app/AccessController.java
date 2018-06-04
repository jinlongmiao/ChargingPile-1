package com.swust.back.web.app;


import com.alibaba.fastjson.JSONObject;
import com.swust.back.service.app.AccessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* Created by CodeGenerator on 2018/05/03.
*/
@RestController
public class AccessController {
    @Resource
    private AccessService accessService;

//    static Logger logger = Logger.getLogger(AccessController.class.getName());


    /**
     * 通过roleId查询到相关的权限
     * @param roleId
     * @return
     */
    @GetMapping("/getPermissions/{roleId}/{account}")
    public JSONObject getRolePermissions(@PathVariable("roleId") Integer roleId, @PathVariable("account") String account,
                                         HttpServletRequest request)
    {
        return accessService.selectRolePermissionsArray(roleId);
    }
}
