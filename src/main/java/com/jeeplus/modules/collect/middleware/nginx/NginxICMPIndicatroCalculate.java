package com.jeeplus.modules.collect.middleware.nginx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Le on 2017/12/7.
 * 连接响应时间
 */
public class NginxICMPIndicatroCalculate {
    public static  String getValue(String ip,String port){
        String result="-1";
       long start= System.currentTimeMillis();

        try {
            StringBuffer buffer2 = new StringBuffer();
            buffer2.append("http://");
            buffer2.append(ip);
            buffer2.append(":");
            buffer2.append(port);
            buffer2.append("/status");
            String httpUrl = buffer2.toString();
            URL url = new URL(httpUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            result=String.valueOf(System.currentTimeMillis()-start);
            in.close();
        } catch (Exception e) {
        }

        return result;
    }

}