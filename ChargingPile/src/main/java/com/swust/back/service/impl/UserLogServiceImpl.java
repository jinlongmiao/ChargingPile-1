package com.swust.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.UserLogMapper;
import com.swust.back.model.UserLog;
import com.swust.back.service.UserLogService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/05/07.
 */
@Service
@Transactional
public class UserLogServiceImpl  implements UserLogService {
    @Resource
    private UserLogMapper tUserlogMapper;


    public void addLog(UserLog userLog){
        tUserlogMapper.addLog(userLog);
    }

    @Override
    public JSONObject listUserLog(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = tUserlogMapper.countLogs(jsonObject);
        List<JSONObject> list = tUserlogMapper.listLogs(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }


}
