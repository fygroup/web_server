<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>问题申报</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="oaIssueReturnList.js" %>
	<script>
		function detail(id){
		    alert(id);
		}

	</script>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">问题申报列表</h3>
		</div>
		<div class="panel-body">
			<sys:message content="${message}"/>

			<!-- 搜索 -->
			<div class="accordion-group">
				<div id="collapseTwo" class="accordion-body collapse">
					<div class="accordion-inner">
						<form:form id="searchForm" modelAttribute="oaIssueReturn" class="form form-horizontal well clearfix">
							<div class="col-xs-12 col-sm-6 col-md-4">
								<label class="label-item single-overflow pull-left" title="申报人：">申报人：</label>
								<input type="text" name="name" maxlength="100"  class=" form-control"/>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-4">
								<div class="form-group">
									<label class="label-item single-overflow pull-left" title="申报时间：">&nbsp;申报时间：</label>
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
				<shiro:hasPermission name="oa:oaIssueReturn:add">
					<a id="add" class="btn btn-primary" href="${ctx}/oa/oaIssueReturn/form" title="问题申报"><i class="glyphicon glyphicon-plus"></i> 新建</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:oaIssueReturn:edit">
					<button id="edit" class="btn btn-success" disabled onclick="edit()">
						<i class="glyphicon glyphicon-edit"></i> 修改
					</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="oa:oaIssueReturn:del">
					<button id="deleteAll" class="btn btn-danger" disabled onclick="deleteAll()">
						<i class="glyphicon glyphicon-remove"></i> 删除
					</button>
				</shiro:hasPermission>
				<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
				<shiro:hasPermission name="oa:oaIssueReturn:sends">
				<button id="sends" class="btn btn-warning" disabled onclick="sends()">
					<i class="glyphicon glyphicon-send"></i> 批量派单
				</button>
				</shiro:hasPermission>


			</div>

			<!-- 表格 -->
			<table id="table"   data-toolbar="#toolbar"></table>

			<%--<c:if test="${!isSelf}">--%>
			<%--<!-- context menu -->--%>
			<%--<ul id="context-menu" class="dropdown-menu">--%>
			<%--<shiro:hasPermission name="oa:oaNotify:edit">--%>
			<%--<li data-item="edit"><a>编辑</a></li>--%>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="oa:oaNotify:del">--%>
			<%--<li data-item="delete"><a>删除</a></li>--%>
			<%--</shiro:hasPermission>--%>
			<%--<li data-item="action1"><a>取消</a></li>--%>
			<%--</ul>--%>
			<%--</c:if>--%>
		</div>
	</div>
</div>
</body>
</html>