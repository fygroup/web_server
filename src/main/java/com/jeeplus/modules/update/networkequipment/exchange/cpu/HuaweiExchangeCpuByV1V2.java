package com.jeeplus.modules.update.networkequipment.exchange.cpu;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.exchange.cpu.CpuIndicatorCalculateH3C;
import com.jeeplus.modules.collect.networkequipment.exchange.cpu.CpuIndicatorCalculateHuawei;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.util.Date;

public class HuaweiExchangeCpuByV1V2 implements UpdateService {

    private CpuService cpuService= (CpuService) SpringBeanUtils.getBeanByClass(CpuService.class);
    private String uuid="00a3ff2973c94717a826145698444e48";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Date date=new Date();
            ResourceGatherItem resourceGatherItem=null;
            CpuUsedRate cpuUsedRate=new CpuUsedRate();
            if(resource.getResourceType()==null){
                return;
            }
            String cpuUsedPercent= CpuIndicatorCalculateHuawei.getCpuUse(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            cpuUsedRate.setCreateDate(date);
            cpuUsedRate.setResourceId(resource.getId());
            cpuUsedRate.setUsedRate(cpuUsedPercent);
            cpuService.saveCpuUsedRate(resource,resourceGatherItem,cpuUsedRate);
        }
    }
}
