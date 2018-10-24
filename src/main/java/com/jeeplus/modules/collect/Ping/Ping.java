package com.jeeplus.modules.collect.Ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Le on 2017/11/20.
 */
public class Ping {

    public static Long ping(String ipAddress) {
        int pingTimes = 1;
        int timeOut=1000;

        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        long start=System.currentTimeMillis();
        String pingCommand = "Ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {   // 执行命令并获取输出
           // System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return -1L;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            String line = null;
            while ((line = in.readLine()) != null) {
                if(getCheckResult(line)==1){
                    return System.currentTimeMillis()-start;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return -1L;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1L;
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }


    public static void main(String[] args) throws Exception {
        String ipAddress = "192.168.1.53";

        System.out.println(ping(ipAddress));
    }

}
