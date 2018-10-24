<%--
  Created by IntelliJ IDEA.
  User: PC_DZY
  Date: 2018/4/9
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>设备管理列表</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp" %>
    <%@include file="/webpage/include/treeview.jsp" %>
    <%@include file="deviceApplyList.js" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">设备管理列表</h3>
        </div>
        <div class="panel-body">
            <sys:message content="${message}"/>

            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form:form id="searchForm" modelAttribute="deviceApply"
                                   class="form form-horizontal well clearfix">

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="设备名称">设备名称</label>
                                <form:input path="deviceId" htmlEscape="false" maxlength="200"
                                            class=" form-control input-sm"/>
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="机构">机构</label>
                                <form:input path="appDepartment" htmlEscape="false" maxlength="200"
                                            class=" form-control input-sm"/>
                            </div>

                            <%--<div class="col-xs-12 col-sm-6 col-md-4">--%>
                            <%--<label class="label-item single-overflow pull-left" title="是否审核">是否审核</label>--%>
                            <%--&lt;%&ndash;<form:input path="isaudit" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>&ndash;%&gt;--%>
                            <%--</div>--%>
                            <%--<div class="col-sm-4">--%>
                            <%--<select name="isaudit"  htmlEscape="false" maxlength="200"--%>
                            <%--class="form-control input-sm">--%>
                            <%--<option value="0">未审核</option>--%>
                            <%--<option value="1">审核通过</option>--%>
                            <%--</select>--%>
                            <%--</div>--%>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="是否审核：">是否审核：</label>
                                <form:select path="isaudit" class="form-control required">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('isaudit')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="是否审核：">是否交付：</label>
                                <form:select path="isdelivery" class="form-control required">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('isdelivery')}" itemLabel="label"
                                                  itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div style="margin-top:26px">
                                    <a id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                            class="fa fa-search"></i> 查询</a>
                                    <a id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i
                                            class="fa fa-refresh"></i> 重置</a>
                                </div>
                            </div

                        </form:form>
                    </div>
                </div>
            </div>

            <!-- 工具栏 -->
            <div id="toolbar" class="treetable-bar">
                <shiro:hasPermission name="oa:deviceApply:view">
                    <button id="view" class="btn btn-success" disabled onclick="view()">
                        <i class="glyphicon glyphicon-eye-open"></i>查看
                    </button>
                </shiro:hasPermission>|

                <shiro:hasPermission name="oa:deviceApply:add">
                    <a id="add" class="btn btn-primary" href="${ctx}/oa/deviceApply/form" title="新建申请"><i
                    class="glyphicon glyphicon-plus"></i>新建申请</a>
                </shiro:hasPermission>

                <shiro:hasPermission name="oa:deviceApply:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>

                <shiro:hasPermission name="oa:deviceApply:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                </shiro:hasPermission>

                |<shiro:hasPermission name="oa:deviceApply:audit">
                <button id="audit" class="btn btn-success" disabled onclick="audit()">
                    <i class="glyphicon glyphicon-check"></i> 审核通过
                </button>
            </shiro:hasPermission>

                <shiro:hasPermission name="oa:deviceApply:delivery">
                    <button id="delivery" class="btn btn-success" disabled onclick="delivery()">
                        <i class="glyphicon glyphicon-ok-sign"></i> 交付
                    </button>
                </shiro:hasPermission>|

                <shiro:hasPermission name="oa:deviceApply:import">
                    <button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
                    <div id="importBox" class="hide">
                        <form id="importForm" action="${ctx}/oa/deviceApply/import" method="post"
                              enctype="multipart/form-data"
                              style="padding-left:20px;text-align:center;"><br/>
                            <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
                        </form>
                    </div>
                </shiro:hasPermission>

                <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2"
                   href="#collapseTwo">
                    <i class="fa fa-search"></i> 检索
                </a>
            </div>

            <!-- 表格 -->
            <table id="table" data-toolbar="#toolbar"></table>

        </div>
    </div>
</div>
</body>
</html>
