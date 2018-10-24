package com.jeeplus.modules.collect.database.mysql;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Le on 2017/11/23.
 */
public class ConnectionsCalculateMysql {

    public static String getMysqlConnections(Resource resource) {
        int nums = 0;
        try {
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+resource.getIp()+":"+resource.getResourceBaseInfo().getAccessConfigPort()+"/mysql";    //JDBC的URL
            //调用DriverManager对象的getConnection()方法，获得一个Connection对象
            Connection con = DriverManager.getConnection(url, resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword());
            Statement stat = con.createStatement();
            String sql = "show status like 'Threads_connected'";
            ResultSet queryResult  = stat.executeQuery(sql);
            if(queryResult.next()) {
                nums=Integer.parseInt(queryResult.getString(2));
            }
            if(nums > 0) {
                --nums;
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
        return String.valueOf(nums);
    }


    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://139.4.48.244:3306/mysql";    //JDBC的URL
        //调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection con = DriverManager.getConnection(url, "root", "123456");
        Statement stat = con.createStatement();
        String sql = "SELECT concat(round(sum(DATA_LENGTH  ), 2), '') FROM information_schema.tables";
        ResultSet queryResult  = stat.executeQuery(sql);
        if(queryResult.next()) {
            System.out.println(queryResult.getString(1));
        }
    }





}
