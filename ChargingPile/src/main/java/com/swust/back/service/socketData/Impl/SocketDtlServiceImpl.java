package com.swust.back.service.socketData.Impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.back.dao.socketData.SocketDtlMapper;
import com.swust.back.service.socketData.SocketDtlService;
import com.swust.back.util.CommonUtil;
import com.swust.back.util.CustomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SocketDtlServiceImpl implements SocketDtlService {
    @Resource
    private SocketDtlMapper socketDtlMapper;

    @Override
    public JSONObject list(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = socketDtlMapper.allCount(jsonObject);
        List<JSONObject> list = socketDtlMapper.list(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
