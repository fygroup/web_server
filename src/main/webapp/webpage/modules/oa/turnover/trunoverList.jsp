<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>进出一览</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/oa/turnover/">待办任务</a></li>
		<li class="active"><a href="${ctx}/oa/turnover/list">所有任务</a></li>
		<shiro:hasPermission name="oa:turnover:edit"><li><a href="${ctx}/oa/turnover/form">进出申请</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="turnover" action="${ctx}/oa/turnover/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>进出编号：&nbsp;</label><form:input path="ids" htmlEscape="false" maxlength="50" class="input-medium" placeholder="多个用逗号或空格隔开"/>
		</div>
		<div style="margin-top:8px;">
			<label>创建时间：</label>
			<input id="createDateStart"  name="createDateStart"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${turnover.entryTimeStart}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
				　--　
			<input id="createDateEnd" name="createDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
				value="<fmt:formatDate value="${turnover.entryTimeStart}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th>进出编号</th>
			<th>创建人</th>
			<th>创建时间</th>
			<th>请假原因</th>
			<th>当前节点</th>
			<th>操作</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="turnover">
			<c:set var="task" value="${turnover.task }" />
			<c:set var="pi" value="${turnover.processInstance }" />
			<c:set var="hpi" value="${turnover.historicProcessInstance }" />
			<tr>
				<td>${turnover.id}</td>
				<td>${turnover.createBy.name}</td>
				<td><fmt:formatDate value="${leave.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${turnover.reason}</td>
				<c:if test="${not empty task}">
					<td>${task.name}</td>
					<td><a target="_blank" href="${ctx}/act/task/trace/photo/${task.processDefinitionId}/${task.executionId}">跟踪</a></td>
				</c:if>
				<c:if test="${empty task}">
					<td>已结束</td>
					<td>&nbsp;</td>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>