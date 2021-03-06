package com.jeeplus.modules.collect.database.mysql;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Le on 2017/11/23.
 */
public class UpTimeCalculateMysql {

    public static String getMysqlUptime(Resource resource) {
        String time = "";
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+resource.getIp()+":"+resource.getResourceBaseInfo().getAccessConfigPort()+"/mysql";    //JDBC的URL
            //调用DriverManager对象的getConnection()方法，获得一个Connection对象
            Connection con = DriverManager.getConnection(url, resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword());
            Statement stat = con.createStatement();
            String sql = "show status like 'uptime'";
            ResultSet queryResult  = stat.executeQuery(sql);
            if(queryResult.next()) {
                time=getDuration(Long.parseLong(queryResult.getString(2)));
            }
            if(stat != null) {
                stat.close();
            }
            if(con != null) {
                con.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }

    //计算持续时间
    static   String getDuration(Long diff){
        String result="";
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        diff=diff*1000;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        if(day>0){
            result+=day + "天";
        }
        if(hour>0){
            result+=hour + "小时";
        }
        if(min>0){
            result+=min + "分钟";
        }

        if(sec>0){
            result+=sec + "秒";
        }

        if(result.length()==0){
            result+="秒";
        }

        return  result;
    }

}
