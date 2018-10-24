<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源配置信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					jp.loading();
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/resourceconfiginfo/resourceConfigInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resourceConfigInfo" action="${ctx}/resourceconfiginfo/resourceConfigInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">CPU 个数：</label>
					<div class="col-sm-10">
						<form:input path="cpuNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单个 CPU 型号：</label>
					<div class="col-sm-10">
						<form:input path="singleCpuType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单个 CPU 频率：</label>
					<div class="col-sm-10">
						<form:input path="singleCpuRate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单个 CPU 核数：</label>
					<div class="col-sm-10">
						<form:input path="singleCpuCorenum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内存：</label>
					<div class="col-sm-10">
						<form:input path="memory" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内存条个数：</label>
					<div class="col-sm-10">
						<form:input path="memoryNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内存条容量：</label>
					<div class="col-sm-10">
						<form:input path="memorycCapacity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">硬盘个数：</label>
					<div class="col-sm-10">
						<form:input path="diskNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">硬盘容量：</label>
					<div class="col-sm-10">
						<form:input path="diskCapacity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内置硬盘类型：</label>
					<div class="col-sm-10">
						<form:input path="diskType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单个内置硬盘容量：</label>
					<div class="col-sm-10">
						<form:input path="singleDiskCapacity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内置硬盘可用容量：</label>
					<div class="col-sm-10">
						<form:input path="diskAvailableCapacity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">内置硬盘是否RAID级别：</label>
					<div class="col-sm-10">
						<form:input path="diskIfRaid" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">网卡个数：</label>
					<div class="col-sm-10">
						<form:input path="netcardNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<%--<div class="form-group">
					<label class="col-sm-2 control-label">资源编号：</label>
					<div class="col-sm-10">
						<form:input path="resourceId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">U数：</label>
					<div class="col-sm-10">
						<form:input path="uNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">控制器数量：</label>
					<div class="col-sm-10">
						<form:input path="controllerNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电源模块数量：</label>
					<div class="col-sm-10">
						<form:input path="elepowerModuleNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单个电源模块功率：</label>
					<div class="col-sm-10">
						<form:input path="singlePowermodulePower" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">已用接口数量：</label>
					<div class="col-sm-10">
						<form:input path="usedInterfaceNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">可用接口数量：</label>
					<div class="col-sm-10">
						<form:input path="avaliableInterfaceNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">特殊板卡数量：</label>
					<div class="col-sm-10">
						<form:input path="specialBoardNum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">特殊板卡类型：</label>
					<div class="col-sm-10">
						<form:input path="specialBoardType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电源模块功率：</label>
					<div class="col-sm-10">
						<form:input path="elepowerModulePower" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('resourceconfiginfo:resourceConfigInfo:edit') || isAdd}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>