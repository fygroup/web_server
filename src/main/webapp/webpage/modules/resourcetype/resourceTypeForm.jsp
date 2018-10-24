<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源类型管理</title>
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
				<a class="panelButton" href="${ctx}/resourcetype/resourceType/list"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resourceType" action="${ctx}/resourcetype/resourceType/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-8">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上级父级编号:</label>
					<div class="col-sm-8">
						<sys:treeselect id="parent" name="parent.id" value="${resourceType.parent.id}" labelName="parent.name" labelValue="${resourceType.parent.name}"
						title="父级编号" url="/resourcetype/resourceType/treeData" extId="${resourceType.id}" cssClass="form-control " allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资源名称：</label>
					<div class="col-sm-8">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>排序：</label>
					<div class="col-sm-8">
						<form:input path="sort" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>编号：</label>
					<div class="col-sm-8">
						<p><span class="margin-right">1:网络设备</span> <span>2:服务器</span> <span>3:链路</span> <span>4:数据库</span> <span>5:中间件</span> <span>6:安全设备</span> <span>7:存储设备</span> <span>8:应用</span></p>
						<form:input path="code" htmlEscape="false"    class="form-control required isIntGtZero"/>
					</div>
				</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>图标：</label>
				<div class="col-sm-8">
					<sys:iconselect id="img" name="img" value="${resourceType.img}"/>

				</div>
			</div>
			<c:if test="${fns:hasPermission('resourcetype:resourceType:edit') || isAdd}">
			<div class="form-group">
				<div class="col-sm-2"></div>
				<div class="col-sm-8">
					<div class="form-group text-center">
						<div>
							<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
						</div>
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