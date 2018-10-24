package com.jeeplus.modules.update.link;

import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.GeneralMethod;
import com.jeeplus.modules.linkindicator.entity.LinkIndicator;
import com.jeeplus.modules.linkindicator.service.LinkIndicatorService;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.entity.ResourceOidValue;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.update.UpdateService;
import java.text.DecimalFormat;

public class LinkRateIndicator implements UpdateService {

    private ResourceService resourceService = (ResourceService) SpringBeanUtils.getBeanByClass(ResourceService.class);

    private NetworkInterfaceService networkInterfaceService= (NetworkInterfaceService)SpringBeanUtils.getBeanByClass(NetworkInterfaceService.class);

    private LinkIndicatorService linkIndicatorService= (LinkIndicatorService)SpringBeanUtils.getBeanByClass(LinkIndicatorService.class);
    private String uuid="c6e0080e06014abd9240f870aadf3200";

    @Override
    public  void updateIndicator(Resource resource) {
        synchronized(uuid.intern()) {

            LinkIndicator linkIndicator=linkIndicatorService.getByResource(resource.getId());

            if(linkIndicator==null){
                return;
            }

            NetworkInterface upNetworkInterface=networkInterfaceService.get(linkIndicator.getUpLinkInterface());
            NetworkInterface downNetworkInterface=networkInterfaceService.get(linkIndicator.getDownLinkInterface());
            if(upNetworkInterface==null||downNetworkInterface==null){
                return;
            }


            Resource upResource=resourceService.get(upNetworkInterface.getResourceId());
            Resource downResource=resourceService.get(downNetworkInterface.getResourceId());
            if(upResource==null||downResource==null){
                return;
            }

            linkIndicator.setUpLinkEquequipment(upNetworkInterface.getResourceId());
            linkIndicator.setDownLinkEquequipment(downNetworkInterface.getResourceId());
            if("0.00Mbps".equals(upNetworkInterface.getCapacity())){
                linkIndicator.setCapacity(downNetworkInterface.getCapacity());
            }else{
                linkIndicator.setCapacity(upNetworkInterface.getCapacity());
            }
            linkIndicator.setStatus("UP");
            linkIndicator.setHealthDegree("100");
            linkIndicator.setAvailability("100");


            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0

            ResourceOidValue upOid = GeneralMethod.getSysOid(upResource.getIp(), upResource.getResourceBaseInfo().getRdcommunity(), upResource.getResourceBaseInfo().getPort(), "500", "2");
            ResourceOidValue downOid = GeneralMethod.getSysOid(downResource.getIp(), downResource.getResourceBaseInfo().getRdcommunity(), downResource.getResourceBaseInfo().getPort(), "500", "2");
            if(upOid==null||upOid.getValue()==null||downOid==null||downOid.getValue()==null){
                linkIndicator.setStatus("DOWN");
                linkIndicator.setHealthDegree("0");
                linkIndicator.setAvailability("0");
            }

            NetInterfaceInOutRate upInput=networkInterfaceService.findTopRate(upNetworkInterface.getId(),"input");
            double upInputRate=0.00;
            if(upInput!=null){
                upInputRate= Transformation.null2Double(upInput.getRate());
            }


            NetInterfaceInOutRate upOut=networkInterfaceService.findTopRate(upNetworkInterface.getId(),"output");
            double upOutRate=0.00;
            if(upOut!=null){
                upOutRate=Transformation.null2Double(upOut.getRate());
            }


            NetInterfaceInOutRate downInput=networkInterfaceService.findTopRate(downNetworkInterface.getId(),"input");
            double downInputRate=0.00;
            if(downInput!=null){
                downInputRate=Transformation.null2Double(downInput.getRate());
            }

            NetInterfaceInOutRate downOut=networkInterfaceService.findTopRate(downNetworkInterface.getId(),"output");
            double downOutRate=0.00;
            if(downOut!=null){
                downOutRate=Transformation.null2Double(downOut.getRate());
            }

            double upRateNum=(upInputRate+downOutRate)/2;
            double downRateNum=(upOutRate+downInputRate)/2;
            String upRate=initRate(String.valueOf(upRateNum));
            String downRate=initRate(String.valueOf(downRateNum));
            String upUsedRate="";
            String downUsedRate="";
            if("0.00Mbps".equals(linkIndicator.getCapacity())){
                upUsedRate= "0.00";
                downUsedRate= "0.00";
            }else{
                upUsedRate= df.format(upRateNum/((Transformation.null2Double(linkIndicator.getCapacity().replaceAll("Mbps",""))*1000*1000))*100);
                downUsedRate=df.format(downRateNum/((Transformation.null2Double(linkIndicator.getCapacity().replaceAll("Mbps",""))*1000*1000))*100);
            }
            linkIndicator.setUpRate(upRate);
            linkIndicator.setUpUsedRate(upUsedRate);
            linkIndicator.setDownRate(downRate);
            linkIndicator.setDownUsedRate(downUsedRate);
            resourceService.saveLink(resource, linkIndicator,null);//保存
        }
    }


    String initRate(String rate){
        DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
        double result= Transformation.null2Double(rate)/1024/1024;
        if(result>1.0){
            return df.format(result)+"Mbps";
        }else{
            result=result*1024;
            return df.format(result)+"Kbps";
        }
    }

}