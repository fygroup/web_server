package com.jeeplus.modules.collect.database.oracle;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Le on 2017/11/23.
 */
public class ResponseTimeCalculateOracle {

    public static String getOracleResponseTime(Resource resource) {
        long time = -1l;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@"+resource.getIp()+":"+resource.getResourceBaseInfo().getPort()+":orcl";
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
