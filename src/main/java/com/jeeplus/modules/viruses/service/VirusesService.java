package com.jeeplus.modules.viruses.service;

import java.util.Date;
import java.util.List;
import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.attack.entity.Attack;
import com.jeeplus.modules.attack.mapper.AttackMapper;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indicator.service.IndicatorService;
import com.jeeplus.modules.loophole.entity.Loophole;
import com.jeeplus.modules.loophole.mapper.LoopholeMapper;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.mapper.ResourceIndicatorlistMapper;
import com.jeeplus.modules.viruses.entity.Viruses;
import com.jeeplus.modules.viruses.mapper.VirusesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.mapper.CpuMapper;
import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

/**
 * 安全攻击Service
 * @author lei
 * @version 2018-09-28
 */
@Service
@Transactional(readOnly = true)
public class VirusesService extends CrudService<VirusesMapper, Viruses> {

	@Autowired
	private VirusesMapper virusesMapper;

	public Viruses getTopViruses(String id) {
		return virusesMapper.getTopViruses(id);
	}

}