package com.swust.back.service.socketData.Impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.socketData.PileTransMapper;
import com.swust.back.service.socketData.PileTransService;
import com.swust.back.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PileTransServiceImpl implements PileTransService {
    @Resource
    private PileTransMapper pileTransMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = pileTransMapper.allCount(jsonObject);
        List<JSONObject> list = pileTransMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
