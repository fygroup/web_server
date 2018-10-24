<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>resource管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var managetType="1";

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
		});

		function setManagerType(obj) {
            if($(obj).is(':checked')) {
                $("#managerTypeDiv").html("");
                if($(obj).val()=="1"||$(obj).val()=="2"){
                    managetType="1";
                    $("#managerTypeDiv").html($("#v12").html());
				}else if($(obj).val()=="3"){
                    managetType="3";
                    $("#managerTypeDiv").html($("#v3").html());
				}else if($(obj).val()=="4"){
                    $("#managerTypeDiv").html($("#accessConfig").html());
                    managetType="4";
                }
            }
        }

        function accessConfigType(obj) {
            if($(obj).is(':checked')) {
                if($(obj).val()=="1"){
                    $("#accessConfigPort").val("23");
                }else if($(obj).val()=="2"){
                    $("#accessConfigPort").val("24");
                }

            }
        }

        function showAuthenticationProtocol(obj) {
            if($(obj).val() == "1"){

            }else{
			}

        }

        function showOperatingSystem(id) {
            $("#resourceTypeId").val(id);
            $("#operatingSystemDiv").empty();
                $.get("${ctx}/operatingsystem/operatingSystem/selectOperatingSystem",{id:id},function(msg){
                    $.each(msg,function(key,value){
                        if(key==0){
                            $("#operatingSystemDiv").html($("#operatingSystemDivContext").html());
						}
                        $("#operatingSystemId").append('<option value="'+value.id+'">'+value.name+'</option>');
                    })
                });
        }

        function addRow(list, idx, tpl, row){
            $(list).append(Mustache.render(tpl, {
                idx: idx, delBtn: true, row: row
            }));
            $(list+idx).find("select").each(function(){
                $(this).val($(this).attr("data-value"));
            });
            $(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
                var ss = $(this).attr("data-value").split(',');
                for (var i=0; i<ss.length; i++){
                    if($(this).val() == ss[i]){
                        $(this).attr("checked","checked");
                    }
                }
            });
            $(list+idx).find(".form_datetime").each(function(){
                $(this).datetimepicker({
                    format: "YYYY-MM-DD HH:mm:ss"
                });
            });
        }
        function delRow(obj, prefix){
            var id = $(prefix+"_id");
            var delFlag = $(prefix+"_delFlag");
            if (id.val() == ""){
                $(obj).parent().parent().remove();
            }else if(delFlag.val() == "0"){
                delFlag.val("1");
                $(obj).html("&divide;").attr("title", "撤销删除");
                $(obj).parent().parent().addClass("error");
            }else if(delFlag.val() == "1"){
                delFlag.val("0");
                $(obj).html("&times;").attr("title", "删除");
                $(obj).parent().parent().removeClass("error");
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
				<a class="panelButton" href="${ctx}/resource/resource/application"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resource" action="${ctx}/resource/resource/applicationFormSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>资源类型：</label>
				<div class="col-sm-8">
					<sys:resourceselect id="resourceType" name="resourceType.id" value="${resource.resourceType.id}" labelName="" labelValue="${resource.resourceType.name}"
									title="资源类型" url="/resourcetype/resourceType/treeDataByCode?code=8"  cssClass="form-control required" notAllowSelectParent="true"/>
					<input id="resourceTypeId" value="" type="hidden">
				</div>
			</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>资源名称：</label>
					<div class="col-sm-8">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>所在服务器IP：</label>
				<div class="col-sm-8">
					<form:input path="ip" htmlEscape="false" id="ip"   class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>应用Url：</label>
				<div class="col-sm-8">
					<form:input path="url" htmlEscape="false" id="url"   class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>应用级别：</label>
				<div class="col-sm-8">
					<select class="form-control" id="applicationLevel" name="applicationLevel">
						<option value="1" selected>核心应用</option>
						<option value="2">重要应用</option>
						<option value="3">一般应用</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">数据库服务器IP：</label>
				<div class="col-sm-8">
					<input name="databaseIp" htmlEscape="false" id="databaseIp"   class="form-control "/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-2 control-label">中间件IP：</label>
				<div class="col-sm-8">
					<input name="middlewareIp" htmlEscape="false" id="middlewareIp"   class="form-control "/>
				</div>
			</div>

			<div class="tabs-container">
				<div id="tab-3" class="tab-pane fade in  active">
					<a class="btn btn-white btn-sm" onclick="addRow('#applicationIndicatorList', testDataChildRowIdx, testDataChildTpl);testDataChildRowIdx = testDataChildRowIdx + 1;" title="新增指标"><i class="fa fa-plus"></i> 新增指标</a>
					<table class="table table-striped table-bordered table-condensed">
						<thead>
						<tr>
							<th class="hide"></th>
							<th>指标名称</th>
							<th>指标单位</th>
							<th>URL链接</th>
							<th>类型</th>
							<th width="10">&nbsp;</th>
						</tr>
						</thead>
						<tbody id="applicationIndicatorList">
						</tbody>
					</table>
					<script type="text/template" id="testDataChildTpl">//<!--
				<tr id="applicationIndicatorList{{idx}}">
					<td class="hide">
                         <input id="applicationIndicatorList{{idx}}_id"  type="hidden" value="{{row.id}}"/>
					</td>
					<td  class="max-width-250">
						<input  name="applicationIndicatorList[{{idx}}].indicatorName" type="text" value=""    class="form-control required"/>
					</td>
					<td  class="max-width-250">
						<input  name="applicationIndicatorList[{{idx}}].unit" type="text" value=""    class="form-control "/>
					</td>


					<td  class="max-width-250">
						<input  name="applicationIndicatorList[{{idx}}].url" type="text" value=""    class="form-control required"/>
					</td>

					<td  class="max-width-250" >

				         <select name="applicationIndicatorList[{{idx}}].type"  class="form-control required">
							 <c:forEach items="${typeList}" var="type">
								  <option value="${type.value}"  > ${type.label}</option>
							  </c:forEach>
							  </select>
					</td>


					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#applicationIndicatorList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
					</script>
					<script type="text/javascript">
                        var testDataChildRowIdx = 0, testDataChildTpl = $("#testDataChildTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					</script>
				</div>
			</div>

				<%--<div class="form-group">
					<c:if test="${fns:hasPermission('resource:resource:edit') || isAdd}">
					<div class="col-sm-2"></div>
					<div class="col-sm-8">
						<div class="form-group text-center">
							<div>
								<button  class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在保存，请稍后...">保 存</button>

							</div>
						</div>
					</div>
					</c:if>
				</div>--%>
			<div class="form-group">
				<c:if test="${fns:hasPermission('resource:resource:edit') || isAdd}">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-8">
						<button  class="btn-primary form-control" data-loading-text="正在发现，请稍后...">保 存</button>
					</div>
				</c:if>
			</div>
		</form:form>



			</div>
		</div>				
	</div>
	</div>
</div>
</div>





</body>
</html>