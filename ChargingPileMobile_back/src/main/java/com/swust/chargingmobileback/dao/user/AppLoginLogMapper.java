package com.swust.chargingmobileback.dao.user;

import com.swust.chargingmobileback.model.AppLoginLog;

public interface AppLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppLoginLog record);

    int insertSelective(AppLoginLog record);

    AppLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppLoginLog record);

    int updateByPrimaryKey(AppLoginLog record);
}