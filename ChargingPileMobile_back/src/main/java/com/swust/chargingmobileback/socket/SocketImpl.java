package com.swust.chargingmobileback.socket;

import com.swust.chargingmobileback.dao.home.PileTransMapper;
import com.swust.chargingmobileback.model.PileTrans;
import com.swust.chargingmobileback.service.home.impl.ScanServiceImpl;
import com.swust.chargingmobileback.util.algorithm.CRC16;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author 73559
 * @Date 2018/7/8 17:20
 */

@Service
@Transactional(rollbackFor=RuntimeException.class)
public class SocketImpl {

    static Logger logger = LoggerFactory.getLogger(SocketImpl.class);

    @Resource
    private PileTransMapper pileTransMapper;
    /**
     *  状态码
     */
    int stateCode = Integer.MAX_VALUE;

    /**
     * 向网关发送充电请求
     *
     * @param ip
     *            网关地址
     * @param port
     *            端口
     * @param list
     *            卡号及卡余额
     * @param obj
     *            部分数据内容
     * @return 状态码
     */
    public String sendScanSocket(String ip, int port, List<String> list,
                                 Object[] obj, String button) {
        // 判断是否开始充电，默认开启充电失败
        boolean flag = false;
        // 由于网关发送多次信息，可能出现这种情况：前一次开启,flag=true，
        // 后一次又发来其他状态码，返回flag=false，将前一次给覆盖
        boolean flag1 = false;
        // 生成crc校验码
        // 十六进制数据内容
        String data = initStartData(list, obj, button);
        // 数据内容转为16进制字节数组
        byte[] byteData = CRC16.HexStringToBinary(data);
        // crc校验码数据内容
        int crcInt = CRC16.calcCrc16(byteData);
        // 根据数据内容生成CRC校验码
        String crcString = (Integer.toHexString(crcInt)).toUpperCase();
        if (crcString.length() < 4) {
            crcString = "0" + crcString;
        }
        // 组装数据包
        // 拼接完整数据包内容
        String packageData = "A42400" + data + crcString + "B4";
        logger.info("开始充电客户端完整数据包=" + packageData);

        logger.info(
                "处理后的客户端数据展示开始------------------------------------", 3);
        logger.info("网关号=" + obj[1]);
        logger.info("电桩号=" + obj[2]);
        logger.info("功能码=" + "051C");
        logger.info("充电端口=" + obj[3]);
        logger.info("充电方式=" + obj[4]);
        // 获取卡余额时候已转为16进制整数
        logger.info("充电预设值=" + trans(obj[5].toString()));
        logger.info("辅助电源电压=" + obj[6]);
        // 高低位转换即可
        logger.info("充电卡号=" + cardIdTrans(list.get(0)));
        logger.info("卡余额=" + trans(list.get(1)));
        logger.info(
                "处理后的客户端数据展示结束------------------------------------");
        logger.info("");

        // 开始请求
        try {
            logger.info("ip=" + ip);
            logger.info("port=" + port);

            Socket sockett = new Socket(ip, port);
            BufferedInputStream bufiss = new BufferedInputStream(
                    sockett.getInputStream());
            OutputStream oss = sockett.getOutputStream();
            byte[] by = CRC16.HexStringToBinary(packageData);
            // 如果套接字已经连接成功
            if (sockett.isConnected()) {
                logger.info("socket连接上了");
                oss.write(by);
                oss.flush();
                // 接收到网关发送过来的数据包总长度
                byte[] buf = new byte[26];
                int len = 0;
                while ((len = bufiss.read(buf)) != -1) {
                    // 字节数组转为16进制字符串
                    String hexString = CRC16.bytesToHexString(buf);
                    logger.info("服务端：" + hexString);
                    if (hexString
                            .equals("A3110B0000000000000000000000000000000000000000000000")) {
                        break;
                    }
                    String A3 = hexString.substring(0, 2);
                    String B3 = hexString.substring(50, 52);
                    if (A3.equals("A3") && B3.equals("B3")) {
                        logger.info("服务端正确的数据：" + hexString);
                        flag = handleScanMessage(flag, hexString);
                        if (stateCode != Integer.MAX_VALUE){
                            // 出现故障码或者充电已经结束，退出监听
                            break;
                        }
                        if (flag) {
                            flag1 = true;
                            break;
                        }
                    }
                }
            }
            bufiss.close();
            oss.close();
            sockett.close();
            logger.info("socket正常断开");
        } catch (Exception e) {
            logger.info("连接失败!");
            e.printStackTrace();
            return "2";
        }
        if (flag1) {
            logger.info("开启充电成功！");
            return packageData;
        } else {
            // 整型最大值代表服务端那边报错或者开启充电成功，后者不可能在这里出现，在上一括号中进行；
            // 其他故障码（包括充电已经结束）
            logger.info("故障state=" + stateCode);
            return "" + stateCode;
        }
    }

    /**
     * 初始化数据包中的内容
     *
     * @param list
     *            卡号及余额
     * @param obj
     *            用户上传的充电数据
     * @param button
     *            开启充电01，关闭充电02
     * @return
     */
    public String initStartData(List<String> list, Object[] obj, String button) {
        // 16进制扩大一百倍的余额
        String money = list.get(1);
        // 16进制的卡号
        String cardId = list.get(0);
        // 充电预设值
        String prevalue = obj[5].toString();
        // 低位在前
        money = trans(money);
        // 低位在前
        prevalue = trans(prevalue);
        // 充电卡号反转
        cardId = cardIdTrans(cardId);

        StringBuilder data = new StringBuilder();
        // data = gateid + pileid + functioncode + basedata
        data.append(obj[1].toString()).append(obj[2].toString()).append("051C")
                .append(button).append(obj[3].toString())
                .append(obj[4].toString()).append(prevalue)
                .append(obj[6].toString()).append(cardId).append(money)
                .append("0000");
        return data.toString();
    }



    /**
     * 将值大小转为符合高定位在前，低电位在后
     */
    public String trans(String str) {
        int length = str.length();
        // 预设值剩余的0位数
        int len;
        // 如果为奇数，则必须在16进制字符串的前边加上一个"0"
        if (length % 2 != 0) {
            length++;
            str = "0" + str;
        }
        String line = str;
        char[] ch = line.toCharArray();
        String[] lineArray = new String[line.length() / 2];
        int k = 0;
        for (int i = 0; i < lineArray.length; i++) {
            String string = "";
            for (int j = 0; j < 2; j++) {
                string += ch[k];
                k++;
            }
            lineArray[i] = string;
        }
        str = "";
        for (int i = lineArray.length - 1; i >= 0; i--) {
            str += lineArray[i];
        }
        /* 结束 */
        // 预设值剩余的0位数
        len = 8 - length;
        for (int i = 0; i < len; i++) {
            str += "0";
        }
        // sop("值的16进制值="+str);
        return str;
    }


    /**
     * 反转充电卡卡号
     *
     * @param str
     *            卡号
     * @return
     */
    public String cardIdTrans(String str) {
        byte[] bytes = invertByte(CRC16.HexStringToBinary(str));
        return CRC16.bytesToHexString(bytes);
    }


    /**
     * byte高低位倒顺序
     *
     * @param
     * @return byte[]
     */
    public byte[] invertByte(byte[] b) {
        byte[] b2 = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            b2[i] = b[b.length - 1 - i];
        }
        return b2;
    }

    /**
     * 处理接收到网关的数据，状态码为0则开启充电，为1则停止充电
     *
     * @param flag
     *            默认操作失败false
     * @param string
     * @return
     */
    public boolean handleScanMessage(boolean flag, String string) {
        if (string.length() == 52) {
            // 返回结果（状态码）
            String result = string.substring(34, 36);
            logger.info("网关发送的状态码=" + result);

            String CRC = string.substring(46, 50);
            String data = string.substring(6, 46);
            // 数据内容（字符串）转为16进制字节数组
            byte[] byteData = CRC16.HexStringToBinary(data);
            // 数据内容（字节数组）转为16进制字节数组
            int crcInt = CRC16.calcCrc16(byteData);
            // 根据数据内容生成CRC校验码
            String crcString = (Integer.toHexString(crcInt)).toUpperCase();
            if (crcString.length() < 4) {
                crcString = "0" + crcString;
            }
            int len = data.length();
            // 检查数据内容的长度
            if (len == 40) {
                logger.info(
                        "网关发送的crc=" + CRC + ",data=" + data + ",app发送的crc="
                                + crcString);
                // 检查CRC校验码
                // 状态码
                if (crcString.equals(CRC)) {
                    switch (result) {
                        case "06":
                            stateCode = 6;
                            break;
                        case "10":
                            stateCode = 10;
                            break;
                        case "11":
                            stateCode = 11;
                            break;
                        case "13":
                            stateCode = 13;
                            break;
                        case "14":
                            stateCode = 14;
                            break;
                        case "15":
                            stateCode = 15;
                            break;
                        case "16":
                            stateCode = 16;
                            break;
                        case "1F":
                            stateCode = 31;
                            break;
                        // 上面的都是出现故障以后的报错码
                        // 充电已开始
                        case "00":
                            flag = true;
                            break;
                        // 充电已结束
                        case "01":
                            stateCode = 1;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return flag;
    }


    /**
     * 停止充电
     *
     * @param ip
     * @param port
     * @param cardId
     * @return
     */
    public String stopCharging(String ip, int port, String cardId, String lastTransUid) {
        // 判断是否结束充电成功，默认开启充电失败
        boolean flag = false;
        // 由于网关发送多次信息，可能出现这种情况：前一次开启,flag=true，后一次又发来其他状态码，返回flag=false，将前一次给覆盖
        boolean flag1 = false;
        // 停止充电，获取一条最新的交易记录
        List<PileTrans> list = pileTransMapper.stopCharging(lastTransUid);
        // 网关、电桩、端口、充电卡号、交易时间
        PileTrans trans = list.get(0);
        // 组装数据内容格式 = 网关+电桩+功能码+开/关+端口+...+充电卡号(需要反转)+...
        String data = initStopData(trans, cardId);
        logger.info("数据内容长度=" + data.length());
        // 组装CRC校验码
        // 数据内容转为16进制字节数组
        byte[] byteData = CRC16.HexStringToBinary(data);
        // crc校验码数据内容
        int crcInt = CRC16.calcCrc16(byteData);
        String crcString = (Integer.toHexString(crcInt)).toUpperCase();
        // 根据数据内容生成CRC校验码
        if (crcString.length() < 4) {
            crcString = "0" + crcString;
        }
        // 组装数据包
        String packageData = "A42400" + data + crcString + "B4";
        logger.info("充电进度页面停止充电完整数据包=" + packageData);
        logger.info("处理后的客户端数据展示开始------------------------------------", 3);
        logger.info("数据包长度=" + packageData.length());
        logger.info("网关号=" + trans.getGateid());
        logger.info("电桩号=" + trans.getPileid());
        logger.info("功能码=" + "051C");
        logger.info("充电端口=" + trans.getPort());
        logger.info("充电卡号=" + trans.getChargeCardId());
        logger.info("充电时间=" + trans.getTransTime());
        logger.info("处理后的客户端数据展示结束------------------------------------");
        logger.info("");

        // 开始请求
        try {
            Socket sockett = new Socket(ip, port);
            BufferedInputStream bufiss = new BufferedInputStream(
                    sockett.getInputStream());
            OutputStream oss = sockett.getOutputStream();
            byte[] by = CRC16.HexStringToBinary(packageData);
            // 如果套接字已经连接成功
            if (sockett.isConnected()) {
                System.out.println("socket连接上了");
                oss.write(by);
                oss.flush();
                // 接收到网关发送过来的数据包总长度
                byte[] buf = new byte[26];
                int len = 0;
                while ((len = bufiss.read(buf)) != -1) {
                    // 字节数组转为16进制字符串
                    String hexString = CRC16.bytesToHexString(buf);
                    logger.info("服务端：" + hexString);
                    if (("A3110B0000000000000000000000000000000000000000000000")
                                    .equals(hexString)) {
                        break;
                    }
                    String A3 = hexString.substring(0, 2);
                    String B3 = hexString.substring(50, 52);
                    if (A3.equals("A3") && B3.equals("B3")) {
                        logger.info("服务端正确的数据：" + hexString);
                        flag = handleStopMessage(flag, hexString);
                        if (stateCode != Integer.MAX_VALUE){
                            break;// 出现故障码，退出监听
                        }
                        if (flag) {
                            flag1 = true;
                            break;
                        }
                    }
                }// while结束
            }// 监听socket传值结束
            bufiss.close();
            oss.close();
            sockett.close();
            logger.info("socket正常断开");
        } catch (Exception e) {
            logger.info("连接失败");
            e.printStackTrace();
            return "2";
        }
        if (flag1) {
            logger.info("关闭成功");
            return "0";
        } else {
            return "" + stateCode;
        }
    }


    public boolean handleStopMessage(boolean flag, String string) {
        if (string.length() == 52) {
            // 返回结果（状态码）
            String result = string.substring(34, 36);
            logger.info("网关发送的状态码=" + result);

            String CRC = string.substring(46, 50);
            String data = string.substring(6, 46);
            // 数据内容（字符串）转为16进制字节数组
            byte[] byteData = CRC16.HexStringToBinary(data);
            // 数据内容（字节数组）转为16进制字节数组
            int crcInt = CRC16.calcCrc16(byteData);
            // 根据数据内容生成CRC校验码
            String crcString = (Integer.toHexString(crcInt)).toUpperCase();
                if (crcString.length() < 4) {
                crcString = "0" + crcString;
            }
            int len = data.length();
            if (len == 40) {
                logger.info(
                        "网关发送的crc=" + CRC + ",data=" + data + ",app发送的crc="
                                + crcString);
                if (crcString.equals(CRC)) {
                    // 检查CRC校验码
                    // 状态码
                    switch (result) {
                        case "06":
                            stateCode = 6;
                            break;
                        case "10":
                            stateCode = 10;
                            break;
                        case "11":
                            stateCode = 11;
                            break;
                        case "13":
                            stateCode = 13;
                            break;
                        case "14":
                            stateCode = 14;
                            break;
                        case "15":
                            stateCode = 15;
                            break;
                        case "16":
                            stateCode = 16;
                            break;
                        case "1F":
                            stateCode = 31;
                            break;
                        // 上面的都是出现故障以后的报错码
                        // 充电已开始
                        case "00":
                            stateCode = 0;
                            break;
                        // 充电已结束
                        case "01":
                            flag = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 组装停止充电的数据内容
     *
     * @param trans
     * @param cardId
     * @return
     */
    private String initStopData(PileTrans trans, String cardId) {
        StringBuilder sb = new StringBuilder();
        // 网关
        sb.append(trans.getGateid());
        // 设备序列号
        sb.append(trans.getPileid());
        // 功能码+开关
        sb.append("051C" + "02");
        // 端口
        sb.append(trans.getPort());
        // 充电方式+充电预设值+辅助电源电压
        sb.append("000000000000");
        // 充电卡号
        sb.append(cardIdTrans(cardId));
        // 卡内余额+额外2个字节
        sb.append("000000000000");
        return sb.toString();
    }

    public void initReturn(HttpServletResponse response, String jsonString)
            throws Exception {
        // 不设置返回字符串编码有可能乱码
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonString);
        out.close();
    }
}
