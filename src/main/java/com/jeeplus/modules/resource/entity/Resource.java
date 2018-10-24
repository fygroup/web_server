
package com.jeeplus.modules.resource.entity;

import com.google.common.collect.Lists;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.indextemplate.entity.IndexTemplate;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.manufacturer.entity.Manufacturer;
import com.jeeplus.modules.resourcebaseinfo.entity.ResourceBaseInfo;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.test.entity.manytoone.Category;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 资源Entity
 * @author le
 * @version 2017-10-27
 */
public class Resource extends DataEntity<Resource> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 资源名称
	private String status= "1";		// 状态
	private ResourceType resourceType;		// 资源类型
	private String ip;		// IP
	private String databaseIp;		// 数据库IP
	private String middlewareIp;		// 中间件IP
	private String mac;		// mac地址
	private String subnetmask;		// 子网掩码
	private Office company;		// 归属公司
	private String description;
	private String sysOid;     //系统oid
	private ResourceBaseInfo resourceBaseInfo;
	private String sysName;
	private Manufacturer manufacturer; //厂商
	private String model;              // 型号
	private String operatingSystemId;  //操作系统id
	private String parentId;           //父类id
	private String resourceTypeCodeIds;
	private String selectResourceTypeCodeIds;
	private LinkIndicator linkIndicator;
	private String img;
	private IndexTemplate indexTemplate;  //指标模板
    private String url;//应用url
	private String applicationLevel;//应用级别
	private MokaCollector mokaCollector;


	public MokaCollector getMokaCollector() {
		return mokaCollector;
	}

	public void setMokaCollector(MokaCollector mokaCollector) {
		this.mokaCollector = mokaCollector;
	}

	public String getApplicationLevel() {
		return applicationLevel;
	}

	public void setApplicationLevel(String applicationLevel) {
		this.applicationLevel = applicationLevel;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<ApplicationIndicator> applicationIndicatorList= Lists.newArrayList();		// 子表列表
	public Resource() {
		super();
	}

	public List<ApplicationIndicator> getApplicationIndicatorList() {
		return applicationIndicatorList;
	}

	public void setApplicationIndicatorList(List<ApplicationIndicator> applicationIndicatorList) {
		this.applicationIndicatorList = applicationIndicatorList;
	}

	public IndexTemplate getIndexTemplate() {
		return indexTemplate;
	}

	public void setIndexTemplate(IndexTemplate indexTemplate) {
		this.indexTemplate = indexTemplate;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDatabaseIp() {
		return databaseIp;
	}

	public void setDatabaseIp(String databaseIp) {
		this.databaseIp = databaseIp;
	}

	public String getMiddlewareIp() {
		return middlewareIp;
	}

	public void setMiddlewareIp(String middlewareIp) {
		this.middlewareIp = middlewareIp;
	}

	public LinkIndicator getLinkIndicator() {
		return linkIndicator;
	}

	public void setLinkIndicator(LinkIndicator linkIndicator) {
		this.linkIndicator = linkIndicator;
	}

	public String getResourceTypeCodeIds() {
		return resourceTypeCodeIds;
	}

	public void setResourceTypeCodeIds(String resourceTypeCodeIds) {
		this.resourceTypeCodeIds = resourceTypeCodeIds;
	}

	public String getSelectResourceTypeCodeIds() {
		return selectResourceTypeCodeIds;
	}

	public void setSelectResourceTypeCodeIds(String selectResourceTypeCodeIds) {
		this.selectResourceTypeCodeIds = selectResourceTypeCodeIds;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOperatingSystemId() {
		return operatingSystemId;
	}

	public void setOperatingSystemId(String operatingSystemId) {
		this.operatingSystemId = operatingSystemId;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public Resource(String id){
		super(id);
	}

	public ResourceBaseInfo getResourceBaseInfo() {
		return resourceBaseInfo;
	}

	public void setResourceBaseInfo(ResourceBaseInfo resourceBaseInfo) {
		this.resourceBaseInfo = resourceBaseInfo;
	}

	@ExcelField(title="资源名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="状态", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="资源类型", fieldType=Category.class, value="resourceType.name", align=2, sort=9)
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	@ExcelField(title="IP", align=2, sort=10)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	

	
	@ExcelField(title="mac地址", align=2, sort=12)
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	

	@ExcelField(title="子网掩码", align=2, sort=14)
	public String getSubnetmask() {
		return subnetmask;
	}

	public void setSubnetmask(String subnetmask) {
		this.subnetmask = subnetmask;
	}

	
	@ExcelField(title="归属公司", fieldType=String.class, value="", align=2, sort=17)
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSysOid() {
		return sysOid;
	}

	public void setSysOid(String sysOid) {
		this.sysOid = sysOid;
	}
}