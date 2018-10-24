package com.jeeplus.modules.update.safetyequipment.indicator;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.networkequipment.others.ConnectionsIndicatorCalculateNessus;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.update.UpdateService;

import java.util.List;

import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

public class TopsecNessusIndicator implements UpdateService {

    private ResourceIndicatorlistService resourceIndicatorlistService= (ResourceIndicatorlistService)SpringBeanUtils.getBeanByClass(ResourceIndicatorlistService.class);
    private ResourceExceptionService resourceExceptionService= (ResourceExceptionService)SpringBeanUtils.getBeanByClass(ResourceExceptionService.class);
    private String uuid="be1sa41c89be47cfa070f694e5e782da";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            List<ResourceIndicatorlist> updateList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"6");
            if(CheckObject.checkList(updateList)){
                for(ResourceIndicatorlist resourceIndicatorlist : updateList){
                    String value="";
                    if("64".equals(resource.getResourceType().getCode())){   //漏洞扫描
                        if("61001".equals(resourceIndicatorlist.getIndicator().getItemType())){
                            value = ConnectionsIndicatorCalculateNessus.getCurrentConnections(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
                        }else if("61002".equals(resourceIndicatorlist.getIndicator().getItemType())){
                            value = ConnectionsIndicatorCalculateNessus.getCps(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
                        }
                    }

                    if(StringUtils.isNotBlank(value)) {
                        ResourceException resourceException = null;
                        try {
                            resourceException = calculationThreshold(resource, resourceIndicatorlist, value,resourceExceptionService.getByResourceIndicatorId(resourceIndicatorlist.getId()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (resourceException != null) {
                            resourceExceptionService.save(resourceException);
                        }
                        resourceIndicatorlistService.setValue(value,resourceIndicatorlist.getId());
                    }
                }
            }
        }
    }

}
