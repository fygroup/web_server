package com.jeeplus.modules.update.storageequipment.disk;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.linux.disk.DiskIndicatorCalculateLinuxByV1V2;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StorageEquipmentDiskByV1V2 implements UpdateService {

    private DiskService diskService= (DiskService) SpringBeanUtils.getBeanByClass(DiskService.class);
    private String uuid="47c9f2e5165347rrewre342d9750b2d";

    @Override
    public synchronized void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star=System.currentTimeMillis();
            ResourceGatherItem resourceGatherItem = null;
            List<Disk> list = null;
            double totalFree=0;
            List<Map<String, Object>> diskList = DiskIndicatorCalculateLinuxByV1V2.getHardpan(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
            if (CheckObject.checkList(diskList)) {
                Date date = new Date();
                list = new ArrayList<>();
                int i = 1;

                for (Map<String, Object> map : diskList) {
                    Disk disk = new Disk();
                    disk.setId(IdGen.uuid());
                    disk.setCreateDate(date);
                    disk.setResourceId(resource.getId());
                    disk.setName(String.valueOf(map.get("name")));
                    disk.setTotal(String.valueOf(map.get("total")));
                    disk.setUsed(String.valueOf(map.get("used")));
                    disk.setFree(String.valueOf(map.get("free")));
                    disk.setUsedRate(String.valueOf(map.get("usedRate")));
                    disk.setSort(String.valueOf(i));
                    list.add(disk);
                    totalFree += Double.parseDouble(String.valueOf(map.get("free")).replace("GB",""));
                    i++;
                }
            }
            if (CheckObject.checkList(list)) {
                diskService.saveList(resourceGatherItem, list, resource,String.valueOf(totalFree));
            }
            //System.out.println(resource.getName()+"磁盘方法结束："+(System.currentTimeMillis()-star));
        }
    }
}
