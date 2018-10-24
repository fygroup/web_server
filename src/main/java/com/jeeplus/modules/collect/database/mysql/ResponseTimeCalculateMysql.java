package com.jeeplus.modules.collect.database.mysql;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Le on 2017/11/23.
 */
public class ResponseTimeCalculateMysql {

    public static String getMysqlResponseTime(Resource resource) {
        long time = -1l;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+resource.getIp()+":"+resource.getResourceBaseInfo().getAccessConfigPort()+"/mysql";
            long startTime = System.currentTimeMillis();
            Connection con = DriverManager.getConnection(url, resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword());
            if(con != null) {
                long endTime = System.currentTimeMillis();
                time = Long.valueOf(endTime - startTime);
            }
            if(con != null) {
                con.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return String.valueOf(time);
    }


}
