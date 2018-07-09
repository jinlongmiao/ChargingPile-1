package com.swust.chargingmobileback.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.user.CustomerMapper;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.service.user.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/8 10:04
 */

@Service
@Transactional(rollbackFor=RuntimeException.class)
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public JSONObject findMoney(String phone) {
        JSONObject returnJson = new JSONObject();
        // 根据手机号码获取用户
        List<Customer> customers = customerMapper.findCustomerByPhone(phone);
        if (customers.size() > 0) {
            Customer customer = customers.get(0);
            returnJson.put("flag", true);
            returnJson.put("money", customer.getChargeCardMoney());
        } else {
            returnJson.put("flag", false);
        }

        return returnJson;
    }
}
