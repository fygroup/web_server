<%--
  Created by IntelliJ IDEA.
  User: PC_DZY
  Date: 2018/4/8
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>知识库</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;
        function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                $("#inputForm").submit();
                return true;
            }
            return false;
        }
        $(document).ready(function() {
            //$("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function(form){
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
            $('#date').datetimepicker({
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
                        <a class="panelButton" href="${ctx}/oa/knowledgeBase"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="knowledgeBase" action="${ctx}/oa/knowledgeBase/save" method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>问题原因：</label>
                            <div class="col-sm-10">
                                <form:input path="cause" htmlEscape="false" maxlength="200" class="form-control required"  readonly="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>申报方式：</label>
                            <div class="col-sm-10">
                                <form:select path="ediId" class="form-control required" disabled="true">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('oa_issueReturn_ediid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>申报类型：</label>
                            <div class="col-sm-10">
                                <form:select path="declareType" class="form-control required" disabled="true">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('oa_issueReturn_declaretype')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                                </form:select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>解决方案：</label>
                            <div class="col-sm-10">
                                <form:textarea path="plan" htmlEscape="false" rows="6" maxlength="2000" class="form-control required" readonly="true"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>问题描述：</label>
                            <div class="col-sm-10">
                                <form:textarea path="description" htmlEscape="false" rows="6" maxlength="2000" class="form-control required" readonly="true"/>
                            </div>
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
