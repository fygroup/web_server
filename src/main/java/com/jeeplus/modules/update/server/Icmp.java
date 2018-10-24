package com.jeeplus.modules.update.server;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.modules.collect.Ping.Ping;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResponseTime;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;
import java.util.Date;


public class Icmp implements UpdateService {

    private ResourceService resourceService = (ResourceService) SpringBeanUtils.getBeanByClass(ResourceService.class);
    private String uuid="c90525a91d8a4b12b82c9734246cb102";


    @Override
    public void updateIndicator(Resource resource) {
        synchronized (uuid.intern()) {
            Date date = new Date();
            ResourceGatherItem resourceGatherItem = null;
            Long time = Ping.ping(resource.getIp());
            ResponseTime responseTime = new ResponseTime();
            responseTime.setCreateDate(date);
            responseTime.setResourceId(resource.getId());
            responseTime.setTime(String.valueOf(time));
            resourceService.saveICMPTime(resourceGatherItem, responseTime, resource);
        }
    }
}
