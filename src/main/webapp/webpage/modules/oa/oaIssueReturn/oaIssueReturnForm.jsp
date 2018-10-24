<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<title>问题申报</title>
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


        $(function () {
                       $("input[name='problemClass']").click(function () {
                               if ($(this).val() == "3") {
                                        $("#usermsg").hide();
                                   } else {
                                        $("#usermsg").show();
                                    }
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
					<form:form id="inputForm" modelAttribute="oaIssueReturn" action="${ctx}/oa/oaIssueReturn/save" method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>
						<%--<form:hidden path="user_id" value="${fns:getUser().id}" htmlEscape="false" maxlength="200" class="form-control"/>--%>
						<%--<div class="form-group">--%>
							<%--<label class="col-sm-2 control-label"><font color="red">*</font>申报时间：</label>--%>
							<%--<div class="col-sm-10">--%>
								<%--<p class="input-group">--%>
								<%--<div class='input-group form_datetime' id='date'>--%>
									<%--<input type='text'  name="date" class="form-control required"  value="<fmt:formatDate value="${oaIssueReturn.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>--%>
									<%--<span class="input-group-addon">--%>
										<%--<span class="glyphicon glyphicon-calendar"></span>--%>
									<%--</span>--%>
								<%--</div>--%>
								<%--</p>--%>
							<%--</div>--%>
						<%--</div>--%>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>申报人：</label>
							<div class="col-sm-10">
								<sys:gridselect url="${ctx}/oa/oaIssueReturn/maildata" id="user_id" name="mailList.id" value="${oaIssueReturn.mailList.id}" labelName="oaIssueReturn.name" labelValue="${oaIssueReturn.name}"
												title="申报人"  cssClass="form-control required" fieldLabels="姓名|地址" fieldKeys="name|address" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>申报地点：</label>
							<div class="col-sm-10">
									<sys:gridselect url="${ctx}/oa/oaIssueReturn/maildata" id="place" name="mailList.address" value="${oaIssueReturn.mailList.address}" labelName="mailList.address" labelValue="${oaIssueReturn.place}"
													title="申报地点-(“请选择对应的申报人地址!”)"  cssClass="form-control required" fieldLabels="姓名|地址" fieldKeys="name|address" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">详细地址：</label>
							<div class="col-sm-10">
								<form:input path="detailplace" htmlEscape="false" maxlength="200" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>问题来源：</label>
							<div class="col-sm-10">
								<%--<form:select path="ediid" class="form-control required">--%>
									<%--<form:option value="" label=""/>--%>
									<%--<option value="0" label="系统提交"/>--%>
									<%--<option value="1" label="巡检"/>--%>
									<%--<option value="2" label="其他"/>--%>
									<%--<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
								<%--</form:select>--%>
									<form:select path="ediid" class="form-control required">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('oa_issueReturn_ediid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>申报类型：</label>
							<div class="col-sm-10">
								<%--<form:select path="declaretype" class="form-control required">--%>
									<%--<form:option value="" label=""/>--%>
									<%--<option value="0" label="软件"/>--%>
									<%--<option value="1" label="硬件"/>--%>
									<%--<option value="2" label="网络"/>--%>
									<%--&lt;%&ndash;<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>&ndash;%&gt;--%>
								<%--</form:select>--%>
									<form:select path="declaretype" class="form-control required">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('oa_issueReturn_declaretype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>问题等级：</label>
							<div class="col-sm-10">
								<%--<form:radiobuttons  path="problemClass" items="${fns:getDictList('exception_class')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required" onclick=""/>--%>
								<form:radiobuttons path="problemClass" items="${fns:getDictList('exception_class')}"
													                                itemLabel="label" itemValue="value"
													                                htmlEscape="false" class="" onclick=""/>
							</div>
						</div>



						<%--<div class="form-group">--%>
							<%--<label class="col-sm-2 control-label"><font color="red">*</font>问题状态：</label>--%>
							<%--<div class="col-sm-10">--%>
								<%--&lt;%&ndash;<form:select path="problemstate" class="form-control required">&ndash;%&gt;--%>
									<%--&lt;%&ndash;<form:option value="" label=""/>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<option value="0" label="创建"/>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<option value="1" label="派单"/>&ndash;%&gt;--%>
									<%--&lt;%&ndash;<option value="2" label="完成"/>&ndash;%&gt;--%>
									<%--&lt;%&ndash;&lt;%&ndash;<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>&ndash;%&gt;&ndash;%&gt;--%>
								<%--&lt;%&ndash;</form:select>&ndash;%&gt;--%>
									<%--<form:select path="problemstate" class="form-control required">--%>
										<%--<form:option value="" label=""/>--%>
										<%--<form:options items="${fns:getDictList('oa_issueReturn_problemstate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
									<%--</form:select>--%>
							<%--</div>--%>
						<%--</div>--%>
						<form:hidden path="problemstate" title="问题状态默认创建" value="0"/>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>问题描述：</label>
							<div class="col-sm-10">
								<form:textarea path="description" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/>
							</div>
						</div>

						<div class="form-group" id="usermsg">
							<label class="col-sm-2 control-label"><font color="red">*</font>处理人员：</label>
							<div class="col-sm-10">
								<%--<sys:opsselect id="user" name="user.id" value="${oaIssueReturn.conductorid}" labelName="oaIssueReturn.user.name" labelValue="${oaIssueReturn.conductorid}"--%>
												<%--cssClass="form-control required"  isMultiSelected="true"/>--%>

								<sys:opsselect id="user" name="user.id" value="${oaIssueReturn.user.id}" labelName="oaIssueReturn.opsname" labelValue="${oaIssueReturn.opsname}"
												cssClass="form-control required"  isMultiSelected="false"/>
							</div>
						</div>
						<shiro:hasPermission name="oa:oaOutRecords:edit">
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
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>