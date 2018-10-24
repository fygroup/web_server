
package com.jeeplus.modules.datainterface;

import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.service.CpuService;
import com.jeeplus.modules.disk.service.DiskService;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.indextemplate.service.IndexTemplateService;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.manufacturer.service.ManufacturerService;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.service.MemoryService;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.oa.entity.OaIssueReturn;
import com.jeeplus.modules.oa.service.OaIssueReturnService;
import com.jeeplus.modules.operatingsystem.service.OperatingSystemService;
import com.jeeplus.modules.process.service.ProcessService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceconfiginfo.service.ResourceConfigInfoService;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.resourceinformation.service.ResourceInformationService;
import com.jeeplus.modules.resourcephysicinfo.service.ResourcePhysicInfoService;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;
import com.jeeplus.modules.software.service.SoftwareService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.service.AreaService;
import com.jeeplus.modules.sys.service.DictTypeService;
import com.jeeplus.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运维工单数据接口-Controller
 * @author lei
 * @version 2017-09-29
 */
@org.springframework.stereotype.Controller
@RequestMapping(value = "${adminPath}/workInterface")
public class WorkOrderController extends BaseController {

	@Autowired
	private OaIssueReturnService oaIssueReturnService;
	@Autowired
	private OfficeService officeService;

	@ResponseBody
	@RequestMapping(value = "workOrderDataTest")
	public void workOrderData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<OaIssueReturn> oaIssueReturnsList =oaIssueReturnService.findList(new OaIssueReturn());
		int count = oaIssueReturnsList.size();//工单数量
		for(OaIssueReturn oaIssueReturn:oaIssueReturnsList){
			    String data="{\"count\":\""+count+"\",\"data\":[";
				String dataValue="";
				String title = "";//工单标题
				if(oaIssueReturn.getDescription()==null){
					title = oaIssueReturn.getException().getIndicatorName();
				}else{
					title= oaIssueReturn.getDescription();
				}
				String type = "";//工单流程状态
				if(oaIssueReturn.getProblemstate().equals("0")){
					type="创建";
				}else if(oaIssueReturn.getProblemstate().equals("1")){
					type="派单";
				}else if(oaIssueReturn.getProblemstate().equals("2")){
					type="处理中";
				}else{
					type="完成";
				}
				String appLYer = "";//工单提出人
				if(oaIssueReturn.getName()==null){
					appLYer= oaIssueReturn.getApplicant();
				}else{
					appLYer = oaIssueReturn.getName();
				}
				String  priority = "";//工单优先级
				if(oaIssueReturn.getException().getExceptionClass() ==null){
					if (oaIssueReturn.getProblemClass().equals("3")) {
						priority ="紧急";
					} else if (oaIssueReturn.getProblemClass().equals("2")) {
						priority ="较急";
					} else if (oaIssueReturn.getProblemClass().equals("1")) {
						priority ="一般";
					} else if (oaIssueReturn.getProblemClass().equals("0")) {
						priority ="提示";
					}
				}else{
					if (oaIssueReturn.getException().getExceptionClass().equals("3")) {
						priority ="紧急";
					} else if (oaIssueReturn.getException().getExceptionClass().equals("2")) {
						priority ="较急";
					} else if (oaIssueReturn.getException().getExceptionClass().equals("1")) {
						priority ="一般";
					} else if (oaIssueReturn.getException().getExceptionClass().equals("0")) {
						priority ="提示";
					}
				}
				String state = "";//工单状态 0已结束, 1未结束
				if(oaIssueReturn.getProblemstate()=="0" || oaIssueReturn.getProblemstate()=="1" || oaIssueReturn.getProblemstate()=="2" ){
					state="1";//未结束
				}else{
					state="0";//已结束
				}
				Date createTime =null;
				if(oaIssueReturn.getDate()==null){
					createTime =null;
				}else{
					createTime = oaIssueReturn.getDate();
				}
			    Date finishTimeT =null;
			    String finishTime="";
				if(oaIssueReturn.getFinishTime()==null){
					finishTimeT =null;
					finishTime="null";
				}else{
					finishTimeT = oaIssueReturn.getFinishTime();
					finishTime =sdf.format(finishTimeT);
				}
				String  sysCode = "";//工单对应业务编码
			    if(oaIssueReturn.getDeclaretype().equals("0")){
					sysCode = "软件";
				}else if(oaIssueReturn.getDeclaretype().equals("1")){
					sysCode = "硬件";
				}else if(oaIssueReturn.getDeclaretype().equals("2")) {
					sysCode = "网络";
				}else{
					sysCode = "null";
				}
				String  department ="";//发起人部门编码
			    if(oaIssueReturn.getMailList()==null){
					department="信息中心";
			    }else{
					Office  office= officeService.get(oaIssueReturn.getMailList().getId());
					department=office.getName();
			    }
			    String  completionTime ="";//工单耗时
			    DecimalFormat    df   = new DecimalFormat("######0.0");
			    if(finishTimeT!=null){
					completionTime = df.format((finishTimeT.getTime())-(oaIssueReturn.getDate().getTime())/(60*60*1000)).toString();
				}else{
			    	completionTime="null";
				}
				dataValue+="{\"id\":\""+oaIssueReturn.getId()+"\",\"title\":\""+title+"\",\"type\":\""+type+"\",\"applyer\":\""+appLYer+"\",\"priority\":\""+priority+"\"," +
						"\"state\":"+state+"\",\"createtime\":\""+sdf.format(createTime)+"\",\"finishtime\":\""+finishTime+ "\"," +
						"\"syscode\":"+sysCode+"\",\"department\":"+department+"\",\"completiontime\":"+completionTime+"\"},";

				if(dataValue.length()>0){
					dataValue=dataValue.substring(0,dataValue.length()-1);
				}
				data+=dataValue;
				data+="]}";
				System.out.println(data);
				/*params.put("data", data);
				String result = HttpPostTest.post("http://localhost:8080/xxxx", params);*/
			}


	}




}