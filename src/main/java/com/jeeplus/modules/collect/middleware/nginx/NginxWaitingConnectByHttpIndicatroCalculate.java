package com.jeeplus.modules.collect.middleware.nginx;

/**
 * Created by Le on 2017/12/7.
 * 驻留连接数
 */
public class NginxWaitingConnectByHttpIndicatroCalculate {
    public static  String getValue(String ip,String port){
        String  result = NginxIndicator.getWebCon(ip, port);
        String value = null;
        try {
            if(result != null && result.length() > 2) {
                String[] val = result.split("\n");
                int index1 = val[3].indexOf("Waiting");
                value = val[3].substring(index1 + 8, val[3].length()).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value==null?"0":value;
    }

}
