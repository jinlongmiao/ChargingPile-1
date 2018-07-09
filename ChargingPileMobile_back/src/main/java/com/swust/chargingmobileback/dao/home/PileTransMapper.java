package com.swust.chargingmobileback.dao.home;

import com.swust.chargingmobileback.model.PileTrans;

import java.util.List;

public interface PileTransMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PileTrans record);

    int insertSelective(PileTrans record);

    PileTrans selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PileTrans record);

    int updateByPrimaryKey(PileTrans record);

    List<PileTrans> stopCharging(String lastTransUid);
}