<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>资源发现确认</title>
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
        var ip="#resourceIp"+index;
        var nameId="#resourceName"+index;
        var rdcommunity="#rdcommunity"+index;
	    var selectid="#manufacturer"+index;
        var resourceTypeId= "#resourceType"+index+"Id";
        var snmpTypeId= "#snmpType"+index;
        var name=$(nameId).val().trim();       //资源名称
        var manufacturer=$(selectid).val();       //厂商
        var resourceType=$(resourceTypeId).val(); //资源类型

        if(name==""){
            alert("请填写资源名称");
            return;
        }
        if(resourceType==""){
            alert("请选择资源类型");
            return;
        }
            $.ajax({
                type : 'POST',
                url :  "${ctx}/resource/resource/confirmDiscoverySave",
                timeout:10*1000,    //超时时间 10s
                data:{name:name,ip:$(ip).val(),rdcommunity:$(rdcommunity).val(),resourceType:resourceType,manufacturer:manufacturer,snmpType:$(snmpTypeId).val()},
                beforeSend: function () {
                    jp.loading();
                },
                success : function(result) {
                    if(result=='success'){
                         alert("添加成功");
						 $(view).html("");
						 totalNum=totalNum-1;
						 if(totalNum==0){
							window.location.href="${ctx}/sys/systemConfig/confirmLinkDiscovery";
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
            var ip="#resourceIp"+index;

            $.ajax({
                type : 'POST',
                url :  "${ctx}/resource/resource/confirmDiscoveryDel",
                timeout:10*1000,    //超时时间 10s
                data:{ip:$(ip).val()},
                beforeSend: function () {
                    jp.loading();
                },
                success : function(result) {
                    if(result=='success'){
                        $(view).html("");
                        totalNum=totalNum-1;
                        if(totalNum==0){
                            window.location.href="${ctx}/sys/systemConfig/confirmLinkDiscovery";
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
                                    <a class="panelButton" href="${ctx}/resource/resource/server">资源发现确认</a>
                                </h3>
                            </div>
							<input value="${discoveryEntrys.size()}" id="totalNum" type="hidden">
							<c:forEach items="${discoveryEntrys}" var="item" varStatus="index">
								<div id="resourceView${index.count}">
									<div class="panel-body" >
									   <hr style="margin:0;height: 2px;border-top-color: #00b3ee;"/>
										 <div onclick="del('${index.count}')" style="width: 100%;text-align: right;padding:4px 4px"><img style="cursor: pointer;" src="${ctxStatic}/common/images/close_blue.png" width="22"/></div>
											<div class="form-group">
												<label class="col-sm-2 control-label">名称：</label>
												<div class="col-sm-8">
													<input id="resourceName${index.count}"  value=""class="form-control required" type="text"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">IP：</label>
												<div class="col-sm-8">
													<input id="resourceIp${index.count}" name="" value="${item.value.ip}" readonly="readonly" class="form-control required" type="text"/>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">读共同体：</label>
												<div class="col-sm-8">
													<input id="rdcommunity${index.count}" type="text" value="${item.value.rdcommunity}"   readonly="readonly" class="form-control" />
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">管理方式：</label>
												<div class="col-sm-8">
													<select name="" id="snmpType${index.count}" class="form-control valid">
														<option value="1">SNMPV1</option>
														<option value="2">SNMPV2</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">设备描述：</label>
												<div class="col-sm-8">
													<p>${item.value.desc}</p>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">厂商：</label>
												<div class="col-sm-8">
													<select name="" id="manufacturer${index.count}" class="form-control valid">
														<c:forEach items="${manufacturerList}" var="item2" varStatus="num">
															<option value="${item2.id}">${item2.name}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">资源类型：</label>
												<div class="col-sm-8">
													<sys:resourceselect id="resourceType${index.count}" name="" value="" labelName="" labelValue=""
																		title="资源类型" url="/resourcetype/resourceType/treeData"  cssClass="form-control required" notAllowSelectParent="true"/>
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