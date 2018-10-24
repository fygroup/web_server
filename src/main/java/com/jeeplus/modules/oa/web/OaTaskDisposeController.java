package com.jeeplus.modules.oa.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.entity.OaTask;
import com.jeeplus.modules.oa.entity.OaTaskDispose;
import com.jeeplus.modules.oa.mapper.OaNotifyMapper;
import com.jeeplus.modules.oa.mapper.OaNotifyRecordMapper;
import com.jeeplus.modules.oa.mapper.OaTaskDisposeMapper;
import com.jeeplus.modules.oa.mapper.OaTaskMapper;
import com.jeeplus.modules.oa.service.OaTaskDisposeService;
import com.jeeplus.modules.oa.service.OaTaskService;
import com.jeeplus.modules.sys.entity.User;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 任务处理 controller
 * @Author huanglei
 * @Date 2018/04/11
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/oaTaskDispose")
	public class OaTaskDisposeController extends BaseController {

	@Autowired
	private OaTaskDisposeService oaTaskDisposeService;

	@Autowired
	private OaTaskService oaTaskService;

	@Autowired
	private OaNotifyMapper OaNotifyMapper;

	@Autowired
	private com.jeeplus.modules.oa.mapper.OaNotifyRecordMapper OaNotifyRecordMapper;

	@Autowired
	private OaTaskDisposeMapper oaTaskDisposeMapper;
	@Autowired
	private OaTaskMapper oaTaskMapper;

	@ModelAttribute
	public OaTaskDispose get(@RequestParam(required=false) String id) {
		OaTaskDispose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTaskDisposeService.get(id);
		}
		if (entity == null){
			entity = new OaTaskDispose();
		}
		return entity;
	}

	/**
	 * 任务处理列表页面
	 */
	@RequiresPermissions("oa:oaTaskDispose:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/oaTaskDispose/oaTaskDisposeList";
	}


	/**
	 * 任务处理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaTaskDispose:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaTaskDispose oaTaskDispose, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTaskDispose> page = oaTaskDisposeService.findPage(new Page<OaTaskDispose>(request, response), oaTaskDispose);
		return getBootstrapData(page);
	}
	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaTaskDispose:view","oa:oaTaskDispose:add","oa:oaTaskDispose:edit"},logical= Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaTaskDispose oaTaskDispose,Model model) {
		if (StringUtils.isNotBlank(oaTaskDispose.getId())){
			User user = UserUtils.getUser();
		}
		model.addAttribute("oaTaskDispose", oaTaskDispose);
		return "modules/oa/oaTaskDispose/oaTaskDisposeForm";
	}

	/**
	 * 任务处理操作
	 */
	@RequiresPermissions(value={"oa:oaTaskDispose:dispose"},logical= Logical.OR)
	@RequestMapping(value = "dispose")
	public String dispose(OaTaskDispose oaTaskDispose, Model model, RedirectAttributes redirectAttributes) {
		oaTaskDispose.setIsFlag("1");
		Date taskDisposeTime = new Date(); //获取当前系统时间
		oaTaskDispose.setStartTime(taskDisposeTime);//保存开始时间
		oaTaskDisposeMapper.update(oaTaskDispose);

		String taskId = oaTaskDispose.getTaskId();
		int num=oaTaskDisposeService.amountdispose(taskId);
		if(num==0){
//			OaTask oaTask = new OaTask();
//			oaTask.setIsFlag("1");
            String i = oaTaskService.get(oaTaskDispose.getTaskId()).getCreateUser();//创建人id

			OaNotify oaNotify = new OaNotify();//任务开始执行时后给任务创建人通知
			oaNotify.setId(IdGen.uuid());//主键
			oaNotify.setTitle("任务处理");//标题
			oaNotify.setType("6");  //系统派单状态
			oaNotify.setContent(oaTaskDispose.getDescription());//任务描述->内容
			oaNotify.setStatus("1");//状态
			oaNotify.setReadFlag("0"); //查阅状态未读
			Date sentTime = new Date(); //获取当前系统时间
			oaNotify.setUpdateDate(sentTime);//当前时间
			OaNotifyMapper.insert(oaNotify);

			OaNotifyRecord oaNotifyRecord =new OaNotifyRecord();//通知公告
			oaNotifyRecord.setId(IdGen.uuid()); //主键id
			oaNotifyRecord.setOa_notify_id(oaNotify.getId());//通知公告id
			oaNotifyRecord.setUser_id(i);      //通知创建人id
			oaNotifyRecord.setReadFlag("0");   //阅读状态
			OaNotifyRecordMapper.insert(oaNotifyRecord);


			String sql =("select DISTINCT a.login_name from  sys_user a  LEFT JOIN oa_notify_record b on a.id = b.user_id  where a.id = "+"\'"+i+"\'");

			String r = oaTaskDisposeService.qexecuteSelectSql(sql);
			//发送通知到客户端
			ServletContext context = SpringContextHolder
					.getBean(ServletContext.class);
			new SystemInfoSocketHandler().sendMessageToUser(r, "收到一条新通知，请到我的通知查看！");

			oaTaskService.setIsFlag(taskId);//如果任务处理列表所有的分配人员都处于处理任务状态-对应的此任务安排的任务状态变为处理

		}

		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/oaTaskDispose/?repage";
	}


	/**
	 * 任务完成操作
	 */
	@RequiresPermissions(value={"oa:oaTaskDispose:finish"},logical= Logical.OR)
	@RequestMapping(value = "finish")
	public String finish(OaTaskDispose oaTaskDispose, Model model, RedirectAttributes redirectAttributes) {
		oaTaskDispose.setIsFlag("2");
		Date taskDisposeTime = new Date(); //获取当前系统时间
		oaTaskDispose.setEndTime(taskDisposeTime);//保存开始时间
		oaTaskDisposeMapper.update(oaTaskDispose);

		String taskId = oaTaskDispose.getTaskId();
		int num=oaTaskDisposeService.amountfinish(taskId);
		if(num==0){
//			OaTask oaTask = new OaTask();
//			oaTask.setIsFlag("1");
			String i = oaTaskService.get(oaTaskDispose.getTaskId()).getCreateUser();//创建人id

			OaNotify oaNotify = new OaNotify();//任务开始执行时后给任务创建人通知
			oaNotify.setId(IdGen.uuid());//主键
			oaNotify.setTitle("任务完成");//标题
			oaNotify.setType("7");  //系统派单状态
			oaNotify.setContent(oaTaskDispose.getDescription());//任务描述->内容
			oaNotify.setStatus("1");//状态
			oaNotify.setReadFlag("0"); //查阅状态未读
			Date sentTime = new Date(); //获取当前系统时间
			oaNotify.setUpdateDate(sentTime);//当前时间
			OaNotifyMapper.insert(oaNotify);

			OaNotifyRecord oaNotifyRecord =new OaNotifyRecord();//通知公告
			oaNotifyRecord.setId(IdGen.uuid()); //主键id
			oaNotifyRecord.setOa_notify_id(oaNotify.getId());//通知公告id
			oaNotifyRecord.setUser_id(i);      //通知创建人id
			oaNotifyRecord.setReadFlag("0");   //阅读状态
			OaNotifyRecordMapper.insert(oaNotifyRecord);


			String sql2 =("select DISTINCT a.login_name from  sys_user a  LEFT JOIN oa_notify_record b on a.id = b.user_id  where a.id = "+"\'"+i+"\'");

			String r = oaTaskDisposeService.qexecuteSelectSql(sql2);
			//发送通知到客户端
			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			new SystemInfoSocketHandler().sendMessageToUser(r, "收到一条新通知，请到我的通知查看！");
			oaTaskService.setIsFlagFinish(taskId);//如果任务处理列表所有的分配人员都处于完成任务状态-对应的此任务安排的任务状态变为完成
		}

		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/oaTaskDispose/?repage";
	}

}