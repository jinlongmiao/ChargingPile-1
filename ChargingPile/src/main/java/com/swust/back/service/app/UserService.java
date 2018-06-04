package com.swust.back.service.app;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.model.User;


/**
 * Created by CodeGenerator on 2018/04/26.
 */
public interface UserService {

    User selectUserByAccount(String account);

    /**
     * 用户列表
     * @param jsonObject
     * @return
     */
    JSONObject listUser(JSONObject jsonObject);

    /**
     * 添加用户
     * @param jsonObject
     * @return
     */
    JSONObject addUser(JSONObject jsonObject);

    /**
     * 删除用户
     * @param jsonObject
     * @return
     */
    JSONObject deleteUser(JSONObject jsonObject);

    /**
     * 修改用户
     * @param jsonObject
     * @return
     */
    JSONObject updateUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     * @return
     */
    JSONObject getAllRoles();


    /**
     * 角色列表
     * @return
     */
    JSONObject listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     * @return
     */
    JSONObject listAllPermission();

    /**
     * 修改角色
     * @param jsonObject
     * @return
     */
    void updateRole(JSONObject jsonObject);

    /**
     * 添加角色
     * @param jsonObject
     * @return
     */
    JSONObject addRole(JSONObject jsonObject);

    /**
     * 删除角色
     *
     * @param jsonObject
     * @return
     */
    JSONObject deleteRole(JSONObject jsonObject);
}
