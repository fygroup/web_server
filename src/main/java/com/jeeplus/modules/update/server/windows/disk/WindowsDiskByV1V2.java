package com.jeeplus.modules.update.server.windows.disk;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.windows.disk.DiskIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WindowsDiskByV1V2 implements UpdateService {

    private DiskService diskService= (DiskService) SpringBeanUtils.getBeanByClass(DiskService.class);
    private String uuid="39bce43488064b6cbf5748853c3be9ec";

    @Override
    public synchronized void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
            Long star = System.currentTimeMillis();
            ResourceGatherItem resourceGatherItem = null;
            List<Disk> list = null;
            double totalFree = 0;
            List<Map<String, Object>> diskList = DiskIndicatorCalculateWindowsByV1V2.getHardpan(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));
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
                    totalFree += Double.parseDouble(String.valueOf(map.get("free")).replace("GB", ""));
                    i++;
                }
            }
            if (CheckObject.checkList(list)) {
                diskService.saveList(resourceGatherItem, list, resource, df.format(totalFree));
            }
            //System.out.println(resource.getName() + "磁盘方法结束：" + (System.currentTimeMillis() - star));
        }
    }
}
