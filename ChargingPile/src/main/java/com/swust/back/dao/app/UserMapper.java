package com.swust.back.dao.app;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.swust.back.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User selectUser(Integer id);
    User selectUserByAccount(String account);


    /**
     * 查询用户数量
     * @param jsonObject
     * @return
     */
    int countUser(JSONObject jsonObject);

    /**
     * 校验用户名是否已存在
     * @param jsonObject
     * @return
     */
    int queryExistUsername(JSONObject jsonObject);

    /**
     * 校验用户是否在t_role_user
     */
    int queryExistUserIdInRoleUser(JSONObject jsonObject);

    int queryExistRole(JSONObject jsonObject);

    int queryExistRoleUser(JSONObject jsonObject);
    /**
     * 新增用户
     * @param jsonObject
     * @return
     */
    int addUser(JSONObject jsonObject);

    /**
     * 新增角色用户对应关系
     * @param jsonObject
     * @return
     */
    int addRoleUser(JSONObject jsonObject);

    /**
     * 修改用户
     * @param jsonObject
     * @return
     */
    int updateUser(JSONObject jsonObject);

    /**
     * 修改角色用户对应关系
     * @param jsonObject
     * @return
     */
    int updateRoleUser(JSONObject jsonObject);

    /**
     * 查询用户列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listUser(JSONObject jsonObject);

    /**
     * 查询所有的角色
     * 在添加/修改用户的时候要使用此方法
     * @return
     */
    List<JSONObject> getAllRoles();

    /**
     * 角色列表
     *
     * @return
     */
    List<JSONObject> listRole();

    /**
     * 查询所有权限, 给角色分配权限时调用
     *
     * @return
     */
    List<JSONObject> listAllPermission();

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     * @param jsonObject
     * @return
     */
    JSONObject getRoleAllInfo(JSONObject jsonObject);

    /**
     * 删除角色
     * @param jsonObject
     * @return
     */
    int removeRole(JSONObject jsonObject);

    /**
     * 修改角色名称
     *
     * @param jsonObject
     * @return
     */
    int updateRoleName(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     * @return
     */
    int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 删除旧权限
     * @param roleId
     * @param permissions
     * @return
     */
    int removeOldPermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);


    /**
     * 新增角色
     * @param jsonObject
     * @return
     */
    int insertRole(JSONObject jsonObject);

    /**
     * 删除本角色全部权限
     * @param jsonObject
     * @return
     */
    int removeRoleAllPermission(JSONObject jsonObject);

    // 通过ID删除本角色
    void deleteByIdInUser(JSONObject jsonObject);

    void deleteByIdInRoleUser(JSONObject jsonObject);

    List<Integer> getUsers(JSONObject jsonObject);

    void insertRoleUser(@Param("roleId") String roleId, @Param("users") List<Integer> waitInsert);

    void removeRoleUser(@Param("roleId") String roleId, @Param("users") List<Integer> waitRemove);
}