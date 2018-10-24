/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.networkinterface.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeeplus.common.utils.CheckObject;
import com.jeeplus.common.utils.Transformation;
import com.jeeplus.modules.disk.entity.Disk;
import com.jeeplus.modules.disk.mapper.DiskMapper;
import com.jeeplus.modules.indicator.entity.Indicator;
import com.jeeplus.modules.indicator.service.IndicatorService;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.service.ResourceService;
import com.jeeplus.modules.resourcegatheritem.entity.ResourceGatherItem;
import com.jeeplus.modules.resourcegatheritem.service.ResourceGatherItemService;
import com.jeeplus.modules.resourceindicatorlist.entity.ResourceIndicatorlist;
import com.jeeplus.modules.resourceindicatorlist.service.ResourceIndicatorlistService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import com.jeeplus.modules.networkinterface.mapper.NetworkInterfaceMapper;

/**
 * 网络接口Service
 * @author le
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class NetworkInterfaceService extends CrudService<NetworkInterfaceMapper, NetworkInterface> {

	@Autowired
	private NetworkInterfaceMapper networkInterfaceMapper;


	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ResourceIndicatorlistService resourceIndicatorlistService;

	@Autowired
	private ResourceGatherItemService resourceGatherItemService;


	/**
	 * 全部列表
	 * @return
	 */
	public List<NetworkInterface> list() {
		return networkInterfaceMapper.list();
	}



	public NetworkInterface get(String id) {
		return super.get(id);
	}
	
	public List<NetworkInterface> findList(NetworkInterface networkInterface) {
		List<NetworkInterface> list=super.findList(networkInterface);
		if(CheckObject.checkList(list)){
			for(NetworkInterface netInterface : list){
				NetInterfaceInOutRate inputRate=networkInterfaceMapper.findTopRate(netInterface.getId(),"input");
				if(inputRate!=null){
					inputRate.setRate(initRate(inputRate.getRate()));
					netInterface.setInRate(inputRate);
				}
				NetInterfaceInOutRate outRate=networkInterfaceMapper.findTopRate(netInterface.getId(),"output");
				if(outRate!=null){
					outRate.setRate(initRate(outRate.getRate()));
					netInterface.setOutRate(outRate);
				}
			}
		}
		return list;
	}


	@Transactional(readOnly = false)
	public void delByResourceId(String id) {
		 networkInterfaceMapper.delByResourceId(id);
	}

	public List<NetworkInterface> findListByResourceTypeCode(String code) {
		return networkInterfaceMapper.findListByResourceTypeCode(code);
	}



/*
	public List<NetworkInterface> findListBySort(String resourceId) {
		Long start=System.currentTimeMillis();
		List<NetworkInterface> list=networkInterfaceMapper.findListBySort(resourceId);
		if(CheckObject.checkList(list)){
			for(NetworkInterface netInterface : list){
				NetInterfaceInOutRate inputRate=networkInterfaceMapper.findTopRate(netInterface.getId(),"input");
				if(inputRate!=null){
					inputRate.setRate(initRate(inputRate.getRate()));
					netInterface.setInRate(inputRate);
				}
				NetInterfaceInOutRate outRate=networkInterfaceMapper.findTopRate(netInterface.getId(),"output");
				if(outRate!=null){
					outRate.setRate(initRate(outRate.getRate()));
					netInterface.setOutRate(outRate);
				}
			}
		}
		return list;
	}
*/






	/*public List<NetworkInterface> findListBySort(String resourceId) {

		List<NetworkInterface> list=networkInterfaceMapper.findListBySort(resourceId);
		if(CheckObject.checkList(list)){
			List<NetInterfaceInOutRate> inputList=networkInterfaceMapper.findRateListByResourceIdType("input",resourceId,String.valueOf(list.size()));
			List<NetInterfaceInOutRate> outputList=networkInterfaceMapper.findRateListByResourceIdType("output",resourceId,String.valueOf(list.size()));
			if(CheckObject.checkList(inputList)&&CheckObject.checkList(outputList)&&(list.size()>=inputList.size())&&(list.size()>=outputList.size())) {
				for (int i=0;i<list.size();i++) {
					NetworkInterface netInterface=list.get(i);
					NetInterfaceInOutRate inputRate=null;
					NetInterfaceInOutRate outRate=null;
					for(NetInterfaceInOutRate item:inputList){
						if(item.getNetworkInterfaceId().equals(netInterface.getId())){
							inputRate = item;
							break;
						}
					}
					for(NetInterfaceInOutRate item:outputList){
                        if(item.getNetworkInterfaceId().equals(netInterface.getId())){
							outRate = item;
							break;
						}
					}
					if (inputRate != null) {
						inputRate.setRate(initRate(inputRate.getRate()));
						netInterface.setInRate(inputRate);
					}
					if (outRate != null){
						outRate.setRate(initRate(outRate.getRate()));
						netInterface.setOutRate(outRate);
					}
				}
			}
		}
		return list;
	}
*/



	public List<NetworkInterface> findListBySort(String resourceId) {

		List<NetworkInterface> list=networkInterfaceMapper.findListBySort(resourceId);
		if(CheckObject.checkList(list)){
			//List<NetInterfaceInOutRate> inputList=networkInterfaceMapper.findRateListByResourceIdType("input",resourceId,String.valueOf(list.size()));
			//List<NetInterfaceInOutRate> outputList=networkInterfaceMapper.findRateListByResourceIdType("output",resourceId,String.valueOf(list.size()));

				for (int i=0;i<list.size();i++) {
					NetworkInterface netInterface=list.get(i);
					NetInterfaceInOutRate inputRate=new NetInterfaceInOutRate();
					NetInterfaceInOutRate outRate=new NetInterfaceInOutRate();

					inputRate.setRate(initRate(netInterface.getInputRate()));
					netInterface.setInRate(inputRate);
					outRate.setRate(initRate(netInterface.getOutputRate()));
					netInterface.setOutRate(outRate);
			}
		}
		return list;
	}




	public List<NetworkInterface> onlineNetInterface(String resourceId) {
		List<NetworkInterface> list=networkInterfaceMapper.onlineNetInterface(resourceId);
		return list;
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

	public List<NetInterfaceInOutRate> findRateList(String networkInterfaceId,String type) {
		return networkInterfaceMapper.findRateList(networkInterfaceId,type);
	}


	public Page<NetworkInterface> findPage(Page<NetworkInterface> page, NetworkInterface networkInterface) {
		return super.findPage(page, networkInterface);
	}
	
	@Transactional(readOnly = false)
	public void save(NetworkInterface networkInterface) {
		super.save(networkInterface);
	}
	
	@Transactional(readOnly = false)
	public void delete(NetworkInterface networkInterface) {
		super.delete(networkInterface);
	}


	@Transactional(readOnly = false)
	public void saveList(List<NetInterfaceInOutRate> rateList, ResourceGatherItem resourceGatherItem, List<NetworkInterface> list, String resourceId) {
		if(resourceGatherItem!=null) {
			resourceGatherItemService.save(resourceGatherItem);
		}
		if(CheckObject.checkList(rateList)){
			networkInterfaceMapper.saveRateList(rateList);
		}

		networkInterfaceMapper.delByResourceId(resourceId);

		networkInterfaceMapper.saveList(list);
	}



	public NetInterfaceInOutRate findTopRate(String netInterfaceId,String type){
		return networkInterfaceMapper.findTopRate(netInterfaceId,type);
	}



	public List<NetworkInterface> getListByIpType(String ip) {
		NetworkInterface networkInterface=new NetworkInterface();
		Resource resource=resourceService.getByIpType(ip,"1");
		if(resource==null){
			resource=resourceService.getByIpType(ip,"2");
		}
		if(resource==null){
			resource=resourceService.getByIpType(ip,"6");
		}
		if(resource!=null){
			networkInterface.setResourceId(resource.getId());
			return networkInterfaceMapper.findList(networkInterface);
		}

		return new ArrayList<NetworkInterface>();
	}


	public NetInterfaceInOutRate getTopUsed( String resourceId, Date beginDate , Date endDate ){
		return mapper.getTopUsed( resourceId,  beginDate ,  endDate);
	}
}