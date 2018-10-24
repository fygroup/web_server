package com.jeeplus.modules.update.server.windows.software;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.windows.software.SoftwareIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.software.entity.Software;
import com.jeeplus.modules.software.service.SoftwareService;
import com.jeeplus.modules.update.UpdateService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class WindowsSoftwareByV1V2 implements UpdateService {

    private SoftwareService softwareService= (SoftwareService) SpringBeanUtils.getBeanByClass(SoftwareService.class);
    private String uuid="dafa13434a2fe3a45c88a23ff11e0684256";


    @Override
    public void updateIndicator(Resource resource) {
        synchronized (uuid.intern()) {
            Long star = System.currentTimeMillis();
            ResourceGatherItem resourceGatherItem = null;
            if (resource.getResourceType() == null) {
                return;
            }
            List<Software> list = null;
            List<Map<String, Object>> softwareList = SoftwareIndicatorCalculateWindowsByV1V2.GetSoftwareInfo(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            if (CheckObject.checkList(softwareList)) {
                Date date = new Date();
                list = new ArrayList<>();
                for (Map<String, Object> map : softwareList) {
                    Software software = new Software();
                    software.setId(IdGen.uuid());
                    software.setCreateDate(date);
                    software.setResourceId(resource.getId());
                    software.setModifyTime(String.valueOf(map.get("modifyTime")));
                    software.setName(String.valueOf(map.get("softwareName")));
                    software.setType(String.valueOf(map.get("softwareType")));
                    list.add(software);
                }
            }
            if (CheckObject.checkList(list)) {
                softwareService.saveList(resourceGatherItem, list, resource);
            }
            //System.out.println(resource.getName() + "ruanjian方法结束：" + (System.currentTimeMillis() - star));
        }
    }
}
