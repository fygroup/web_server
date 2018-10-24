package com.jeeplus.modules.collect.networkequipment.exchange.memory;

import com.jeeplus.common.utils.CheckObject;
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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MemoryIndicatorCalculateCisco {
    /**
     * 取得思科内存利用率
     *
     */
    public static Double  getMemoryUsedPercent(String ip, String community, String port, String timeout, String retries,int version) {
        List<ResourceOidValue> used= SnmpUtil.snmpWalk( ip, community,"1.3.6.1.4.1.9.9.48.1.1.1.5", port,  timeout,retries,version);
        List<ResourceOidValue> free= SnmpUtil.snmpWalk( ip, community,"1.3.6.1.4.1.9.9.48.1.1.1.6", port,  timeout,retries,version);
        if(CheckObject.checkList(used)&&CheckObject.checkList(free)&&free.size()==used.size()){
            double total=0;
            double useds=0;
            for(int i=0;i<used.size();i++){
                total+=(Double.parseDouble(used.get(i).getValue())+Double.parseDouble(free.get(i).getValue()));
                useds+=Double.parseDouble(used.get(i).getValue());
            }
            if(total>0){
                return (useds/total)*100;
            }
        }
        return 0.00;
    }
    /**
     * main测试方法
     */
    public static void main(String[] args) throws Exception {
        try {
            getMemoryUsedPercent("192.168.0.107", "public","161", "500", "2",1);
            //System.out.println(CpuIndicatorCalculateLinuxByV1V2.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}