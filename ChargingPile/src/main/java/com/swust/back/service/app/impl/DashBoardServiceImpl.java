package com.swust.back.service.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.datadict.PileMapper;
import com.swust.back.service.app.DashBoardService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DashBoardServiceImpl implements DashBoardService {

    @Resource
    private PileMapper pileMapper;

    @Override
    public JSONObject getPiles(JSONObject jsonObject) {
        List<JSONObject> piles = pileMapper.getPiles();
        CustomUtils.show(piles);
        JSONObject returnData = new JSONObject();
        returnData.put("list", piles);
        return CommonUtil.successJson(returnData);
    }
}
