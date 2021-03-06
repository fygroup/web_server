package com.jeeplus.modules.update.server.linux.memory;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.linux.memory.MemoryIndicatorCalculateLinuxByV1V2;
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


public class LinuxMemoryByV1V2 implements UpdateService{
    private  MemoryService memoryService= (MemoryService)SpringBeanUtils.getBeanByClass(MemoryService.class);
    private String uuid="6a66e7b9d3af48a3a4430907cd78f4a0";


    @Override
    public void updateIndicator(Resource resource) {
        synchronized (uuid.intern()) {
            Long star=System.currentTimeMillis();
            Date date = new Date();
            ResourceGatherItem resourceGatherItem = null;
            List<Memory> list = null;
            Memory physicalMemory=new Memory();
            Memory virtualMemory=new Memory();

            double memoryUsedPercent = 0.00;
            List<Map<String, Object>> memoryList = MemoryIndicatorCalculateLinuxByV1V2.getVirtualPhysicalMemory(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            if (CheckObject.checkList(memoryList)) {
                list = new ArrayList<>();
                for (Map<String, Object> map : memoryList) {
                    Memory memory = new Memory();
                    memory.setId(IdGen.uuid());
                    memory.setCreateDate(date);
                    memory.setResourceId(resource.getId());
                    memory.setName(String.valueOf(map.get("name")));
                    memory.setTotal(String.valueOf(map.get("total")));
                    memory.setUsed(String.valueOf(map.get("used")));
                    memory.setFree(String.valueOf(map.get("free")));
                    memory.setUsedRate(String.valueOf(map.get("usedRate")));
                    memory.setType(String.valueOf(map.get("type")));
                    list.add(memory);
                    memoryUsedPercent += Transformation.null2Double(memory.getUsedRate());
                    if("1".equals(String.valueOf(map.get("type")))){
                        physicalMemory=memory;
                    }else if("2".equals(String.valueOf(map.get("type")))){
                        virtualMemory=memory;
                    }
                }
            }
            if (CheckObject.checkList(list)) {
                DecimalFormat df = new DecimalFormat("######0.00");
                MemoryUsedRate memoryUsedRate = new MemoryUsedRate();
                memoryUsedRate.setCreateDate(date);
                memoryUsedRate.setResourceId(resource.getId());
                memoryUsedRate.setUsedRate(df.format(memoryUsedPercent / list.size()));
                memoryService.saveList(resourceGatherItem, list, resource, memoryUsedRate,physicalMemory,virtualMemory);
            }
            //System.out.println(resource.getName()+"内存方法结束："+(System.currentTimeMillis()-star));
        }
    }

}
