package com.jeeplus.modules.update.safetyequipment.memory;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.firewall.memory.MemoryIndicatorCalculateFirewall;
import com.jeeplus.modules.collect.networkequipment.others.memory.MemoryIndicatorCalculateIDS;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.update.UpdateService;

import java.util.Date;

public class TopsecIDSMemoryByV1V2 implements UpdateService {


    private MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);
    private String uuid="afab2db430e2457f9cf3a11fea28b869";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star=System.currentTimeMillis();
            Date date = new Date();
            MemoryUsedRate memoryUsedRate = new MemoryUsedRate();
            memoryUsedRate.setCreateDate(date);
            memoryUsedRate.setResourceId(resource.getId());
            memoryUsedRate.setUsedRate(MemoryIndicatorCalculateIDS.getMemory(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
            memoryService.saveList(null, null, resource, memoryUsedRate,null,null);
           // System.out.println(resource.getName()+"内存方法结束："+(System.currentTimeMillis()-star));
        }
    }
}
