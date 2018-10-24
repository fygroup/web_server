package com.jeeplus.modules.oa.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.oa.entity.KnowledgeBase;
import com.jeeplus.modules.oa.entity.OaDispose;
import com.jeeplus.modules.oa.entity.OaIssueReturn;
import com.jeeplus.modules.oa.mapper.KnowledgeBaseMapper;
import com.jeeplus.modules.oa.mapper.OaDisposeMapper;
import com.jeeplus.modules.oa.mapper.OaIssueReturnMapper;
import com.jeeplus.modules.oa.service.KnowledgeBaseService;
import com.jeeplus.modules.oa.service.OaDisposeService;
import com.jeeplus.modules.oa.service.OaInspectionService;
import com.jeeplus.modules.oa.service.OaIssueReturnService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sourceforge.jtds.jdbc.DateTime;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.h2.mvstore.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;


/**
 * 问题处理Controller
 * @author huanglei
 * @version 2017-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaDispose")
public class OaDisposeController extends BaseController {

	@Autowired
	private OaDisposeService oaDisposeService;

	@Autowired
	private OaIssueReturnService oaIssueReturnService;


	@Autowired
	private KnowledgeBaseMapper knowledgeBaseMapper;


	@Autowired
	private OaDisposeMapper OaDisposeMapper;

	@Autowired
	private ResourceExceptionService resourceExceptionService;


	@ModelAttribute
	public OaDispose get(@RequestParam(required=false) String id) {
		OaDispose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaDisposeService.get(id);
		}
		if (entity == null){
			entity = new OaDispose();
		}
		return entity;
	}

	/**
	 * 问题处理列表页面
	 */
	@RequiresPermissions("oa:oaDispose:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/oaDispose/oaDisposeList";
	}


	/**
	 * 问题处理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaDispose:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaDispose oaDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaDispose> page = oaDisposeService.findPage(new Page<OaDispose>(request, response), oaDispose);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */


	@RequestMapping(value = "form")
	public String form(OaDispose oaDispose,Model model) {
		if (StringUtils.isNotBlank(oaDispose.getId())){
			User user = UserUtils.getUser();
		}
		model.addAttribute("oaDispose", oaDispose);
		return "modules/oa/oaDispose/oaDisposeForm";
	}


	/**
	 * 问题处理--完成补充解决方案-补充知识库
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(OaDispose oaDispose, Model model) {
		AjaxJson j = new AjaxJson();
		String id = oaDispose.getId();
		String userId = oaDispose.getUser_id();
		String plan = oaDispose.getPlan();
		String cause = oaDispose.getCause();
		String remark = oaDispose.getRemark();
		String declareId = oaDispose.getDeclareId();

		Date d = new Date();//获取系统时间
		DateFormat complete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String completedate = complete.format(d);//格式化时间

		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		if (!beanValidator(model, oaDispose)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		String sql=("update oa_dispose a set a.plan="+"\'"+plan+"\'"+",a.cause="+"\'"+cause+"\'"+",a.remark="+"\'"+remark+"\'\n"+
				",a.end_time="+"\'"+completedate+"\'"+",a.complete="+"\'"+completedate+"\'\n"+
				",a.problemstate='3'"+"where a.id ="+"\'"+id+"\'");
		oaDisposeService.executeUpdateSql(sql);


		String sql2= ("update oa_issue_return a set a.problemstate='3' where a.id="+"\'"+declareId+"\'");
		OaDisposeMapper.execUpdateSql(sql2);//修改问题申报的问题状态为完成
//		oaDispose.setPlan(plan);
//		oaDispose.setCause(cause);
//		oaDispose.setRemark(remark);
//		oaDisposeService.saveOaDispose(oaDispose);


		//知识库
		OaIssueReturn oaIssueReturn=oaIssueReturnService.get(declareId);
		String description = oaIssueReturn.getDescription();//问题描述
		String  ediid = oaIssueReturn.getEdiid();//申报方式
		String declaretype =  oaIssueReturn.getDeclaretype();//申报类型


		OaIssueReturn oaIssueReturns =new OaIssueReturn();
		String indicatorname= oaIssueReturn.getException().getIndicatorName();//指标名称->问题描述
		KnowledgeBase knowledgeBase = new KnowledgeBase();
		knowledgeBase.setId(IdGen.uuid()); //主键id
		if(description==null){
			knowledgeBase.setDescription(indicatorname);//指标名称->问题描述
		}else{
			knowledgeBase.setDescription(description);//问题描述
		}
		knowledgeBase.setCause(cause);//问题原因
		knowledgeBase.setPlan(plan);//解决方案
		knowledgeBase.setUserId(userId);//处理人
		knowledgeBase.setEdiId(ediid);//申报方式
		knowledgeBase.setDeclareType(declaretype);//申报类型
		knowledgeBaseMapper.insert(knowledgeBase);

		if(oaIssueReturn!=null&&oaIssueReturn.getException()!=null){
			if("0".equals(oaIssueReturn.getException().getCurrentStatus())){ //未恢复
//					OaDispose exceptionOaDispose=oaDisposeService.get(oaDispose.getUser_id());
				resourceExceptionService.indexManualRecovery(oaIssueReturn.getException().getId(),new Date(),"1","1", userId);
			}
		}
		j.setSuccess(true);
		j.setMsg("处理完成");
		return j;
	}


	//删除单条
	@ResponseBody
	@RequiresPermissions("oa:oaDispose:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaDispose oaDispose, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaDisposeService.delete(oaDispose);
		j.setMsg("删除问题处理成功");
		return j;
	}

	//删除多条
	@ResponseBody
	@RequiresPermissions("oa:oaDispose:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			System.out.print("取到的id："+id);
			System.out.print(oaDisposeService.get(id));
			oaDisposeService.delete(oaDisposeService.get(id));

		}
		j.setMsg("删除问题处理成功");
		return j;
	}

	/**
	 * 问题处理接单
	 */
	@ResponseBody

	@RequestMapping(value = "order")
	public AjaxJson order(String id, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String sql = ("update oa_dispose a set a.problemstate='1' where a.id="+"\'"+id+"\'");//问题状态改为接单
		OaDisposeMapper.execUpdateSql(sql);
//		String sql2 =("select declare_id from oa_dispose WHERE id ="+"\'"+id+"\'");
//		String k = oaDisposeService.qexecuteSelectSql(sql2);
//		String sql3 = ("update oa_issue_return a set a.problemstate='2' where a.id="+"\'"+k+"\'");//修改问题申报的问题状态为处理中
//		OaDisposeMapper.execUpdateSql(sql3);
		j.setMsg("接单成功");
		return j;
	}


	/**
	 * 问题处理--处理
	 */
	@ResponseBody
	@RequestMapping(value = "solve")
	public AjaxJson solve(String id, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		Date solveTime = new Date(); //获取当前系统时间
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(solveTime);
		String sql = ("update oa_dispose a set a.problemstate='2',a.start_time="+"\'"+d+"\'"+" where a.id="+"\'"+id+"\'");//问题状态改为处理
		OaDisposeMapper.execUpdateSql(sql);
		String sql2 =("select declare_id from oa_dispose WHERE id ="+"\'"+id+"\'");
		String k = oaDisposeService.qexecuteSelectSql(sql2);
		String sql3 = ("update oa_issue_return a set a.problemstate='2' where a.id="+"\'"+k+"\'");//修改问题申报的问题状态为处理中
		OaDisposeMapper.execUpdateSql(sql3);
		j.setMsg("请您处理");
		return j;
	}

}