package com.swust.back.service.operation.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.datadict.PileMapper;
import com.swust.back.dao.operation.PileDataMapper;
import com.swust.back.service.operation.PileServiceDataService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PileServiceDataServiceImpl implements PileServiceDataService {
    @Resource
    private PileDataMapper pileDataMapper;



    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        CustomUtils.show(jsonObject);
        int count = pileDataMapper.pileDataCount();
        List<JSONObject> list = pileDataMapper.pileDataList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }


}
