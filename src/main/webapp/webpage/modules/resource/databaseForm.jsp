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

				    if(managetType=="1"){
                       jp.loading();
                        form.submit();

                       /* $.ajax({
                            url:"${ctx}/resource/resource/checkDatabaseIp",
                            type:'POST', //
                            async:true,    //或false,是否异步
                            data:{
                                ip:$("#ip").val(),port:$("#accessConfigPort").val(),accessConfigUserName:$("#accessConfigUserName").val(),accessConfigPassword:$("#accessConfigPassword").val(),resourceType:$("#resourceTypeId").val()
                            },
                            timeout:100*1000,    //超时时间 100s
                            dataType:'json',    //返回的数据格式：
                            success:function(data,textStatus,jqXHR){
                                if(data[0]["status"]==200){
                                    form.submit();
                                }else if(data[0]["status"]==401){
                                    alert("数据库已存在");
                                    jp.close();
                                }else if(data[0]["status"]==402){
                                    alert("数据库连接错误");
                                    jp.close();
                                }else{
                                    alert("添加数据库失败");
                                    jp.close();
                                }
                            },
                            error:function(xhr,textStatus){
                                jp.close();
                            },
                            complete:function(XMLHttpRequest,status){
                                if(status=='timeout'){
                                    jp.close();
                                    alert("请求超时，请检查网络后重试");
                                }
                            }
                        })*/
				    }else{
                        alert("敬请期待");
                    }

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

	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/resource/resource/databaseList"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resource" action="${ctx}/resource/resource/databaseFormSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>资源类型：</label>
				<div class="col-sm-8">
				<%--	<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="" labelValue="${resource.company.name}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
--%>
					<sys:resourceselect id="resourceType" name="resourceType.id" value="${resource.resourceType.id}" labelName="" labelValue="${resource.resourceType.name}"
									title="资源类型" url="/resourcetype/resourceType/treeDataByCode?code=4"  cssClass="form-control required" notAllowSelectParent="true"/>

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
				<label class="col-sm-2 control-label"><font color="red">*</font>IP：</label>
				<div class="col-sm-8">
					<form:input path="ip" htmlEscape="false" id="ip"   class="form-control required"/>
				</div>
			</div>

			<div class="form-group" >
				<label class="col-sm-2 control-label"><font color="red">*</font>厂商：</label>
				<div class="col-sm-8">
					<select name="manufacturer" id="manufacturer" class="form-control" >
						<c:forEach items="${manufacturerList}" var="item" varStatus="index">
							    <option value="${item.id}">${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group" id="operatingSystemDiv">

			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label">型号：</label>
				<div class="col-sm-8">
					<form:input path="model" htmlEscape="false" id="ip"   class="form-control"/>
				</div>
			</div>

			<div class="form-group" >
				<label class="col-sm-2 control-label"><font color="red">*</font>所属采集器：</label>
				<div class="col-sm-8">
					<select name="mokaCollector.id"  class="form-control" >
						<c:forEach items="${mokaCollectorList}" var="item" varStatus="index">
							<option value="${item.id}" <c:if test="${item.id == mokaCollector.id}"> selected="selected"</c:if>>${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>归属公司：</label>
				<div class="col-sm-8">
					<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="company" labelValue="${resource.company.name}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>


			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>访问端口：</label>
				<div class="col-sm-8">
					<input value="" name="accessConfigPort" id="accessConfigPort" class="form-control required">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>访问用户名：</label>
				<div class="col-sm-8">
					<input id="accessConfigUserName" name= "accessConfigUserName" htmlEscape="false"    class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>访问密码：</label>
				<div class="col-sm-8">
					<input id="accessConfigPassword" name= "accessConfigPassword" htmlEscape="false"    class="form-control required"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">描述：</label>
				<div class="col-sm-8">
					<form:input path="description" htmlEscape="false"    class="form-control  isIntGtZero"/>
				</div>
			</div>
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
</body>
</html>