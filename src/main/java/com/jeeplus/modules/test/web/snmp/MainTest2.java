package com.jeeplus.modules.test.web.snmp;

import com.jeeplus.common.utils.SnmpUtil;
import org.apache.commons.collections.map.HashedMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class MainTest2 {


    public static String oidval = "1.3.6.1.2.1.1.2.0";
    public static  String sysNameOid = "1.3.6.1.2.1.1.5.0";
    public static void main(String[] args) {
        String ip = "139.0.1.249";
        String community = "public";
        String port="161";
        String retries="2";
       // String oidval ="1.3.6.1.2.1.2.2.1";
        System.out.println("====== "+ SnmpUtil.snmpGetString( ip, community,sysNameOid, port,  "400",retries,2));

      /*  try{
           // Class.forName("com.sybase.jdbc3.jdbc.SybDriver");     // 加载sybase驱动程序
            String url = "jdbc:mysql://192.168.1.253:3306/mysql";    //JDBC的URL
            //String url = "jdbc:sybase:Tds:192.168.1.253:3306";
            Connection conn = DriverManager.getConnection(url, "user", "123456");// 获取连接
            conn.close();

        }catch(Exception e) {
            e.printStackTrace();
        }*/

    }
}