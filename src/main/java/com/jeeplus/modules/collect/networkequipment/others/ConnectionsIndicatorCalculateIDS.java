package com.jeeplus.modules.collect.networkequipment.others;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;

public class ConnectionsIndicatorCalculateIDS {
     public static String currentConnectionsOid="1.3.6.1.4.1.14331.5.5.1.4.9.0"; //当前连接数
     public static String cpsOid="1.3.6.1.4.1.14331.5.5.1.4.10.0";               //每秒连接数

    public static String  getCurrentConnections(String ip, String community, String port, String timeout, String retries, int version) {
        int result=Transformation.null2Integer(SnmpUtil.snmpGetString( ip, community,currentConnectionsOid, port,  timeout,retries,version));
       return String.valueOf(result);
    }

    public static String getCps(String ip, String community, String port, String timeout, String retries, int version) {
        int result=Transformation.null2Integer(SnmpUtil.snmpGetString( ip, community,cpsOid, port,  timeout,retries,version));
        return String.valueOf(result);

    }

    /**
     * main测试方法
     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getCurrentConnections("139.32.15.102", "public","161", "500", "2",1));
            System.out.println(getCps("139.32.15.102", "public","161", "500", "2",1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
