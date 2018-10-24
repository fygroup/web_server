package com.jeeplus.modules.collect.networkequipment.exchange.cpu;

import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import java.text.DecimalFormat;

public class CpuIndicatorCalculateRuijie {

    private static final String CPU_LOAD_5SCONDS_AVG = "1.3.6.1.4.1.4881.1.1.10.2.36.1.1.1.0";
    private static final String CPU_LOAD_1MINUTS_AVG = "1.3.6.1.4.1.4881.1.1.10.2.36.1.1.2.0";
    private static final String CPU_LOAD_5MINUTS_AVG = "1.3.6.1.4.1.4881.1.1.10.2.36.1.1.3.0";

    /**
     * 获取锐捷CPU利用率 百分比
     */
    public static String getCpuUsedPercent(String ip, String community,  String port, String timeout, String retries, int version) {
       DecimalFormat df = new DecimalFormat("######0.00");
        String result=null;
        double resultDouble=0.00;

        resultDouble= Transformation.null2Double(SnmpUtil.snmpGetString(ip, community, CPU_LOAD_5SCONDS_AVG, port, timeout, retries, version));
        if(resultDouble<=0.0){
            resultDouble= Transformation.null2Double(SnmpUtil.snmpGetString(ip, community, CPU_LOAD_1MINUTS_AVG, port, timeout, retries, version));
        }
        if(resultDouble<=0.0){
            resultDouble= Transformation.null2Double(SnmpUtil.snmpGetString(ip, community, CPU_LOAD_5MINUTS_AVG, port, timeout, retries, version));
        }
        if(resultDouble>0.0){
           return df.format(resultDouble);
        }
        return "0.00";
    }

}
