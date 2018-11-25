package com.jeeplus.modules.collect;


import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import java.util.ArrayList;
import java.util.List;

public class GeneralMethod {
    public static String oidval = "1.3.6.1.2.1.25.3.3.1.2";//企业的主 OID
    public static  String sysNameOid = "1.3.6.1.2.1.1.5.0";//系统名称
    public static  String descriptionOid = "1.3.6.1.2.1.1.1.0";
    public static  String subNetMaskOid = "1.3.6.1.2.1.4.20.1.3";//子网掩码

    /**
     * 获取系统OID
     * @param ip
     * @param community
     * @param port
     * @param timeout
     * @param retries
     * @return
     */
    public static ResourceOidValue getSysOid(String ip, String community, String port, String timeout, String retries) {
        return SnmpUtil.snmpGet(ip, community, oidval, port, timeout,retries,1);
    }



    public static void main(String[] args) throws Exception {
        try {
            System.out.println(getSysName("172.16.212.200", "ssfy","161", "500", "1").getValue());
            //getHardpan();
            //System.out.println(CpuIndicatorCalculateLinux.getCpuUse().get("cpuLoad").toString().equals("-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取系统名称
     * @param ip
     * @param community
     * @param port
     * @param timeout
     * @param retries
     * @return
     */
    public static ResourceOidValue getSysName (String ip,String community,String port, String timeout, String retries) {

        return SnmpUtil.snmpGet(ip, community, sysNameOid, port, timeout,retries,1);
    }

    /**
     * 获取描述信息
     * @param ip
     * @param community
     * @param port
     * @param timeout
     * @param retries
     * @return
     */
    public static ResourceOidValue getDescription (String ip,String community,String port, String timeout, String retries) {

        return SnmpUtil.snmpGet(ip, community, descriptionOid, port, timeout,retries,1);
    }

    public static String getDescriptionValue (String ip,String community,String port, String timeout, String retries) {

        ResourceOidValue resourceOidValue=SnmpUtil.snmpGet(ip, community, descriptionOid, port, timeout,retries,1);
        if(resourceOidValue==null||resourceOidValue.getValue()==null){
            return "";
        }
        return resourceOidValue.getValue();
    }



    /**
     * 获取子网掩码
     * @param ip
     * @param community
     * @param port
     * @param timeout
     * @param retries
     * @return
     */

    public static ResourceOidValue getSubNetMask(String ip,String community,String port, String timeout, String retries) {
        ResourceOidValue oidValue=new ResourceOidValue();
        List<ResourceOidValue> resourceOidValueList=new ArrayList<>();
        resourceOidValueList=SnmpUtil.snmpWalk(ip, community, subNetMaskOid, port, timeout,retries,1);
        if(CheckObject.checkList(resourceOidValueList)){
            for(ResourceOidValue resourceOidValue : resourceOidValueList){
                if((subNetMaskOid+"."+ip).equals(resourceOidValue.getOid())){
                    return resourceOidValue;
                }

            }
        }
        return oidValue;
    }
}
