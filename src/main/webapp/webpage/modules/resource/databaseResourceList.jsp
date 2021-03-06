<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="databaseResourceList.js" %>


	<link href="${ctxStatic}/plugin/bootstrapTree/bootstrap-treeview.css" rel="stylesheet" type="text/css"/>
	<script src="${ctxStatic}/plugin/bootstrapTree/bootstrap-treeview.js" type="text/javascript"></script>

	<script type="text/javascript">


			function detail(id){
				var id=id;
				var url='${ctx}/resource/resource/dataBaseDetail?id='+id;             //转向网页的地址;
				var name='';                  //网页名称，可为空;
                var iWidth=document.body.scrollWidth;                          //弹出窗口的宽度;
                var iHeight=document.body.scrollHeight;                         //弹出窗口的高度;
                window.open(url, name, 'height='  + iHeight + ',width=' + iWidth + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
            }

            function myRefresh(){
                $('#resourceTable').bootstrapTable('refresh');
            }
	</script>

</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">数据库资源列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>




	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">

			<form:form id="searchForm" modelAttribute="resource" class="form form-horizontal well clearfix">
				<input id="resourceTypeId" name="resourceType.id" value="" TYPE="hidden">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="资源名称：">资源名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="IP：">IP：</label>
				<form:input path="ip" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			 <%--<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="读共同体：">读共同体：</label>
				<form:input path="rdcommunity" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>--%>
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


		<div class="col-sm-3 col-md-2" >
			<div id="jstree" style="margin-top: 10px;"></div>
		</div>
		<div class="col-sm-9 col-md-10" >
			<!-- 工具栏 -->
			<div id="toolbar">
				<shiro:hasPermission name="resource:resource:add">
					<a id="add" class="btn btn-primary" href="${ctx}/resource/resource/databaseForm" title="resource"><i class="glyphicon glyphicon-plus"></i> 添加数据库</a>
				</shiro:hasPermission>

				<%--<shiro:hasPermission name="resource:resource:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>--%>
				<shiro:hasPermission name="resource:resource:del">
					<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
						<i class="glyphicon glyphicon-remove"></i> 删除
					</button>
				</shiro:hasPermission>
				<%--<shiro:hasPermission name="resource:resource:import">
                    <button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
                    <div id="importBox" class="hide">
                            <form id="importForm" action="${ctx}/resource/resource/import" method="post" enctype="multipart/form-data"
                                 style="padding-left:20px;text-align:center;" ><br/>
                                <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　


                            </form>
                    </div>
                </shiro:hasPermission>--%>
				<a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
					<i class="fa fa-search"></i> 检索
				</a>
			</div>

			<!-- 表格 -->
			<table id="resourceTable"   data-toolbar="#toolbar"></table>

			<!-- context menu -->
			<ul id="context-menu" class="dropdown-menu">
				<shiro:hasPermission name="resource:resource:edit">
					<li data-item="edit"><a>编辑</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="resource:resource:del">
					<li data-item="delete"><a>删除</a></li>
				</shiro:hasPermission>
				<li data-item="action1"><a>取消</a></li>
			</ul>
		</div>
	</div>
	</div>
	</div>
</body>
</html>