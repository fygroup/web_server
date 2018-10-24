/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcebaseinfo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源访问信息Entity
 * @author le
 * @version 2017-10-31
 */
public class ResourceBaseInfo extends DataEntity<ResourceBaseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String port;		// 端口
	private String rdcommunity;		// 读共同体
	private String delay;		// 超时时间
	private String repeatnum;		// 超时次数
	private String userName;		// 用户名
	private String securityLeve;		// 安全级别
	private String authenticationProtocol;		// 认证协议
	private String authenticationPassword;		// 认证密码
	private String encryptionProtocol;		// 加密协议
	private String encryptionPassword;		// 加密密码
	private String accessConfigType;		// 访问类型
	private String accessConfigPort;		// 访问端口
	private String accessConfigUserName;		// 访问用户名
	private String accessConfigPassword;		// 访问密码
	private String privilegedModeCommand;		//  特权模式口令
	private String privilegedModePassword;		// 特权模式密码
	private String commandPrompt;		// 命令提示符
	private String managerType;         //管理方式
	
	public ResourceBaseInfo() {
		super();
	}

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public ResourceBaseInfo(String id){
		super(id);
	}

	@ExcelField(title="端口", align=2, sort=7)
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@ExcelField(title="读共同体", align=2, sort=8)
	public String getRdcommunity() {
		return rdcommunity;
	}

	public void setRdcommunity(String rdcommunity) {
		this.rdcommunity = rdcommunity;
	}
	
	@ExcelField(title="超时时间", align=2, sort=9)
	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}
	
	@ExcelField(title="超时次数", align=2, sort=10)
	public String getRepeatnum() {
		return repeatnum;
	}

	public void setRepeatnum(String repeatnum) {
		this.repeatnum = repeatnum;
	}
	
	@ExcelField(title="用户名", align=2, sort=11)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="安全级别", dictType="", align=2, sort=12)
	public String getSecurityLeve() {
		return securityLeve;
	}

	public void setSecurityLeve(String securityLeve) {
		this.securityLeve = securityLeve;
	}
	
	@ExcelField(title="认证协议", dictType="", align=2, sort=13)
	public String getAuthenticationProtocol() {
		return authenticationProtocol;
	}

	public void setAuthenticationProtocol(String authenticationProtocol) {
		this.authenticationProtocol = authenticationProtocol;
	}
	
	@ExcelField(title="认证密码", align=2, sort=14)
	public String getAuthenticationPassword() {
		return authenticationPassword;
	}

	public void setAuthenticationPassword(String authenticationPassword) {
		this.authenticationPassword = authenticationPassword;
	}
	
	@ExcelField(title="加密协议", dictType="", align=2, sort=15)
	public String getEncryptionProtocol() {
		return encryptionProtocol;
	}

	public void setEncryptionProtocol(String encryptionProtocol) {
		this.encryptionProtocol = encryptionProtocol;
	}
	
	@ExcelField(title="加密密码", align=2, sort=16)
	public String getEncryptionPassword() {
		return encryptionPassword;
	}

	public void setEncryptionPassword(String encryptionPassword) {
		this.encryptionPassword = encryptionPassword;
	}
	
	@ExcelField(title="访问类型", align=2, sort=17)
	public String getAccessConfigType() {
		return accessConfigType;
	}

	public void setAccessConfigType(String accessConfigType) {
		this.accessConfigType = accessConfigType;
	}
	
	@ExcelField(title="访问端口", align=2, sort=18)
	public String getAccessConfigPort() {
		return accessConfigPort;
	}

	public void setAccessConfigPort(String accessConfigPort) {
		this.accessConfigPort = accessConfigPort;
	}
	
	@ExcelField(title="访问用户名", align=2, sort=19)
	public String getAccessConfigUserName() {
		return accessConfigUserName;
	}

	public void setAccessConfigUserName(String accessConfigUserName) {
		this.accessConfigUserName = accessConfigUserName ;
	}
	
	@ExcelField(title="访问密码", align=2, sort=20)
	public String getAccessConfigPassword() {
		return accessConfigPassword;
	}

	public void setAccessConfigPassword(String accessConfigPassword) {
		this.accessConfigPassword = accessConfigPassword;
	}
	
	@ExcelField(title=" 特权模式口令", align=2, sort=21)
	public String getPrivilegedModeCommand() {
		return privilegedModeCommand;
	}

	public void setPrivilegedModeCommand(String privilegedModeCommand) {
		this.privilegedModeCommand = privilegedModeCommand;
	}
	
	@ExcelField(title="特权模式密码", align=2, sort=22)
	public String getPrivilegedModePassword() {
		return privilegedModePassword;
	}

	public void setPrivilegedModePassword(String privilegedModePassword) {
		this.privilegedModePassword = privilegedModePassword;
	}
	
	@ExcelField(title="命令提示符", align=2, sort=23)
	public String getCommandPrompt() {
		return commandPrompt;
	}

	public void setCommandPrompt(String commandPrompt) {
		this.commandPrompt = commandPrompt;
	}
	
}