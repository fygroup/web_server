package com.jeeplus.modules.collect.middleware.weblogic;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import java.util.List;

/**
 * Created by Le on 2018/1/11.
 */
public class ApplicationNameBySNMPIndicatorCalculator {
    private static String oids = "1.3.6.1.4.1.140.625.105.1.15";

    public static String getApplicationName(String ip, String community, String port, String timeout, String retries,int version){
        String result="";
        List<ResourceOidValue> list= SnmpUtil.snmpWalk(ip, community, oids, port, timeout, retries, version);
        if(CheckObject.checkList(list)){
            StringBuffer tempvalue = new StringBuffer();
            for(int i = 0; i < list.size(); ++i) {
                if(tempvalue.indexOf(list.get(i).getValue()) == -1) {
                    tempvalue.append(list.get(i).getValue()).append(",");
                }
            }
            result = tempvalue.toString();
            if(result.length()>1){
                result=result.substring(0,result.length()-1);
            }
        }
        return  result;
    }
}
