<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>厂商信息管理</title>
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
				<a class="panelButton" href="${ctx}/manufacturer/manufacturer"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="manufacturer" action="${ctx}/manufacturer/manufacturer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>名称：</label>
					<div class="col-sm-8">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>编号：</label>
					<div class="col-sm-8">
						<form:input path="code" htmlEscape="false"    class="form-control isIntGtZero required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">描述：</label>
					<div class="col-sm-8">
						<form:input path="description" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-8">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<c:if test="${fns:hasPermission('manufacturer:manufacturer:edit') || isAdd}">
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
</body>
</html>