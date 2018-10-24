<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>通讯录</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;
        var $table; // 父页面table表格id
        var $topIndex;//弹出窗口的 index
        function doSubmit(table, index) {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if (validateForm.form()) {
                $table = table;
                $topIndex = index;
                jp.loading();
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function () {
            //$("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function (form) {
                    // form.submit();
                    jp.post("${ctx}/oa/mailList/save", $('#inputForm').serialize(), function (data) {
                        if (data.success) {
                            $table.bootstrapTable('refresh');
                            jp.success(data.msg);

                        } else {
                            jp.error(data.msg);
                        }

                        jp.close($topIndex);//关闭dialog
                    });
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
        });
    </script>
</head>
<body class="bg-white">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="mailList" action="${ctx}/oa/mailList/save"
                               method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
                                <td><form:input path="name" htmlEscape="false" maxlength="100"
                                                class="form-control"/></td>

                            </tr>
                            <tr>
                                <td class="active"><label class="pull-right"><font color="red">*</font>性别:</label></td>
                                <td><form:select path="sex"  class="form-control">
                                        <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select></td>
                            </tr>
                            <tr>
                                <td class="active"><label class="pull-right"><font color="red">*</font>部门:</label></td>
                                <td><sys:treeselect id="orgId" name="orgId" value="${mailList.orgId}" labelName="mailList.orgId" labelValue="${mailList.orgName}"
                                                    allowClear="true" title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="true"/></td>

                            </tr>
                            <tr>
                                <td class="active"><label class="pull-right"><font color="red">*</font>地址:</label></td>
                                <td><form:input path="address" htmlEscape="false" maxlength="100"
                                                class="form-control"/></td>
                            </tr>
                            <tr>
                                <td class="active"><label class="pull-right"><font color="red">*</font>电话:</label></td>
                                <td><form:input path="phone" htmlEscape="false" maxlength="100"
                                                class="form-control"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
