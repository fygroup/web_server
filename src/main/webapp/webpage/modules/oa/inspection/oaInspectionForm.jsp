<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>巡检记录</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/summernote.jsp" %>
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
            $("#inputForm").validate({
                submitHandler: function(form){
                    jp.loading();
                    $("input[name='contents']").val($('#contents').summernote('code'));//取富文本的值
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
            //富文本初始化
            $('#contents').summernote({
                height: 400,
                lang: 'zh-CN'
            });

            $('#date').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
        });

        function aa(){
            // document.getElementById('tt').value=;
            //富文本初始化
            $('#contents').summernote({
                height: 400,
                lang: 'zh-CN'
            });
            // $('.summernote').code(document.getElementById('tt').value);
            $('#contents').summernote('code',document.getElementById('ss').value);
            //下拉框的名称赋值给隐藏域input
            var se =document.getElementById("ss");
            var option=se.getElementsByTagName("option");
            var str = "" ;
            for(var i=0;i<option.length;++i){
                if(option[i].selected){
                    document.getElementById("positionTypeName").value = option[i].text;
                }
            }
        }

	</script>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<a class="panelButton" href="${ctx}/oa/oaInspection"><i class="ti-angle-left"></i> 返回</a>
					</h3>
				</div>
				<div class="panel-body">
					<form:form id="inputForm" modelAttribute="oaInspection" action="${ctx}/oa/oaInspection/save" method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>检查人员：</label>
							<div class="col-sm-10">
								<sys:userselect id="user" name="user.id" value="${oaInspection.user.id}" labelName="oaInspection.name" labelValue="${oaInspection.name}"
												cssClass="form-control required"  isMultiSelected="true"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>巡检日期：</label>
							<div class="col-sm-10">
								<p class="input-group">
								<div class='input-group form_datetime' id='date'>

									<input type='text'  name="date" class="form-control required"  value="<fmt:formatDate value="${oaInspection.date}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
								</p>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>是否正常：</label>
							<div class="col-sm-10">
								<td class="width-35"><form:select path="isflag" class="form-control required">
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
								</td>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>巡检模板：</label>
							<div class="col-sm-10">
								<input type="hidden" id="positionTypeName" name="pagename" path="pagename"/>
								<td class="width-35">
									<form:select path="pagevalue" class="form-control required" id="ss" onchange="aa()">
									<form:options items="${fns:getDictList('reportTemplate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
								</td>
							</div>
						</div>

						<div class="form-group" >
							<label class="col-sm-2 control-label"><font color="red">*</font>报表内容：</label>
							<div class="col-sm-10">
								<input type="hidden" name="contents" id="tt"/>
								<div id="contents">
										${fns:unescapeHtml(oaInspection.contents)}
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">备注：</label>
							<div class="col-sm-10">
								<form:textarea path="remark" htmlEscape="false" rows="6" maxlength="1000" class="form-control"/>
							</div>
						</div>
						<c:if test="${fns:hasPermission('oa:oaInspection:edit') || isAdd}">
							<div class="col-lg-3"></div>
							<div class="col-lg-6">
								<div class="form-group text-center">
									<div>
										<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
									</div>
								</div>
							</div>
						</c:if>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
</html>