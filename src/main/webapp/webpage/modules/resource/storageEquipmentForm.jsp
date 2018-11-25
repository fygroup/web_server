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
                        /*$.ajax({
                            url:"${ctx}/resource/resource/checkIp",
                            type:'POST', //
                            async:true,    //或false,是否异步
                            data:{
                                type:managetType,ip:$("#ip").val(),port:$("#port").val(),rdcommunity:$("#rdcommunity").val(),resourceType:$("#resourceTypeId").val()
                            },
                            timeout:100*1000,    //超时时间 100s
                            dataType:'json',    //返回的数据格式：
                            success:function(data,textStatus,jqXHR){
                                if(data[0]["status"]==200){
                                    form.submit();
                                }else if(data[0]["status"]==401){
                                    alert("设备已存在");
                                    jp.close();
                                }else if(data[0]["status"]==402){
                                    alert("管理方式错误");
                                    jp.close();
                                }else{
                                    alert("添加设备失败");
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

	</script>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<a class="panelButton" href="${ctx}/resource/resource/storageEquipmentList"><i class="ti-angle-left"></i> 返回</a>
					</h3>
				</div>
				<div class="panel-body">
					<form:form id="inputForm" modelAttribute="resource" action="${ctx}/resource/resource/storageEquipmentFormSave" method="post" class="form-horizontal">
						<form:hidden path="id"/>
						<sys:message content="${message}"/>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>资源类型：</label>
							<div class="col-sm-8">
									<%--	<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="" labelValue="${resource.company.name}"
                                                        title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
                    --%>
								<sys:resourceselect id="resourceType" name="resourceType.id" value="${resource.resourceType.id}" labelName="" labelValue="${resource.resourceType.name}"
													title="资源类型" url="/resourcetype/resourceType/treeDataByCode?code=7"  cssClass="form-control required" notAllowSelectParent="true"/>

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
								<form:input path="model" htmlEscape="false" id="ip"   class="form-control "/>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>归属法院：</label>
							<div class="col-sm-8">
								<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="company" labelValue="${resource.company.name}"
												title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
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
						<input type="hidden" name="managerType" value="1"  checked="checked" onchange="setManagerType(this)">
					<%--	<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>管理方式：</label>

							<div class="col-sm-8">
								<input type="radio" name="managerType" value="1"  checked="checked" onchange="setManagerType(this)"> SNMPV1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="managerType" value="2" onchange="setManagerType(this)"> SNMPV2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

									&lt;%&ndash;<input type="radio" name="managerType" value="3" onchange="setManagerType(this)"> SNMPV3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="managerType" value="4" onchange="setManagerType(this)"> 访问配置
                                &ndash;%&gt;
							</div>

						</div>--%>

						<div id="managerTypeDiv">
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>端口：</label>
								<div class="col-sm-8">
									<input name="port"   class="form-control  isIntGtZero required" value="161"/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>读共同体：</label>
								<div class="col-sm-8">
									<input name= "rdcommunity" htmlEscape="false"   id="rdcommunity"  class="form-control required"/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>延时（毫秒）：</label>
								<div class="col-sm-8">
									<input name= "delay" htmlEscape="false"    class="form-control required isIntGtZero" value="${resourceBaseInfo.delay}"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><font color="red">*</font>重试次数：</label>
								<div class="col-sm-8">
									<input name="repeatnum" htmlEscape="false"    class="form-control required isIntGtZero" value="${resourceBaseInfo.repeatnum}"/>
								</div>
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
								<label class="col-sm-2"></label>
								<div class="col-sm-8">
									<div class=" text-center">
										<div>
											<button  class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在发现，请稍后...">保 存</button>

										</div>
									</div>
								</div>
							</c:if>
						</div>

					</form:form>



					<div id="v12" style="display: none">

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>端口：</label>
							<div class="col-sm-8">
								<input name="port"  id="port" class="form-control  isIntGtZero required" value="161"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>读共同体：</label>
							<div class="col-sm-8">
								<input name= "rdcommunity" htmlEscape="false" id="rdcommunity"   class="form-control required"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>延时（毫秒）：</label>
							<div class="col-sm-8">
								<input name= "delay" htmlEscape="false"    class="form-control required isIntGtZero"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>重试次数：</label>
							<div class="col-sm-8">
								<input name=repeatnum" htmlEscape="false"    class="form-control required isIntGtZero"/>
							</div>
						</div>
					</div>


					<div  id="v3" style="display: none">

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>端口：</label>
							<div class="col-sm-8">
								<input name="port"   class="form-control  isIntGtZero required" value="161"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>延时（毫秒）：</label>
							<div class="col-sm-8">
								<input name= "delay" htmlEscape="false"    class="form-control required isIntGtZero"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>重试次数：</label>
							<div class="col-sm-8">
								<input name=repeatnum" htmlEscape="false"    class="form-control required isIntGtZero"/>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label"><font color="red">*</font>用户名：</label>
							<div class="col-sm-8">
								<input name="userName " htmlEscape="false"    class="form-control required"/>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">安全级别：</label>
							<div class="col-sm-8">
								<select name="securityLevel" id="securityLevel" onchange="showAuthenticationProtocol(this)" class="form-control" >
									<c:forEach items="${securityLevel}" var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
								</select>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label">认证协议：</label>
							<div class="col-sm-8" >
								<select name="authenticationProtocol" id="authenticationProtocol"  class="form-control" >

								</select>

								<%--	<form:select path=""  class="form-control">
                                        <form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                    </form:select>--%>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">认证密码：</label>
							<div class="col-sm-8">
								<input name="authenticationPassword"   class="form-control required">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">加密协议：</label>
							<div class="col-sm-8">
								<%--	<form:select path=""  class="form-control">
                                        <form:options items="${fns:getDictList('encryption_protocol')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                    </form:select>--%>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">加密密码：</label>
							<div class="col-sm-8">
								<input name="encryptionPassword"   class="form-control required">
							</div>
						</div>


					</div>


					<div  id="accessConfig" style="display: none">

						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-8">
								<input name="protocol" value="1" type="radio" checked="checked" onchange="accessConfigType(this)"> Telnet&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="protocol" type="radio" value="2"  onchange="accessConfigType(this)"> SSH
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">访问端口：</label>
							<div class="col-sm-8">
								<input value="23" name="accessConfigPort" id="accessConfigPort" class="form-control required">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">访问用户名：</label>
							<div class="col-sm-8">
								<input name= "accessConfigUserName" htmlEscape="false"    class="form-control "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">访问密码：</label>
							<div class="col-sm-8">
								<input name= "accessConfigPassword" htmlEscape="false"    class="form-control "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"> 特权模式口令：</label>
							<div class="col-sm-8">
								<input name="privilegedModeCommand" htmlEscape="false"    class="form-control "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">特权模式密码：</label>
							<div class="col-sm-8">
								<input name="privilegedModePassword" htmlEscape="false"    class="form-control "/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">命令提示符：</label>
							<div class="col-sm-8">
								<input name="commandPrompt" htmlEscape="false"    class="form-control "/>
							</div>
						</div>
					</div>




					<div class="form-group" id="operatingSystemDivContext" style="display: none">
						<label class="col-sm-2 control-label">操作系统：</label>
						<div class="col-sm-8">
							<select name="operatingSystemId" id="operatingSystemId" class="form-control" >

							</select>
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>