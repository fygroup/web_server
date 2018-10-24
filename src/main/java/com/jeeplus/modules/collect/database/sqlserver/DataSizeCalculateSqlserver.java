package com.jeeplus.modules.collect.database.sqlserver;

import com.jeeplus.modules.resource.entity.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

/**
 * Created by Le on 2017/11/23.
 */
public class DataSizeCalculateSqlserver {

    public static String getSqlserverDataSize(Resource resource) {
        String tableName="sysperfinfo";
        return getDataSize(tableName,resource.getIp(),resource.getResourceBaseInfo().getAccessConfigPort(),resource.getResourceBaseInfo().getAccessConfigUserName(),resource.getResourceBaseInfo().getAccessConfigPassword());

    }

    static String getDataSize(String tableName,String ip,String port,String userName,String password ) {
        long dataSize = 0l;
        Statement stat = null;
        Connection con = null;
        ResultSet queryResult = null;
        String url="jdbc:sqlserver://"+ip+":"+port;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection(url,userName,password);
            stat = con.createStatement();
            String sql = "select cntr_value from  " + tableName + " where counter_name='Data File(s) Size (KB)' and" + " instance_name='_Total'";
            queryResult = stat.executeQuery(sql);
            if(queryResult.next()) {
                dataSize=Long.parseLong(queryResult.getString(1));
            }
            if(stat != null) {
                stat.close();
            }
            if(con != null) {
                con.close();//关闭数据库连接
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return getSize(dataSize);
    }

    //KB转为MB
    static  String getSize(long dataSize){
        if(dataSize<=0){
            return  "0";
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        Double result=0.0;
        result=dataSize/1024.0;
        return  df.format(result);
    }

}
