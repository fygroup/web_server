package com.jeeplus.modules.collect.networkequipment.exchange.memory;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SnmpUtil;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import java.text.DecimalFormat;
import java.util.List;

public class MemoryIndicatorCalculateRuijie {

    private static final String MEMORY_LOAD = "1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3";
    private static final String MEMORY_LOAD_TEMP = "1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.0";

    /**
     * 取得Ruijie内存利用率
     *
     */
    public static String  getMemoryUsedPercent(String ip, String community, String port, String timeout, String retries,int version) {
        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
        String result=null;
        List<ResourceOidValue> list = SnmpUtil.snmpWalk(ip, community, MEMORY_LOAD, port, timeout, retries, version);
        double usedPercent=0.00;
        if(CheckObject.checkList(list)){
            for(ResourceOidValue resourceOidValue:list){
                usedPercent+= Transformation.null2Double(resourceOidValue.getValue());
            }
            result=df.format(usedPercent/list.size());
        }else {
            double resultTemp = 0.00;
            resultTemp=Transformation.null2Double(SnmpUtil.snmpGetString(ip, community, MEMORY_LOAD_TEMP, port, timeout, retries, version));
            if(resultTemp>0) {
                result = df.format(resultTemp);
            }
        }
        return result==null?"0.00":result;
    }

}