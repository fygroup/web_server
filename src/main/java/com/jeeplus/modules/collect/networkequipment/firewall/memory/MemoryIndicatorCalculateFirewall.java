package com.jeeplus.modules.collect.networkequipment.firewall.memory;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import java.text.DecimalFormat;

public class MemoryIndicatorCalculateFirewall {

        public static String freeMemOid="1.3.6.1.4.1.14331.5.5.1.4.7.0";
        public static String totalMemOid="1.3.6.1.4.1.14331.5.5.1.4.8.0";


    public static String getMemory(String ip, String community, String port, String timeout, String retries, int version) {
        String result=null;
        DecimalFormat df = new DecimalFormat("######0.00");
        double freeMem=Transformation.null2Double(SnmpUtil.snmpGetString( ip, community,freeMemOid, port,  timeout,retries,version));
        double totalMem=Transformation.null2Double(SnmpUtil.snmpGetString( ip, community,totalMemOid, port,  timeout,retries,version));
        if(totalMem!=0) {
            result = df.format(((totalMem - freeMem) / totalMem) * 100);
        }


        if(result == null){
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.2.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null&&Transformation.null2String(resourceOidValue.getValue()).indexOf("3.3.002") != -1) {
                resourceOidValue = SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.4.3.0", port, timeout, retries, version);
                if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue())/10);
                }
            }
        }

        if(result == null) {
            ResourceOidValue resourceOidValue = SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.3.5.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null&&Transformation.null2String(resourceOidValue.getValue()).indexOf("3.3.005") != -1) {
                resourceOidValue = SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.4.6.0", port, timeout, retries, version);
                if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }
        }


        if(result==null){
            ResourceOidValue total= SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.2.3.0", port, timeout, retries, version);
            ResourceOidValue free= SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.2.4.0", port, timeout, retries, version);
            if(total!=null&&total.getValue()!=null&&free!=null&&free.getValue()!=null){
                Double  usedMems=0.00;
                Double  totalMems = Transformation.null2Double(total.getValue());
                Double  freeMems = Transformation.null2Double(free.getValue());
                usedMems=totalMems-freeMems;
                if(totalMems > 0.00) {
                    double percent=(usedMems / totalMems)*100.00;
                    if(percent>0.00){
                        result = df.format(percent);
                    }
                }
            }
        }


        return result==null?"0.00":result;
    }


    /**
     * main测试方法
     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getMemory("139.0.31.251", "public","161", "500", "2",1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
