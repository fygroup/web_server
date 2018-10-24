<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>解决方案</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">
        var validateForm;
        var $table; // 父页面table表格id
        var $topIndex;//弹出窗口的 index
        function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                $table = table;
                $topIndex = index;
                jp.loading();
                $("#inputForm").submit();
                return true;
            }

            return false;
        }

        $(document).ready(function(){
            //$("#name").focus();

            validateForm= $("#inputForm").validate({
                submitHandler: function(form){
                    jp.post("${ctx}/oa/oaDispose/save",$('#inputForm').serialize(),function(data){
                        if(data.success){
                            $table.bootstrapTable('refresh');
                            jp.success(data.msg);

                        }else{
                            jp.error(data.msg);
                        }

                        jp.close($topIndex);//关闭dialog
                    })
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
        });

	</script>
</head>
<body class="bg-white">
<form:form id="inputForm" modelAttribute="oaDispose" autocomplete="off" action="${ctx}/oa/oaDispose/save" method="post" class="form-horizontal" >
	<form:hidden path="id"/>
	<input type="hidden" id="id" value="${oaDispose.id}">
	<input type="hidden" id="user.id" name="user_id" value="${oaDispose.user.id}">
	<input type="hidden" id="declareId" value="${oaDispose.declareId}">
	<sys:message content="${message}"/>
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>

			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>解决方案:</label></td>
			<td class="width-35"><form:textarea path="plan" htmlEscape="false" rows="5" maxlength="400" class="form-control required"/></td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right"><font color="red">*</font>问题原因:</label></td>
			<td class="width-35"><form:textarea path="cause" htmlEscape="false" rows="3" maxlength="400" class="form-control required"/></td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">备注:</label></td>
			<td class="width-35"><form:textarea path="remark" htmlEscape="false" rows="3" maxlength="400" class="form-control "/></td>
		</tr>
		</tbody>
	</table>

</form:form>
</body>
</html>