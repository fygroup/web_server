package com.jeeplus.modules.update.application;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.http.HttpUrl;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicatorValue;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.update.UpdateService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationIndicator implements UpdateService {

    private ApplicationIndicatorService applicationIndicatorService= (ApplicationIndicatorService)SpringBeanUtils.getBeanByClass(ApplicationIndicatorService.class);

    private String uuid="c3de25a76785419b8a6820db3935941d";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {
            Long star=System.currentTimeMillis();
            Date date=new Date();
            List<com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator> list=applicationIndicatorService.findListByResource(resource.getId());
            List<ApplicationIndicatorValue> valueList=new ArrayList<>();
            if(CheckObject.checkList(list)){
                for(com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator applicationIndicator : list){
                    if("81".equals(resource.getResourceType().getCode())){   //web应用
                        ApplicationIndicatorValue applicationIndicatorValue=new ApplicationIndicatorValue();
                        applicationIndicatorValue.setApplicationIndicator(applicationIndicator);
                        applicationIndicatorValue.setCreateDate(date);
                        applicationIndicatorValue.setValue(HttpUrl.getURLContent(applicationIndicator.getUrl()));
                        valueList.add(applicationIndicatorValue);
                    }
                }
            }
            if(CheckObject.checkList(valueList)){
                applicationIndicatorService.saveValueList(valueList);
            }

            //System.out.println("应用指标方法结束=====================："+(System.currentTimeMillis()-star));
        }
    }

}
