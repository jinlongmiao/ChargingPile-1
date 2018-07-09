package com.swust.chargingmobileback.dao.message;

import com.swust.chargingmobileback.model.UserAdvice;

public interface UserAdviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAdvice record);

    int insertSelective(UserAdvice record);

    UserAdvice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAdvice record);

    int updateByPrimaryKey(UserAdvice record);
}