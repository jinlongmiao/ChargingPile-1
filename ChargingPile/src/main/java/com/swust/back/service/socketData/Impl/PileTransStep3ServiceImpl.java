package com.swust.back.service.socketData.Impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.socketData.PileTransStep3Mapper;
import com.swust.back.service.socketData.PileTransStep3Service;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PileTransStep3ServiceImpl implements PileTransStep3Service{
    @Resource
    private PileTransStep3Mapper pileTransStep3Mapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = pileTransStep3Mapper.allCount(jsonObject);
        List<JSONObject> list = pileTransStep3Mapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
