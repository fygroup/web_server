<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>进出申请</title>
	<meta name="decorator" content="ani"/>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<a class="panelButton" href="#"  onclick="history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
					</h3>
				</div>
				<div class="panel-body">
			<form:form id="inputForm" modelAttribute="turnover" action="${ctx}/oa/turnover/saveAudit" method="post" class="form-horizontal">
			<fieldset>
			<legend>${turnover.act.taskName}</legend>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>单位名称：</label>
					<div class="col-sm-10">
						<form:input path="units" htmlEscape="false" maxlength="200" class="form-control" readonly="readonly"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>人员名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control" readonly="readonly"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">进入事由：</label>
					<div class="col-sm-10">
						<form:textarea path="reason" class="form-control"  rows="5" readonly="true" maxlength="20"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">进入时间：</label>
					<div class="col-sm-10">
						<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="form-control"
							   value="<fmt:formatDate value="${turnover.entryTime}" pattern="yyyy-MM-dd"/>"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">离开时间：</label>
					<div class="col-sm-10">
						<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="form-control"
							   value="<fmt:formatDate value="${turnover.departureTime}" pattern="yyyy-MM-dd"/>"/>
					</div>
				</div>


				<act:flowChart procInsId="${turnover.act.procInsId}"/>
				<act:histoicFlow procInsId="${turnover.act.procInsId}"/>
		
			</fieldset>
			</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>


