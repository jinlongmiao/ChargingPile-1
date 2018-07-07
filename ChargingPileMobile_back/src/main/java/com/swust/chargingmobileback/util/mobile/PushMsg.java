package com.swust.chargingmobileback.util.mobile;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidRequest;
import com.aliyuncs.push.model.v20160801.PushNoticeToAndroidResponse;
import com.aliyuncs.push.model.v20160801.PushNoticeToiOSRequest;
import com.aliyuncs.push.model.v20160801.PushNoticeToiOSResponse;
import com.aliyuncs.http.ProtocolType;
import com.swust.chargingmobileback.dao.user.AppUsersMapper;
import com.swust.chargingmobileback.model.AppUsers;
import com.swust.chargingmobileback.service.user.LoginService;
import com.swust.chargingmobileback.service.user.UserService;
import com.swust.chargingmobileback.util.constants.PullJson;

import javax.annotation.Resource;
import java.io.FileReader;

/**
 * @author 73559
 * @Date 2018/7/7 11:55
 */
public class PushMsg {

    /**
     * 推送自定义json
     */
    PullJson pullJson = new PullJson();

    /**
     * ISO端处理
     * @param title
     * @param content
     * @param user
     * @param newImei
     * @return
     */
    public JSONObject cancelOtherIos(String title, String content, AppUsers user, String newImei) {
        // 初始化参数
        JSONObject pullJsonObj = pullJson.getPullJson();
        double time = (double) pullJsonObj.get("time");
        String accessKeyId = (String) pullJsonObj.get("accessKeyId");
        String accessKeySecret = (String) pullJsonObj.get("accessKeySecret");
        String key = (String) pullJsonObj.get("iphoneKey");
        String region = (String) pullJsonObj.get("region");
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 开始推送
        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        // 安全性比较高的内容建议使用HTTPS
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(Long.valueOf(key));
        // iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV :表示开发环境, PRODUCT: 表示生产环境
        iOSRequest.setApnsEnv("DEV");
        iOSRequest.setTarget("ACCOUNT");
        iOSRequest.setTargetValue(user.getImei());
        iOSRequest.setTitle(title);
        iOSRequest.setBody(content);
        iOSRequest.setExtParameters("{\"k1\":\"v1\",\"k2\":\"v2\"}");

        try {
            PushNoticeToiOSResponse pushNoticeToiOSResponse = client
                    .getAcsResponse(iOSRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.printf("RequestId: %s, MessageId: %s\n",


        System.out.println("oldImei=" + user.getImei() + " , newImei=" + newImei);
        // 更新用户IMEI
        user.setImei(newImei);
        JSONObject returnJson = new JSONObject();
        returnJson.put("newImei",newImei);
        returnJson.put("time",time);
        return returnJson;
    }

    /**
     * 移动端处理
     * @param title
     * @param content
     * @param user
     * @param newImei
     * @return
     */
    public JSONObject cancelOtherAndroid(String title, String content, AppUsers user, String newImei) {
        // 初始化参数
        JSONObject pullJsonObj = pullJson.getPullJson();
        double time = (double) pullJsonObj.get("time");
        String accessKeyId = (String) pullJsonObj.get("accessKeyId");
        String accessKeySecret = (String) pullJsonObj.get("accessKeySecret");
        String key = (String) pullJsonObj.get("androidKey");
        long appKey = Long.valueOf(key);
        String region = (String) pullJsonObj.get("region");
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 开始推送
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        // 安全性比较高的内容建议使用HTTPS
        androidRequest.setProtocol(ProtocolType.HTTPS);
        // 内容较大的请求，使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        // 这个指定推送的设备
        androidRequest.setTarget("ACCOUNT");
        // 这个指定推送的设备
        androidRequest.setTargetValue(user.getImei());
        androidRequest.setTitle(title);
        androidRequest.setBody(content);
        androidRequest.setExtParameters("{\"k1\":\"v1\"}");
        PushNoticeToAndroidResponse pushNoticeToAndroidResponse;
        try {
            client.getAcsResponse(androidRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("oldImei=" + user.getImei() + " , newImei=" + newImei);
        // 更新用户IMEI
        user.setImei(newImei);
        System.out.println("user info push msg ===> " + user.toString());

        JSONObject returnJson = new JSONObject();
        returnJson.put("newImei",newImei);
        returnJson.put("time",time);
        return returnJson;
    }
}
