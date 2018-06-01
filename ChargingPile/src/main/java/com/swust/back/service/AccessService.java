package com.swust.back.service;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.Access;
import com.swust.back.model.User;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/05/03.
 */
public interface AccessService  {

    JSONObject selectRolePermissionsArray(int roleId);

    List<Access> selectByNodeId(int nodeId);

    JSONObject getPermissions(User user);
}
