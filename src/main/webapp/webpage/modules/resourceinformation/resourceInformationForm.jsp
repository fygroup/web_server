<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源信息表管理</title>
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


            $('#addedDate').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
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
				<a class="panelButton" href="${ctx}/resourceinformation/resourceInformation"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resourceInformation" action="${ctx}/resourceinformation/resourceInformation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<%--<div class="form-group">
					<label class="col-sm-2 control-label">资源编码：</label>
					<div class="col-sm-10">
						<form:input path="resourceId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">设备大类：</label>
					<div class="col-sm-10">
						<form:input path="equipmentCategory" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">设备小类：</label>
					<div class="col-sm-10">
						<form:input path="equipmentType" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">系统网址：</label>
					<div class="col-sm-10">
						<form:input path="sysUrl" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">运维厂商：</label>
					<div class="col-sm-10">
						<form:input path="opsFirm" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">运维人员：</label>
					<div class="col-sm-10">
						<form:input path="opsPerson" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">运维人员联系方式：</label>
					<div class="col-sm-10">
						<form:input path="opsContact" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">设备供应商：</label>
					<div class="col-sm-10">
						<form:input path="equipmentSupplier" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购金额：</label>
					<div class="col-sm-10">
						<form:input path="purchaseSum" htmlEscape="false"    class="form-control "/>
					</div>
				</div>


			<div class="form-group">
				<label class="col-sm-2 control-label">上架日期：</label>
				<div class="col-sm-10">
						<div class='input-group form_datetime' id='addedDate'>
							<input type='text'  name="addedDate" class="form-control"  value="<fmt:formatDate value="${resourceInformation.addedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
						</div>
				</div>
			</div>

			<%--
						<input type='text'  name="added_date" class="form-control"  value="<fmt:formatDate value="${resourceInformation.added_date}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>

					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">维保年限：</label>
					<div class="col-sm-10">
						<form:input path="maintenancePeriod" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属机构编码：</label>
					<div class="col-sm-10">
						<form:input path="courtId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">优先级：</label>
					<div class="col-sm-10">
						<form:input path="priority" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所在服务器：</label>
					<div class="col-sm-10">
						<form:input path="server" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">数据库端口：</label>
					<div class="col-sm-10">
						<form:input path="dbPort" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">数据库版本：</label>
					<div class="col-sm-10">
						<form:input path="dbEdition" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">操作系统：</label>
					<div class="col-sm-10">
						<form:input path="os" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">系统版本：</label>
					<div class="col-sm-10">
						<form:input path="osEdition" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('resourceinformation:resourceInformation:edit') || isAdd}">
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-lg-10">
						<button class="btn-primary form-control" data-loading-text="正在提交...">提 交</button>
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