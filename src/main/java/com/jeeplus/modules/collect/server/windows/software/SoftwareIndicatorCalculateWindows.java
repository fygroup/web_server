package com.jeeplus.modules.collect.server.windows.software;


import com.jeeplus.common.utils.Transformation;
import org.apache.commons.collections.map.HashedMap;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class SoftwareIndicatorCalculateWindows {

    private static Snmp snmp = null;
    private static CommunityTarget target = null;
    private static PDU responsepdu = new PDU();


    public static  int DEFAULT_VERSION = SnmpConstants.version1;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final int DEFAULT_PORT = 161;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;



    /**
     * 创建对象communityTarget，用于返回target
     * @return CommunityTarget
     */
    public static CommunityTarget createDefault(String ip, String community,int port,int timeout, int retries,int DEFAULT_VERSION) {
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip
                + "/" + (port==0?DEFAULT_PORT:port));
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(timeout==0?DEFAULT_TIMEOUT:timeout); // milliseconds
        target.setRetries(retries==0?DEFAULT_RETRY:retries);
        return target;
    }

    public static CommunityTarget getCommunityTarget(String ip, String community, String port, String timeout, String retries,int version) {
        if(version==2){
            DEFAULT_VERSION=SnmpConstants.version2c;
        }
        CommunityTarget target = createDefault(ip, community, Transformation.null2Integer(port), Transformation.null2Integer(timeout),  Transformation.null2Integer(retries),DEFAULT_VERSION);
        return target;
    }



    /**
     * 获取软件列表
     *
     */
    public static List<Map<String, Object>> GetSoftwareInfo(String ip, String community, String port, String timeout, String retries,int version) {
        List<Map<String, Object>> softwareResultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> softwareMap = new HashMap<String, Object>();
        List<String> softwareList = new ArrayList<String>();          //软件列表
        List<String> modifyTimeList = new ArrayList<String>();        //修改时间
        List<String> softwareTypeList = new ArrayList<String>();      //软件类型


        try{

        target=getCommunityTarget( ip,  community,  port,  timeout,  retries, version);
        DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);

        // 创建 PDU
        PDU pdu = new PDU();
        snmp.listen();
        pdu.setType(PDU.GETBULK);
        responsepdu = pdu;
         pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.6.3.1.2")));
         getSoftwareListIteration(softwareList, "1.3.6.1.2.1.25.6.3.1.2");
        responsepdu.clear();
        responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.6.3.1.4")));
        getSoftwareListIteration(softwareTypeList, "1.3.6.1.2.1.25.6.3.1.4");

        responsepdu.clear();
        responsepdu.add(new VariableBinding(new OID("1.3.6.1.2.1.25.6.3.1.5")));
        getSoftwareListIteration(modifyTimeList, "1.3.6.1.2.1.25.6.3.1.5");



            Map<String ,String> map=new HashedMap();
        int i = 0;
        if (softwareList.size() > 0 ) {
            for (String value : softwareList) {
                if(map.containsKey(value)){
                    continue;
                }
                map.put(value,value);
                softwareMap = new HashMap<String, Object>();
                softwareMap.put("softwareName", hexToChinese(value));
                if(i<softwareTypeList.size()){
                    softwareMap.put("softwareType", softwareTypeList.get(i));
                }else{
                    softwareMap.put("softwareType", null);
                }

                if(i<modifyTimeList.size()){
                    softwareMap.put("modifyTime", parse16ToTime(modifyTimeList.get(i)));
                }else{
                    softwareMap.put("modifyTime",null);
                }

                softwareResultList.add(softwareMap);
                i++;
            }
        }
       // System.out.println(softwareResultList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return softwareResultList;
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

    public static void main(String[] args) throws Exception {
        System.out.println(hexToChinese("44:3a:5c:20:4c:61:62:65:6c:3a:d0:c2:bc:d3:be:ed:20:20:53:65:72:69:61:6c:20:4e:75:6d:62:65:72:20:63:63:32:62:32:37:31:30"));
    }


}
