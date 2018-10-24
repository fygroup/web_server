package com.jeeplus.modules.oa.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 巡检记录Entity
 * @author rockdeaf
 * @version 2017-11-06
 */
public class OaInspection extends DataEntity<OaInspection> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 检查人员
	private String name;		//对应用户名
	private Date date;		// 巡检日期
	private String isflag;		// 是否有问题
	private String pagevalue;       //巡检模板value值
	private String pagename;    //模板名称
	private String contents;		// 内容
	private String remark;		// 备注
	private Date dateStart;
	private Date dateEnd;

	public OaInspection() {
		super();
	}

	public OaInspection(String id){
		super(id);
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}



	@Length(min=0, max=1, message="是否有问题长度必须介于 0 和 1 之间")
	public String getIsflag() {
		return isflag;
	}

	public void setIsflag(String isflag) {
		this.isflag = isflag;
	}


	@ExcelField(title="内容", align=2, sort=8)
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Length(min=0, max=200, message="remark长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}


	public String getPagevalue() {
		return pagevalue;
	}

	public void setPagevalue(String pagevalue) {
		this.pagevalue = pagevalue;
	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
}