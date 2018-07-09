package com.swust.chargingmobileback.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.dao.user.AppLoginLogMapper;
import com.swust.chargingmobileback.dao.user.AppUsersMapper;
import com.swust.chargingmobileback.dao.user.CustomerMapper;
import com.swust.chargingmobileback.model.AppLoginLog;
import com.swust.chargingmobileback.model.AppUsers;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.service.user.LoginService;
import com.swust.chargingmobileback.util.algorithm.ThreeDESUtil;
import com.swust.chargingmobileback.util.mobile.PushMsg;
import com.swust.chargingmobileback.util.tools.IpAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/7 9:38
 */

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private AppUsersMapper appUsersMapper;

    @Resource
    private AppLoginLogMapper appLoginLogMapper;

    @Override
    public JSONObject auth(JSONObject requestObj, HttpServletRequest request) {
        // 电话号码
        String phone = requestObj.getString("phone");
        // 用户密码
        String pwd = ThreeDESUtil.decrypt(requestObj.getString("pwd"));
        // IMEI号，手机唯一识别码
        String imei =  ThreeDESUtil.decrypt(requestObj.getString("imei"));
        // 设备标识，判断是否是苹果请求
        String deviceType = requestObj.getString("deviceType");
        List<Customer> customers = customerMapper.findCustomerByPhone(phone);
        Customer customer = new Customer();
        JSONObject jsonObj = new JSONObject();
        // 默认值(1小时)
        double time = 1.0;
        if (customers.size() > 0) {
            customer = customers.get(0);
            // 登陆成功
            if (customer.getPassword().equals(pwd)) {
                AppUsers user = appUsersMapper.findUserByPhone(phone);
                // 移动用户表没有该用户
                if (user == null) {
                    user = new AppUsers(customer.getName(), pwd, phone, null,
                            null);
                    appUsersMapper.insert(user);
                    user = appUsersMapper.findUserByPhone(phone);
                }
                logger.info(user.toString());
                if (imei != null) {
                    // 用户更新后第一次登陆
                    if (user.getImei() == null) {
                        user.setImei(imei);
                        appUsersMapper.updateByPrimaryKey(user);
                    }
                    // 多台手机登陆同一账号
                    else if (!imei.equals(user.getImei())) {
                        PushMsg pushMsg = new PushMsg();
                        JSONObject pushObj = new JSONObject();
                        // 强制另一个用户退出登录状态
                        if ("iphone".equals(deviceType)) {
                            pushObj = pushMsg.cancelOtherIos("下线通知",
                                    "您的账号已于另一台设备上登录，请退出后重新登录！", user, imei);
                        } else {
                            pushObj = pushMsg.cancelOtherAndroid("下线通知",
                                    "您的账号已于另一台设备上登录，请重新登录！", user, imei);
                        }
                        time = pushObj.getDouble("time");
                        user.setImei(pushObj.getString("newImei"));
                        appUsersMapper.updateByPrimaryKey(user);
                    }
                }
                /**
                 * 1.无论用户更新否，都返回以下数值 ；2.返回一个经3DES加密过的token(时间戳+有效时间(小时记录))
                 */
                String token = ""
                        + (long) (System.currentTimeMillis() / 1000 + time * 60 * 60);
                token = ThreeDESUtil.encrypt(token);
                jsonObj.put("flag", 2);
                jsonObj.put("name", customer.getName());
                jsonObj.put("token", token);
                // 添加登录日志
                AppLoginLog appLoginLog = this.getAppLoginLog(phone,request);
                appLoginLogMapper.insert(appLoginLog);
            } else {
                // 密码错误
                jsonObj.put("flag", 1);
            }
        } else {
            // 尚未注册
            jsonObj.put("flag", 0);
        }

        logger.info(jsonObj.toString());
        return jsonObj;
    }

    private AppLoginLog getAppLoginLog(String phone,HttpServletRequest request){
        AppLoginLog appLoginLog = new AppLoginLog();
        appLoginLog.setPhone(phone);
        appLoginLog.setIp(IpAddr.getIpAddr(request));
        return appLoginLog;
    }

}

