package com.jeeplus.modules.collect.networkequipment.exchange.cpu;


import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;
import java.util.List;

public class CpuIndicatorCalculateHuawei {

    private static final String HUAWEI_CPU_LOAD_AVG1 = "1.3.6.1.4.1.2011.6.1.1.1.4";
    private static final String HUAWEI_CPU_LOAD_AVG2 = "1.3.6.1.4.1.2011.2.23.1.18.1.3.0";
    private static final String HUAWEI_CPU_LOAD_AVG3 = "1.3.6.1.4.1.2011.2.2.4.12.0";
    private static final String HUAWEI_CPU_MIB_5SEC = "1.3.6.1.4.1.2011.6.3.4.1.2";
    private static   String OID="1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5";
    /**
     * 获取CPU利用率 百分比
     */
    public static String getCpuUse(String ip, String community , String port, String timeout, String retries, int version) {
        String result=null;
        DecimalFormat df = new DecimalFormat("######0.00");
        try {
            ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, HUAWEI_CPU_LOAD_AVG1, port, timeout, retries, version);
            if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
            }
            if(result==null) {
                resourceOidValue=SnmpUtil.snmpGet(ip, community, HUAWEI_CPU_LOAD_AVG2, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }
            if(result==null) {
                resourceOidValue=SnmpUtil.snmpGet(ip, community, HUAWEI_CPU_LOAD_AVG3, port, timeout, retries, version);
                if(resourceOidValue!=null&&resourceOidValue.getValue()!=null){
                    result= df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }

            if(result==null) {
                List<ResourceOidValue> list=SnmpUtil.snmpWalk(ip, community, HUAWEI_CPU_MIB_5SEC, port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    double cpu=0.00;
                    for(ResourceOidValue item:list){
                        cpu+= Transformation.null2Double(item.getValue());
                    }
                    result= df.format(cpu/list.size());
                }
            }

            if(result==null) {
                List<ResourceOidValue> list=SnmpUtil.snmpWalk(ip, community, OID, port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    double cpu=0.00;
                    for(ResourceOidValue item:list){
                        cpu+= Transformation.null2Double(item.getValue());
                    }
                    result= df.format(cpu/list.size());
                }
            }

            if(result==null) {
                OID="1.3.6.1.4.1.2011.5.25.31.1.1.1.1.3";
                List<ResourceOidValue> list=SnmpUtil.snmpWalk(ip, community, OID, port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    double cpu=0.00;
                    for(ResourceOidValue item:list){
                        cpu+= Transformation.null2Double(item.getValue());
                    }
                    result= df.format(cpu/list.size());
                }
            }




            if(result==null){
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.4", port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    int count = 0;
                    Double value=0.00;
                    for(ResourceOidValue item:list) {
                        if (item.getValue() != null) {
                            if(Transformation.null2Double(item.getValue())>0) {
                                value = Transformation.null2Double(item.getValue());
                                if(value>0) {
                                    ++count;
                                }
                            }
                        }
                    }
                    if(count==1){
                        result= df.format(value);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultList = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.1", port, timeout, retries, version);
                        if(CheckObject.checkList(resultList)){
                            Boolean has = false;
                            double temp = 0.00;
                            try {
                                for(int i = 0; i < resultList.size(); ++i) {
                                    if(resultList.get(i) != null &&resultList.get(i).getValue() != null&& list.get(i) != null&& list.get(i).getValue() != null && resultList.get(i).getValue().equals("4")) {
                                        temp += Transformation.null2Double(list.get(i).getValue());
                                        has = true;
                                    }
                                }
                            } catch (Exception e) {
                            }
                            if(has) {
                                result=df.format(temp);
                            }
                        }
                    }
                }
            }




            if(result==null){
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.4", port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    int count = 0;
                    Double value=0.00;
                    for(ResourceOidValue item:list) {
                        if (item.getValue() != null) {
                            if(Transformation.null2Double(item.getValue())>0) {
                                value = Transformation.null2Double(item.getValue());
                                if(value>0) {
                                    ++count;
                                }
                            }
                        }
                    }
                    if(count==1){
                        result= df.format(value);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultList = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.1", port, timeout, retries, version);
                        if(CheckObject.checkList(resultList)){
                            Boolean has = false;
                            double temp = 0L;
                            try {
                                for(int i = 0; i < resultList.size(); ++i) {
                                    if(resultList.get(i) != null &&resultList.get(i).getValue() != null&& list.get(i) != null&& list.get(i).getValue() != null && resultList.get(i).getValue().equals("4")) {
                                        temp += Transformation.null2Double(list.get(i).getValue());
                                        has = true;
                                    }
                                }
                            } catch (Exception e) {
                            }
                            if(has) {
                                result=df.format(temp);
                            }
                        }
                    }
                }
            }

            if(result==null) {
                List<ResourceOidValue> list = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.4.1.7", port, timeout, retries, version);
                if (CheckObject.checkList(list)) {
                    Double value = 0.00;
                    for (ResourceOidValue item : list) {
                           value += Transformation.null2Double(item.getValue());
                    }
                    result=df.format(value/list.size());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result==null?"0.00":result;
    }

    public static void main(String[] args) throws Exception {
        try {

            System.out.println(getCpuUse("192.168.0.108", "public","161", "500", "4",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

