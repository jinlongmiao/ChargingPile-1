package com.swust.back.service.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.app.AccessMapper;
import com.swust.back.model.Access;
import com.swust.back.model.User;
import com.swust.back.service.app.AccessService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/05/03.
 */
@Service
@Transactional
public class AccessServiceImpl implements AccessService {
    @Resource
    private AccessMapper tAccessMapper;

    public JSONObject selectRolePermissionsArray(int roleId){

        List<JSONObject> iList = tAccessMapper.selectRolePermissionsArray(roleId);
        // 存放关于group的数据
        List<JSONObject> jList = new ArrayList<>();

        for ( JSONObject map : iList ) {
            // 将关于group的数据放入groupMap中，并修改key值
            JSONObject groupMap = new JSONObject();
            groupMap.put("id",map.get("parent_id"));
            groupMap.put("name",map.get("groupName"));
            groupMap.put("route",map.get("groupRoute"));

            if( !jList.contains(groupMap) ){
                jList.add(groupMap);
            }

            map.remove("groupName");
            map.remove("groupRoute");

        }

        jList.addAll(iList);

        return CommonUtil.successPage(jList);
    }

    public List<Access> selectByNodeId(int nodeId){
        return tAccessMapper.selectByNodeId(nodeId);
    }


    public JSONObject getPermissions(User user){
        return getUserPermissionFromDB(user);
    }


    /**
     * 从数据库中查询用户权限
     * @param user
     * @return
     */
    public JSONObject getUserPermissionFromDB(User user){
        JSONObject userPermission = new JSONObject();
        CustomUtils.show(user.getRoleId());
        // 获得路由信息
        Set<String> menuList = tAccessMapper.getMenus(user.getRoleId());
        // 获得权限信息
        List<Map<String,Object>> permissions = tAccessMapper.getPermissions(user.getRoleId());
        Set<String> permissionList = changeFromat(permissions);

        userPermission.put("menuList", menuList);
        userPermission.put("nickname",user.getNickname());
        userPermission.put("roleId",user.getRoleId());
        userPermission.put("roleName",user.getRoleName());
        userPermission.put("userId",user.getId());
        userPermission.put("permissionList", permissionList);
        return userPermission;
    }

    /**
     * 对取出来的数据进行处理
     * @param permissions
     * @return
     */
    private Set<String> changeFromat(List<Map<String,Object>> permissions){
        Set<String> iSet = new HashSet<>();
        for ( Map<String,Object> map : permissions ) {
            String temp = map.get("pName").toString() + ":" + map.get("name").toString();
            iSet.add(temp);
        }
        return iSet;
    }

}
