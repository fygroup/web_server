package com.jeeplus.modules.oa.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.oa.entity.OaDispose;
import com.jeeplus.modules.oa.entity.OaInspection;
import com.jeeplus.modules.oa.mapper.OaDisposeMapper;
import com.jeeplus.modules.oa.mapper.OaInspectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 问题处理Service
 * @author huanglei
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class OaDisposeService extends CrudService<OaDisposeMapper, OaDispose> {

	@Autowired
	private OaDisposeMapper oaDisposeMapper;

	public OaDispose get(String id) {
		OaDispose entity = mapper.get(id);
		return entity;
	}

	public List<OaDispose> findList(OaDispose oaDispose) {
		return super.findList(oaDispose);
	}
	
	public Page<OaDispose> findPage(Page<OaDispose> page, OaDispose oaDispose) {
		return super.findPage(page, oaDispose);
	}
	
	@Transactional(readOnly = false)
	public void save(OaDispose oaDispose) {
		super.save(oaDispose);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaDispose oaDispose) {
		super.delete(oaDispose);
	}

	//返回String结果
	public String qexecuteSelectSql(String sql){
		return mapper.qexecSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void saveOaDispose(OaDispose oaDispose) {
		oaDispose.preUpdate();
		oaDisposeMapper.update(oaDispose);
	}
}