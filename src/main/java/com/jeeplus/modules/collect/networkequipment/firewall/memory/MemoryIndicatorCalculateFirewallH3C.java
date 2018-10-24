package com.jeeplus.modules.collect.networkequipment.firewall.memory;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;

import java.text.DecimalFormat;
import java.util.List;

public class MemoryIndicatorCalculateFirewallH3C {

        private static final String MEMORY_LOAD = "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.8.0";
        private static final String MEM_25506_0 = "1.3.6.1.4.1.25506.2.6.1.1.1.1.8.0";
        private static final String MEM_25506_1 = "1.3.6.1.4.1.25506.8.35.18.1.16.0";
        private static final String MEM_25506 = "1.3.6.1.4.1.25506.2.6.1.1.1.1.8";
        private static final String MEM_25506_STATE = "1.3.6.1.4.1.25506.2.6.1.1.1.1.4";

        private static final String MEM_TOTAL1 = "1.3.6.1.4.1.2011.6.1.2.1.1.2";
        private static final String MEM_FREE1 =  "1.3.6.1.4.1.2011.6.1.2.1.1.3";

        private static final String MEM_USED2 = "1.3.6.1.4.1.2011.2.2.5.1";
        private static final String MEM_FREE2 =  "1.3.6.1.4.1.2011.2.2.5.2";


    /**
     * 取得H3C内存利用率
     *
     */
    public static String getMemoryUsedPercent(String ip, String community, String port, String timeout, String retries,int version) {
        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
        String result=null;
        double memLoad=0.00;
        try {
            ResourceOidValue resourceOidValue = SnmpUtil.snmpGet(ip, community, MEMORY_LOAD, port, timeout, retries, version);
            if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                result = df.format(Transformation.null2Double(resourceOidValue.getValue()));
            }

            if(result==null) {
                resourceOidValue = SnmpUtil.snmpGet(ip, community, MEM_25506_0, port, timeout, retries, version);
                if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }

            if(result==null) {
                resourceOidValue = SnmpUtil.snmpGet(ip, community, MEM_25506_1, port, timeout, retries, version);
                if (resourceOidValue != null && resourceOidValue.getValue() != null) {
                    result = df.format(Transformation.null2Double(resourceOidValue.getValue()));
                }
            }

            if(result==null) {
                List<ResourceOidValue> resultList25506 = SnmpUtil.snmpWalk(ip, community, MEM_25506, port, timeout, retries, version);
                if (CheckObject.checkList(resultList25506)) {
                    int count = 0;
                    double memLoad25506 = 0.00;

                    for(int i = 0; i < resultList25506.size(); ++i) {
                        memLoad = Transformation.null2Double(resultList25506.get(i));
                        if(memLoad > 0) {
                            memLoad25506 = memLoad;
                            ++count;
                        }
                    }
                    if(count == 1) {
                        result = df.format(memLoad25506);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat = SnmpUtil.snmpWalk(ip, community, MEM_25506_STATE, port, timeout, retries, version);
                        if (CheckObject.checkList(resultListStat)) {
                            boolean has = false;
                            double temp = 0.0F;
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i).getValue() != null && resultList25506.get(i).getValue() != null && resultListStat.get(i).getValue().equals("4")) {
                                    temp += Transformation.null2Double(resultList25506.get(i).getValue());
                                    has = true;
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }


            if(result==null) {
                List<ResourceOidValue> totalList = SnmpUtil.snmpWalk(ip, community, MEM_TOTAL1, port, timeout, retries, version);
                List<ResourceOidValue> freeList = SnmpUtil.snmpWalk(ip, community, MEM_FREE1, port, timeout, retries, version);
                if(CheckObject.checkList(totalList)&&CheckObject.checkList(freeList)&&totalList.size()==freeList.size()){
                    Double totalMem=0.00;
                    Double freeMem=0.00;
                    for(int i = 0; i < totalList.size(); ++i) {
                        totalMem += Transformation.null2Double(totalList.get(i).getValue());
                        freeMem += Transformation.null2Double(freeList.get(i).getValue());
                    }
                    if(totalMem > 0.00) {
                        double percent=((totalMem - freeMem) * 100.00) / totalMem;
                        if(percent>0.00){
                            result = df.format(percent);
                        }
                    }
                }
            }


            if(result==null) {
                List<ResourceOidValue> usedList = SnmpUtil.snmpWalk(ip, community, MEM_USED2, port, timeout, retries, version);
                List<ResourceOidValue> freeList = SnmpUtil.snmpWalk(ip, community, MEM_FREE2, port, timeout, retries, version);
                if(CheckObject.checkList(usedList)&&CheckObject.checkList(freeList)&&usedList.size()==freeList.size()){
                    Double totalMem=0.00;
                    Double usedMem=0.00;
                    Double freeMem=0.00;
                    for(int i = 0; i < usedList.size(); ++i) {
                        usedMem += Transformation.null2Double(usedList.get(i).getValue());
                        freeMem += Transformation.null2Double(freeList.get(i).getValue());
                    }
                    totalMem=usedMem+freeMem;
                    if(totalMem > 0.00) {
                        double percent=(usedMem * 100.00) / totalMem;
                        if(percent>0.00){
                            result = df.format(percent);
                        }
                    }
                }
            }



            if(result==null) {
                List<ResourceOidValue> resultList25506 = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.5.25.31.1.1.1.1.7", port, timeout, retries, version);
                if (CheckObject.checkList(resultList25506)) {
                    int count = 0;
                    double memLoads = 0.00;

                    for(int i = 0; i < resultList25506.size(); ++i) {
                        memLoad = Transformation.null2Double(resultList25506.get(i).getValue());
                        if(memLoad > 0) {
                            memLoads = memLoad;
                            ++count;
                        }
                    }
                    //result=df.format(memLoad25506/count);
                    if(count == 1) {
                        result = df.format(memLoads);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.5.25.31.1.1.1.1.3", port, timeout, retries, version);
                        if (CheckObject.checkList(resultListStat)) {
                            boolean has = false;
                            double temp = 0.0F;
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i).getValue() != null && resultList25506.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("4")) {
                                    temp += Transformation.null2Double(resultList25506.get(i).getValue());
                                    has = true;
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }




            if(result==null) {
                List<ResourceOidValue> resultList25506 = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.8", port, timeout, retries, version);
                if (CheckObject.checkList(resultList25506)) {
                    int count = 0;
                    double memLoads = 0.00;

                    for(int i = 0; i < resultList25506.size(); ++i) {
                        memLoad = Transformation.null2Double(resultList25506.get(i).getValue());
                        if(memLoad > 0) {
                            memLoads = memLoad;
                            ++count;
                        }
                    }
                    //result=df.format(memLoad25506/count);
                    if(count == 1) {
                        result = df.format(memLoads);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.10.2.6.1.1.1.1.4", port, timeout, retries, version);
                        if (CheckObject.checkList(resultListStat)) {
                            boolean has = false;
                            double temp = 0.0F;
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i).getValue() != null && resultList25506.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("4")) {
                                    temp += Transformation.null2Double(resultList25506.get(i).getValue());
                                    has = true;
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }



            if(result==null) {
                List<ResourceOidValue> resultList25506 = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.5", port, timeout, retries, version);
                if (CheckObject.checkList(resultList25506)) {
                    int count = 0;
                    double memLoads = 0.00;

                    for(int i = 0; i < resultList25506.size(); ++i) {
                        memLoad = Transformation.null2Double(resultList25506.get(i).getValue());
                        if(memLoad > 0) {
                            memLoads = memLoad;
                            ++count;
                        }
                    }
                    //result=df.format(memLoad25506/count);
                    if(count == 1) {
                        result = df.format(memLoads);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.1.1.1", port, timeout, retries, version);
                        if (CheckObject.checkList(resultListStat)) {
                            boolean has = false;
                            double temp = 0.0F;
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i).getValue() != null && resultList25506.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("4")) {
                                    temp += Transformation.null2Double(resultList25506.get(i).getValue());
                                    has = true;
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }



            if(result==null) {
                List<ResourceOidValue> resultList25506 = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.5", port, timeout, retries, version);
                if (CheckObject.checkList(resultList25506)) {
                    int count = 0;
                    double memLoads = 0.00;

                    for(int i = 0; i < resultList25506.size(); ++i) {
                        memLoad = Transformation.null2Double(resultList25506.get(i).getValue());
                        if(memLoad > 0) {
                            memLoads = memLoad;
                            ++count;
                        }
                    }
                    //result=df.format(memLoad25506/count);
                    if(count == 1) {
                        result = df.format(memLoads);
                    } else if(count > 1) {
                        List<ResourceOidValue> resultListStat = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.10.1.1", port, timeout, retries, version);
                        if (CheckObject.checkList(resultListStat)) {
                            boolean has = false;
                            double temp = 0.0F;
                            for(int i = 0; i < resultListStat.size(); ++i) {
                                if(resultListStat.get(i).getValue() != null && resultList25506.get(i).getValue() != null && Transformation.null2String(resultListStat.get(i).getValue()).equals("4")) {
                                    temp += Transformation.null2Double(resultList25506.get(i).getValue());
                                    has = true;
                                }
                            }
                            if(has) {
                                result = df.format(temp);
                            }
                        }
                    }
                }
            }

            if(result==null) {
                List<ResourceOidValue> resultList = SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.2.17.1.2.3.4.1.8", port, timeout, retries, version);
                if (CheckObject.checkList(resultList)) {
                    double memLoads = 0.00;
                    for (int i = 0; i < resultList.size(); ++i) {
                        memLoads += Transformation.null2Double(resultList.get(i).getValue());
                    }
                    result = df.format(memLoads/ resultList.size());
                }
            }
            if(result == null) {
                Double  total_other= 0.00;
                Double free_other = 0.00;
                Double used_other = 0.00;
                List<ResourceOidValue> listTotal=SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.6.3.5.1.1.2", port, timeout, retries, version);
                List<ResourceOidValue> listFree=SnmpUtil.snmpWalk(ip, community, "1.3.6.1.4.1.2011.6.3.5.1.1.3", port, timeout, retries, version);
                if(CheckObject.checkList(listTotal)&&CheckObject.checkList(listFree)&&listTotal.size()==listFree.size()){
                    for(int i = 0; i < listTotal.size(); ++i) {
                        total_other += Transformation.null2Double(listTotal.get(i));
                        free_other += Transformation.null2Double(listFree.get(i));
                    }
                    used_other = total_other - free_other;
                    if(total_other != 0.00) {
                        result = df.format((used_other / total_other)*100);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
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