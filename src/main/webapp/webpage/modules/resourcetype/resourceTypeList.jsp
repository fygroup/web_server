<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源类型管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="resourceTypeList.js" %>

</head>
<body>

	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">资源类型列表  </h3>
			</div>
			
			<div class="panel-body">
			<sys:message content="${message}"/>
	
			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="resourcetype:resourceType:add">
						<a id="add" class="btn btn-primary" href="${ctx}/resourcetype/resourceType/form" title="资源类型"><i class="glyphicon glyphicon-plus"></i> 新建</a>
					</shiro:hasPermission>
			       <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				
				</div>
			</div>
			</div>
			<table id="resourceTypeTreeTable" class="table table-hover">
				<thead>
					<tr>
						<th>资源名称</th>
						<th>排序</th>
						<th>图标</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="resourceTypeTreeTableList"></tbody>
			</table>
			<br/>
			</div>
			</div>
	</div>
</body>
</html>