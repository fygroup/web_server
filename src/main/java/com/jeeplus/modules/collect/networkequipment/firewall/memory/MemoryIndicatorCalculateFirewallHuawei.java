package com.jeeplus.modules.collect.networkequipment.firewall.memory;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;
import java.util.List;

public class MemoryIndicatorCalculateFirewallHuawei {


    private static final String MEMORY_LOAD = "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.8.0";

    private static final String MEMORY_TOTAL1 = "1.3.6.1.4.1.2011.6.1.2.1.1.2";
    private static final String MEMORY_FREE1 = "1.3.6.1.4.1.2011.6.1.2.1.1.3";


    private static final String MEMORY_USED2 = "1.3.6.1.4.1.2011.2.2.5.1";
    private static final String MEMORY_FREE2 = "1.3.6.1.4.1.2011.2.2.5.2";


    private static final String MEMORY_OID3 = "1.3.6.1.4.1.2011.5.25.31.1.1.1.1.7";
    private static final String MEMORY_STATOID3 = "1.3.6.1.4.1.2011.5.25.31.1.1.1.1.3";


    private static final String MEMORY_OID4 = "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.8";
    private static final String MEMORY_STATOID4 = "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.4";


    private static final String MEMORY_TOTAL5 = "1.3.6.1.4.1.2011.6.3.5.1.1.2";
    private static final String MEMORY_FREE5 = "1.3.6.1.4.1.2011.6.3.5.1.1.3";


    public static String getMemoryUsedPercent(String ip, String community, String port, String timeout, String retries,int version) {
        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
        String result=null;
        try {
            ResourceOidValue resourceOidValue = SnmpUtil.snmpGet(ip, community, MEMORY_LOAD, port, timeout, retries, version);
            if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                result = df.format(resourceOidValue.getValue());
            }
            if(result==null){
                List<ResourceOidValue> total= SnmpUtil.snmpWalk(ip, community, MEMORY_TOTAL1, port, timeout, retries, version);
                List<ResourceOidValue> free= SnmpUtil.snmpWalk(ip, community, MEMORY_FREE1, port, timeout, retries, version);
                if(CheckObject.checkList(total)&&CheckObject.checkList(free)&&total.size()==free.size()){
                    Double totalMem=0.00;
                    Double freeMem=0.00;
                    for(int i = 0; i < total.size(); ++i) {
                        totalMem += Transformation.null2Double(total.get(i).getValue());
                        freeMem += Transformation.null2Double(free.get(i).getValue());
                    }
                    if(totalMem > 0.00) {
                        double percent=((totalMem - freeMem) * 100.00) / totalMem;
                        if(percent>0.00){
                            result = df.format(percent);
                        }
                    }
                }
            }

            if(result==null){
                List<ResourceOidValue> total= SnmpUtil.snmpWalk(ip, community, MEMORY_USED2, port, timeout, retries, version);
                List<ResourceOidValue> free= SnmpUtil.snmpWalk(ip, community, MEMORY_FREE2, port, timeout, retries, version);
                if(CheckObject.checkList(total)&&CheckObject.checkList(free)&&total.size()==free.size()){
                    Double usedMem=0.00;
                    Double freeMem=0.00;
                    for(int i = 0; i < total.size(); ++i) {
                        usedMem += Transformation.null2Double(total.get(i).getValue());
                        freeMem += Transformation.null2Double(free.get(i).getValue());
                    }
                    Double totalMem=usedMem+freeMem;
                    if(totalMem > 0.00) {
                        double percent=(usedMem / totalMem)*100.00;
                        if(percent>0.00){
                            result = df.format(percent);
                        }
                    }
                }
            }


            if(result==null){
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, MEMORY_OID3, port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    int num = 0;
                    double memLoad=0.00;
                    for(int i = 0; i < list.size(); ++i) {
                        try {
                            double value = Transformation.null2Double(list.get(i).getValue());
                            if(value > 0) {
                                memLoad = (float)value;
                                ++num;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(num == 1) {
                        result=df.format(memLoad);
                    }else if(num>1){
                        List<ResourceOidValue>  listStat= SnmpUtil.snmpWalk(ip, community, MEMORY_STATOID3, port, timeout, retries, version);
                        if(CheckObject.checkList(listStat)){
                            boolean has = false;
                            double temp = 0.00;
                            for(int i = 0; i < listStat.size(); ++i) {
                                if(listStat.get(i).getValue() != null && list.get(i).getValue() != null && listStat.get(i).getValue().equals("4")) {
                                    try {
                                        temp += Transformation.null2Double(list.get(i).getValue());
                                        has = true;
                                    } catch (Exception e2) {
                                    }
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }


            if(result==null){
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, MEMORY_OID4, port, timeout, retries, version);
                if(CheckObject.checkList(list)){
                    int val = 0;
                    double memLoad=0.00;
                    for(int i = 0; i < list.size(); ++i) {
                        try {
                           double value = Transformation.null2Double(list.get(i).getValue());
                            if(value > 0) {
                                memLoad = value;
                                ++val;
                            }
                        } catch (Exception e) {
                        }
                    }

                    //result=df.format(memLoad/list.size());

                    if(val == 1) {
                        result=df.format(memLoad);
                    }else if(val>1){
                        List<ResourceOidValue>  listStat= SnmpUtil.snmpWalk(ip, community, MEMORY_STATOID4, port, timeout, retries, version);
                        if(CheckObject.checkList(listStat)){
                            boolean has = false;
                            double temp = 0.00;
                            for(int i = 0; i < listStat.size(); ++i) {
                                if(listStat.get(i).getValue() != null && list.get(i).getValue() != null && listStat.get(i).getValue().equals("4")) {
                                    try {
                                        temp += Transformation.null2Double(list.get(i).getValue());
                                        has = true;
                                    } catch (Exception e2) {
                                    }
                                }
                            }

                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }

            if(result==null){
                List<ResourceOidValue> total= SnmpUtil.snmpWalk(ip, community, MEMORY_TOTAL5, port, timeout, retries, version);
                List<ResourceOidValue> free= SnmpUtil.snmpWalk(ip, community, MEMORY_FREE5, port, timeout, retries, version);
                if(CheckObject.checkList(total)&&CheckObject.checkList(free)&&total.size()==free.size()){
                    Double totalMem=0.00;
                    Double usedMem=0.00;
                    Double freeMem=0.00;
                    for(int i = 0; i < total.size(); ++i) {
                        totalMem += Transformation.null2Double(total.get(i).getValue());
                        freeMem += Transformation.null2Double(free.get(i).getValue());
                    }
                    usedMem=totalMem-freeMem;
                    if(totalMem > 0.00) {
                        double percent=(usedMem / totalMem)*100.00;
                        if(percent>0.00){
                            result = df.format(percent);
                        }
                    }
                }
            }





            if(result == null) {
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.5", port, timeout, retries, version);
                if(CheckObject.checkList(list)) {
                    int  count = 0;
                    double memLoad = 0.00;
                    for(int i = 0; i < list.size(); ++i) {
                        memLoad = Transformation.null2Double(list.get(i).getValue());
                        if(memLoad>0) {
                            ++count;
                        }
                    }
                    if(count == 1) {
                        result = df.format(memLoad);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.1", port, timeout, retries, version);
                        if(resultListStat != null) {
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i) != null && list.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("1") && !Transformation.null2String(list.get(i).getValue()).equals("0")) {
                                    result = df.format(Transformation.null2Double(list.get(i).getValue()));
                                    break;
                                }
                            }
                        }
                    }
                }
            }



            if(result == null) {
                List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.5", port, timeout, retries, version);
                if(CheckObject.checkList(list)) {
                    int  count = 0;
                    double memLoad = 0.00;
                    for(int i = 0; i < list.size(); ++i) {
                        memLoad = Transformation.null2Double(list.get(i).getValue());
                        if(memLoad>0) {
                            ++count;
                        }
                    }
                    if(count == 1) {
                        result = df.format(memLoad);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat= SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.1", port, timeout, retries, version);
                        if(resultListStat != null) {
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i) != null && list.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("1") && !Transformation.null2String(list.get(i).getValue()).equals("0")) {
                                    result = df.format(Transformation.null2Double(list.get(i).getValue()));
                                    break;
                                }
                            }
                        }
                    }
                }
            }



            if(result == null) {
                List<ResourceOidValue> list = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.4.1.8", port, timeout, retries, version);
                if (CheckObject.checkList(list)) {
                    double memLoad = 0.00;
                    for (int i = 0; i < list.size(); ++i) {
                        memLoad = Transformation.null2Double(list.get(i).getValue());
                    }
                    result = df.format(memLoad/list.size());
                }
            }


        }catch (Exception e){
        }

        return result==null?"0.00":result;
    }




    /**
     * main测试方法

     */
    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getMemoryUsedPercent("139.0.1.249", "public","161", "500", "2",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}