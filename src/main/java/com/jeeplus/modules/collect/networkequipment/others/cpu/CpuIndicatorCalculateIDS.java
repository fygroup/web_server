package com.jeeplus.modules.collect.networkequipment.others.cpu;


import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;
import java.util.List;

public class CpuIndicatorCalculateIDS {



    private static String oidval = "1.3.6.1.4.1.14331.5.5.1.4.5.0";

    /**
     * 获取CPU利用率列表
     */
    public static String getCpu(String ip, String community, String port, String timeout, String retries, int version) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String result=null;
        try {
            List<ResourceOidValue> list=SnmpUtil.snmpWalk(ip, community, oidval, port, timeout, retries, version);
            double num=0.00;
            if(CheckObject.checkList(list)){
                for(ResourceOidValue resourceOidValue:list){
                    num+= Transformation.null2Double(resourceOidValue.getValue());
                }
                num=num/list.size();
            }
            result= df.format(num);
        }catch (Exception e){
            e.printStackTrace();
        }


        if(result == null){
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.4.2.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null&&Transformation.null2String(resourceOidValue.getValue()).indexOf("3.3.002") != -1) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue())/10.00);
            }
        }


        if(result == null){
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.3.5.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null&&Transformation.null2String(resourceOidValue.getValue()).indexOf("3.3.005") != -1) {
                resourceOidValue = SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.5.5.1.4.5.0", port, timeout, retries, version);
                if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }
        }


        if(result == null){
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.14331.2.2.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                result=df.format(Transformation.null2Double(resourceOidValue.getValue())) ;
            }
        }

        if(result == null){
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, "1.3.6.1.4.1.9952.1.5.1.17.0", port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                result=df.format(Transformation.null2Double(resourceOidValue.getValue())) ;
            }
        }

     return result==null?"0.00":result;
    }



    public static void main(String[] args) throws Exception {
        try {
            System.out.println( getCpu("139.32.15.102", "public","161", "500", "4",1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
