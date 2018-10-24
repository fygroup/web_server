<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>指标列表管理</title>
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


        function showOperatingSystem(id) {
            $("#operatingSystemDiv").empty();
            $.get("${ctx}/operatingsystem/operatingSystem/selectOperatingSystem",{id:id},function(msg){
                $.each(msg,function(key,value){
                    if(key==0){
                        $("#operatingSystemDiv").html($("#operatingSystemDivContext").html());
                    }
                    $("#operatingSystemId").append('<option value="'+value.id+'" >'+value.name+'</option>');
                })
            });
        }

	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/indicator/indicator"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="indicator" action="${ctx}/indicator/indicator/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>名称：</label>
					<div class="col-sm-8">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资源类型：</label>
					<div class="col-sm-8">
							<%--	<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="" labelValue="${resource.company.name}"
												title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
			--%>
						<sys:resourceselect id="resourceTypeId" name="resourceTypeId" value="${indicator.resourceTypeId}" labelName="" labelValue="${indicator.resourceType.name}"
											title="资源类型" url="/resourcetype/resourceType/treeData"  cssClass="form-control required" notAllowSelectParent="true"/>
					</div>
				</div>

			<div class="form-group" id="operatingSystemDiv">
				<c:if test="${ !isAdd}">
					<label class="col-sm-2 control-label">操作系统：</label>
					<div class="col-sm-8">
					<select name="operatingSystemId"  class="form-control ">
						<c:forEach items="${operatingSystemList}" var="operatingSystem">
							<option value="${operatingSystem.id}" <c:if test="${operatingSystem.id == indicator.operatingSystemId}"> selected="selected"</c:if> >${operatingSystem.name}</option>
						</c:forEach>
					</select>
					</div>

				</c:if>

			</div>

			<%--	<div class="form-group">
					<label class="col-sm-2 control-label">操作系统：</label>
					<div class="col-sm-8">
						<form:input path="operatingSystemId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>--%>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>事件类型：</label>
					<div class="col-sm-8">
						<form:select path="eventType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('indicator_event_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>

				</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>指标类型：</label>
				<div class="col-sm-8">
					<form:select path="type" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('indicator_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>

			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label">单位：</label>
				<div class="col-sm-8">
					<form:input path="unit" htmlEscape="false"    class="form-control "/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>子类型：</label>
				<div class="col-sm-8">
					<form:select path="itemType" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('resource_indicator_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</div>
			</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-8">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>

			<div class="form-group">
				<c:if test="${fns:hasPermission('indicator:indicator:edit') || isAdd}">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-8">
						<button class="btn-primary form-control" data-loading-text="正在提交...">提 交</button>
					</div>
				</c:if>
			</div>

		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>


	<div class="form-group" id="operatingSystemDivContext" style="display: none">
		<label class="col-sm-2 control-label">操作系统：</label>
		<div class="col-sm-8">
			<select name="operatingSystemId" id="operatingSystemId" class="form-control" >

			</select>
		</div>
	</div>

</body>
</html>