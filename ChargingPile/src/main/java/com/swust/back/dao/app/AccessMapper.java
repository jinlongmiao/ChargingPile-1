package com.swust.back.dao.app;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.Access;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AccessMapper  {
    List<JSONObject> selectRolePermissionsArray(int roleId);

    List<Access> selectByNodeId(int nodeId);

    Set<String> getMenus(int roleId);

    List<Map<String,Object>> getPermissions(int roleId);
}