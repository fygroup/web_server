package com.jeeplus.modules.collect.server.linux.process;


import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
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

public class ProcessIndicatorCalculateLinuxByV1V2 {

    private static Snmp snmp = null;
    private static CommunityTarget target = null;
    private static PDU responsepdu = new PDU();
    private static String uuid="a002ef716ea8540d19b0ee0decb45c5f7b";




    /**
     * 获取进程数量
     */
    public static Map<String, Object> GetProcessesCount() throws IOException {

        target = SnmpUtil.getCommunityTarget("192.168.1.139", "le", "161", "500", "4", 1);

        DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);

        PDU responsepdu = new PDU();
        Map<String, Object> temMap = new HashMap<String, Object>();
        try {
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.1.6.0")));
            snmp.listen();
            responsepdu.setType(PDU.GET);
            ResponseEvent response = snmp.send(responsepdu, target);
            if (response.getResponse() == null) {
                temMap.put("success", "false");
                temMap.put("msg", "0");
            } else {
                responsepdu = response.getResponse();
            }
            String tem = responsepdu.getVariableBindings().toString();
            tem = tem.split("=")[1].split("]")[0].trim();
            temMap.put("success", "true");
            temMap.put("ProcessesCount", tem);
            System.out.println("num : " + tem);
        } catch (Exception e) {
            e.printStackTrace();
            temMap.put("success", "false");
            temMap.put("msg", e.getMessage());
            // System.out.println("获取进程失败");
        }

        return temMap;
    }

    /**
     * 获取进程列表
     */
    public static List<Map<String, Object>> GetProcessesRunInfo(String ip, String community, String port, String timeout, String retries, int version) {
          //  synchronized (uuid.intern()) {
                List<Map<String, Object>> temMapList = new ArrayList<Map<String, Object>>();
                Map<String, Object> temMap = new HashMap<String, Object>();
                List<String> cpuUsedTimeList = new ArrayList<String>();      //进程利用cpu时间（百分之一秒）
                List<String> cpuUsedPercentList = new ArrayList<String>();      //进程利用cpu百分比
                List<String> pidList = new ArrayList<String>();             //pid
                List<String> runNameList = new ArrayList<String>();         //进程名称
                List<String> runPathList = new ArrayList<String>();         //路径
                List<String> initParameterList = new ArrayList<String>();     //初始化参数
                List<String> runTypeList = new ArrayList<String>();          //进程类型
                List<String> perfMemList = new ArrayList<String>();          //内存

                try {

                    target = SnmpUtil.getCommunityTarget(ip, community, port, timeout, retries, version);
                    DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
                    snmp = new Snmp(transport);

                    // 创建 PDU
                    PDU pdu = new PDU();
                    snmp.listen();
                    pdu.setType(PDU.GETBULK);
                    responsepdu = pdu;
                    pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.4.2.1.2")));  /*8*/
                    getProcessesListIteration(runNameList, "1.3.6.1.2.1.25.4.2.1.2");
                    responsepdu.clear();
                    responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.5.1.1.1")));
                    getProcessesListIteration(cpuUsedTimeList, "1.3.6.1.2.1.25.5.1.1.1");
                    responsepdu.clear();
                    responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.4.2.1.1")));
                    getProcessesListIteration(pidList, "1.3.6.1.2.1.25.4.2.1.1");
                    responsepdu.clear();
                    responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.4.2.1.4")));
                    getProcessesListIteration(runPathList, "1.3.6.1.2.1.25.4.2.1.4");
                    responsepdu.clear();
                    responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.4.2.1.5")));
                    getProcessesListIteration(initParameterList, "1.3.6.1.2.1.25.4.2.1.5");
       /* responsepdu.clear();
        responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.4.2.1.6")));
        getProcessesListIteration(runTypeList, "1.3.6.1.2.1.25.4.2.1.6");*/
                    responsepdu.clear();
                    responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.5.1.1.2")));
                    getProcessesListIteration(perfMemList, "1.3.6.1.2.1.25.5.1.1.2");

                    Long cpuTotalUserdTime = 0l;
                    for (String item : cpuUsedTimeList) {
                        cpuTotalUserdTime += Transformation.null2Integer(item);
                    }
                    DecimalFormat df = new DecimalFormat("######0.00");
                    for (String item : cpuUsedTimeList) {
                        double result = ((Transformation.null2Integer(item) * 1.0) / cpuTotalUserdTime) * 100;
                        cpuUsedPercentList.add(df.format(result));
                    }


                    if (runNameList.size() > 0 && runPathList.size() > 0 && perfMemList.size() > 0 && cpuUsedPercentList.size() > 0) {
                        int num = runNameList.size() > runPathList.size() ? runPathList.size() : runNameList.size();
                        num = num > perfMemList.size() ? perfMemList.size() : num;
                        num = num > cpuUsedPercentList.size() ? cpuUsedPercentList.size() : num;
                        num = num > initParameterList.size() ? initParameterList.size() : num;
                        num = num > perfMemList.size() ? perfMemList.size() : num;
                        for (int i = 0; i < num; i++) {
                            try {
                                temMap = new HashMap<String, Object>();
                                temMap.put("runName", i < runNameList.size() ? runNameList.get(i) : null);
                                temMap.put("pid", i < pidList.size() ? pidList.get(i) : null);
                                temMap.put("initParameter", i < initParameterList.size() ? initParameterList.get(i) : null);
                                temMap.put("runPath", i < runPathList.size() ? runPathList.get(i) : null);
                                // temMap.put("runType", runTypeList.get(i));
                                temMap.put("perfMem", i < perfMemList.size() ? perfMemList.get(i) + "K" : "");
                                temMap.put("cpuUsedPercent", i < cpuUsedPercentList.size() ? cpuUsedPercentList.get(i) + "%" : "");
                                temMapList.add(temMap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // System.out.println(temMapList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return temMapList;
          //  }
    }

    /**
     * 迭代获取进程列表
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void getProcessesListIteration(List listType, String endOid)
            throws IOException {
        ResponseEvent response = snmp.getBulk(responsepdu, target);

        if (response.getResponse() == null) {
            return;
        } else {
            Vector<VariableBinding> recVBs = response.getResponse()
                    .getVariableBindings();
            String oid = recVBs.elementAt(0).getOid().toString();

            // 传入终止OID是父节点，如果当前迭代的oid不包含父节点前缀则退出迭代
            if (!oid.contains(endOid)) {
                return;
            }
            responsepdu = response.getResponse();
            String tem = responsepdu.getVariableBindings().toString();
            tem = tem.split("=")[1].split("]")[0].trim();
            listType.add(tem);

            getProcessesListIteration(listType, endOid);
        }
    }



    /**
     * 判断字符串是否是数字
     *
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

}
