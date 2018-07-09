package com.swust.chargingmobileback.service.message.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.message.UserAdviceMapper;
import com.swust.chargingmobileback.dao.user.CustomerMapper;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.model.UserAdvice;
import com.swust.chargingmobileback.service.message.UserAdviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserAdviceServiceImpl implements UserAdviceService{

    static Logger logger = LoggerFactory.getLogger(UserAdviceServiceImpl.class);
    @Resource
    private UserAdviceMapper userAdviceMapper;
    @Resource
    private CustomerMapper customerMapper;

    public JSONObject insertUserAdvice(JSONObject requestObj){

        // 用户手机号
        String telephone = requestObj.getString("phone");
        // 用户建议
        String advice = requestObj.getString("advice");

        List<Customer> customer = customerMapper.findCustomerByPhone(telephone);
        JSONObject json = new JSONObject();
        if (customer.size() > 0){
            UserAdvice userAdvice = new UserAdvice();
            userAdvice.setTelephone(telephone);
            userAdvice.setAdvice(advice);
            int value = userAdviceMapper.insert(userAdvice);
            if (value > 0){
                //添加用户建议成功
                json.put("code","0");
            }else {
                //用户建议添加失败
                json.put("code","1");
            }
        }else{
            //用户尚未注册
            json.put("code","2");
        }
        return json;
    }
}
