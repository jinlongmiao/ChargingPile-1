package com.swust.chargingmobileback.service.user.impl;

import com.swust.chargingmobileback.dao.user.AppUsersMapper;
import com.swust.chargingmobileback.model.AppUsers;
import com.swust.chargingmobileback.service.user.UserService;

import javax.annotation.Resource;

/**
 * @author 73559
 * @Date 2018/7/7 12:44
 */
public class UserServiceImpl implements UserService {
    @Resource
    private AppUsersMapper appUsersMapper;

    @Override
    public int updateByPrimaryKey(AppUsers record) {
        return appUsersMapper.updateByPrimaryKey(record);
    }
}
