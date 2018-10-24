package com.jeeplus.modules.update.safetyequipment.cpu;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.others.cpu.CpuIndicatorCalculateIDS;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.update.UpdateService;

import java.util.Date;

public class TopsecIDSCpuByV1V2 implements UpdateService {

    private CpuService cpuService= (CpuService) SpringBeanUtils.getBeanByClass(CpuService.class);
    private String uuid="aab54af57cbcqq1287223cc01a53d0af";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star=System.currentTimeMillis();
            Date date=new Date();
            if(resource.getResourceType()==null){
                return;
            }
            CpuUsedRate cpuUsedRate = new CpuUsedRate();
            cpuUsedRate.setCreateDate(date);
            cpuUsedRate.setResourceId(resource.getId());
            cpuUsedRate.setUsedRate(CpuIndicatorCalculateIDS.getCpu(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
            cpuService.saveList(null, null, resource, cpuUsedRate);

            //System.out.println(resource.getName()+"获取CPU方法结束："+(System.currentTimeMillis()-star));
        }
    }
}
