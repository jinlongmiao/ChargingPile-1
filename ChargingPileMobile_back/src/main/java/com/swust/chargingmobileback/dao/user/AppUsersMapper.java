package com.swust.chargingmobileback.dao.user;

import com.swust.chargingmobileback.model.AppUsers;

public interface AppUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppUsers record);

    int insertSelective(AppUsers record);

    AppUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppUsers record);

    int updateByPrimaryKey(AppUsers record);

    AppUsers findUserByPhone(String phone);
}