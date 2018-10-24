package com.jeeplus.modules.update.safetyequipment.cpu;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.firewall.cpu.CpuIndicatorCalculateFirewallH3C;
import com.jeeplus.modules.collect.networkequipment.firewall.cpu.CpuIndicatorCalculateFirewallHuawei;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.update.UpdateService;

import java.util.Date;

public class H3CFireWallCpuByV1V2 implements UpdateService {

    private CpuService cpuService= (CpuService) SpringBeanUtils.getBeanByClass(CpuService.class);
    private String uuid="12c9b47a101a470fbc97d8f2b38b78cf";

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
            cpuUsedRate.setUsedRate(CpuIndicatorCalculateFirewallH3C.getCpuUsedPercent(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType())));
            cpuService.saveList(null, null, resource, cpuUsedRate);

           // System.out.println(resource.getName()+"获取CPU方法结束："+(System.currentTimeMillis()-star));
        }
    }
}
