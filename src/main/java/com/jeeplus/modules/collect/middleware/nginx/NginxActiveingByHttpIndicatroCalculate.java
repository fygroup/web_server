package com.jeeplus.modules.collect.middleware.nginx;

/**
 * Created by Le on 2017/12/7.
 * 正在处理的活动连接数
 */
public class NginxActiveingByHttpIndicatroCalculate {
    public static  String getValue(String ip,String port){
        String  result = NginxIndicator.getWebCon(ip, port);
        String value = null;
        try {
            if(result != null && result.length() > 0) {
                String[] val = result.split("\n");
                int index = val[0].indexOf(":");
                value = val[0].substring(index + 1, val[0].length()).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value==null?"0":value;
    }

}