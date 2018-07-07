package com.swust.chargingmobileback.dao.user;

import com.swust.chargingmobileback.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Short id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);
}