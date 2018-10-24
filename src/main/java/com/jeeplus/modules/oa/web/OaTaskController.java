package com.jeeplus.modules.oa.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.time.ClockUtil;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.*;
import com.jeeplus.modules.oa.mapper.*;
import com.jeeplus.modules.oa.service.OaTaskDisposeService;
import com.jeeplus.modules.oa.service.OaTaskService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 任务安排 controller
 * @Author huanglei
 * @Date 2018/04/10
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/oaTask")
	public class OaTaskController extends BaseController {

	@Autowired
	private OaTaskService oaTaskService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OaTaskMapper oaTaskMapper;
	@Autowired
	private OaTaskDisposeService oaTaskDisposeService;
	@Autowired
	private OaTaskDisposeMapper oaTaskDisposeMapper;

	@Autowired
	private OaNotifyMapper OaNotifyMapper;

	@Autowired
	private OaNotifyRecordMapper OaNotifyRecordMapper;


	@ModelAttribute
	public OaTask get(@RequestParam(required=false) String id) {
		OaTask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaTaskService.get(id);
		}
		if (entity == null){
			entity = new OaTask();
		}
		return entity;
	}

	/**
	 *  任务安排列表页面
	 */
	@RequiresPermissions("oa:oaTask:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/oa/oaTask/oaTaskList";
	}


	/**
	 * 任务安排列表数据
	 */
	@ResponseBody
	@RequiresPermissions("oa:oaTask:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OaTask oaTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaTask> page = oaTaskService.findPage(new Page<OaTask>(request, response), oaTask);
		return getBootstrapData(page);
	}
	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"oa:oaTask:view","oa:oaTask:add","oa:oaTask:edit"},logical= Logical.OR)
	@RequestMapping(value = "form")
	public String form(OaTask oaTask,Model model) {
		if (StringUtils.isNotBlank(oaTask.getId())){
			User user = UserUtils.getUser();
		}
		model.addAttribute("oaTask", oaTask);
		return "modules/oa/oaTask/oaTaskForm";
	}


	@RequiresPermissions("oa:oaTask:edit")
	@RequestMapping(value = "save")
	public String save(OaTask oaTask,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaTask)){
			return form(oaTask, model);
		}
		String id = oaTask.getId();
		String ids = oaTask.getUser().getId();//获取选择的单个人员id或者多个人员的id

		//新增操作

		if(id==null||id.equals("")) {
			String result[] = ids.split(",");
			String username;
			String resultname = "";
			if (result.length == 1) {
				username = systemService.getUser(result[0]).getName();
				resultname = username;
			} else if (result.length > 1) {
				for (int i = 0; i < result.length; i++) {
					username = systemService.getUser(result[i]).getName();
					resultname += username+",";

				}
				StringBuffer sb = new StringBuffer(resultname);
				sb.deleteCharAt(resultname.length()-1);
				resultname =sb.toString();
			}
			oaTask.setIsFlag("0");//默认待处理
			oaTask.setDelFlag("0");//默认未删除
			Date taskorderTime = new Date(); //获取当前系统时间
			oaTask.setCreateUser(UserUtils.getUser().getId());//创建人
			oaTask.setOrderTime(taskorderTime);//创建时间
			oaTask.setUserId(resultname);//参与人员
			oaTaskService.save(oaTask);

		OaTaskDispose oaTaskDispose = new OaTaskDispose();//引入任务处理实体对象
		String taskId = oaTask.getId();//获取任务id
		int j;
		for(j=0; j<result.length; j++){
			String joinId = result[j];
			oaTaskDispose.setId(IdGen.uuid());
			oaTaskDispose.setTaskId(taskId);
			oaTaskDispose.setJoinId(joinId);
			oaTaskDispose.setIsFlag("0");
			oaTaskDispose.setDelFlag("0");
			Date orderTime = new Date(); //获取当前系统时间
			oaTaskDispose.setOrderTime(orderTime);
			oaTaskDisposeMapper.insert(oaTaskDispose);

			//分配人员后发送通知
			OaNotify oaNotify = new OaNotify();//派单后发送通知
			oaNotify.setId(IdGen.uuid());//主键
			oaNotify.setTitle("任务分配");//标题
			oaNotify.setType("5");  //任务分配状态
			oaNotify.setContent(oaTask.getDescription());//问题描述->内容
			oaNotify.setStatus("1");//状态
			oaNotify.setReadFlag("0"); //查阅状态未读
			Date sentTime = new Date(); //获取当前系统时间
			oaNotify.setUpdateDate(sentTime);//当前时间
			OaNotifyMapper.insert(oaNotify);

			OaNotifyRecord oaNotifyRecord =new OaNotifyRecord();//通知公告
			oaNotifyRecord.setId(IdGen.uuid()); //主键id
			oaNotifyRecord.setOa_notify_id(oaNotify.getId());//通知公告id
			oaNotifyRecord.setUser_id(result[j]);      //通知用户id
			oaNotifyRecord.setReadFlag("0");   //阅读状态
			OaNotifyRecordMapper.insert(oaNotifyRecord);


			//发送通知到客户端
			String r = systemService.getUser(result[j]).getLoginName();
			ServletContext context = SpringContextHolder.getBean(ServletContext.class);
			new SystemInfoSocketHandler().sendMessageToUser(r, "收到一条新通知，请到我的通知查看！");



		}


		//修改操作

		}else{//不修改参与人员
			String resultname = oaTask.getUserId();
			if(resultname.equals(ids)){
				oaTaskService.save(oaTask);
			}else{
				//删除之前分配的数据
				String sql =("update oa_task_dispose a set a.del_flag=1 where a.task_id = "+"\'"+id+"\'");
				oaTaskDisposeMapper.execUpdateSql(sql);
				//修改数据
				String resultnames = "";
				String username;
				String result[] = ids.split(",");
				if (result.length == 1) {
					username = systemService.getUser(result[0]).getName();
					resultnames = username;
				} else if (result.length > 1) {
					for (int s = 0; s < result.length; s++) {
						username = systemService.getUser(result[s]).getName();
						resultnames += username+",";

					}
					StringBuffer sb = new StringBuffer(resultnames);
					sb.deleteCharAt(resultnames.length()-1);
					resultnames =sb.toString();
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

				oaTaskMapper.execUpdateSql("update oa_task a set a.user_id=" +"\'"+resultnames+"\'"+ ", a.task_name = "+"\'"+oaTask.getTaskName()+"\'"+
						", a.start_time = "+"\'"+df.format(oaTask.getStartTime())+"\'"+", a.end_time = "+"\'"+df.format(oaTask.getEndTime())+"\'"+", a.description = "+"\'"+oaTask.getDescription()+"\'"+
						", a.is_flag = "+"\'"+oaTask.getIsFlag()+"\'"+", a.remark = "+"\'"+oaTask.getRemark()+"\'"+
						", a.del_flag = "+"\'"+oaTask.getDelFlag()+"\'"+" where a.id="+"\'"+id+"\'");

				OaTaskDispose oaTaskDispose = new OaTaskDispose();//引入任务处理实体对象
				for(int z=0; z<result.length; z++){
					oaTaskDispose.setId(IdGen.uuid());
					oaTaskDispose.setTaskId(oaTask.getId());
					oaTaskDispose.setJoinId(result[z]);
					oaTaskDispose.setIsFlag("0");
					oaTaskDispose.setDelFlag("0");
					Date orderTime = new Date(); //获取当前系统时间
					oaTaskDispose.setOrderTime(orderTime);
					oaTaskDisposeMapper.insert(oaTaskDispose);
				}
			}

		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+ Global.getAdminPath()+"/oa/oaTask/?repage";
	}



	//删除单条
	@ResponseBody
	@RequiresPermissions("oa:oaTaskDispose:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OaTaskDispose oaTaskDispose, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oaTaskDisposeService.delete(oaTaskDispose);
		j.setMsg("删除成功");
		return j;
	}

	//删除多条
	@ResponseBody
	@RequiresPermissions("oa:oaTaskDispose:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oaTaskDisposeService.delete(oaTaskDisposeService.get(id));
		}
		j.setMsg("删除进出记录成功");
		return j;
	}



}