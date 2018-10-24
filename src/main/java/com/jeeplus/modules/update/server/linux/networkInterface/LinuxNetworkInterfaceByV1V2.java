package com.jeeplus.modules.update.server.linux.networkInterface;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.SpringBeanUtils;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.collect.server.linux.networkinterface.NetworkInterfaceIndicatorCalculateLinuxByV1V2;
import com.jeeplus.modules.collect.server.windows.networkinterface.NetworkInterfaceIndicatorCalculateWindowsByV1V2;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.service.NetworkInterfaceService;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.update.UpdateService;
import org.apache.commons.collections.map.HashedMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LinuxNetworkInterfaceByV1V2 implements UpdateService {

    private NetworkInterfaceService networkInterfaceService= (NetworkInterfaceService) SpringBeanUtils.getBeanByClass(NetworkInterfaceService.class);
    private String uuid="a9beb8c645ff448d89e71f96dc97bc09";


    @Override
    public void updateIndicator(Resource resource) {
        synchronized (uuid.intern()) {
            DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
            Long star=System.currentTimeMillis();
            List<NetworkInterface> list=null;
            List<NetInterfaceInOutRate> rateList=null;
            ResourceGatherItem resourceGatherItem=null;
            List<Map<String, Object>> netInterfaceList= NetworkInterfaceIndicatorCalculateLinuxByV1V2.GetNetworkInterfaceInfo(resource.getIp(), resource.getResourceBaseInfo().getRdcommunity(), resource.getResourceBaseInfo().getPort(), resource.getResourceBaseInfo().getDelay(), resource.getResourceBaseInfo().getRepeatnum(), Transformation.null2Integer(resource.getResourceBaseInfo().getManagerType()));

            List<NetworkInterface> oldNetworkInterfaceList= networkInterfaceService.findList(new NetworkInterface(resource.getId()));
            Map<String,NetworkInterface> oldNetworkInterfaceMap=null;

            if(CheckObject.checkList(netInterfaceList)) {
                boolean initRate=false;
                if(CheckObject.checkList(oldNetworkInterfaceList)&& oldNetworkInterfaceList.size()==netInterfaceList.size()){
                    initRate=true;
                    rateList=new ArrayList<>();
                    oldNetworkInterfaceMap = initOldNetworkInterfaceMap(oldNetworkInterfaceList);
                }
                Date date=new Date();
                list=new ArrayList<>();

                for (Map<String, Object> map : netInterfaceList) {
                    NetworkInterface networkInterface = new NetworkInterface();
                    String id=IdGen.uuid();
                    networkInterface.setId(id);
                    networkInterface.setCreateDate(date);
                    networkInterface.setResourceId(resource.getId());
                    networkInterface.setName(String.valueOf(map.get("name")));
                    networkInterface.setInterfaceType(String.valueOf(map.get("interfaceType")));
                    networkInterface.setMac(String.valueOf(map.get("mac")));
                    networkInterface.setCapacity(String.valueOf(map.get("capacity")));
                    networkInterface.setInputByte(String.valueOf(map.get("inputByte")));
                    networkInterface.setOutputByte(String.valueOf(map.get("outputByte")));
                    networkInterface.setSort(String.valueOf(map.get("sort")));
                    networkInterface.setStatus(String.valueOf(map.get("status")));

                    if(initRate){
                        NetworkInterface oldNetworkInterface =oldNetworkInterfaceMap.get(String.valueOf(map.get("name")));
                        if(oldNetworkInterface!=null) {
                            id = oldNetworkInterface.getId();
                            networkInterface.setId(id);
                            NetInterfaceInOutRate inRate = new NetInterfaceInOutRate();
                            inRate.setNetworkInterfaceId(id);
                            inRate.setType("input");
                            inRate.setCreateDate(date);
                            double inputBytes = Transformation.null2Double(String.valueOf(map.get("inputByte"))) - Transformation.null2Double(oldNetworkInterface.getInputByte());
                            Long times = (date.getTime() - oldNetworkInterface.getCreateDate().getTime()) / 1000;
                            double rate = (inputBytes * 8) / times;
                            inRate.setRate(df.format(rate));

                            NetInterfaceInOutRate outRate = new NetInterfaceInOutRate();
                            outRate.setNetworkInterfaceId(id);
                            outRate.setType("output");
                            outRate.setCreateDate(date);
                            double outBytes = Transformation.null2Double(String.valueOf(map.get("outputByte"))) - Transformation.null2Double(oldNetworkInterface.getOutputByte());
                            times = (date.getTime() - oldNetworkInterface.getCreateDate().getTime()) / 1000;
                            rate = (outBytes * 8) / times;
                            outRate.setRate(df.format(rate));
                            if (outBytes < 0) {
                                outRate.setRate("0.00");
                            }
                            if (inputBytes < 0) {
                                inRate.setRate("0.00");
                            }
                            rateList.add(inRate);
                            rateList.add(outRate);
                            networkInterface.setInputRate(inRate.getRate());
                            networkInterface.setOutputRate(outRate.getRate());
                        }
                    }

                    list.add(networkInterface);
                }
            }



            if(CheckObject.checkList(list)) {
                networkInterfaceService.saveList(rateList,resourceGatherItem,list,resource.getId());
            }
            //System.out.println(resource.getName()+"获取网络接口方法结束："+(System.currentTimeMillis()-star));
        }
    }


    Map<String,NetworkInterface> initOldNetworkInterfaceMap(List<NetworkInterface> list){
        Map<String,NetworkInterface> map=new HashedMap();
        for(NetworkInterface networkInterface :list){
            map.put(networkInterface.getName(),networkInterface);
        }
        return map;
    }
}
