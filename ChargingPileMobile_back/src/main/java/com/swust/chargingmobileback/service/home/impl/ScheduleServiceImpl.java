package com.swust.chargingmobileback.service.home.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.user.AppUsersMapper;
import com.swust.chargingmobileback.dao.user.CustomerMapper;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.service.home.ScheduleService;
import com.swust.chargingmobileback.socket.SocketImpl;
import com.swust.chargingmobileback.util.constants.CommonJson;
import com.swust.chargingmobileback.web.home.ScheduleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileReader;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/9 10:32
 */

@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ScheduleServiceImpl implements ScheduleService {
    static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private SocketImpl socket;

    @Override
    public JSONObject stopCharging(String phone) {
        JSONObject jsonObj = new JSONObject();
        // 获取socket连接的IP与端口号

        String ip = CommonJson.getchargingConfigJson().getString("ip");
        int port = CommonJson.getchargingConfigJson().getInteger("port");
        logger.info("ip=" + ip + " , port=" + port);

        // 查询卡状态
        List<Customer> customers = customerMapper.findCustomerByPhone(phone);
        if(customers.isEmpty()){
            return jsonObj;
        }
        Customer customer = customers.get(0);
        // 卡状态
        String cardState = customer.getChargeCardState();
        logger.info("查询用户信息=" + customer.toString());
        // 卡号
        String cardId = customer.getChargeCardId();
        // uid
        String lastTransUid = customer.getLastTransUid();
        // "0"不在充电状态，提示关闭充电成功，清除客户端数据   "2"：socket连接失败
        String jsonString = "0";

        // 代表正在充电
        if ("03".equals(cardState)) {
            try {
                // 停止充电请求
                jsonString = socket.stopCharging(ip, port, cardId, lastTransUid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jsonObj.put("code",Integer.parseInt(jsonString) );
        logger.info("停止充电指令返回信息=" + jsonObj.toString());
        return jsonObj;
    }
}
