package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.DeviceApply;
import com.jeeplus.modules.oa.entity.DeviceList;
import com.jeeplus.modules.oa.mapper.DeviceApplyMapper;
import com.jeeplus.modules.oa.mapper.DeviceListMapper;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeviceListService extends CrudService<DeviceListMapper, DeviceList> {
    @Autowired
    private DeviceListMapper deviceListMapper;

    public DeviceList get(String id) {
        DeviceList entity = mapper.get(id);
        return entity;
    }

    public List<DeviceList> findList(DeviceList deviceList) {
        return super.findList(deviceList);
    }

    public Page<DeviceList> findPage(Page<DeviceList> page, DeviceList deviceList) {
        return super.findPage(page, deviceList);
    }

    @Transactional(readOnly = false)
    public void save(DeviceList deviceList) {
        super.save(deviceList);
    }

    @Transactional(readOnly = false)
    public void delete(DeviceList deviceList) {
        super.delete(deviceList);
    }

    /**
     * 维修
     */
    @Transactional(readOnly = false)
    public void stopJob(DeviceList deviceList){
        deviceList.setState("1");
        super.save(deviceList);
    }

    /**
     * 正常
     */
    @Transactional(readOnly = false)
    public void restartJob(DeviceList deviceList){
        deviceList.setState("0");
        super.save(deviceList);
    }
}
