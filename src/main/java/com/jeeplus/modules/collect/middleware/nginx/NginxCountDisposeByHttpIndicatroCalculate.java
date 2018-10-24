package com.jeeplus.modules.collect.middleware.nginx;

/**
 * Created by Le on 2017/12/7.
 * 总共处理的连接数
 */
public class NginxCountDisposeByHttpIndicatroCalculate {
    public static  String getValue(String ip,String port){
        String  result = NginxIndicator.getWebCon(ip, port);
        String value = null;
        try {
            if(result != null && result.length() > 2) {
                String[] val = result.split("\n");
                String resultVla = val[2].trim().replaceAll(" ", ":");
                String[] res = resultVla.split(":");
                value = res[0];
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value==null?"0":value;
    }

}
