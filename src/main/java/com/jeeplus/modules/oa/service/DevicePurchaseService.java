package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.DevicePurchase;
import com.jeeplus.modules.oa.mapper.DevicePurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DevicePurchaseService extends CrudService<DevicePurchaseMapper, DevicePurchase> {
    @Autowired
    private DevicePurchaseMapper devicePurchaseMapper;

    public DevicePurchase get(String id) {
        DevicePurchase entity = mapper.get(id);
        return entity;
    }

    public List<DevicePurchase> findList(DevicePurchase devicePurchase) {
        return super.findList(devicePurchase);
    }

    public Page<DevicePurchase> findPage(Page<DevicePurchase> page, DevicePurchase devicePurchase) {
        return super.findPage(page, devicePurchase);
    }

    @Transactional(readOnly = false)
    public void save(DevicePurchase devicePurchase) {
        super.save(devicePurchase);
    }

    @Transactional(readOnly = false)
    public void delete(DevicePurchase devicePurchase) {
        super.delete(devicePurchase);
    }
}
