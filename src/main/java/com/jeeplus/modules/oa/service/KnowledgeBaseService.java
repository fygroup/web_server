package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.KnowledgeBase;
import com.jeeplus.modules.oa.mapper.KnowledgeBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KnowledgeBaseService extends CrudService<KnowledgeBaseMapper, KnowledgeBase> {
    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    public KnowledgeBase get(String id) {
        KnowledgeBase entity = mapper.get(id);
        return entity;
    }

    public List<KnowledgeBase> findList(KnowledgeBase knowledgeBase) {
        return super.findList(knowledgeBase);
    }

    public Page<KnowledgeBase> findPage(Page<KnowledgeBase> page, KnowledgeBase knowledgeBase) {
        return super.findPage(page, knowledgeBase);
    }

    @Transactional(readOnly = false)
    public void save(KnowledgeBase knowledgeBase) {
        super.save(knowledgeBase);
    }

    @Transactional(readOnly = false)
    public void delete(KnowledgeBase knowledgeBase) {
        super.delete(knowledgeBase);
    }
}
