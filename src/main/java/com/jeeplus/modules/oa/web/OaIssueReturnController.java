package com.jeeplus.modules.oa.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.oa.entity.*;
import com.jeeplus.modules.oa.mapper.OaDisposeMapper;
import com.jeeplus.modules.oa.mapper.OaIssueReturnMapper;
import com.jeeplus.modules.oa.mapper.OaNotifyMapper;
import com.jeeplus.modules.oa.mapper.OaNotifyRecordMapper;
import com.jeeplus.modules.oa.service.MailListService;
import com.jeeplus.modules.oa.service.OaIssueReturnService;
import com.jeeplus.modules.oa.service.OaNotifyService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 问题申报 controller
 * @Author huanglei
 * @Date 2017/11/14 下午 4:57
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/oaIssueReturn")
public class OaIssueReturnController extends BaseController {

	@Autowired
	private OaIssueReturnService oaIssueReturnService;

	@Autowired
	private OaIssueReturnMapper OaIssueReturnMapper;

	@Autowired
	private  OaNotifyService oaNotifyService;

	@Autowired
	private MailListService mailListService;

	@Autowired
	private OaDisposeMapper OaDisposeMapper;

	@Autowired
    private OaNotifyMapper OaNotifyMapper;

	@Autowired
	private OaNotifyRecordMapper OaNotifyRecordMapper;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ResourceExceptionService resourceExceptionService;


	@ModelAttribute
	public OaIssueReturn get(@RequestParam(required=false) String id) {
		OaIssueReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaIssueReturnService.get(id);
		}
		if (entity == null){
			entity = new OaIssueReturn();
		}
		return entity;
	}

	/**
	 * 问题申报列表页面
	 */
	@RequiresPermissions("oa:oaIssueReturn:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/oaIssueReturn/oaIssueReturnList";
	}


	/**
	 * 问题申报列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaIssueReturn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaIssueReturn oaIssueReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaIssueReturn> page = oaIssueReturnService.findPage(new Page<OaIssueReturn>(request, response), oaIssueReturn);
		return getBootstrapData(page);
	}
	/**
	 * 通讯录数据
	 */
	@ResponseBody
	@RequestMapping(value = "maildata")
	public Map<String, Object> data(MailList mailList, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MailList> page = mailListService.findPage(new Page<MailList>(request, response), mailList);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑异常告警表单页面
	 */
	@RequiresPermissions(value={"exception:exception:resourceException:view","exception:exception:resourceException:add","exception:exception:resourceException:edit"},logical=Logical.OR)
	@RequestMapping(value = "exceptionForm")
	public String form(String exceptionId, Model model) {
		ResourceException resourceException=resourceExceptionService.get(exceptionId);
		model.addAttribute("resourceException", resourceException);
		if(StringUtils.isBlank(resourceException.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		if(resourceException!=null&&resourceException.getOffice()!=null&&StringUtils.isNotBlank(resourceException.getOffice().getId())){
			resourceException.setArea(areaService.getByOffice(resourceException.getOffice().getId()));
		}
		model.addAttribute("resourceException", resourceException);
		return "modules/oa/oaIssueReturn/resourceExceptionForm";
	}


	/**
	 * 查看，增加，编辑申报表单页面
	 */
	@RequiresPermissions(value={"oa:oaIssueReturn:view","oa:oaIssueReturn:add","oa:oaIssueReturn:edit"},logical= Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaIssueReturn oaIssueReturn,Model model) {
		if (StringUtils.isNotBlank(oaIssueReturn.getId())){
			User user = UserUtils.getUser();
		}
		model.addAttribute("oaIssueReturn", oaIssueReturn);
		return "modules/oa/oaIssueReturn/oaIssueReturnForm";
	}

	@RequiresPermissions("oa:oaIssueReturn:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request,OaIssueReturn oaIssueReturn, Model model, RedirectAttributes redirectAttributes) {

		if (!beanValidator(model, oaIssueReturn)){
			return form(oaIssueReturn, model);
		}
		String id = request.getParameter("mailList.id");//申报人id
		String conductorid =request.getParameter("user.id");//处理人id
		MailList mailList =mailListService.get(id);
		String place= mailList.getAddress();
		oaIssueReturn.setPlace(place);
		oaIssueReturn.setDate(new Date());
		oaIssueReturn.setConductorid(conductorid);
		oaIssueReturnService.save(oaIssueReturn);


		if(oaIssueReturn.getProblemClass().equals("3")){//问题状态为紧急状态直接派单

			String sql =("select id from sys_user sy where sy.id not in ( SELECT od.user_id from oa_dispose od where od.problemstate!=3) \n" +
					"  and sy.id  in (select js.user_id  from sys_user_role js where js.role_id='ac4e16ffc0484547ba87c484409ed05e')\n" +
					"\n" + "and sy.id not in (select pd.update_by  from oa_leave pd  where NOW() BETWEEN pd.start_time and pd.end_time)\n"+
					"\n" +"  ORDER BY RAND() LIMIT 1");

			String i = oaIssueReturnService.qexecuteSelectSql(sql);//随机查询运维人员用户id
			if (i==null){
				addMessage(redirectAttributes, "派单失败，当前没有空闲运维人员");
				return "redirect:"+ Global.getAdminPath()+"/oa/oaIssueReturn/?repage";
			}
            String sql2 =("update oa_issue_return a set  a.conductorid ="+"\'"+i+"\'" +"where a.id ="+"\'"+oaIssueReturn.getId()+"\'");
			OaIssueReturnMapper.execUpdateSql(sql2);//派单为紧急时保存处理人

			OaDispose oaDispose =new OaDispose();
			oaDispose.setId(IdGen.uuid());    //主键
			oaDispose.setDeclareId(oaIssueReturn.getId());     //关联id
			oaDispose.setUser_id(i);//随机指派空闲运维用户id
			oaDispose.setProblemstate("0");//问题状态--初始状态待接单状态
			OaDisposeMapper.insert(oaDispose);


			OaNotify oaNotify = new OaNotify();//派单后发送通知
			oaNotify.setId(IdGen.uuid());//主键
			oaNotify.setTitle("派单");//标题
			oaNotify.setType("4");  //系统派单状态
			oaNotify.setContent(oaIssueReturn.getDescription());//问题描述->内容
			oaNotify.setStatus("1");//状态
			oaNotify.setReadFlag("0"); //查阅状态未读
			Date sentTime = new Date(); //获取当前系统时间
			oaNotify.setUpdateDate(sentTime);//当前时间
			OaNotifyMapper.insert(oaNotify);

			OaNotifyRecord oaNotifyRecord =new OaNotifyRecord();//通知公告
			oaNotifyRecord.setId(IdGen.uuid()); //主键id
			oaNotifyRecord.setOa_notify_id(oaNotify.getId());//通知公告id
			oaNotifyRecord.setUser_id(i);      //通知用户id
			oaNotifyRecord.setReadFlag("0");   //阅读状态
			OaNotifyRecordMapper.insert(oaNotifyRecord);


			String sql3 =("select DISTINCT a.login_name from  sys_user a  LEFT JOIN oa_notify_record b on a.id = b.user_id  where a.id = "+"\'"+i+"\'");

			String r = oaIssueReturnService.qexecuteSelectSql(sql3);
			//发送通知到客户端
			ServletContext context = SpringContextHolder
					.getBean(ServletContext.class);
			new SystemInfoSocketHandler().sendMessageToUser(r, "收到一条新通知，请到我的通知查看！");

			String sql4 = ("update oa_issue_return a set a.problemstate='1' where a.id ="+"\'"+oaIssueReturn.getId()+"\'");
			OaIssueReturnMapper.execUpdateSql(sql4);  //修改问题状态为派单
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/oaIssueReturn/?repage";
	}

	//删除单条
	@ResponseBody
	@RequiresPermissions("oa:oaIssueReturn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaIssueReturn oaIssueReturn, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaIssueReturnService.delete(oaIssueReturn);
		j.setMsg("删除问题申报成功");
		return j;
	}

	//删除多条
	@ResponseBody
	@RequiresPermissions("oa:oaIssueReturn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaIssueReturnService.delete(oaIssueReturnService.get(id));
		}
		j.setMsg("删除问题申报成功");
		return j;
	}

    //批量派单
	@ResponseBody
	@RequiresPermissions("oa:oaIssueReturn:sends")
	@RequestMapping(value = "sends")
	public AjaxJson sends(String ids,OaIssueReturn oaIssueReturn, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
		String sql =("select id from sys_user sy where sy.id not in ( SELECT od.user_id from oa_dispose od where od.problemstate!=3) \n" +
				"  and sy.id  in (select js.user_id  from sys_user_role js where js.role_id='ac4e16ffc0484547ba87c484409ed05e')\n" +
				"\n" + "and sy.id not in (select pd.update_by  from oa_leave pd  where NOW() BETWEEN pd.start_time and pd.end_time)\n"+
				"\n" +"  ORDER BY RAND() LIMIT 1");

		String i = oaIssueReturnService.qexecuteSelectSql(sql);//随机查询运维人员用户id
		if (i==null){
			j.setMsg("派单失败，当前没有空闲运维人员");
			return j;
		}
		String sql2 =("select a.problemstate from oa_issue_return a where a.id="+"\'"+id+"\'");
		String p = oaIssueReturnService.qexecuteSelectSql(sql2);//判断问题有没有被派单即问题状态是不是为0
		if(!p.equals("0")){
			j.setMsg("派单失败，当前问题已派单");
			return j;
		}
		OaDispose oaDispose =new OaDispose();
		oaDispose.setId(IdGen.uuid());    //主键
		oaDispose.setDeclareId(id);     //关联id
		oaDispose.setUser_id(i);//随机指派空闲运维用户id
		oaDispose.setProblemstate("0");//问题状态--初始状态待接单状态
		OaDisposeMapper.insert(oaDispose);

		String indicatorname= oaIssueReturn.getException().getIndicatorName();//指标名称->问题描述
		OaNotify oaNotify = new OaNotify();//派单后发送通知
		oaNotify.setId(IdGen.uuid());//主键
		oaNotify.setTitle("派单");//标题
		oaNotify.setType("4");  //系统派单状态
		if(oaIssueReturn.getDescription()==null){
			oaNotify.setContent(indicatorname);//系统提交时，问题描述异常-指标名称
		}else{
			oaNotify.setContent(oaIssueReturn.getDescription());//问题描述->内容
		}

		oaNotify.setStatus("1");//状态
		oaNotify.setReadFlag("0"); //查阅状态未读
		Date sentTime = new Date(); //获取当前系统时间
		oaNotify.setUpdateDate(sentTime);//当前时间
		OaNotifyMapper.insert(oaNotify);

		OaNotifyRecord oaNotifyRecord =new OaNotifyRecord();//通知公告
		oaNotifyRecord.setId(IdGen.uuid()); //主键id
		oaNotifyRecord.setOa_notify_id(oaNotify.getId());//通知公告id
		oaNotifyRecord.setUser_id(i);      //通知用户id
		oaNotifyRecord.setReadFlag("0");   //阅读状态
		OaNotifyRecordMapper.insert(oaNotifyRecord);


		String sql3 =("select DISTINCT a.login_name from  sys_user a  LEFT JOIN oa_notify_record b on a.id = b.user_id  where a.id = "+"\'"+i+"\'");

		String r = oaIssueReturnService.qexecuteSelectSql(sql3);
			//发送通知到客户端
			ServletContext context = SpringContextHolder
					.getBean(ServletContext.class);
			new SystemInfoSocketHandler().sendMessageToUser(r, "收到一条新通知，请到我的通知查看！");

		String sql4 = ("update oa_issue_return a set a.problemstate='1' where a.id ="+"\'"+id+"\'");
		OaIssueReturnMapper.execUpdateSql(sql4);  //修改问题状态为派单
		}
//        //计时15分钟，如果没有接单，提醒运维人员一次
//		Date notifyTime = new Date();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(notifyTime));
//		notifyTime.setTime(notifyTime.getTime() + 15*60*1000);
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(notifyTime));
		j.setMsg("派单成功");
		return j;
	}

}