<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>编辑资源指标列表</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script>

        $(document).ready(function() {
            init();
        })
		function edit(obj,id,type,index,thresholdUnit) {
			$("#resourceIndicatorlistId").val(id);
            $("#thresholdType").val(type);
            $("#thresholdIndex").val(index);
            $("#thresholdUnit").html("单位（"+thresholdUnit+"）");
            $("#thresholdValue").val("");

            /*if($(obj).html().trim().length>0){
                $("#thresholdValue").val($(obj).html().trim());
            }*/

        }

        function editThresholdSave() {
            if($("#thresholdValue").val().trim().length>0&&!(($("#thresholdValue").val().trim().indexOf("X")>=0)||($("#thresholdValue").val().trim().indexOf("x")>=0))){
                alert("表达式不合逻辑");
                return;
			}

		    var editId="";
            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/editThresholdSave",
                data : {
                    resourceIndicatorlistId: $("#resourceIndicatorlistId").val(), thresholdType : $("#thresholdType").val(), thresholdValue : $("#thresholdValue").val()
                },
                success : function(result) {
                    if(result){
                        if($("#thresholdType").val() == 1){
                            editId="#highUrgentThreshold"+$("#thresholdIndex").val();
                            $(editId).html($("#thresholdValue").val());
                            if($(editId).html().trim().length==0){
                                $(editId).html("编辑");
                            }
                        }else if($("#thresholdType").val() == 2){
                            editId="#middleUrgentThreshold"+$("#thresholdIndex").val();
                            $(editId).html($("#thresholdValue").val());
                            if($(editId).html().trim().length==0){
                                $(editId).html("编辑");
                            }
                        }else if($("#thresholdType").val() == 3){
                            editId="#normalUrgentThreshold"+$("#thresholdIndex").val();
                            $(editId).html($("#thresholdValue").val());
                            if($(editId).html().trim().length==0){
                                $(editId).html("编辑");
                            }
                        }else if($("#thresholdType").val() == 4){
                            editId="#tipThreshold"+$("#thresholdIndex").val();
                            $(editId).html($("#thresholdValue").val());
                            if($(editId).html().trim().length==0){
                                $(editId).html("编辑");
                            }
                        }
                        $("#resourceIndicatorlistId").val("");
                        $("#thresholdType").val("");
                        $("#thresholdIndex").val("");
                        $("#thresholdValue").val("");
                        $("#thresholdUnit").html("");
                    }else{
                        $("#resourceIndicatorlistId").val("");
                        $("#thresholdType").val("");
                        $("#thresholdIndex").val("");
                        $("#thresholdValue").val("");
                        $("#thresholdUnit").html("");
                        alert("保存失败");
                    }
                },
                error:function(){
                    $("#resourceIndicatorlistId").val("");
                    $("#thresholdType").val("");
                    $("#thresholdIndex").val("");
                    $("#thresholdValue").val("");
                    $("#thresholdUnit").html("");
                    alert("保存失败");
                }

            });
        }


        function addIndicator(type,resourceType,childCode) {
		    $("#addDivList").html("");
            $("#eventType").val(type);
            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/indicator/indicator/addIndicator",
                data : {
                    type: type, resourceId : $("#resourceId").val()
                },
                success : function(result) {
                    var html="";
                    for(var i=0;i<result.length;i++){
                            if (result[i].selected) {
                                if(resourceType==4){
                                       html = html + "<tr><td><input type='checkbox' checked='checked' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input name='gather" + i + "' checked='checked' type='radio' value='4'>JDBC</td></tr>";
                                }else if(resourceType==5) {
                                   // if(childCode == 51){ //tomcat
                                        html = html + "<tr><td><input type='checkbox' checked='checked' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input name='gather" + i + "' checked='checked' type='radio' value='5'>HTTP</td></tr>";
                                   // }
                                }else {
                                        if (type == 1 || type == 3 || type == 4) {
                                            html = html + "<tr><td><input type='checkbox' checked='checked' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input name='gather" + i + "' checked='checked' type='radio' value='1'>SNMP</td></tr>";
                                        } else if (type == 2) {
                                            html = html + "<tr><td><input type='checkbox' checked='checked' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input name='gather" + i + "' checked='checked' type='radio' value='2'>PING</td></tr>";
                                        }
                                 }
                            } else {
                                if(resourceType==4){
                                        html = html + "<tr><td><input type='checkbox' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input  checked='checked' name='gather" + i + "' type='radio' value='4'>JDBC</td></tr>";
                                }else if(resourceType==5){
                                   // if(childCode == 51) { //tomcat
                                        html = html + "<tr><td><input type='checkbox' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input  checked='checked' name='gather" + i + "' type='radio' value='5'>HTTP</td></tr>";
                                   // }
                                } else {
                                    if (type == 1 || type == 3 || type == 4) {
                                        html = html + "<tr><td><input type='checkbox' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input  checked='checked' name='gather" + i + "' type='radio' value='1'>SNMP</td></tr>";
                                    } else if (type == 2) {
                                        html = html + "<tr><td><input type='checkbox' name='addIndicatorIds' value='" + result[i].id + "'></td><td>" + result[i].name + "</td><td><input  checked='checked' name='gather" + i + "' type='radio' value='2'>PING</td></tr>";
                                    }
                                }
                        }
                    }
                    $("#addDivList").html(html);
					$("#all").change(function () {
						if($(this).is(':checked')){
                            $("input:checkbox[name='addIndicatorIds']").prop("checked", true);
						}else{
                            $("input:checkbox[name='addIndicatorIds']").prop("checked", false);
						}
                    });
                },
                error:function(){

                }

            });
        }


        function addSave() {
            var addIndicatorIds="";
            var gatherTypes="";
            $("input:checkbox[name='addIndicatorIds']:checked").each(function(index){
                var radioName="gather"+index;
                gatherTypes+=$("input:radio[name="+radioName+"]:checked").val()+",";
                addIndicatorIds+=$(this).val()+",";
            })
            //alert(addIndicatorIds);

            if(addIndicatorIds.length>0){
                addIndicatorIds=addIndicatorIds.substr(0,addIndicatorIds.length-1);
                gatherTypes=gatherTypes.substr(0,gatherTypes.length-1);
                var eventType=$("#eventType").val();
                if(eventType==null||eventType.trim().length<1){
                    alert("操作失败,事件类型错误");
                    return;
                }

                $.ajax({
                    type : 'GET',
                    async : true,
                    url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/addIndicatorSave",
                    data : {
                        addIndicatorIds: addIndicatorIds, gatherTypes: gatherTypes,resourceId : $("#resourceId").val(),eventType :  eventType
                    },
                    success : function(result) {
                        $("#eventType").val("");
                        if(result) {
                            $("#close").click();
                            init();
                        }else{
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                        $("#eventType").val("");
                    }

                });
            }else{
                alert("请选择指标");
                return;
            }


        }



        function delById(id) {
            if(confirm("确定要删除吗？")){
            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/delById",
                data : {
                    resourceIndicatorlistId: id
                },
                success : function(result) {
                    if(result) {
                        init();
                    }else{
                        alert("操作失败");
                    }
                },
                error:function(){
                    alert("操作失败");
                }

            });
            }
        }

        function sure() {
            window.opener.initIndicatorList();
            window.close();
        }


        function init() {
            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/indicator/indicator/initList",
                data : {
                    resourceId: $("#resourceId").val()
                },
                success : function(result) {
                    var html = "";
                    for(var i=0;i<result.length;i++){
                        var type=jp.getDictLabel(${fns:toJson(fns:getDictList('indicator_event_type'))}, result[i].indicator.eventType, "-");
                        html +="<tr><td style='padding-top: 14px;'>"+type+"</td><td style='padding-top: 14px;'>"+result[i].indicator.name+"</td> <td>";
                        if(result[i].indicator.type!='11'&& result[i].indicator.type !='12'){
                            html +="<a type='button' class='' data-toggle='modal' data-target='#myModal' class='btn edit tableEdit' style='display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)'>";

                            if(result[i].highUrgentThreshold!=null){
                                var id="highUrgentThreshold"+(i+1);
                                html +="<b onclick=\"edit(this,'"+result[i].id+"','1','"+(i+1)+"','"+result[i].indicator.unit+"')\" id='"+id+"'>"+result[i].highUrgentThreshold+"</b>";
                            }
                            if(result[i].highUrgentThreshold==null){
                                var id2="highUrgentThreshold"+(i+1);
                                html +="<b onclick=\"edit(this,'"+result[i].id+"','1','"+(i+1)+"','"+result[i].indicator.unit+"')\" id='"+id2+"'>编辑</b>";
                            }
                            html +="</a>";
                        }

                        html +="</td><td>";
                        if(result[i].indicator.type!='11'&& result[i].indicator.type !='12') {
                            html += "<a type='button' class='' data-toggle='modal' data-target='#myModal' class='btn edit tableEdit' style='display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)'>";

                            if (result[i].middleUrgentThreshold != null) {
                                var middleUrgentThreshold = "middleUrgentThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','2','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + middleUrgentThreshold + "'>" + result[i].middleUrgentThreshold + "</b>";
                            }

                            if (result[i].middleUrgentThreshold == null) {
                                var middleUrgentThreshold = "middleUrgentThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','2','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + middleUrgentThreshold + "'>编辑</b>";
                            }
                            html += "</a>";
                        }
                        html +="</td><td>";
                        if(result[i].indicator.type!='11'&& result[i].indicator.type !='12') {
                            html += "<a type='button' class='' data-toggle='modal' data-target='#myModal' class='btn edit tableEdit' style='display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)'>";

                            if (result[i].normalUrgentThreshold != null) {
                                var normalUrgentThreshold = "normalUrgentThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','3','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + normalUrgentThreshold + "'>" + result[i].normalUrgentThreshold + "</b>";
                            }

                            if (result[i].normalUrgentThreshold == null) {
                                var normalUrgentThreshold = "normalUrgentThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','3','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + normalUrgentThreshold + "'>编辑</b>";
                            }
                            html += "</a>";
                        }

                        html += "</td><td>";

                        if(result[i].indicator.type!='11'&& result[i].indicator.type !='12') {
                            html += "<a type='button' class='' data-toggle='modal' data-target='#myModal' class='btn edit tableEdit' style='display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)'>";
                            if (result[i].tipThreshold != null) {
                                var tipThreshold = "tipThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','4','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + tipThreshold + "'>" + result[i].tipThreshold + "</b>";
                            }
                            if (result[i].tipThreshold == null) {
                                var tipThreshold = "tipThreshold" + (i + 1);
                                html += "<b onclick=\"edit(this,'" + result[i].id + "','4','" + (i + 1) + "','" + result[i].indicator.unit + "')\" id='" + tipThreshold + "'>编辑</b>";
                            }
                            html += "</a>";
                        }

                        html += "</td><td style='padding-top: 14px;'>"+result[i].indicator.unit+"</td>"+
                            "<td><button onclick='delById(\"" + result[i].id + "\")' class='btn edit tableEdit' style='background: rgba(0,0,0,0)'>删除</button></td></tr>";
                    }
                    $("#tbodyView").html(html);
                },
                error:function(){
                    $("#tbodyView").html("");
                }
            });

        }

	</script>
</head>
<body>
<input value="${resource.id}" id="resourceId" type="hidden">
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-body">
		<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
			<thead><tr><th>事件类型</th><th>指标名称</th><th>特急告警</th><th>较急告警</th><th>一般告警</th><th>提示告警</th><th>单位</th><th>操作</th></tr></thead>
			<tbody id ="tbodyView">

			<%--  <c:forEach items="${resourceIndicatorlist}" var="item" varStatus="index">
				<tr>
					<td style="padding-top: 14px;">${fns:getDictLabel(item.indicator.eventType, 'indicator_event_type', '')} </td>
					<td style="padding-top: 14px;">${item.indicator.name}</td>
					<td>
						<c:if test="${item.indicator.type !='11' && item.indicator.type !='12'}">
							<a type="button" class="" data-toggle="modal" data-target="#myModal" class="btn edit tableEdit" style="display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)">
								   <c:if test="${not empty item.highUrgentThreshold}">
								        <b onclick="edit(this,'${item.id}','1','${index.count}','${item.indicator.unit}')" id="highUrgentThreshold${index.count}">${item.highUrgentThreshold}</b>    
								  </c:if>
								  <c:if test="${empty item.highUrgentThreshold}"> 
							           <b onclick="edit(this,'${item.id}','1','${index.count}','${item.indicator.unit}')" id="highUrgentThreshold${index.count}">编辑</b>    
							       </c:if>
							</a>
						</c:if>
					</td>
					<td>
						<c:if test="${item.indicator.type !='11' && item.indicator.type !='12'}">
							<a type="button" class="" data-toggle="modal" data-target="#myModal" class="btn edit tableEdit" style="display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)">
								   <c:if test="${not empty item.middleUrgentThreshold}">
								        <b onclick="edit(this,'${item.id}','2','${index.count}','${item.indicator.unit}')" id="middleUrgentThreshold${index.count}">${item.middleUrgentThreshold}</b>
								   </c:if>
							       <c:if test="${empty item.middleUrgentThreshold}"> 
							            <b onclick="edit(this,'${item.id}','2','${index.count}','${item.indicator.unit}')" id="middleUrgentThreshold${index.count}">编辑</b>
							       </c:if>
							</a>
						</c:if>
					</td>

					<td>
						<c:if test="${item.indicator.type !='11' && item.indicator.type !='12'}">
							<a type="button" class="" data-toggle="modal" data-target="#myModal" class="btn edit tableEdit" style="display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)">
								  <c:if test="${not empty item.normalUrgentThreshold}">
								       <b onclick="edit(this,'${item.id}','3','${index.count}','${item.indicator.unit}')" id="normalUrgentThreshold${index.count}">${item.normalUrgentThreshold}  </b>
								   </c:if>
							       <c:if test="${empty item.normalUrgentThreshold}"> 
							           <b onclick="edit(this,'${item.id}','3','${index.count}','${item.indicator.unit}')" id="normalUrgentThreshold${index.count}">编辑</b>
							       </c:if>
							</a>
						</c:if>
					</td>

					<td>
						<c:if test="${item.indicator.type !='11' && item.indicator.type !='12'}">
							<a type="button" class="" data-toggle="modal" data-target="#myModal" class="btn edit tableEdit" style="display: inline-block;margin-top:4px;padding: 6px 12px;background: rgba(0,0,0,0)">
								   <c:if test="${not empty item.tipThreshold}"> 
								       <b onclick="edit(this,'${item.id}','4','${index.count}','${item.indicator.unit}')" id="tipThreshold${index.count}">${item.tipThreshold}  </b>
								   </c:if>
							       <c:if test="${empty item.tipThreshold}"> 
							           <b onclick="edit(this,'${item.id}','4','${index.count}','${item.indicator.unit}')" id="tipThreshold${index.count}">编辑</b>
							       </c:if>
							</a>
						</c:if>
					</td>
					<td style="padding-top: 14px;">${item.indicator.unit}</td>
					<td><button onclick="delById('${item.id}')" class="btn edit tableEdit" style="background: rgba(0,0,0,0)">删除</button></td>
				</tr>
			</c:forEach>--%>

			</tbody>
		</table>


		<div class="form-group">
			<div style="overflow: hidden">
				<div class="col-sm-2 text-center"style="max-width: 126px;">
					<a type="button" class="" data-toggle="modal" data-target="#addView" >
						<button onclick="addIndicator('2','${resource.resourceType.parent.code}','${resource.resourceType.code}')" class="btn  btn-block btn-lg btn-parsley" style="max-width: 96px;background:#fff;border-color:#fff;color: #999;">新增通断</button>
					</a>
				</div>

				<div class="col-sm-2 text-center"style="max-width: 126px;">
					<a type="button" class="" data-toggle="modal" data-target="#addView">
						<button onclick="addIndicator('1','${resource.resourceType.parent.code}','${resource.resourceType.code}')" class="btn  btn-block btn-lg btn-parsley" style="max-width: 96px;background:#fff;border-color:#fff;color: #999;">新增性能</button>
					</a>
				</div>

				<c:if test="${resource.resourceType.parent.code != '1' && resource.resourceType.code != '51'}" >
					<div class="col-sm-2 text-center" style="max-width: 126px;" >
						<a type="button" class="" data-toggle="modal" data-target="#addView">
							<button onclick="addIndicator('3','${resource.resourceType.parent.code}','${resource.resourceType.code}')" class="btn  btn-block btn-lg btn-parsley" style="max-width: 96px;background:#fff;border-color: #fff;color: #999;">新增扩展</button>
						</a>
					</div>
				</c:if>

				<c:if test="${resource.resourceType.parent.code != '1' && resource.resourceType.parent.code != '4'&& resource.resourceType.parent.code != '5'}" >
					<div class="col-sm-2 text-center" style="max-width: 126px;">
						<a type="button" class="" data-toggle="modal" data-target="#addView">
							<button onclick="addIndicator('4','${resource.resourceType.parent.code}','${resource.resourceType.code}')" class="btn  btn-block btn-lg btn-parsley" style="max-width: 96px;background:#fff;border-color: #fff;border-radius: 3px;color: #999;">新增安全</button>
						</a>
					</div>
				</c:if>
			</div>
			<div class="text-center"  style="width:126px;clear: both;margin-top: 10px;">
				<button onclick="sure()" class="btn btn-primary btn-block btn-lg btn-parsley" style="border-radius: 3px;">确定</button>
			</div>
		</div>


		<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
			<div class="modal-dialog" style="width:400px;">
				<div class="modal-content animated bounceInRight" style="width:400px;">
					<div class="modal-body" style="width:100%">
						<div id="editView" style="width:100%;height:120px">
							<h4>编辑阈值</h4>
							<div style="margin-top: 32px;text-align: center">
								<input id="resourceIndicatorlistId" value="" type="hidden" class=" form-control">
								<input id="thresholdIndex" value="" type="hidden" class=" form-control">
								<input id="thresholdType" value="" type="hidden" class=" form-control">
								<input id="thresholdValue" class=" form-control" style="width: 70%;display: inline-block;margin-right: 5px;"><b id="thresholdUnit"></b>
							</div>
							<div class="threshold">请设置表达式如 x&lt;100、x&gt;=100、x==100 等。</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white"  data-dismiss="modal" onclick="editThresholdSave()" >保存</button>
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>



		<div class="modal inmodal" id="addView" tabindex="-1" role="dialog" aria-hidden="true" >
			<div class="modal-dialog" style="width:800px;">
				<div class="modal-content animated bounceInRight" style="width:800px;">
					<div class="modal-body" style="width:100%">
						<div id="addDiv" style="width:750px;height:340px">
							<form id="addForm">
								<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
									<thead><tr><th><input type='checkbox' name='all' id="all" ></th><th>指标名称</th><th>取值类型</th></thead>
									<tbody id="addDivList">


									</tbody>
								</table>
							</form>

						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white"   onclick="addSave()" >保存</button>
						<input id="eventType" type="hidden">
						<button type="button" class="btn btn-white" data-dismiss="modal" id="close">关闭</button>
					</div>
				</div>
			</div>
		</div>


	</div>
	</div>
	</div>
</body>
</html>