package com.swust.chargingmobileback.service.home.impl;

import com.alibaba.fastjson.JSONObject;
import com.swust.chargingmobileback.config.exception.MoneyNotEnoughException;
import com.swust.chargingmobileback.config.exception.NullJsonObjectException;
import com.swust.chargingmobileback.dao.DefinedMapper;
import com.swust.chargingmobileback.dao.user.CustomerMapper;
import com.swust.chargingmobileback.model.Customer;
import com.swust.chargingmobileback.service.home.ScanService;
import com.swust.chargingmobileback.socket.SocketImpl;
import com.swust.chargingmobileback.util.constants.CommonJson;
import com.swust.chargingmobileback.web.home.ScanController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/8 16:08
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ScanServiceImpl implements ScanService {

    static Logger logger = LoggerFactory.getLogger(ScanServiceImpl.class);

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private DefinedMapper definedMapper;

    @Resource
    private SocketImpl socket;

    /**
     * 通过phone获得卡号和余额
     * @param phone
     * @return
     */
    @Override
    public List<String> getCardAndMoney(String phone) {
        List<Customer> customers = customerMapper.findCustomerByPhone(phone);;
        List<String> list = new ArrayList<String>();
        boolean customerIsEmpty = customers.size() == 0;
        if (customerIsEmpty) {
            throw new NullJsonObjectException();
        }

        Customer customer = customers.get(0);
        // 充电卡号
        list.add(customer.getChargeCardId());
        // 卡余额是十进制浮点型，需要转为16进制整型字符串
        int moneyInt = Integer.parseInt(""
                + Math.round(customer.getChargeCardMoney() * 100));
        String moneyString = Integer.toHexString(moneyInt).toUpperCase();
        list.add(moneyString);
        list.add("" + moneyInt);
        boolean moneyNotEnough = Integer.parseInt(list.get(2)) <= 1;

        if(moneyNotEnough){
            throw new MoneyNotEnoughException();
        }
        return list;
    }

    @Override
    public JSONObject findPileState(JSONObject requestJson, List<String> list) {
        JSONObject chargingJsonObject = CommonJson.getchargingConfigJson();
        JSONObject jsonObj = new JSONObject();
        String ip = chargingJsonObject.getString("ip");
        int port = chargingJsonObject.getInteger("port");
        logger.info("pileid === " + requestJson.getString("pileid"));
        // 查询电桩运行状态
        String pileState = definedMapper.findPileState(requestJson.getString("pileid"));
        if(pileState.isEmpty()){
            jsonObj.put("code", 2);
            return jsonObj;
        }
        // 13：一口为3，二口为1。这里一口充电中，二口空闲中
        logger.info("电桩运行状态=" + pileState);
        String chargingPort = String.valueOf(requestJson.getString("port").charAt(1));
        boolean couldCharging = ("11").equals(pileState)
                || (pileState.substring(0, 1).equals("1")
                && chargingPort.equals("2"))
                || (pileState.substring(1, 2).equals("1"))
                && chargingPort.equals("1");
        if (couldCharging) {
            // "01"表示开启充电指令
//            SocketImpl socket = new SocketImpl();
            Object[] obj = process(requestJson);
            String jsonstring = socket.sendScanSocket(ip, port, list, obj, "01");
            if (jsonstring.length() > 3) {
                // 开启充电成功
                jsonObj.put("code", 0);
                jsonObj.put("packageData", jsonstring);
            } else if (jsonstring.equals("2")) {
                // socket连接失败
                jsonObj.put("code", 2);
            } else {
                // 充电故障
                jsonObj.put("code", Integer.parseInt(jsonstring));
            }
        } else {
            // 设备不可充（正在充电或者设备故障等情况）
            jsonObj.put("code", 5);
        }
        return jsonObj;
    }

    private Object[] process(JSONObject jsonObj) {
        // json = phone + gateid + pileid + port + style + prevalue + ev
        // 电话号码
        Object phone = jsonObj.getString("phone");
        // 网关编号
        Object gateid = jsonObj.getString("gateid");
        // 电桩编号
        Object pileid = jsonObj.getString("pileid");
        // 端口
        Object port = jsonObj.getString("port");
        // 充电方式
        Object style = jsonObj.getString("style");
        // 充电预付值
        Object prevalue = jsonObj.getString("prevalue");
        // 直流还是交流
        Object ev = jsonObj.getString("ev");
        Object[] obj = new Object[7];
        obj[0] = phone;
        obj[1] = gateid;
        obj[2] = pileid;
        obj[3] = port;
        obj[4] = style;
        obj[5] = prevalue;
        obj[6] = ev;
        return obj;
    }


}
