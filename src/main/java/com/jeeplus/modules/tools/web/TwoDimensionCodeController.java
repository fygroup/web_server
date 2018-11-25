/**
 * Copyright &copy; 2015-2020 <a href="http://www.clutek.org/">clutek</a> All rights reserved.
 */
package com.jeeplus.modules.tools.web;

import javax.servlet.http.HttpServletRequest;

import com.jeeplus.modules.cpu.entity.CpuUsedRate;
import com.jeeplus.modules.cpu.mapper.CpuMapper;
import com.jeeplus.modules.memory.entity.MemoryUsedRate;
import com.jeeplus.modules.memory.mapper.MemoryMapper;
import com.jeeplus.modules.resource.entity.AvailabilityRate;
import com.jeeplus.modules.resource.entity.HealthDegree;
import com.jeeplus.modules.resource.entity.Resource;
import com.jeeplus.modules.resource.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tools.utils.TwoDimensionCode;

import java.io.File;

/**
 * 二维码Controller
 * @author clutek
 * @version 2015-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/TwoDimensionCodeController")
public class TwoDimensionCodeController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private CpuMapper cpuMapper;
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private MemoryMapper memoryMapper;

	/**
	 * 二维码页面
	 */
	@RequestMapping(value = {"index", ""})
	public String index() throws Exception{
		return "modules/tools/qrcode/TwoDimensionCode";
	}
	
	/**
	 *	生成二维码
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="createTwoDimensionCode")
	@ResponseBody
	public AjaxJson createTwoDimensionCode(HttpServletRequest request, String encoderContent){
		AjaxJson j = new AjaxJson();
		Principal principal = (Principal) UserUtils.getPrincipal();
		User user = UserUtils.getUser();
		if (principal == null){
			j.setSuccess(false);
			j.setErrorCode("0");
			j.setMsg("没有登录");
		}
		String realPath = Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL
							+ principal + "/qrcode/";
		FileUtils.createDirectory(realPath);
		String name="test.png"; //encoderImgId此处二维码的图片名
			try {
				String filePath = realPath + name;  //存放路径
				TwoDimensionCode.encoderQRCode(encoderContent, filePath, "png");//执行生成二维码
				user.setQrCode(request.getContextPath()+Global.USERFILES_BASE_URL
						+ principal + "/qrcode/"+name);
				systemService.updateUserInfo(user);
				j.setSuccess(true);
				j.setMsg("二维码生成成功");
				j.put("filePath", request.getContextPath()+Global.USERFILES_BASE_URL
						+ principal + "/qrcode/"+name);
			} catch (Exception e) {
				
			}
		return j;
	}





	@RequestMapping(value="myCreateTwoDimensionCode")
	@ResponseBody
	public AjaxJson myCreateTwoDimensionCode(HttpServletRequest request,String resourceId){
		AjaxJson j = new AjaxJson();
		Principal principal = (Principal) UserUtils.getPrincipal();
		if (principal == null){
			j.setSuccess(false);
			j.setErrorCode("0");
			j.setMsg("没有登录");
		}
		String realPath = Global.getUserfilesBaseDir() + Global.QRCodeImg_BASE_URL;
		FileUtils.createDirectory(realPath);
		String name=resourceId+".png";
		try {
			String filePath = realPath + name;  //存放路径
			File file=new File(filePath);
			String  resourceName = resourceMapper.getResourceName(resourceId);
			HealthDegree getTopHealthDegree = resourceMapper.getTopHealthDegree(resourceId);
			AvailabilityRate getTopAvailabilityRate=resourceMapper.getTopAvailabilityRate(resourceId);
			CpuUsedRate cpuUsedRate =cpuMapper.getTopCpuUsedRate(resourceId);
			MemoryUsedRate getTopMemoryUsedRate =memoryMapper.getTopMemoryUsedRate(resourceId);
			if(!file.exists()){
				TwoDimensionCode.encoderQRCode("设备名："+resourceName+ "\n健康度："+getTopHealthDegree.getHealthDegree() +"%\n可用率："+getTopAvailabilityRate.getAvailabilityRate() + "%\nCPU利用率："+cpuUsedRate.getUsedRate() +"%\nMEM利用率："+getTopMemoryUsedRate.getUsedRate()+"%", filePath, "png");//执行生成二维码
			}
			j.setSuccess(true);
			j.setMsg("二维码生成成功");
			j.put("filePath", request.getContextPath()+Global.QRCodeImg_BASE_URL+name);
		} catch (Exception e) {
		}
		return j;
	}


}