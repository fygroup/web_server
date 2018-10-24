package com.jeeplus.modules.update.server.windows.process;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.windows.process.ProcessIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.process.entity.Process;
import com.jeeplus.modules.process.service.ProcessService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class WindowsProcesByV1V2 implements UpdateService {

    private ProcessService processService = (ProcessService) SpringBeanUtils.getBeanByClass(ProcessService.class);
    private String uuid="17c38ab64b3b4814b16215c549c6e99e9";


    @Override
    public void updateIndicator(Resource resource) {
        synchronized (uuid.intern()) {
            //System.out.println(resource.getName() + "获取进程方法开始=====");
            Long star = System.currentTimeMillis();
            List<Process> list = null;
            ResourceGatherItem resourceGatherItem = null;
            List<Map<String, Object>> processList = ProcessIndicatorCalculateWindowsByV1V2.GetProcessesRunInfo(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            if (CheckObject.checkList(processList)) {
                Date date = new Date();
                list = new ArrayList<>();
                for (Map<String, Object> map : processList) {
                    Process process = new Process();
                    process.setId(IdGen.uuid());
                    process.setCreateDate(date);
                    process.setResourceId(resource.getId());
                    process.setCpuUsedPercent(String.valueOf(map.get("cpuUsedPercent")));
                    process.setInitParameter(String.valueOf(map.get("initParameter")));
                    process.setName(String.valueOf(map.get("runName")));
                    process.setPath(String.valueOf(map.get("runPath")));
                    process.setPid(String.valueOf(map.get("pid")));
                    process.setMemory(String.valueOf(map.get("perfMem")));
                    list.add(process);
                }
            }
            if (CheckObject.checkList(list)) {
                processService.saveList(resourceGatherItem, list, resource);
            }
            //System.out.println(resource.getName() + "获取进程方法结束：" + (System.currentTimeMillis() - star));
        }
    }
}
