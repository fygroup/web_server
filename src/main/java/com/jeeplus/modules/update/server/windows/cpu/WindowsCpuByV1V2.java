package com.jeeplus.modules.update.server.windows.cpu;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.windows.cpu.CpuIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WindowsCpuByV1V2 implements UpdateService {

    private CpuService cpuService= (CpuService) SpringBeanUtils.getBeanByClass(CpuService.class);
    private String uuid="fa4e4f61171c4aa387a6b28109923702";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star = System.currentTimeMillis();
            Date date = new Date();
            ResourceGatherItem resourceGatherItem = null;
            List<Cpu> list = null;
            String oidval = "1.3.6.1.2.1.25.3.3.1.2";
            DecimalFormat df = new DecimalFormat("######0.00");
            List<ResourceOidValue> cpuList = null;
            if (resource.getResourceType() == null) {
                return;
            }
            cpuList = CpuIndicatorCalculateWindowsByV1V2.getCpuUse(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), oidval, resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            if (CheckObject.checkList(cpuList)) {
                list = new ArrayList<>();
                int countTimes = cpuList.size();
                double cpuUsedPercent = 0.00;
                for (int i = 0; i < countTimes; i++) {
                    Cpu cpu = new Cpu();
                    cpu.setId(IdGen.uuid());
                    cpu.setName("CPU[" + (cpuList.get(cpuList.size() - countTimes + i).getOid()).replaceAll("1.3.6.1.2.1.25.3.3.1.2.", "") + "]");
                    cpu.setResourceId(resource.getId());
                    cpu.setSort(String.valueOf(i));
                    cpu.setCreateDate(date);
                    cpu.setValue(cpuList.get(cpuList.size() - countTimes + i).getValue());
                    cpuUsedPercent = cpuUsedPercent + Transformation.null2Double(cpuList.get(cpuList.size() - countTimes + i).getValue());
                    list.add(cpu);
                }
                if (CheckObject.checkList(list)) {
                    CpuUsedRate cpuUsedRate = new CpuUsedRate();
                    cpuUsedRate.setCreateDate(date);
                    cpuUsedRate.setResourceId(resource.getId());
                    cpuUsedRate.setUsedRate(df.format(cpuUsedPercent / list.size()));
                    cpuService.saveList(resourceGatherItem, list, resource, cpuUsedRate);
                }
            }
           // System.out.println(resource.getName() + "获取CPU方法结束：" + (System.currentTimeMillis() - star));
        }
    }
}
