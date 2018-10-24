package com.jeeplus.modules.collect.networkequipment.others.cpu;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;
import java.util.List;

public class CpuIndicatorCalculateNsfocus {
    private static final String CPU_LOAD_AVG1 = "1.3.6.1.4.1.19849.2.3.1.0";
    private static final String CPU_LOAD_AVG2 = "1.3.6.1.4.1.19849.2.8.2.0";
    private static final String CPU_LOAD_AVG3 = "1.3.6.1.4.1.19849.5.3.1.0";

    /**
     * 获取CPU利用率 百分比
     */
    public static String getCpuUsedPercent(String ip, String community,  String port, String timeout, String retries, int version) {
        String result=null;
        DecimalFormat df = new DecimalFormat("######0.00");
        try {
            ResourceOidValue resourceOidValue= SnmpUtil.snmpGet(ip, community, CPU_LOAD_AVG1, port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                result= df.format(resourceOidValue.getValue());
            }

            if(result==null){
                resourceOidValue= SnmpUtil.snmpGet(ip, community, CPU_LOAD_AVG2, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(resourceOidValue.getValue());
                }
            }

            if(result==null){
                resourceOidValue= SnmpUtil.snmpGet(ip, community, CPU_LOAD_AVG3, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(resourceOidValue.getValue());
                }
            }

            if(result==null){
                List<ResourceOidValue> list=  SnmpUtil.snmpWalk(ip, community, "1.3.6.1.2.1.25.3.3.1.2", port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    double total=0.00;
                    for(ResourceOidValue item:list){
                        total+= Transformation.null2Double(item.getValue());
                    }
                    result= df.format(total/list.size());
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return result==null?"0.00":result;
    }


    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getCpuUsedPercent("139.32.15.102", "public","161", "500", "4",1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
