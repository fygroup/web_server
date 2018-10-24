<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>异常告警管理</title>
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

            $('#firstTriggerTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#lastTriggerTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
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
                        <a class="panelButton" href="${ctx}/oa/oaIssueReturn"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="resourceException" action="${ctx}/exception/exception/resourceException/save" method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="fixed-table-body">
                            <h4>异常明细</h4>
                            <table class="table" >
                                <tbody>
                                <tr>
                                    <td class="tit">异常名称</td><td class="td-hover">${resourceException.indicatorName}</td>
                                    <td class="tit">异常来源</td><td class="td-hover">${resourceException.exceptionSource}</td>
                                </tr>
                                <tr>
                                    <td class="tit">累计次数</td><td class="td-hover">${resourceException.totalQuantity}</td>
                                    <td class="tit">异常等级</td><td class="td-hover">${fns:getDictLabels(resourceException.exceptionClass,'exception_class', "-")}</td>
                                </tr>
                                <tr>
                                    <td class="tit">当前指标值</td><td class="td-hover">${resourceException.currentValue}${resourceException.indicatorItem.unit}</td>
                                    <td class="tit">当前状态</td><td class="td-hover">${fns:getDictLabels(resourceException.currentStatus,'exception_current_status', "-")}</td>
                                </tr>
                                <tr>
                                    <td class="tit">第一次产生指标值</td><td class="td-hover">${resourceException.firstTriggerValue}${resourceException.indicatorItem.unit}</td>
                                    <td class="tit">指标名称</td><td class="td-hover">${resourceException.indicatorItem.name}</td>
                                </tr>
                                <tr>
                                    <td class="tit">最后一次产生异常的时间</td><td class="td-hover"><fmt:formatDate value="${resourceException.firstTriggerTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td class="tit">资源类型</td><td class="td-hover">${resourceException.resourceType.name}</td>
                                </tr>
                                <tr>
                                    <td class="tit">指标事件类型</td><td class="td-hover">${fns:getDictLabels(resourceException.eventType,'indicator_event_type', "-")}</td>
                                    <td class="tit">确认状态</td><td class="td-hover">${fns:getDictLabels(resourceException.confirmStatus,'exception_confirm_status', "-")}</td>
                                </tr>
                                <tr>
                                    <td class="tit">确认人</td><td class="td-hover">${resourceException.confirmUser.name}</td>
                                    <td class="tit">地域</td><td class="td-hover">${resourceException.area.name}</td>
                                </tr>
                                <tr>
                                    <td class="tit">归属部门</td><td class="td-hover">${resourceException.office.name}</td>
                              <%--      <td class="tit">资源列表项id</td><td class="td-hover">${resourceException.indicatorName}</td>--%>
                                    <td class="tit">备注信息</td><td class="td-hover" colspan="3">${resourceException.remarks}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <%--<div class="fixed-table-body">
                            <h4><普通Windows服务器内建模板></h4>
                            <table id="" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th>事件类型</th>
                                    <th>轮训周期</th>
                                    <th>异常过滤</th>
                                    <th>特急告警</th>
                                    <th>较急告警</th>
                                    <th>一般告警</th>
                                    <th>提示告警</th>
                                    <th>恢复告警</th>
                                    <th>告警过滤</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class=""></div>
                            <table id="" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                <thead>
                                <tr>
                                    <th>事件类型</th>
                                    <th>指标名称</th>
                                    <th>特急阈值</th>
                                    <th>较急阈值</th>
                                    <th>一般阈值</th>
                                    <th>提示阈值</th>
                                    <th>采集方式</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>--%>
                        <%--<div class="form-group">
                            <label class="col-sm-2 control-label">备注信息：</label>
                            <div class="col-sm-10">
                                <form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>异常来源：</label>
                            <div class="col-sm-10">
                                <form:input path="exceptionSource" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>指标名称：</label>
                            <div class="col-sm-10">
                                <form:input path="indicatorName" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>累计次数：</label>
                            <div class="col-sm-10">
                                <form:input path="totalQuantity" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>异常等级：</label>
                            <div class="col-sm-10">
                                <form:radiobuttons path="exceptionClass" items="${fns:getDictList('exception_class')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>当前指标值：</label>
                            <div class="col-sm-10">
                                <form:input path="currentValue" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>当前状态：</label>
                            <div class="col-sm-10">
                                <form:radiobuttons path="currentStatus" items="${fns:getDictList('exception_current_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>第一次产生指标值：</label>
                            <div class="col-sm-10">
                                <form:input path="firstTriggerValue" htmlEscape="false"    class="form-control required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>第一次产生级别：</label>
                            <div class="col-sm-10">
                                <form:radiobuttons path="firstTriggerClass" items="${fns:getDictList('exception_class')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>第一次产生时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                    <div class='input-group form_datetime' id='firstTriggerTime'>
                                        <input type='text'  name="firstTriggerTime" class="form-control"  value="<fmt:formatDate value="${resourceException.firstTriggerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>资源指标id：</label>
                            <div class="col-sm-10">
                                <sys:gridselect url="${ctx}/resourceindicatorlist/resourceIndicatorlist/data" id="resourceIndicator" name="resourceIndicator.id" value="${resourceException.resourceIndicator.id}" labelName="resourceIndicator.id" labelValue="${resourceException.resourceIndicator.id}"
                                     title="选择资源指标id" cssClass="form-control required" fieldLabels="" fieldKeys="" searchLabels="" searchKeys="" ></sys:gridselect>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">最后一次产生异常的时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                    <div class='input-group form_datetime' id='lastTriggerTime'>
                                        <input type='text'  name="lastTriggerTime" class="form-control"  value="<fmt:formatDate value="${resourceException.lastTriggerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>资源类型：</label>
                            <div class="col-sm-10">
                                <sys:treeselect id="resourceType" name="resourceType.id" value="${resourceException.resourceType.id}" labelName="resourceType.name" labelValue="${resourceException.resourceType.name}"
                                    title="资源类型（冗余）" url="/resourcetype/resourceType/treeData" extId="${resourceException.id}" cssClass="form-control required" allowClear="true"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>指标事件类型：</label>
                            <div class="col-sm-10">
                                <form:radiobuttons path="eventType" items="${fns:getDictList('indicator_event_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确认状态：</label>
                            <div class="col-sm-10">
                                <form:radiobuttons path="confirmStatus" items="${fns:getDictList('exception_confirm_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确认人：</label>
                            <div class="col-sm-10">
                                <sys:userselect id="confirmUser" name="confirmUser.id" value="${resourceException.confirmUser.id}" labelName="confirmUser.name" labelValue="${resourceException.confirmUser.name}"
                                        cssClass="form-control "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>地域：</label>
                            <div class="col-sm-10">
                        <div class=" input-group" style=" width: 100%;">
                              <form:input path="area" htmlEscape="false"  class="required" data-toggle="city-picker" style="height: 34px;"/>
                        </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>归属部门：</label>
                            <div class="col-sm-10">
                                <sys:treeselect id="office" name="office.id" value="${resourceException.office.id}" labelName="office.name" labelValue="${resourceException.office.name}"
                                    title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">资源列表项id：</label>
                            <div class="col-sm-10">
                                <sys:gridselect url="${ctx}/indicator/indicator/data" id="indicatorItem" name="indicatorItem.id" value="${resourceException.indicatorItem.id}" labelName="indicatorItem.id" labelValue="${resourceException.indicatorItem.id}"
                                     title="选择资源列表项id" cssClass="form-control required" fieldLabels="" fieldKeys="" searchLabels="" searchKeys="" ></sys:gridselect>
                            </div>
                        </div>--%>
                        <%--<c:if test="${fns:hasPermission('exception:exception:resourceException:edit') || isAdd}">
                            <div class="form-group">
                                <div class="col-xs-2 col-lg-2 control-label"></div>
                                <div class="col-xs-6 col-sm-6">
                                     <div class="form-group text-center">
                                         <div>
                                             <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
                                         </div>
                                     </div>
                                </div>
                            </div>
                        </c:if>--%>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>