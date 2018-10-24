package com.jeeplus.modules.collect.networkequipment.ngids.cpu;


import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuIndicatorCalculateNGIDS {



    private static String oidval = "1.3.6.1.2.1.25.3.3.1.2";

    /**
     * 获取CPU利用率列表
     */
    public static List<ResourceOidValue> getCpuList(String ip, String community, String port, String timeout, String retries, int version) {
        try {
            return SnmpUtil.snmpWalk(ip, community, oidval, port, timeout, retries, version);
        }catch (Exception e){
            e.printStackTrace();
        }
     return null;
    }



    public static void main(String[] args) throws Exception {
        try {

            System.out.println(getCpuList("139.0.1.249", "public","161", "500", "4",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
