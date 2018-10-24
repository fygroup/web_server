package com.jeeplus.modules.collect.database.sqlserver;

import com.jeeplus.modules.resource.entity.Resource;
import java.sql.*;

/**
 * Created by Le on 2017/11/23.
 */
public class ConnectionsCalculateSqlserver {

    public static String getSqlserverConnections(Resource resource) {
        String tableName="sysperfinfo";
        return getNums(tableName,resource.getIp(),resource.getResourceBaseInfo().getAccessConfigPort(),resource.getResourceBaseInfo().getAccessConfigUserName(),resource.getResourceBaseInfo().getAccessConfigPassword());
    }

   static String getNums(String tableName,String ip,String port,String userName,String password ) {
      int nums = 0;
       Statement stat = null;
       Connection con = null;
       ResultSet queryResult = null;
       String url="jdbc:sqlserver://"+ip+":"+port;
       try {
               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
               con=DriverManager.getConnection(url,userName,password);
               stat = con.createStatement();
               String sql = "select cntr_value from  "+tableName+" where counter_name='User Connections'";
               queryResult = stat.executeQuery(sql);
              if(queryResult.next()) {
                  nums=Integer.valueOf(queryResult.getInt(1));
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

           }catch(Exception e){
              e.printStackTrace();
           }
           return String.valueOf(nums);
   }


    /*public static void main(String[] args) {
          System.out.println(getNums("sysperfinfo","192.168.1.253","1433","sa","123456"));
    }*/

}
