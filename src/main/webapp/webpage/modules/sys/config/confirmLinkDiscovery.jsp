<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>链路资源发现确认</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        var totalNum=0;
	$(document).ready(function(){
	     totalNum=$("#totalNum").val();
	});
	function  showOperatingSystem(id) {
    }

    function  saveResource(index) {
	    var view="#resourceView"+index;
        var upResourceId="#upResourceId"+index;
        var downResourceId="#downResourceId"+index;
        var upInterfaceId="#upInterface"+index;
        var downInterface="#downInterface"+index;
        var nameId="#resourceName"+index;
        var upIpId="#upIp"+index;
        var downIpId="#downIp"+index;

        var name=$(nameId).val().trim();       //资源名称
		var upResource=$(upResourceId).val();
        var downResource=$(downResourceId).val();
        var upInterface=$(upInterfaceId).val();
        var downInterface=$(downInterface).val();
        var upIp=$(upIpId).val();
        var downIp=$(downIpId).val();

            $.ajax({
                type : 'POST',
                url :  "${ctx}/resource/resource/confirmLinkDiscoverySave",
                timeout:10*1000,    //超时时间 10s
                data:{name:name,upResourceId:upResource,downResourceId:downResource, upInterfaceId:upInterface, downInterfaceId:downInterface,upIp:upIp,downIp:downIp},
                beforeSend: function () {
                    jp.loading();
                },
                success : function(result) {
                    if(result=='success'){
                         alert("添加成功");
						 $(view).html("");
						 totalNum=totalNum-1;
						 if(totalNum==0){
							window.location.href="${ctx}";
						 }
                    }else{
                        alert(result);
                    }
                },
                error:function(){
                    alert("保存失败");
                },
                complete:function(XMLHttpRequest,status){
                    jp.close();
                    if(status=='timeout'){
                        alert("请求超时，请检查网络后重试");
                    }
                }
            });
    }

        function del(index) {
            var view="#resourceView"+index;
            var upIpId="#upIp"+index;
            var downIpId="#downIp"+index;

            var upIp=$(upIpId).val();
            var downIp=$(downIpId).val();

            $.ajax({
                type : 'POST',
                url :  "${ctx}/resource/resource/confirmLinkDiscoveryDel",
                timeout:10*1000,    //超时时间 10s
                data:{upIp:upIp,downIp:downIp},
                beforeSend: function () {
                    jp.loading();
                },
                success : function(result) {
                    if(result=='success'){
                        $(view).html("");
                        totalNum=totalNum-1;
                        if(totalNum==0){
                            window.location.href="${ctx}";
                        }
                    }else{
                        alert(result);
                    }
                },
                error:function(){
                    alert("删除失败");
                },
                complete:function(XMLHttpRequest,status){
                    jp.close();
                    if(status=='timeout'){
                        alert("请求超时，请检查网络后重试");
                    }
                }
            });
        }


	</script>
	<style>
		.control-label{
			text-align: right;
		}
		.form-group{
			overflow: hidden;
		}
	</style>
</head>
<body class="wrapper wrapper-content">
			<div class="wrapper wrapper-content">
				<div class="row" style="max-width: 760px;margin:auto;padding:20px auto">
					<div class="col-md-12">
						<div class="panel panel-primary">
                            <div class="panel-heading"style="background: #fff;border-color:#fff;color: #3ca2e0;">
                                <h3 class="panel-title" >
                                    <a class="panelButton" href="${ctx}/resource/resource/server">链路资源发现确认</a>
                                </h3>
                            </div>
							   <input value="${linkEntrys.size()}" id="totalNum" type="hidden">
			                   <c:forEach items="${linkEntrys}" var="item" varStatus="index">
								<div id="resourceView${index.count}">
									<div class="panel-body" >
									<hr style="margin:0;height: 2px;border-top-color: #00b3ee;"/>
									<div onclick="del('${index.count}')" style="width: 100%;text-align: right;padding:4px 4px"><img style="cursor: pointer;" src="${ctxStatic}/common/images/close_blue.png" width="22"/></div>


												<div class="form-group">
													<label class="col-sm-2 control-label">名称：</label>
													<div class="col-sm-8">
														<input id="resourceName${index.count}"  value="${item.up}/${item.down}"class="form-control required" type="text"/>
														<input id="upIp${index.count}"  value="${item.up}" type="hidden"/>
														<input id="downIp${index.count}"  value="${item.down}" type="hidden"/>
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label">上联（<b style="color: #0c97e2">${item.upResource.name}</b>）接口：</label>
													<div class="col-sm-8">
														<input id="upResourceId${index.count}" value="${item.upResource.id}" type="hidden">
														<select name="" id="upInterface${index.count}" class="form-control valid">
															<c:forEach items="${interfaceMap}" var="interfaceItem" >
																<c:if test="${interfaceItem.key == item.up}">
																	<c:forEach items="${interfaceItem.value}" var="interfaceItems" >
																		<option value="${interfaceItems.id}">${interfaceItems.name}</option>
																	</c:forEach>
																</c:if>
															</c:forEach>
														</select>
													</div>
												</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">下联（<b style="color: #0c97e2">${item.downResource.name}</b>）接口：</label>
												<div class="col-sm-8">
													<input id="downResourceId${index.count}" value="${item.downResource.id}" type="hidden">
													<select name="" id="downInterface${index.count}" class="form-control valid">
														<c:forEach items="${interfaceMap}" var="interfaceItem" >
															<c:if test="${interfaceItem.key == item.down}">
																<c:forEach items="${interfaceItem.value}" var="interfaceItems" >
																	<option value="${interfaceItems.id}">${interfaceItems.name}</option>
																</c:forEach>
															</c:if>
														</c:forEach>
													</select>
												</div>
											</div>


												<div class="form-group">
													<div class="col-sm-2"></div>
													<div class="col-sm-8">
														<div class="form-group text-center">
															<div>
																<button  class="btn btn-primary btn-block btn-lg btn-parsley" id="save${index.count}" onclick="saveResource('${index.count}')">保 存</button>

															</div>
														</div>
													</div>
												</div>
											</div>
								</div>
							</c:forEach>

						</div>
					</div>
				</div>
			</div>

</body>
</html>