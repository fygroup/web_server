package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.DeviceApply;
import com.jeeplus.modules.oa.mapper.DeviceApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DeviceApplyService extends CrudService<DeviceApplyMapper, DeviceApply> {
    @Autowired
    private DeviceApplyMapper deviceApplyMapper;

    public DeviceApply get(String id) {
        DeviceApply entity = mapper.get(id);
        return entity;
    }

    public List<DeviceApply> findList(DeviceApply deviceApply) {
        return super.findList(deviceApply);
    }

    public Page<DeviceApply> findPage(Page<DeviceApply> page, DeviceApply deviceApply) {
        return super.findPage(page, deviceApply);
    }

    @Transactional(readOnly = false)
    public void save(DeviceApply deviceApply) {
        super.save(deviceApply);
    }

    @Transactional(readOnly = false)
    public void delete(DeviceApply deviceApply) {
        super.delete(deviceApply);
    }
}
