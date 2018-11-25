package com.jeeplus.modules.collect.server.windows.networkinterface;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import org.apache.commons.collections.map.HashedMap;
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

public class NetworkInterfaceIndicatorCalculateWindowsByV1V2 {

    private static Snmp snmp = null;
    private static CommunityTarget target = null;
    private static PDU responsepdu = new PDU();


    /**
     * 获取网络接口
     *
     */
    public static List<Map<String, Object>> GetNetworkInterfaceInfo(String ip, String community, String port, String timeout, String retries,int version) {
        List<Map<String, Object>> networkInterfaceResultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> networkInterfaceMap = new HashMap<String, Object>();
        List<String> nameList = new ArrayList<String>();          //名称
        List<String> capacityList = new ArrayList<String>();          //容量
        List<String> macList = new ArrayList<String>();          //MAC

        List<String> inputByteList = new ArrayList<String>();          //接受字节
        List<String> outputByteList = new ArrayList<String>();          //发送字节

        List<String> typeList = new ArrayList<String>();          //类型

        List<String> statusList = new ArrayList<String>();          //状态

        // 1.3.6.1.2.1.2.2.1.0  get接口数目
        // 1.3.6.1.2.1.2.2.1.2 	 网络接口信息描述
        // 1.3.6.1.2.1.2.2.1.3   类型
        // 1.3.6.1.2.1.2.2.1.4   接口发送和接收的最大IP数据报[BYTE]
        // 1.3.6.1.2.1.2.2.1.5   接口当前带宽[bps]
        // 1.3.6.1.2.1.2.2.1.6    接口的物理地址
        // 1.3.6.1.2.1.2.2.1.7
        // 1.3.6.1.2.1.2.2.1.8   接口当前操作状态[up|down]
        // 1.3.6.1.2.1.2.2.1.10  接口收到的字节数
        // 1.3.6.1.2.1.2.2.1.11  接口收到的数据包个数
        // 1.3.6.1.2.1.2.2.1.16    接口发送的字节数
        // 1.3.6.1.2.1.2.2.1.17   接口发送的数据包个数



        try{
            target= SnmpUtil.getCommunityTarget( ip,  community,  port,  timeout,  retries, version);
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);

            // 创建 PDU
            PDU pdu = new PDU();
            snmp.listen();
            pdu.setType(PDU.GETBULK);
            responsepdu = pdu;
            pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.2")));
            getSoftwareListIteration(nameList, "1.3.6.1.2.1.2.2.1.2");
            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.3")));
            getSoftwareListIteration(typeList, "1.3.6.1.2.1.2.2.1.3");

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.5")));
            getSoftwareListIteration(capacityList, "1.3.6.1.2.1.2.2.1.5");

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.6")));
            getSoftwareListIteration(macList, "1.3.6.1.2.1.2.2.1.6");

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.10")));
            getSoftwareListIteration(inputByteList, "1.3.6.1.2.1.2.2.1.10");

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.16")));
            getSoftwareListIteration(outputByteList, "1.3.6.1.2.1.2.2.1.16");

            responsepdu.clear();
            responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.2.2.1.8")));
            getSoftwareListIteration(statusList, "1.3.6.1.2.1.2.2.1.8");
        /* responsepdu.clear();
        responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.6.3.1.5")));
        getSoftwareListIteration(modifyTimeList, "1.3.6.1.2.1.25.6.3.1.5");*/




            int i = 0;
            Map<String,String> map=new HashedMap();
            if (nameList.size() > 0 ) {
                for (String value : nameList) {
                    if(map.containsKey(value)){
                        continue;
                    }
                    map.put(value,value);

                    try {
                        if (!"6".equals(typeList.get(i)) || "".equals(macList.get(i)) || hexToChinese(value).toLowerCase().indexOf("wan miniport") != -1) {
                            i++;
                            continue;
                        }
                        networkInterfaceMap = new HashMap<String, Object>();
                        networkInterfaceMap.put("name", hexToChinese(value));
                        // 输出容量
                        float c = (float) Transformation.null2Float(capacityList.get(i)) / 1000 / 1000;
                        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
                        networkInterfaceMap.put("capacity", df.format(c) + "Mbps");
                        networkInterfaceMap.put("mac", macList.get(i));
                        networkInterfaceMap.put("inputByte", inputByteList.get(i));
                        networkInterfaceMap.put("outputByte", outputByteList.get(i));
                        networkInterfaceMap.put("sort", String.valueOf(i + 1));
                        networkInterfaceMap.put("type", typeList.get(i));
                        networkInterfaceMap.put("status", statusList.get(i));
                        networkInterfaceResultList.add(networkInterfaceMap);
                        i++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //break;
                }
            }
           // System.out.println(networkInterfaceResultList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return networkInterfaceResultList;
    }

    /**
     * 迭代获取软件列表
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void getSoftwareListIteration(List listType, String endOid)
            throws IOException {
        ResponseEvent response = snmp.getBulk(responsepdu, target);

        if (response.getResponse() == null) {
            return;
        } else {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>)response.getResponse()
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

            getSoftwareListIteration(listType, endOid);
        }
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



    public static String parse16ToTime(String value) {
        if (value == null) {
            return null;
        }
        value=value.replaceAll(":"," ");

        String tmp[] = null;
        if (value.indexOf(" ") >= 0) {
            tmp = value.split(" ");
        } else {
            tmp = new String[1];
            tmp[0] = value;
        }

        StringBuffer sb = new StringBuffer();
        for (int i=1; i<tmp.length-1; i++){
            String item=tmp[i];
            if(i==1){
                item=tmp[0]+tmp[i];
            }
            sb.append(String.format("%02d", Integer.parseInt(item, 16)));
            if (i < tmp.length - 2) {
                if(i<=2) {
                    sb.append("-");
                }else if(i==3){
                    sb.append(" ");
                }else {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
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
 *
 * @author linwang
 * @createTime 2014-7-12
 * @param args
 */
        public static void main(String[] args) throws Exception {
            try {
             GetNetworkInterfaceInfo("139.0.31.69", "localhost","161", "500", "4",1);

                //getHardpan();
                //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
