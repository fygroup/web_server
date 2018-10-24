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
    <title>设备采购</title>
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
            $('#purTime').datetimepicker({
                format: "YYYY-MM-DD hh:mm"
            });
        });
    </script>
</head>
<body class="bg-white">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/oa/devicePurchase"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="devicePurchase" action="${ctx}/oa/devicePurchase/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>设备名称：</label>
                            <div class="col-sm-10">
                                <form:input path="devName" htmlEscape="false" maxlength="200"
                                            class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>设备来源：</label>
                            <div class="col-sm-10">
                                <form:input path="devSource" htmlEscape="false" maxlength="200"
                                            class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">采购人：</label>
                            <div class="col-sm-10">
                                <sys:userselect id="purUser" name="purUser" value="${devicePurchase.purUser}"
                                                labelName="devicePurchase.purUser"
                                                labelValue="${devicePurchase.purName}"
                                                cssClass="form-control required" isMultiSelected="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>采购时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='purTime'>
                                    <input type='text' name="purTime" class="form-control required"
                                           value="<fmt:formatDate value="${devicePurchase.purTime}" pattern="yyyy-MM-dd hh:mm"/>"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                </p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>采购金额：</label>
                            <div class="col-sm-10">
                                <form:input path="purMoney" htmlEscape="false" maxlength="200"
                                            class="form-control required"/>
                            </div>
                        </div>

                        <shiro:hasPermission name="oa:devicePurchase:edit">
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
