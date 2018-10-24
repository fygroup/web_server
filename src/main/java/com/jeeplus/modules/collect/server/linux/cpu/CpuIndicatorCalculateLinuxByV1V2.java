package com.jeeplus.modules.collect.server.linux.cpu;


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

public class CpuIndicatorCalculateLinuxByV1V2 {

    private static Snmp snmp = null;
    private static CommunityTarget target = null;
    private static PDU responsepdu = new PDU();



    /**
     * 获取CPU利用率 百分比
     */
    public static List<ResourceOidValue> getCpuUse(String ip, String community, String targetOid, String port, String timeout, String retries, int version) {
        try {
            return SnmpUtil.snmpWalk(ip, community, targetOid, port, timeout, retries, version);
        }catch (Exception e){
            e.printStackTrace();
        }
     return null;
    }

    /**
     * 获取内存利用率
     */
    public static Map<String, Object> getMemoryUse(String ip, String community, String port, String timeout, String retries,int version) throws Exception {
        target= SnmpUtil.getCommunityTarget( ip,  community,  port,  timeout,  retries, version);
        DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);

        Map<String, Object> temMap = new HashMap<String, Object>();
        try {
            int total = 0;
            int used = 0;
            int temInt = 0;
            PDU responsepdu = new PDU();
            responsepdu .add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.2.0")));
            snmp.listen();
            responsepdu.setType(PDU.GET);
            ResponseEvent response = snmp.send(responsepdu, target);
            if (response.getResponse() == null) {
            } else {
                responsepdu = response.getResponse();
                // //System.out.println("全部的: "+
                // responsepdu.getVariableBindings());
                String tem = responsepdu.getVariableBindings().toString();
                total = Integer
                        .parseInt(tem.split("=")[1].split("]")[0].trim());
            }
            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.4.1.9600.1.1.2.2.0")));
            responsepdu.setType(PDU.GET);
            response = snmp.send(responsepdu, target);
            if (response.getResponse() == null) {
            } else {
                responsepdu = response.getResponse();
                // //System.out.println("已用的: " +
                // responsepdu.getVariableBindings());
                String tem = responsepdu.getVariableBindings().toString();
                used = Integer.parseInt(tem.split("=")[1].split("]")[0].trim());
            }
            if (total == 0) {
                temInt = 0;
            }
            if (total != 0) {
                temInt = (total - used) * 100 / total;
                temInt = Math.abs(temInt);
            }

            // System.out.println("内存利用率：" + temInt);

            temMap.put("data", "");
            temMap.put("memoryLoad", temInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temMap;
    }



    /**
     * 判断字符串是否是数字
     */
    public static boolean isNum(String str) {
        try {
            return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        }catch (Exception e){
            System.out.println(" ===  "+str);
        }
        return false;
    }

    /**
     * Integer.parseInt加上非空判断-为null的设置为0
     */
    public static Integer toInteger(String str) {
        Integer integer = 0;
        if (str == null || !isNum(str)) {
            integer = 0;
        } else {
            integer = new Integer(str.trim());
        }
        return integer;
    }


    public static void main(String[] args) throws Exception {
        try {

            getMemoryUse("192.168.1.139", "le","161", "500", "4",1);
            //System.out.println(CpuIndicatorCalculateLinuxByV1V2.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
