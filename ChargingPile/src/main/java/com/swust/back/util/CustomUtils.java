package com.swust.back.util;

import javax.servlet.http.HttpServletRequest;

public  class CustomUtils {

    /**
     * 通过HttpServletRequest得到IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        if( ip!=null && ip.equals("0:0:0:0:0:0:0:1") ){
            ip = "::1";
        }
        return ip;
    }


    /**
     * 获得10位长度的时间戳
     * @return
     */
    public static int getTimeMap(){
        long currentTimeMillis = System.currentTimeMillis();
        int timeTmp =  Integer.parseInt(String.valueOf(currentTimeMillis).substring(0,10));
        return timeTmp;
    }

    /**
     * 快速展示
     * @param T
     */
    public static void show(Object T){
        if(T == null){
            System.out.println("NULL");
        }
        else{

            System.out.println(T.getClass() + "===>" + "\n" + T.toString());
        }

    }



    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     * @param jsonObject
     * @return
     */
//    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
//        if (!StringTools.isNullOrEmpty(requiredColumns)) {
//            //验证字段非空
//            String[] columns = requiredColumns.split(",");
//            String missCol = "";
//            for (String column : columns) {
//                Object val = jsonObject.get(column.trim());
//                if (StringTools.isNullOrEmpty(val)) {
//                    missCol += column + "  ";
//                }
//            }
//            if (!StringTools.isNullOrEmpty(missCol)) {
//                jsonObject.clear();
//                jsonObject.put("returnCode", ErrorEnum.E_90003.getErrorCode());
//                jsonObject.put("returnMsg", "缺少必填参数:" + missCol.trim());
//                jsonObject.put("returnData", new JSONObject());
//                throw new RuntimeException(jsonObject);
//            }
//        }
//    }


}
