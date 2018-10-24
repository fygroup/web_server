package com.jeeplus.modules.collect.database.sybase;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Le on 2018/1/8.
 */
public class UsersCalculateSybase {
       public  static String getUsers(Resource resource) {
        String result="";
        Statement stat = null;
        Connection conn = null;
        ResultSet queryResult = null;
        try{
            // Class.forName("com.sybase.jdbc3.jdbc.SybDriver"); // 加载sybase驱动程序
            String url = "jdbc:sybase:Tds:"+resource.getIp()+":"+resource.getResourceBaseInfo().getAccessConfigPort();
             conn = DriverManager.getConnection(url,resource.getResourceBaseInfo().getAccessConfigUserName(),resource.getResourceBaseInfo().getAccessConfigPassword());
            String sql = "  select name from syslogins";
            stat = conn.createStatement();
            queryResult = stat.executeQuery(sql);
            StringBuffer sb = new StringBuffer();
            while(queryResult.next()) {
                String temResult = queryResult.getString(1);
                sb.append(temResult);
                sb.append(",");
            }
            int size = sb.length();
            if(size > 0) {
                result = sb.substring(0, size - 1);
            }
            conn.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
