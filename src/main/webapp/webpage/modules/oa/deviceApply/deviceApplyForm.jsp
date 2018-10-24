<%--
  Created by IntelliJ IDEA.
  User: PC_DZY
  Date: 2018/4/8
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>设备申请</title>
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
            $('#appTime').datetimepicker({
                format: "YYYY-MM-DD"
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
                        <a class="panelButton" href="${ctx}/oa/deviceApply"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="deviceApply" action="${ctx}/oa/deviceApply/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>设备名称：</label>
                            <div class="col-sm-10">
                                <form:input path="deviceId" htmlEscape="false" maxlength="200"
                                            class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>申请人：</label>
                            <div class="col-sm-10">
                                <sys:userselect id="appUser" name="appUser" value="${deviceApply.appUser}"
                                                labelName="deviceApply.appUser" labelValue="${deviceApply.appName}"
                                                cssClass="form-control required" isMultiSelected="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>申请部门:</label>
                            <div class="col-sm-10">
                                <sys:treeselect id="appDepartment" name="appDepartment"
                                                value="${deviceApply.appDepartment}"
                                                labelName="deviceApply.appDepartment"
                                                labelValue="${deviceApply.departmentName}"
                                                allowClear="true" title="部门" url="/sys/office/treeData?type=2"
                                                cssClass="form-control required" notAllowSelectParent="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>申请地点：</label>
                            <div class="col-sm-10">
                                <form:input path="place" htmlEscape="false" maxlength="200"
                                            class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">申请原由：</label>
                            <div class="col-sm-10">
                                <form:textarea path="remark" htmlEscape="false" rows="6" maxlength="2000"
                                               class="form-control required"/>
                            </div>
                        </div>


                        <shiro:hasPermission name="oa:deviceApply:edit">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <label></label>
                                    <div>
                                        <button class="btn btn-primary btn-block btn-lg btn-parsley"
                                                data-loading-text="正在提交...">提 交
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </shiro:hasPermission>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
