package com.jeeplus.modules.update.networkequipment.router.cpu;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.router.cpu.CpuIndicatorCalculateRouterH3C;
import com.jeeplus.modules.collect.networkequipment.router.cpu.CpuIndicatorCalculateRouterHuawei;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.util.Date;

public class H3CRouterCpuByV1V2 implements UpdateService {

    private CpuService cpuService= (CpuService) SpringBeanUtils.getBeanByClass(CpuService.class);
    private String uuid="0d600c89c50f49c6aeb8188fb7dce170";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star=System.currentTimeMillis();
            Date date=new Date();
            ResourceGatherItem resourceGatherItem=null;
            if(resource.getResourceType()==null){
                return;
            }

            CpuUsedRate cpuUsedRate = new CpuUsedRate();
            cpuUsedRate.setCreateDate(date);
            cpuUsedRate.setResourceId(resource.getId());
            cpuUsedRate.setUsedRate(CpuIndicatorCalculateRouterH3C.getCpuUse(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
            cpuService.saveList(resourceGatherItem, null, resource, cpuUsedRate);
           // System.out.println(resource.getName()+"获取CPU方法结束："+(System.currentTimeMillis()-star));
        }
    }
}
