package com.jeeplus.modules.collect.router;


import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import com.jeeplus.modules.test.web.snmp.MainTest;

import java.util.ArrayList;
import java.util.List;

public class Asus {

    public static ResourceOidValue get(String ip, String community, String oidval, String port, String timeout, String retries) {
        /* ip = "192.168.1.1";
         community = "asus";

           1.3.6.1.2.1.1.1.0    description
           1.3.6.1.2.1.1.2.0    系统oid
           1.3.6.1.2.1.1.3.0    运行天数
           1.3.6.1.2.1.1.5.0    型号
           1.3.6.1.2.1.1.6.0
           1.3.6.1.2.1.2.1.0   接口数

         oidval = "1.3.6.1.2.1.7.1.0"; */
       return SnmpUtil.snmpGet(ip, community, oidval, port, timeout,  retries,1);
    }


    public void getList(){
        String ip = "192.168.1.139";
        String community = "le";
        String port="161";
        String timeout="5000";
        String retries="4";
        List<String> oidList=new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.1.0");
        oidList.add("1.3.6.1.2.1.1.2.0");
        oidList.add("1.3.6.1.2.1.1.3.0");
        oidList.add("1.3.6.1.2.1.1.5.0");  //型号
        oidList.add("1.3.6.1.2.1.1.6.0");
        oidList.add("1.3.6.1.2.1.2.1.0");
        SnmpUtil.snmpGetList(ip, community, oidList, port, timeout,  retries,1);
    }
}
