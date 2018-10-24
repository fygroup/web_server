package com.jeeplus.modules.update.database;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.collect.database.mysql.ConnectionsCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.ResponseTimeCalculateMysql;
import com.jeeplus.modules.collect.database.mysql.UpTimeCalculateMysql;
import com.jeeplus.modules.collect.database.oracle.ConnectionsCalculateOracle;
import com.jeeplus.modules.collect.database.oracle.ResponseTimeCalculateOracle;
import com.jeeplus.modules.collect.database.oracle.UpTimeCalculateOracle;
import com.jeeplus.modules.collect.database.sqlserver.ConnectionsCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.DataSizeCalculateSqlserver;
import com.jeeplus.modules.collect.database.sqlserver.ResponseTimeCalculateSqlserver;
import com.jeeplus.modules.collect.database.sybase.ResponseTimeCalculateSybase;
import com.jeeplus.modules.collect.database.sybase.UsersCalculateSybase;
import com.jeeplus.modules.exception.entity.exception.ResourceException;
import com.jeeplus.modules.exception.service.exception.ResourceExceptionService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResponseTime;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import com.jeeplus.modules.update.UpdateService;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static com.jeeplus.modules.cpu.web.CpuController.calculationThreshold;

public class DatabaseIndicator implements UpdateService {

    private ResourceService resourceService = (ResourceService)SpringBeanUtils.getBeanByClass(ResourceService.class);

    private ResourceIndicatorlistService resourceIndicatorlistService= (ResourceIndicatorlistService)SpringBeanUtils.getBeanByClass(ResourceIndicatorlistService.class);

    private ResourceExceptionService resourceExceptionService= (ResourceExceptionService)SpringBeanUtils.getBeanByClass(ResourceExceptionService.class);

    private String uuid="c176ad1dee4a45be83acef60cf6f46cc";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Date date=new Date();
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
            List<ResourceIndicatorlist> updateList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"4");
            if(CheckObject.checkList(updateList)){
                for(ResourceIndicatorlist resourceIndicatorlist : updateList){
                    String value="";
                    if("41".equals(resource.getResourceType().getCode())){ //mySql
                        if("41001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连接数
                            value= ConnectionsCalculateMysql.getMysqlConnections(resource);
                        }else if("41002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连续工作时长
                            value= UpTimeCalculateMysql.getMysqlUptime(resource);
                        }else if("41003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //mySql数据库连接响应时间
                            value= ResponseTimeCalculateMysql.getMysqlResponseTime(resource);
                            ResponseTime responseTime = new ResponseTime();
                            responseTime.setCreateDate(date);
                            responseTime.setResourceId(resource.getId());
                            responseTime.setTime(value);
                            resourceService.saveMiddlewareResponseTime(responseTime);
                        }
                    }else if("42".equals(resource.getResourceType().getCode())){   //sqlserver
                        if("42001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据库连接数
                            value= ConnectionsCalculateSqlserver.getSqlserverConnections(resource);
                        }else if("42002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据文件大小
                            value= DataSizeCalculateSqlserver.getSqlserverDataSize(resource);
                        }else if("42003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sqlServer数据库连接响应时间
                            value= ResponseTimeCalculateSqlserver.getSqlserverResponseTime(resource);
                            ResponseTime responseTime = new ResponseTime();
                            responseTime.setCreateDate(date);
                            responseTime.setResourceId(resource.getId());
                            responseTime.setTime(value);
                            resourceService.saveMiddlewareResponseTime(responseTime);
                        }
                    }else if("43".equals(resource.getResourceType().getCode())){   //oracle
                        if("43001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //oracle数据库连接数
                            value= ConnectionsCalculateOracle.getOracleConnections(resource);
                        }else if("43002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //oracle数据库连续工作时长
                            value= UpTimeCalculateOracle.getOracleUptime(resource);
                        }else if("43003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //oracle数据库连接响应时间
                            value= ResponseTimeCalculateOracle.getOracleResponseTime(resource);
                            ResponseTime responseTime = new ResponseTime();
                            responseTime.setCreateDate(date);
                            responseTime.setResourceId(resource.getId());
                            responseTime.setTime(value);
                            resourceService.saveMiddlewareResponseTime(responseTime);
                        }
                    }else if("44".equals(resource.getResourceType().getCode())){   //sybase
                        if("44001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sybase数据库连接数
                            value= UsersCalculateSybase.getUsers(resource);
                        }else if("44002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //sybase数据库连接响应时间
                            value= ResponseTimeCalculateSybase.getSybaseResponseTime(resource);
                            ResponseTime responseTime = new ResponseTime();
                            responseTime.setCreateDate(date);
                            responseTime.setResourceId(resource.getId());
                            responseTime.setTime(value);
                            resourceService.saveMiddlewareResponseTime(responseTime);
                        }
                    }

                    if(StringUtils.isNotBlank(value)) {
                        ResourceException resourceException = null;
                        try {
                            resourceException = calculationThreshold(resource, resourceIndicatorlist, value,resourceExceptionService.getByResourceIndicatorId(resourceIndicatorlist.getId()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (resourceException != null) {
                            resourceExceptionService.save(resourceException);
                        }
                        resourceIndicatorlistService.setValue(value,resourceIndicatorlist.getId());
                    }
                }
            }
        }
    }
}
