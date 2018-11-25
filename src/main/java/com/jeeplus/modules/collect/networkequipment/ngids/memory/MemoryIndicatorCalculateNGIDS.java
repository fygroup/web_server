package com.jeeplus.modules.collect.networkequipment.ngids.memory;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.middleware.tomcat.GeneralTomcatIndicator;
import com.jeeplus.modules.collect.middleware.tomcat.entity.TomcatInfo;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
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
import java.util.regex.Pattern;

public class MemoryIndicatorCalculateNGIDS {

    private static Snmp snmp = null;
    private static CommunityTarget target = null;
    private static PDU responsepdu = new PDU();






    /**
     * 取得虚拟内存和物理内存容量
     *
     */
    public static List<Map<String, Object>> getMemory(String ip, String community, String port, String timeout, String retries,int version) {
        List<Map<String, Object>> temList = new ArrayList<Map<String, Object>>();
        try{
            target= SnmpUtil.getCommunityTarget( ip,  community,  port,  timeout,  retries, version);
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            // 1.3.6.1.2.1.25.2.3.1.3 储存类型（硬盘（c、d..),内存、虚拟内存）
            List<String> labelss = new ArrayList<String>();
            // 1.3.6.1.2.1.25.2.3.1.4分配单元
            List<Integer> units = new ArrayList<Integer>();
            // 1.3.6.1.2.1.25.2.3.1.5 存储容量
            List<Integer> size = new ArrayList<Integer>();
            // 1.3.6.1.2.1.25.2.3.1.6已使用容量
            List<Integer> used = new ArrayList<Integer>();

            responsepdu = new PDU();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.3.1.3")));
            snmp.listen();
            responsepdu.setType(PDU.GETNEXT);
            getHardpanIteration(labelss, "1.3.6.1.2.1.25.2.3.1.4.1", 1);

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.3.1.4")));
            getHardpanIteration(units, "1.3.6.1.2.1.25.2.3.1.5.1", 2);

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.3.1.5")));
            getHardpanIteration(size, "1.3.6.1.2.1.25.2.3.1.6.1", 2);

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.2.3.1.6")));
            getHardpanIteration2(used, "1.3.6.1.2.1.25.2.3.1.7.1", "1.3.6.1.2.1.25.3", 2);



            int i = 0;
            double p=0;
            for (String label : labelss) {
                Map<String, Object> temMap = new HashMap<String, Object>();
                if ((!("virtual memory".equals(label.toLowerCase()))&& !("physical memory".equals(label.toLowerCase()))) || size.get(i) == 0) {
                    i++;
                    continue;
                }
                String usedPercent = "";
                String total = "";
                String usedStr = "";
                String freeStr = "";
                int a = used.get(i);// 已使用容量
                int b = size.get(i);// 存储容量
                int u = units.get(i);// 分配单元

                // 输出盘符名称
                if(isHex(label)){
                    label=hexToChinese(label);
                }
                temMap.put("name", label.split(":")[0]);
                if("virtual memory".equals((label.split(":")[0]).toLowerCase())) {
                    temMap.put("type", "2");
                }else {
                    temMap.put("type", "1");
                }

                DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0

                // 输出盘符总容量
                float c = (float) b * (float) u / 1024 / 1024 / 1024;
                total = df.format(c);// 返回的是String类型的
                temMap.put("total", total);

                // 输出盘符已使用百分比
                if (size.get(i) != 0) {
                    // java中两个整数相除得到小数点并保留两位小数的方法--是否选择四舍五入
                    c = (float) a / (float) b * 100;
                    df = new DecimalFormat("0.00");// 格式化小数，不足的补0
                    usedPercent = df.format(c);// 返回的是String类型的

                        p += c;

                }
                temMap.put("usedRate", usedPercent);


                // 输出盘符已使用
                c = (float) a * (float) u / 1024 / 1024 / 1024;
                df = new DecimalFormat("0.00");// 格式化小数，不足的补0
                usedStr = df.format(c);// 返回的是String类型的
                temMap.put("used", usedStr);

                // 输出盘符未使用容量
                c = ((float) ((b - a)) / 1024 / 1024 / 1024 * u);
                freeStr = df.format(c);// 返回的是String类型的
                temMap.put("free", freeStr);
                i++;
                temList.add(temMap);
            }

            System.out.println("result：" + (p));
        }catch (Exception e){
            e.printStackTrace();
        }
        return temList;
    }


    /**
     * 迭代获取各盘信息
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void getHardpanIteration(List listType, String endOid,int flag) throws IOException {
        ResponseEvent response=null;
        try {
            response = snmp.getBulk(responsepdu, target);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        if (response.getResponse() == null) {
            return;
        } else {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>)response.getResponse()
                    .getVariableBindings();
            String oid = recVBs.elementAt(0).getOid().toString();
            if (endOid.equals(oid)) {
                return;
            }
            if (!oid.contains(endOid) && (flag == 3 || flag == 4)) {
                return;
            }
            responsepdu = response.getResponse();
            // System.out.println("【获取硬盘信息】Received Value is: " +
            // responsepdu.getVariableBindings());
            String tem = responsepdu.getVariableBindings().toString();
            tem = tem.split("=")[1].split("]")[0].trim();
            if (flag == 2 || flag == 3) {
                if (isNum(tem)) {
                    listType.add(Transformation.null2Integer(tem));
                }
            } else {
                listType.add(tem);
            }

            getHardpanIteration(listType, endOid, flag);
        }
    }


    /**
     * 迭代获取各盘信息
     *
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void getHardpanIteration2(List listType, String endOid, String endOid2,int flag) throws IOException {
        ResponseEvent response=null;
        try {
            response = snmp.getBulk(responsepdu, target);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }

        if (response.getResponse() == null) {
            return;
        } else {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>)response.getResponse()
                    .getVariableBindings();
            String oid = recVBs.elementAt(0).getOid().toString();
            if (endOid.equals(oid)||endOid2.equals(oid)) {
                return;
            }
            if (!oid.contains(endOid) && (flag == 3 || flag == 4)) {
                return;
            }
            if (oid.contains(endOid2)) {
                return;
            }

            responsepdu = response.getResponse();
            // System.out.println("【获取硬盘信息】Received Value is: " +
            // responsepdu.getVariableBindings());
            String tem = responsepdu.getVariableBindings().toString();
            tem = tem.split("=")[1].split("]")[0].trim();
            if (flag == 2 || flag == 3) {
                if (isNum(tem)) {
                    listType.add(Transformation.null2Integer(tem));
                }
            } else {
                listType.add(tem);
            }

            getHardpanIteration2(listType, endOid, endOid2,flag);
        }
    }


    /**
     * 16进制分隔字符
     */
    static final String[] HEX_SPLIT_STRING = {":", " "};
    public static boolean isHex(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }

        for (String splitStr : HEX_SPLIT_STRING) {
            if (value.indexOf(splitStr) >=0) {
                value += splitStr;
                String rex = "([0-9a-fA-F][0-9a-fA-F]["+splitStr+"])+";
                Pattern p = Pattern.compile(rex);
                if (p.matcher(value).matches()) {
                    return true;
                }
            }
        }
        return false;
    }



    public static String  hexToChinese(String value) {
        if(!isHex(value)){
            return value;
        }
        String hexStr = value.replaceAll(":", "");
        byte[] by = HexStringToBytes(hexStr);
        try{
            return new String(by, "GB2312");
        } catch( Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static byte[] HexStringToBytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
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
     *
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


    /**
     * main测试方法

     */
    public static void main(String[] args) throws Exception {
        try {
         /*   Resource resource=new Resource();
            ResourceBaseInfo resourceBaseInfo=new ResourceBaseInfo();
            resourceBaseInfo.setAccessConfigPort("80");
            resourceBaseInfo.setAccessConfigUserName("tomcat");
            resourceBaseInfo.setAccessConfigPassword("tomcat");
            resource.setIp("139.1.1.85");
            resource.setResourceBaseInfo(resourceBaseInfo);

            GeneralTomcatIndicator generalTomcatIndicator=new GeneralTomcatIndicator(resource);
            TomcatInfo tomcatInfo=generalTomcatIndicator.getTomcatInfo();
*/
            System.out.println(getMemory("139.0.1.249", "public","161", "500", "2",1));
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}