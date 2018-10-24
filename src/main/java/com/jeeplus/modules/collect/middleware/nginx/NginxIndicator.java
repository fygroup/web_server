package com.jeeplus.modules.collect.middleware.nginx;

import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public abstract class NginxIndicator {

    public NginxIndicator() {
    }

    public static String getWebCon(String ip, String port) {
        StringBuffer sb = new StringBuffer();

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

            String line;
            while((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            in.close();
        } catch (Exception e) {
        }

        return sb.toString();
    }
}
