<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>进出记录</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;

        function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            //$("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {

                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            $('#entryTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#departureTime').datetimepicker({
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
                        <a class="panelButton" href="${ctx}/oa/turnover"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="turnover" action="${ctx}/oa/turnover/saveAudit" method="post"
                               class="form-horizontal">
                        <form:hidden path="id"/>
                        <form:hidden path="act.taskId"/>
                        <form:hidden path="act.taskName"/>
                        <form:hidden path="act.taskDefKey"/>
                        <form:hidden path="act.procInsId"/>
                        <form:hidden path="act.procDefId"/>
                        <form:hidden id="flag" path="act.flag"/>
                        <sys:message content="${message}"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>单位名称：</label>
                            <div class="col-sm-10">
                                <form:input path="units" htmlEscape="false" maxlength="200"
                                            class="form-control required" readonly="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>人员名称：</label>
                            <div class="col-sm-10">
                                <form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"
                                            readonly="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>进入事由：</label>
                            <div class="col-sm-10">
                                <form:textarea path="reason" class="form-control" rows="5" readonly="true"
                                               maxlength="20"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>进入时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='entryTime'>
                                    <input type='text' name="entryTime" class="form-control" readonly="readonly" maxlength="20"
                                           value="<fmt:formatDate value="${turnover.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                                </div>
                                </p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>离开时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='departureTime'>

                                    <input type='text' name="departureTime" readonly="readonly"
                                           maxlength="20" class="form-control"
                                           value="<fmt:formatDate value="${turnover.departureTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                                </div>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>审批意见：</label>
                            <div class="col-sm-10">
                                <form:textarea path="act.comment" class="form-control required" rows="5"
                                               maxlength="20"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label"></label>
                            <div class="col-sm-10">
                                <c:if test="${turnover.act.taskDefKey ne 'apply_end'}">
                                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意"
                                           onclick="$('#flag').val('yes')"/>&nbsp;
                                    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回"
                                           onclick="$('#flag').val('no')"/>&nbsp;
                                </c:if>
                            </div>
                        </div>

                        <act:flowChart procInsId="${turnover.act.procInsId}"/>
                        <act:histoicFlow procInsId="${turnover.act.procInsId}"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>