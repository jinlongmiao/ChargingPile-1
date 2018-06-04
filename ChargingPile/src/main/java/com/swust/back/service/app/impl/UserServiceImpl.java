package com.swust.back.service.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.app.UserMapper;
import com.swust.back.model.User;
import com.swust.back.service.app.UserService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import com.swust.back.util.MD5;
import com.swust.back.util.constants.ErrorEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;



@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public User selectUserByAccount(String account){
        return userMapper.selectUserByAccount(account);
    }


    /**
     * 用户列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = userMapper.countUser(jsonObject);
        List<JSONObject> list = userMapper.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 添加用户
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int exist = userMapper.queryExistUsername(jsonObject);
        if (exist > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        userMapper.addUser(jsonObject);
        userMapper.addRoleUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 删除用户
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject deleteUser(JSONObject jsonObject) {
        userMapper.deleteByIdInUser(jsonObject);
        userMapper.deleteByIdInRoleUser(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 修改用户
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        if(!jsonObject.getString("password").equals(""))
            jsonObject.put("password", MD5.MD5(jsonObject.getString("password")));
        if( userMapper.queryExistUserIdInRoleUser(jsonObject) <= 0)
            userMapper.addRoleUser(jsonObject);
        userMapper.updateUser(jsonObject);
        userMapper.updateRoleUser(jsonObject);
        return CommonUtil.successJson();
    }



    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     * @return
     */
    @Override
    public JSONObject getAllRoles() {
        List<JSONObject> roles = userMapper.getAllRoles();
        return CommonUtil.successPage(roles);
    }

    /**
     * 角色列表
     * @return
     */
    @Override
    public JSONObject listRole() {
        List<JSONObject> roles = userMapper.listRole();
        JSONObject returnData = new JSONObject();
        returnData.put("list", roles);
        return CommonUtil.successJson(returnData);
    }

    /**
     * 查询所有权限, 给角色分配权限时调用
     * @return
     */
    @Override
    public JSONObject listAllPermission() {
        List<JSONObject> permissions = userMapper.listAllPermission();
        JSONObject returnData = new JSONObject();
        returnData.put("list", permissions);
        return CommonUtil.successJson(returnData);
    }

    /**
     * 修改角色
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(JSONObject jsonObject) {
        CustomUtils.show(jsonObject);
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleInfo = userMapper.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleInfo.get("permissionIds");
        //修改角色名称
        dealRoleName(jsonObject, roleInfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
    }

    /**
     * 修改角色名称
     * @param paramJson
     * @param roleInfo
     */
    private void dealRoleName(JSONObject paramJson, JSONObject roleInfo) {
        String roleName = paramJson.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            userMapper.updateRoleName(paramJson);
        }
    }

    /**
     * 为角色添加新权限
     * @param newPerms
     * @param oldPerms
     */
    private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }

//        CustomUtils.show("角色的新权限：",waitInsert);

        if (waitInsert.size() > 0) {
            userMapper.insertRolePermission(roleId, waitInsert);
        }
    }

    /**
     * 删除角色 旧的 不再拥有的权限
     * @param roleId
     * @param newPerms
     * @param oldPerms
     */
    private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }

//        CustomUtils.show("角色不再拥有的权限：",waitRemove);
//        System.out.println("roleId =============> " + roleId);

        if (waitRemove.size() > 0) {
            userMapper.removeOldPermission(roleId, waitRemove);
        }

    }

    /**
     * 添加角色
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject addRole(JSONObject jsonObject) {

        userMapper.insertRole(jsonObject);
//        CustomUtils.show("是否返回roleId===>" + jsonObject);
        userMapper.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));

        return CommonUtil.successJson();
    }

    /**
     * 删除角色
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject deleteRole(JSONObject jsonObject) {
        JSONObject roleInfo = userMapper.getRoleAllInfo(jsonObject);
        List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
        if (users != null && users.size() > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008);
        }
        userMapper.removeRole(jsonObject);
        userMapper.removeRoleAllPermission(jsonObject);
        return CommonUtil.successJson();
    }

}
