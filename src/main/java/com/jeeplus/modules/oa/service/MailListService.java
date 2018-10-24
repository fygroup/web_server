package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.MailList;
import com.jeeplus.modules.oa.mapper.MailListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MailListService extends CrudService<MailListMapper, MailList> {
    @Autowired
    private MailListMapper mailListMapper;

    public MailList get(String id) {
        MailList entity = mapper.get(id);
        return entity;
    }

    public List<MailList> findList(MailList mailList) {
        return super.findList(mailList);
    }

    public Page<MailList> findPage(Page<MailList> page, MailList mailList) {
        return super.findPage(page, mailList);
    }

    public Page<MailList> findMail(Page<MailList> page, MailList mailList) {
        dataRuleFilter(mailList);
        // 设置分页参数
        mailList.setPage(page);
        // 执行分页查询
        page.setList(mailListMapper.findList(mailList));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(MailList mailList) {
        super.save(mailList);
    }

    @Transactional(readOnly = false)
    public void delete(MailList mailList) {
        super.delete(mailList);
    }
}
