package com.jeeplus.modules.collect.networkequipment.exchange.cpu;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;

public class CpuIndicatorCalculateCisco {
    /**
     * 获取CPU利用率 百分比
     */
    public static String getCpuUsedPercent(String ip, String community,  String port, String timeout, String retries, int version) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String targetOid="1.3.6.1.4.1.9.2.1.56.0";
        try {
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, targetOid, port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null) {
                return df.format(Double.parseDouble(resourceOidValue.getValue()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
     return "0.00";
    }

    public static void main(String[] args) throws Exception {
        try {
            getCpuUsedPercent("192.168.0.107", "public","161", "500", "4",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
