package com.jeeplus.modules.collect.networkequipment.router.memory;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import java.text.DecimalFormat;
import java.util.*;

public class MemoryIndicatorCalculateRouterCisco {


    public static String getMemory(String ip, String community, String port, String timeout, String retries,int version) {
        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
        Double totalMem=0.00;
        Double usedMem=0.00;
        try{
            List<ResourceOidValue> used = SnmpUtil.snmpWalk( ip, community,"1.3.6.1.4.1.9.9.48.1.1.1.5", port,  timeout,retries,version);
            List<ResourceOidValue> free = SnmpUtil.snmpWalk( ip, community,"1.3.6.1.4.1.9.9.48.1.1.1.6", port,  timeout,retries,version);

            int i=0;
            for (ResourceOidValue resourceOidValue : used) {
                double a = Transformation.null2Double(resourceOidValue.getValue());// 已使用容量
                double f = Transformation.null2Double(free.get(i).getValue());// 存储容量
                totalMem+=(a+f);
                usedMem+=a;
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return df.format((usedMem/totalMem)*100);
    }




    /**
     * main测试方法

     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getMemory("192.168.0.108", "public","161", "500", "2",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}