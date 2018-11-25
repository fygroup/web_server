<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>resource管理</title>
	<meta name="decorator" content="ani"/>

	<script src="${ctxStatic}/proTree.js"></script>
	<script src="${ctxStatic}/SimpleTree.js"></script>

	<script type="text/javascript">
		var data=null;
		var code="1";
        var type="up";
		var managetType="1";
		var id;
		var name;
		$(document).ready(function() {
            $(".selectbtn").hide();

			$("#inputForm").validate({
				submitHandler: function(form){

				    //if(managetType=="1"){
                       jp.loading();
					   form.submit();

                       /* $.ajax({
                            url:"${ctx}/resource/resource/checkExist",
                            type:'POST', //
                            async:true,    //或false,是否异步
                            data:{
                                upLinkInterface:$("#upLinkInterface").val(),downLinkInterface:$("#downLinkInterface").val()
                            },
                            timeout:100*1000,    //超时时间 100s
                            dataType:'json',    //返回的数据格式：
                            success:function(data,textStatus,jqXHR){
                                if(data[0]["status"]==200){
                                    form.submit();
                                }else if(data[0]["status"]==401){
                                    alert("链路添加失败，网络接口不存在");
                                    jp.close();
                                }else if(data[0]["status"]==402){
                                    alert("当前接口已存在链路中");
                                    jp.close();
                                }else{
                                    alert("链路添加失败");
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

            $.ajax({
                type : 'POST',
                async : true,
                url :  "${ctx}/resource/resource/linkSelect",
                timeout:100*1000,    //超时时间 10s
                success : function(result) {
                    data=result;
                    $("#optionView").html(data["select"]);
                    $(".selectbtn").show();
                },
                error:function(){
                    alert("接口列表失败");
                },
                complete:function(XMLHttpRequest,status){
                    if(status=='timeout'){
                        alert("请求超时，请检查网络后重试");
                    }
                }
            });
		});
        function selectThis(obj) {
            $(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
            $(obj).attr('src','${ctxStatic}/images/icon/selected_icon.png');
            name=$(obj).next().val();
            id=$(obj).next().next().val();
        }

        function initSelect(t) {
            if(t!=null){
               type=t;
			}
            if(data!=null){
                $("#innerUl").html(data[code]);
                $(".st_tree").SimpleTree({
                    click:function(a){
                    }
                });
                $(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
			}else{
                $("#innerUl").html("");
			}
        }

        function selectOption() {
            code=$('#resourceTypeSelect  option:selected').val();
            initSelect(null);
        }


        function selectSure() {
            if(type=="up"){
                $("#upEquequipmentInterface").val(name);
                $("#upLinkInterface").val(id);

			}else if(type=="down"){
                $("#downEquequipmentInterface").val(name);
                $("#downLinkInterface").val(id);
			}
           /* alert(name+"  "+id);*/
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
				<a class="panelButton" href="${ctx}/resource/resource/link"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="resource" action="${ctx}/resource/resource/linkFormSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<input id="resourceTypeId" value="31" type="hidden">
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>资源名称：</label>
				<div class="col-sm-8">
					<form:input path="name" htmlEscape="false"    class="form-control required"/>

				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>上联设备和端口：</label>
				<div class="col-sm-8">
					<input name="upEquequipmentInterface" id="upEquequipmentInterface" readonly="readonly" class="form-control" style="width: 80%;display: inline-block">
					<input name="upLinkInterface" id="upLinkInterface" type="hidden">

					<a type="button" class="selectbtn" data-toggle="modal" data-target="#selectView">
						<button onclick="initSelect('up')" class="btn  btn-block btn-lg btn-parsley" style="width: 76px;background:#fff;border-color: #fff;border-radius: 3px;color: #999;display: inline-block">选择</button>
					</a>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>下联设备和端口：</label>
				<div class="col-sm-8">
					<input name="downEquequipmentInterface" id="downEquequipmentInterface" readonly="readonly" class="form-control" style="width: 80%;display: inline-block">
					<input name="downLinkInterface" id="downLinkInterface" type="hidden">

					<a type="button" class="selectbtn" data-toggle="modal" data-target="#selectView">
					    <button onclick="initSelect('down')" class="btn  btn-block btn-lg btn-parsley" style="width: 76px;background:#fff;border-color: #fff;border-radius: 3px;color: #999;display: inline-block">选择</button>
					</a>

				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"><font color="red">*</font>归属法院：</label>
				<div class="col-sm-8">
					<sys:treeselect id="company" name="company" value="${resource.company.id}" labelName="" labelValue="${resource.company.name}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<div><input type="checkbox" id="status"     name="gatherItem" value="11"> 状态  </div>
					<div><input type="checkbox" id="upRate"     name="gatherItem" value="21" onchange="rate(this,'21')"> 上行  <input type="checkbox" id="downRate" name="gatherItem" value="22" onchange="rate(this,'22')"> 下行         <span style="margin-left: 30px;">速率</span></div>
					<div><input type="checkbox" id="upUsedRate" name="gatherItem" value="31" disabled="true" onclick="return  false;"> 上行    <input type="checkbox" id="downUsedRate" disabled="true" name="gatherItem" value="32" onclick="return  false;"> 下行     <span style="margin-left: 30px;">利用率</span></div>
				</div>
			</div>
		<script>
			function rate(obj,type) {
                if($(obj).is(':checked')) {
                    if(type=='21'){
                        $("#upUsedRate").attr('disabled',false);
                        $("#upUsedRate").attr('onclick','');

					}else if(type=='22'){
                        $("#downUsedRate").attr('disabled',false);
                        $("#downUsedRate").attr('onclick','');
					}
                }else{
                    if(type=='21'){
                        $("#upUsedRate").attr('disabled',true);
                        $("#upUsedRate").attr('onclick','return  false;');
                        $("#upUsedRate").removeAttr("checked");
                    }else if(type=='22'){
                        $("#downUsedRate").attr('disabled',true);
                        $("#downUsedRate").attr('onclick','return  false;');
                        $("#downUsedRate").removeAttr("checked");
                    }
				}


			}
		</script>





			<div class="form-group">
				<label class="col-sm-2 control-label">描述：</label>
				<div class="col-sm-8">
					<form:input path="description" htmlEscape="false"    class="form-control  isIntGtZero"/>
				</div>
			</div>
			<c:if test="${fns:hasPermission('resource:resource:edit') || isAdd}">
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">
					<button  class="btn-primary form-control" data-loading-text="正在发现，请稍后...">保 存</button>
				</div>
			</div>
			</c:if>
		</form:form>

		</div>				
	</div>
	</div>
</div>
</div>



	<div class="modal inmodal" id="selectView" tabindex="-1" role="dialog" aria-hidden="true" >
		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content animated bounceInRight" style="width:800px;">
				<div class="modal-body" style="width:100%">
					<div  style="width: 750px;height: 420px;overflow: auto;">

						<div id="optionView"></div><br/>
						<div id="innerUl"></div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white"  data-dismiss="modal" onclick="selectSure()" >确定</button>
					<input id="eventType" type="hidden">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>