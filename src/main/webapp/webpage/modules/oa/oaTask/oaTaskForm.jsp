<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>任务安排</title>
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

            $('#startTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#endTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });

        });
	</script>
</head>
<body>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">
					<a class="panelButton" href="${ctx}/oa/oaTask"><i class="ti-angle-left"></i> 返回</a>
				</h3>
			</div>
<form:form id="inputForm" modelAttribute="oaTask" autocomplete="off" action="${ctx}/oa/oaTask/save" method="post" class="form-horizontal" >
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>任务名称：</label>
		<div class="col-sm-10">
			<form:input path="taskName" htmlEscape="false" maxlength="200" class="form-control required"/>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>参与人员：</label>
		<div class="col-sm-10">
				<sys:userselect id="user" name="user.id" value="${oaTask.user.id}" labelName="oaTask.name" labelValue="${oaTask.user.id}"
							cssClass="form-control required"  isMultiSelected="true"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>开始日期：</label>
		<div class="col-sm-10">
			<p class="input-group">
			<div class='input-group form_datetime' id='startTime'>

				<input type='text'  name="startTime" class="form-control required"  value="<fmt:formatDate value="${oaTask.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<span class="input-group-addon">
				<span class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
			</p>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>结束日期：</label>
		<div class="col-sm-10">
			<p class="input-group">
			<div class='input-group form_datetime' id='endTime'>

				<input type='text'  name="endTime" class="form-control required"  value="<fmt:formatDate value="${oaTask.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
				<span class="input-group-addon">
				<span class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
			</p>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>任务描述：</label>
		<div class="col-sm-10">
			<form:textarea path="description" htmlEscape="false" rows="6" maxlength="1000" class="form-control required"/>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">备注：</label>
		<div class="col-sm-10">
			<form:textarea path="remark" htmlEscape="false" rows="6" maxlength="1000" class="form-control"/>
		</div>
	</div>
	<shiro:hasPermission name="oa:oaTask:edit">
		<div class="col-lg-3"></div>
		<div class="col-lg-6">
			<div class="form-group text-center">
				<label></label>

				<div>
					<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
				</div>
			</div>
		</div>
	</shiro:hasPermission>

</form:form>
</body>
</html>