package com.swust.back.service.datadict.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.datadict.AreaMapper;
import com.swust.back.service.datadict.AreaService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = areaMapper.allCount(jsonObject);
        List<JSONObject> list = areaMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
