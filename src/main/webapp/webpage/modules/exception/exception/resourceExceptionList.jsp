<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>异常告警管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="resourceExceptionList.js" %>

	<script>

	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">异常告警列表</h3>
	</div>
	<div class="panel-body">
		<sys:message content="${message}"/>
	
	<!-- 搜索 -->
	<div class="accordion-group">
	<div id="collapseTwo" class="accordion-body collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="resourceException" class="form form-horizontal well clearfix">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="指标名称：">指标名称：</label>
				<form:input path="indicatorName" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="异常来源：">异常来源：</label>
				<form:input path="exceptionSource" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>

			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="最后一次产生异常的时间：">&nbsp;最后一次产生异常的时间：</label>
					<div class="col-xs-12">
						<div class='input-group date' id='lastTriggerTime' >
							<input type='text'  name="lastTriggerTime" class="form-control"  />
							<span class="input-group-addon">
							   <span class="glyphicon glyphicon-calendar"></span>
						   </span>
						</div>
					</div>
				</div>
			</div>

			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="资源类型：">资源类型：</label>
				<sys:treeselect id="resourceType" name="resourceType.id" value="${resourceException.resourceType.id}" labelName="resourceType.name" labelValue="${resourceException.resourceType.name}"
					title="资源类型" url="/resourcetype/resourceType/treeData" extId="${resourceException.id}" cssClass="form-control required" allowClear="true"/>
			</div>


			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="确认人：">确认人：</label>
				<sys:userselect id="confirmUser" name="confirmUser.id" value="${resourceException.confirmUser.id}" labelName="confirmUser.name" labelValue="${resourceException.confirmUser.name}"
							    cssClass="form-control "/>
			</div>

			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="归属部门：">归属部门：</label>
				<sys:treeselect id="office" name="office.id" value="${resourceException.office.id}" labelName="office.name" labelValue="${resourceException.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="异常等级：">&nbsp;异常等级：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="exceptionClass" items="${fns:getDictList('exception_class')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="当前状态：">&nbsp;当前状态：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="currentStatus" items="${fns:getDictList('exception_current_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="指标事件类型：">&nbsp;指标事件类型(冗余)：</label>
					<div class="col-xs-12">
						<form:radiobuttons class="i-checks" path="eventType" items="${fns:getDictList('indicator_event_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<div class="form-group">
					<label class="label-item single-overflow pull-left" title="第一次触发时间：">&nbsp;第一次触发时间：</label>
					<div class="col-xs-12">
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='beginFirstTriggerTime' style="left: -10px;" >
								<input type='text'  name="beginFirstTriggerTime" class="form-control"  />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
						<div class="col-xs-12 col-sm-1">
							~
						</div>
						<div class="col-xs-12 col-sm-5">
							<div class='input-group date' id='endFirstTriggerTime' style="left: -10px;" >
								<input type='text'  name="endFirstTriggerTime" class="form-control" />
								<span class="input-group-addon">
									   <span class="glyphicon glyphicon-calendar"></span>
								   </span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="地域：">地域：</label>
				<div class=" input-group" style=" width: 100%;">
					<form:input path="area" htmlEscape="false" data-toggle="city-picker" style="height: 34px;font-size: 14px;"/>
				</div>
			</div>


				<%--<div class="col-xs-12 col-sm-6 col-md-4">
                   <label class="label-item single-overflow pull-left" title="资源列表项id：">资源列表项id：</label>
                   <sys:gridselect url="${ctx}/indicator/indicator/data" id="indicatorItem" name="indicatorItem.id" value="${resourceException.indicatorItem.id}" labelName="indicatorItem.name" labelValue="${resourceException.indicatorItem.name}"
                       title="选择资源列表项id" cssClass="form-control required" fieldLabels="" fieldKeys="" searchLabels="" searchKeys="" ></sys:gridselect>
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
	
	<!-- 工具栏 -->
	<div id="toolbar">
			<%--<shiro:hasPermission name="exception:exception:resourceException:add">
				<a id="add" class="btn btn-primary" href="${ctx}/exception/exception/resourceException/form" title="异常告警"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>--%>
			<shiro:hasPermission name="exception:exception:resourceException:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i>查看
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="exception:exception:resourceException:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="exception:exception:resourceException:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
				<div id="importBox" class="hide">
						<form id="importForm" action="${ctx}/exception/exception/resourceException/import" method="post" enctype="multipart/form-data"
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
	<table id="resourceExceptionTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="exception:exception:resourceException:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="exception:exception:resourceException:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>