package com.swust.chargingmobileback.dao.user;

import com.alibaba.fastjson.JSONArray;
import com.swust.chargingmobileback.model.AppFindCost;
import org.apache.ibatis.annotations.Param;

public interface AppFindCostMapper {
    int insert(AppFindCost record);

    int insertSelective(AppFindCost record);

    /**
     * 查询充电记录
     * @param phone
     * @param offSet
     * @param pageRow
     * @return
     */
    JSONArray findCost(@Param("phone") String phone,@Param("offSet") int offSet,
                       @Param("pageRow") int pageRow);
}