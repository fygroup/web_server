<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>巡检记录</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="oaInspectionList.js" %>
	<script src="/web/static/common/js/FileSaver.js"></script>
	<script src="/web/static/common/js/jquery.wordexport.js"></script>
	<style>#export_word{display:none;}</style>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">巡检记录列表</h3>
		</div>
		<div class="panel-body">
			<sys:message content="${message}"/>

			<!-- 搜索 -->
			<div class="accordion-group">
				<div id="collapseTwo" class="accordion-body collapse">
					<div class="accordion-inner">
						<form:form id="searchForm" modelAttribute="oaInspection" class="form form-horizontal well clearfix">
							<div class="col-xs-12 col-sm-6 col-md-4">
								<label class="label-item single-overflow pull-left" title="检查人员：">检查人员：</label>
								<form:input path="name" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4">
								<label class="label-item single-overflow pull-left" title="日期：">巡检日期：</label>
								<div class="col-xs-12">
									<div class="col-xs-12 col-sm-5">
										<div class='input-group date' id='dateStart' style="left: -10px;" >
											<input type='text'  name="dateStart" class="form-control"  />
											<span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
										</div>
									</div>
									<div class="col-xs-12 col-sm-1">
										~
									</div>
									<div class="col-xs-12 col-sm-5">
										<div class='input-group date' id='dateEnd' style="left: -10px;" >
											<input type='text'  name="dateEnd" class="form-control" />
											<span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4">
								<div style="margin-top:26px">
									<a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
									<a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>

			<!-- 工具栏 -->
			<div id="toolbar">
				<shiro:hasPermission name="oa:oaInspection:add">
					<a id="add" class="btn btn-primary" href="${ctx}/oa/oaInspection/form" title="巡检"><i class="glyphicon glyphicon-plus"></i>新建</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:oaInspection:edit">
					<button id="edit" class="btn btn-success" disabled onclick="edit()">
						<i class="glyphicon glyphicon-edit"></i> 修改
					</button>
				</shiro:hasPermission>

				<shiro:hasPermission name="oa:oaInspection:del">
					<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
						<i class="glyphicon glyphicon-remove"></i> 删除
					</button>
				</shiro:hasPermission>


				<button id="exportWord" class="btn btn-info" disabled onclick="methodName()">
					<i class="fa fa-file-word-o"></i> 导出
				</button>

				<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				</div>

			<!-- 表格 -->
			<table id="table" data-toolbar="#toolbar"></table>
            <div id="export_word">

			</div>
		</div>
	</div>
</div>
</body>
</html>