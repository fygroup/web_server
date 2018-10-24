package com.jeeplus.modules.collect.middleware.nginx;

/**
 * Created by Le on 2017/12/7.
 *  读取到客户端的Headerer信息数
 */
public class NginxReadHeaderCountByHttpIndicatroCalculate {
    public static  String getValue(String ip,String port){
        String  result = NginxIndicator.getWebCon(ip, port);
        String value = null;
        try {
            if(result != null && result.length() > 3) {
                String[] val = result.split("\n");
                int index1 = val[3].indexOf("Reading");
                int index2 = val[3].indexOf("Writing");
                value = val[3].substring(index1 + 8, index2).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value==null?"0":value;
    }

}

