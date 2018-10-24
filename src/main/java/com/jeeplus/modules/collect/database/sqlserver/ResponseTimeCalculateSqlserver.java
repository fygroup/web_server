package com.jeeplus.modules.collect.database.sqlserver;

import com.jeeplus.modules.resource.entity.Resource;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Le on 2017/11/23.
 */
public class ResponseTimeCalculateSqlserver {

    public static String getSqlserverResponseTime(Resource resource) {
        long time = -1l;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://"+resource.getIp()+":"+resource.getResourceBaseInfo().getAccessConfigPort();
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
