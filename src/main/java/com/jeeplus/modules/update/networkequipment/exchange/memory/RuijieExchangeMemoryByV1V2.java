package com.jeeplus.modules.update.networkequipment.exchange.memory;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.exchange.memory.MemoryIndicatorCalculateHuawei;
import com.jeeplus.modules.collect.networkequipment.exchange.memory.MemoryIndicatorCalculateRuijie;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.text.DecimalFormat;
import java.util.Date;

public class RuijieExchangeMemoryByV1V2 implements UpdateService {


    private MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);
    private String uuid="017a269c9fe94b24b09b4c31c8195f3a";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Date date = new Date();
            ResourceGatherItem resourceGatherItem = null;
            DecimalFormat df = new DecimalFormat("######0.00");

            String memUsedPercent= MemoryIndicatorCalculateRuijie.getMemoryUsedPercent(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            MemoryUsedRate memoryUsedRate = new MemoryUsedRate();
            memoryUsedRate.setCreateDate(date);
            memoryUsedRate.setResourceId(resource.getId());
            memoryUsedRate.setUsedRate(memUsedPercent);
            memoryService.saveMemoryUsedRate(resourceGatherItem, resource, memoryUsedRate);
        }
    }
}
