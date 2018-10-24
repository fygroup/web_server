package com.jeeplus.modules.update.middleware;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.collect.middleware.nginx.*;
import com.jeeplus.modules.collect.middleware.tomcat.GeneralTomcatIndicator;
import com.jeeplus.modules.collect.middleware.tomcat.entity.TomcatInfo;
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

public class MiddlewareIndicator implements UpdateService {

    private ResourceService resourceService = (ResourceService)SpringBeanUtils.getBeanByClass(ResourceService.class);

    private ResourceIndicatorlistService resourceIndicatorlistService= (ResourceIndicatorlistService)SpringBeanUtils.getBeanByClass(ResourceIndicatorlistService.class);

    private ResourceExceptionService resourceExceptionService= (ResourceExceptionService)SpringBeanUtils.getBeanByClass(ResourceExceptionService.class);

    private String uuid="c2e4d9082a0b4386884a0b203afe2c5c";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Date date=new Date();
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
            List<ResourceIndicatorlist> updateList =  resourceIndicatorlistService.getUpdateListByCode(resource.getId(),resource.getResourceType().getId(),"5");
            if(CheckObject.checkList(updateList)){
                for(ResourceIndicatorlist resourceIndicatorlist : updateList){
                    String value="";
                    if("51".equals(resource.getResourceType().getCode())){   //tomcat
                        GeneralTomcatIndicator generalTomcatIndicator=new GeneralTomcatIndicator(resource);
                        TomcatInfo tomcatInfo=generalTomcatIndicator.getTomcatInfo();
                        if(tomcatInfo!=null) {
                            double mem=0.00;
                            if("51001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存总量
                                if(tomcatInfo.getTotalMemory()>0){
                                    mem=tomcatInfo.getTotalMemory()/1024/1024.00;
                                    value = df.format(mem);
                                }
                            }else if("51002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存剩余量
                                if(tomcatInfo.getFreeMemory()>0){
                                    mem=tomcatInfo.getFreeMemory()/1024/1024.00;
                                    value = df.format(mem);
                                }
                            }else if("51003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //tomcat内存已用量
                                if(tomcatInfo.getTotalMemory()>0&&tomcatInfo.getFreeMemory()>0){
                                    mem=(tomcatInfo.getTotalMemory()-tomcatInfo.getFreeMemory())/1024/1024.00;
                                    value = df.format(mem);
                                }
                            }else if("51004".equals(resourceIndicatorlist.getIndicator().getItemType())){  //Tomcat连接响应时间
                                value = String.valueOf(tomcatInfo.getResponseTime());
                                ResponseTime responseTime = new ResponseTime();
                                responseTime.setCreateDate(date);
                                responseTime.setResourceId(resource.getId());
                                responseTime.setTime(value);
                                resourceService.saveMiddlewareResponseTime(responseTime);
                            }
                        }
                    }else if("52".equals(resource.getResourceType().getCode())) {   //nginx
                        if("52001".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx总共处理的连接数
                            value = NginxCountDisposeByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52002".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx成功创建的握手数
                            value =  NginxSuccessfullycreatedByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52003".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx正在处理的活动连接数
                            value =  NginxActiveingByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52004".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx总共处理的请求数
                            value =  NginxTotalProcessingByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52005".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx下一次请求指令的驻留连接数
                            value =  NginxWaitingConnectByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52006".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx返回给客户端的Header信息数
                            value =  NginxWritingHeaderCountByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52007".equals(resourceIndicatorlist.getIndicator().getItemType())){  //nginx读取到客户端的Header信息数
                            value =  NginxReadHeaderCountByHttpIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
                        }else if("52008".equals(resourceIndicatorlist.getIndicator().getItemType())){   //nginx连接响应时间
                            value =  NginxICMPIndicatroCalculate.getValue(resource.getIp(),resource.getResourceBaseInfo().getPort());
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
