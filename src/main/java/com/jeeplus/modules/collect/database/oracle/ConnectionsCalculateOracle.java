package com.jeeplus.modules.collect.database.oracle;

import com.jeeplus.modules.resource.entity.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Le on 2017/11/23.
 */
public class ConnectionsCalculateOracle {

    public static String getOracleConnections(Resource resource) {
        int nums = 0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@"+resource.getIp()+":"+resource.getResourceBaseInfo().getPort()+":orcl";
            Connection con = DriverManager.getConnection(url, resource.getResourceBaseInfo().getAccessConfigUserName(), resource.getResourceBaseInfo().getAccessConfigPassword());
            Statement stat = con.createStatement();
            String sql = "SELECT count(*) FROM v$session";
            ResultSet queryResult  = stat.executeQuery(sql);
            if(queryResult.next()) {
                nums=queryResult.getInt(1);
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


}
