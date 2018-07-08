package com.swust.chargingmobileback.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.user.AppFindCostMapper;
import com.swust.chargingmobileback.service.user.CostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 73559
 * @Date 2018/7/8 13:19
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class CostServiceImpl implements CostService{

    @Resource
    private AppFindCostMapper appFindCostMapper;


    @Override
    public JSONObject findCost(String phone, int count) {
        JSONObject returnObj = new JSONObject();
        // 查询用户充值记录
        JSONArray array = appFindCostMapper.findCost(phone,count,6);
        // array不为空
        if ( !array.isEmpty() ) {
            if (count != 0) {
                // 非第一次请求
                returnObj.put("flag", 3);
                returnObj.put("recode", array);
            } else {
                // 第一次请求
                returnObj.put("flag", 2);
                returnObj.put("recode", array);
            }

        } else {
            if (count != 0) {
                // 刷新数据时候，提示没有更多充电记录
                returnObj.put("flag", 4);
            } else {
                // 没有充电记录
                returnObj.put("flag", 1);
            }
        }
        System.out.println("\n" + returnObj.toJSONString() + "\n" + count);
        return returnObj;
    }
}
