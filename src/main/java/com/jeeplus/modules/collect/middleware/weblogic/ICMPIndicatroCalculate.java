package com.jeeplus.modules.collect.middleware.weblogic;


import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

/**
 * Created by Le on 2017/12/7.
 * 连接响应时间
 */
public class ICMPIndicatroCalculate {
    private static String oid="1.3.6.1.4.1.140.625.360.1.60";
    public static  String getValue(String ip, String community, String port, String timeout, String retries,int version){
        String result="-1";
        long start= System.currentTimeMillis();
           ResourceOidValue resourceOidValue= SnmpUtil.snmpGet(ip, community, oid, port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null) {
                result=String.valueOf(System.currentTimeMillis()-start);
            }
        return result;
    }
}