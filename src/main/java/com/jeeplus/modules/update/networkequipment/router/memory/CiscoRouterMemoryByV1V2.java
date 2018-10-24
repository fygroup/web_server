package com.jeeplus.modules.update.networkequipment.router.memory;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.router.memory.MemoryIndicatorCalculateRouter;
import com.jeeplus.modules.collect.networkequipment.router.memory.MemoryIndicatorCalculateRouterCisco;
import com.jeeplus.modules.memory.entity.Memory;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CiscoRouterMemoryByV1V2 implements UpdateService {


    private MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);
    private String uuid="9bc1aa1053144a608b73f6fbd841d1c6";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Date date = new Date();
            ResourceGatherItem resourceGatherItem = null;
            MemoryUsedRate memoryUsedRate = new MemoryUsedRate();
            memoryUsedRate.setCreateDate(date);
            memoryUsedRate.setResourceId(resource.getId());
            memoryUsedRate.setUsedRate(MemoryIndicatorCalculateRouterCisco.getMemory(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
            memoryService.saveList(resourceGatherItem, null, resource, memoryUsedRate,null,null);
        }
    }
}
