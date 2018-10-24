<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源物理信息管理</title>
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
				<a class="panelButton" href="${ctx}/resourcephysicinfo/resourcePhysicInfo"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resourcePhysicInfo" action="${ctx}/resourcephysicinfo/resourcePhysicInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">建筑：</label>
					<div class="col-sm-10">
						<form:input path="building" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机房：</label>
					<div class="col-sm-10">
						<form:input path="machineroom" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<%--<div class="form-group">
					<label class="col-sm-2 control-label">资源编号：</label>
					<div class="col-sm-10">
						<form:input path="resourceId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">机柜：</label>
					<div class="col-sm-10">
						<form:input path="cabinet" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">柜内编号：</label>
					<div class="col-sm-10">
						<form:input path="cabinetNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机柜容量：</label>
					<div class="col-sm-10">
						<form:input path="cabinetCapacity" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属位置：</label>
					<div class="col-sm-10">
						<form:input path="locate" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-10">
						<form:input path="datailedAddress" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">重量：</label>
					<div class="col-sm-10">
						<form:input path="weight" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">高度：</label>
					<div class="col-sm-10">
						<form:input path="height" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">功耗：</label>
					<div class="col-sm-10">
						<form:input path="powerDissipation" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">发热：</label>
					<div class="col-sm-10">
						<form:input path="heat" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
		<c:if test="${fns:hasPermission('resourcephysicinfo:resourcePhysicInfo:edit') || isAdd}">
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