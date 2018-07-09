package com.swust.chargingmobileback.dao.message;

import com.swust.chargingmobileback.model.AppMessage;

import java.util.List;

public interface AppMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMessage record);

    int insertSelective(AppMessage record);

    AppMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMessage record);

    int updateByPrimaryKey(AppMessage record);

    List<AppMessage> findAllAppMessage();
}