package com.jeeplus.modules.collect.networkequipment.router.cpu;


import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuIndicatorCalculateRouterCisco {

    private static final String CISCO_CPU_LOAD_1MINUTS_AVG = "1.3.6.1.4.1.9.2.1.57.0";
    private static final String CISCO_CPU_LOAD_10MINUTS_AVG = "1.3.6.1.4.1.9.2.1.58.0";
    private static final String CISCO_CPU_LOAD_5SCONDS_AVG = "1.3.6.1.4.1.9.2.1.56.0";
    private static final String CISCO_CPU_LOAD_OTHER_AVG = "1.3.6.1.4.1.9.9.109.1.1.1.1.3";
    /**
     * 获取CPU利用率 百分比
     */
    public static String getCpuUse(String ip, String community , String port, String timeout, String retries, int version) {
        String result=null;
        DecimalFormat df = new DecimalFormat("######0.00");
        try {
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, CISCO_CPU_LOAD_5SCONDS_AVG, port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
            }
            if(result==null) {
                resourceOidValue=SnmpUtil.snmpGet(ip, community, CISCO_CPU_LOAD_10MINUTS_AVG, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }
            if(result==null) {
                resourceOidValue=SnmpUtil.snmpGet(ip, community, CISCO_CPU_LOAD_1MINUTS_AVG, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result==null?"0.00":result;
    }

    public static void main(String[] args) throws Exception {
        try {

            System.out.println(getCpuUse("192.168.1.1", "asus","161", "500", "4",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
