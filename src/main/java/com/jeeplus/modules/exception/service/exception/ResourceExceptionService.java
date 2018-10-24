package com.jeeplus.modules.exception.service.exception;

import java.util.Date;
import java.util.List;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.oa.entity.OaDispose;
import com.jeeplus.modules.oa.entity.OaIssueReturn;
import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.oa.entity.OaNotifyRecord;
import com.jeeplus.modules.oa.mapper.OaDisposeMapper;
import com.jeeplus.modules.oa.mapper.OaNotifyRecordMapper;
import com.jeeplus.modules.oa.service.OaIssueReturnService;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourcephysicinfo.entity.ResourcePhysicInfo;
import com.jeeplus.modules.resourcephysicinfo.service.ResourcePhysicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.mapper.exception.ResourceExceptionMapper;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;

/**
 * 异常告警信息Service
 * @author clouology
 * @version 2017-11-16
 */
@Service
@Transactional(readOnly = true)
public class ResourceExceptionService extends CrudService<ResourceExceptionMapper, ResourceException> {

	@Autowired
	private ResourceExceptionMapper resourceExceptionMapper;

	@Autowired
	private OaDisposeMapper OaDisposeMapper;

	@Autowired
	private com.jeeplus.modules.oa.mapper.OaIssueReturnMapper OaIssueReturnMapper;

	@Autowired
	private com.jeeplus.modules.oa.mapper.OaNotifyMapper OaNotifyMapper;

	@Autowired
	private OaIssueReturnService oaIssueReturnService;

	@Autowired
	private OaNotifyRecordMapper OaNotifyRecordMapper;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourcePhysicInfoService resourcePhysicInfoService;


	public ResourceException get(String id) {
		return super.get(id);
	}
	
	public List<ResourceException> findList(ResourceException resourceException) {
		return super.findList(resourceException);
	}
	
	public Page<ResourceException> findPage(Page<ResourceException> page, ResourceException resourceException) {
		return super.findPage(page, resourceException);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceException resourceException, Model model, RedirectAttributes redirectAttributes) {
		super.save(resourceException);
		if ("1".equals(resourceException.getTotalQuantity())) {


			if(resourceException.getExceptionClass().equals("3")){//问题状态为紧急状态直接派单

				String sql =("select id from sys_user sy where sy.id not in ( SELECT od.user_id from oa_dispose od where od.problemstate!=3) \n" +
						"  and sy.id  in (select js.user_id  from sys_user_role js where js.role_id='ac4e16ffc0484547ba87c484409ed05e')\n" +
						"\n" + "and sy.id not in (select pd.update_by  from oa_leave pd  where NOW() BETWEEN pd.start_time and pd.end_time)\n"+
						"\n" +"  ORDER BY RAND() LIMIT 1");

				String i = oaIssueReturnService.qexecuteSelectSql(sql);//随机查询运维人员用户id
				if (i==null){
					return;
				}

				OaIssueReturn oaIssueReturn=new OaIssueReturn();
				oaIssueReturn.setException(resourceException);
				oaIssueReturn.setDate(new Date());
				oaIssueReturn.setDeclaretype("1");
				oaIssueReturn.setEdiid("3");
				oaIssueReturn.setProblemstate("0");
				ResourcePhysicInfo resourcePhysicInfo=resourcePhysicInfoService.getByResourceId(resourceException.getResourceIndicator().getResourceId());
				if(resourcePhysicInfo!=null){
					oaIssueReturn.setPlace(resourcePhysicInfo.getDatailedAddress());
				}
				oaIssueReturn.setApplicant("系统告警");
				oaIssueReturn.setConductorid(i);//处理人id
				oaIssueReturnService.save(oaIssueReturn);



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

		}
	}

	@Transactional(readOnly = false)
	public void delete(ResourceException resourceException) {
		super.delete(resourceException);
	}

	@Transactional(readOnly = false)
	public int delById(String id) {
		return resourceExceptionMapper.delete(id);
	}


	public ResourceException getByResourceIndicatorId( String resourceIndicatorId){
		return resourceExceptionMapper.getByResourceIndicatorId(resourceIndicatorId);
	}

	/**
	 * 手动恢复
	 * @param id
	 * @param updateDate
	 * @param currentStatus
	 * @return
	 */
	@Transactional(readOnly = false)
	public int manualRecovery(String id, Date updateDate, String currentStatus,String confirmStatus,String confirmUserId){
		return resourceExceptionMapper.manualRecovery(id,updateDate,currentStatus, confirmStatus,confirmUserId);
	}

	/**
	 * 我的面板异常手动恢复
	 * @param id
	 * @param updateDate
	 * @param currentStatus
	 * @param confirmStatus
	 * @param confirmUserId
	 * @return
	 */
	@Transactional(readOnly = false)
	public int indexManualRecovery(String id, Date updateDate, String currentStatus,String confirmStatus,String confirmUserId){
		return resourceExceptionMapper.manualRecovery(id,updateDate,currentStatus, confirmStatus,confirmUserId);
	}

	/**
	 * 异常确认
	  * @param id
	 * @param updateDate
	 * @param confirmStatus
	 * @param confirmUserId
	 * @return
	 */
	@Transactional(readOnly = false)
	public int confirmException (String id,Date updateDate, String confirmStatus, String confirmUserId){
		return resourceExceptionMapper.confirmException(id,updateDate,confirmStatus,confirmUserId);
	}

	/**
	 * 根据资源查询异常
	 * @param resourceId
	 * @return
	 */
	public List<ResourceException> findListByResource(String resourceId,String exceptionClass){
		return resourceExceptionMapper.findListByResource(resourceId,exceptionClass);
	}



	/**
	 * 根据资源和时间段查询异常
	 * @param resourceId
	 * @return
	 */
	public List<ResourceException> findListByResourceDate(String resourceId,String exceptionClass, Date beginDate, Date endDate) {
		return resourceExceptionMapper.findListByResourceDate(resourceId,exceptionClass,beginDate,  endDate);
	}


	/**
	 *
	 *	获取所有未处理的异常
	 * @return
	 */
	public List<ResourceException> indexFindList(){
		return resourceExceptionMapper.indexFindList();
	}

}