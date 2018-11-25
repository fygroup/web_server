<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="ani"/>
	<link rel="stylesheet" href="${ctxStatic}/common/css/animate.min.css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/style.min.css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/main.css">
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@ include file="/webpage/include/echarts.jsp"%>
	<%@ include file="/webpage/modules/sys/templates/templatesEditor.js"%>
	<style>

		#body-container {
			margin-left: 0px !important;
			/**padding: 10px;*/
			margin-top: 0px !important;
			overflow-x: hidden!important;
			transition: all 0.2s ease-in-out !important;
			height: 100% !important;
		}
		.ibox{
			cursor: pointer;
		}
		ul{

			padding-left:0 !important;
		}
		.agile-list li{
			padding:0
		}
	</style>
</head>
<body class="">
<div id="body-container" class="wrapper wrapper-content">
	<div class="animated fadeInRight">
		<%--<div class="row">
			<div class="panel-heading">
				<shiro:hasPermission name="sys:role:add">
					<a id="add" class="btn btn-primary"  onclick="add()"><i class="glyphicon glyphicon-plus"></i> 添加</a>
				</shiro:hasPermission>
			</div>
		</div>--%>
		<div class="row">
			<input value="${myViewSort}" id="viewSort" type="hidden"/>
			<div class="col-sm-12">
				<div class="ibox">
					<div class="ibox-content">
						<ul class="sortable-list connectList agile-list">
							<%--<li class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">拓扑图
										<a class="operate-icon panelButton pull-right" id="close" href="#" onclick="del()"><i class="fa fa-close"></i></a>
										<a class="operate-icon panelButton pull-right" id="editor-topo" href="#" onclick="editTopo()"><i class="fa fa-cog"></i></a>
										<a class="operate-icon panelButton pull-right" href="${ctx}/topo" target="_blank"><i class="fa fa-external-link"></i></a>
									</h3>
									<div class="edit-box" id="topo-edit" style="">
										<div class="form-group">
											<div class="edit-topo">
												<label style="min-width: 66px;font-weight: normal">栏目标题：</label><input class="form-control required" style="display: inline-block;width: 60%;" value="" placeholder="拓扑图"/>
											</div>
										</div>
										<div class="form-group">
											<button class="edit-topo-btn btn btn-lg btn-parsley" style="padding: 4px 8px;font-size: 13px;">保 存</button>
											<button class="edit-topo-btn btn btn-lg btn-danger " style="padding: 4px 8px;font-size: 13px;" onclick="cancelTopo()">取 消</button>
										</div>
									</div>
								</div>
								<div class="index-table" style="overflow: auto;">
									<div class="row">
										<iframe class="" src="${ctx}/topo" width="100%" height="660px" style="overflow: auto;border: none;"></iframe>
									</div>
								</div>
							</li>--%>

							<li class="panel panel-default" id="exceptionView">
								<div class="panel-heading">
									<h3 class="panel-title">异常列表
										<shiro:hasPermission name="sys:templates:del">
										<a  onclick="return del(this, '{{d.id}}')"><i class="fa fa-trash"></i> 删除</a>
										</shiro:hasPermission>
								<%--		<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-close"></i></a>--%>
									<%--	<a class="operate-icon panelButton pull-right" href="${ctx}/exception/exception/resourceException" target="_blank"><i class="fa fa-sign-in"></i></a>--%>
									</h3>
								</div>
								<div class="index-table"><!--异常-->
									<div class="row">
										<table class="table table-hover tree_table" >
											<thead>
											<tr>
												<th>异常名称</th>
												<th>异常来源</th>
												<th>异常等级</th>
												<th>操作</th>
											</tr>
											</thead>
											<tbody id="getExceptionList">
											<%--<tr>
												<td>异常名称</td>
												<td>来源来源</td>
												<td>严重</td>
												<td>
													<a class="operate-icon operate delet" href="#" title="手动确认"><i class="fa fa-hand-o-right"></i></a>
													<a class="operate-icon operate delet" href="#" title="设置"><i class="fa fa-cog"></i></a>
													<a class="operate-icon operate delet" href="#" title="删除"><i class="fa fa-trash"></i></a>
												</td>
											</tr>
											<tr>
												<td>异常名称</td>
												<td>来源来源</td>
												<td>严重</td>
												<td>
													<a class="operate-icon operate delet" href="#" title="手动确认"><i class="fa fa-hand-o-right"></i></a>
													<a class="operate-icon operate delet" href="#" title="设置"><i class="fa fa-cog"></i></a>
													<a class="operate-icon operate delet" href="#" title="删除"><i class="fa fa-trash"></i></a>
												</td>
											</tr>--%>
											</tbody>
										</table>
									</div>
								</div>
							</li>
							<li class="panel panel-default" id="indicatorView"><!--指标一览-->
								<div class="panel-heading">
									<h3 class="panel-title"><b id="title">指标一览</b>
									<%--	<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-close"></i></a>--%>
										<a class="operate-icon panelButton pull-right" href="#" id="indicators_list"><i class="fa fa-cog"></i></a>
									</h3>
								</div>
								<div class="index-table">
									<div class="row">
										<table class="table table-hover tree_table">
											<thead>
											<tr>
												<th>资源名称</th>
												<th>指标名称</th>
												<th>当前值</th>
											</tr>
											</thead>
											<tbody id="indicatorsList">


											</tbody>
										</table>
									</div>
								</div>
							</li>
							<li class="panel panel-default"  id="singleIndicatorView">
								<div class="panel-heading">
									<h3 class="panel-title"><b id="singTitle">单个资源一览</b>
										<%--<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-close"></i></a>--%>
										<a class="operate-icon panelButton pull-right" href="#" id="templatesTopoEditor"><i class="fa fa-cog"></i></a>
										<%--	<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-sign-in"></i></a>--%>
									</h3>
								</div>
								<div class="index-table">
									<div class="row">
										<table class="table table-hover tree_table">
											<tbody id="userSingleIndicator">
											<%--<tr>
												<td colspan="2">
													<div id="health" style="width:100%;height:200px;"> </div>
												</td>
											</tr>--%>
											</tbody>
										</table>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<%--<div class="col-sm-5"><!--单个资源一览与TopN-->
				<div class="ibox">
					<div class="ibox-content">
						<ul class="sortable-list connectList agile-list">
							&lt;%&ndash;<li class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">TopN
										<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-close"></i></a>
										<a class="operate-icon panelButton pull-right" href="#"><i class="fa fa-cog"></i></a>
									</h3>
								</div>
								<div class="index-table">
									<div class="row">
										<div id="topN" style="width:100%;height:320px;"></div>
									</div>
								</div>
							</li>&ndash;%&gt;

						</ul>
					</div>
				</div>
			</div>--%>




			<div class="modal inmodal" id="exceptionDetail" tabindex="-1" role="dialog" aria-hidden="true" >
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content animated bounceInRight" style="width:800px;">
						<div class="modal-body" style="width:100%">
							<div id="netInterfaceRateView" style="width:750px;height:340px">
								<div class="fixed-table-body">
									<h4>异常明细</h4>
									<table class="table" >
										<tbody>
										<tr>
											<td class="tit">异常名称</td><td class="td-hover" id="ename"></td>
											<td class="tit">异常来源</td><td class="td-hover" id="efrom"></td>
										</tr>
										<tr>
											<td class="tit">累计次数</td><td class="td-hover" id="etotalNum"></td>
											<td class="tit">异常等级</td><td class="td-hover" id="eclass"></td>
										</tr>
										<tr>
											<td class="tit">当前指标值</td><td class="td-hover" id="evalue" ></td>
											<td class="tit">当前状态</td><td class="td-hover" id="estatus" ></td>
										</tr>
										<tr>
											<td class="tit">第一次产生指标值</td><td class="td-hover" id="efirstTimeValue" ></td>
											<td class="tit">指标名称</td><td class="td-hover" id="eindicator" ></td>
										</tr>
										<tr>
											<td class="tit">最后一次产生异常的时间</td><td class="td-hover" id="elastTime" ></td>
											<td class="tit">资源类型</td><td class="td-hover" id="resourceType"  ></td>
										</tr>
										<tr>
											<td class="tit">指标事件类型</td><td class="td-hover" id="event" ></td>
											<td class="tit">确认状态</td><td class="td-hover" id="econfirmStatus" ></td>
										</tr>
										<tr>
											<td class="tit">备注信息</td><td class="td-hover" colspan="3" id="eremarks"></td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>


		</div>

	</div>
</div>

<script src="${ctxStatic}/common/js/content.min.js?v=1.0.0"></script>
<%--<script src="${ctxStatic}/common/js/TOPNChart.js"></script>--%>
<%--<script src="${ctxStatic}/common/js/CPUChart.js"></script>--%>
<script type="text/javascript" language="javascript">

/*var topNChart,healthChart;*/
	$(document).ready(function(){
        initUserIndicatorlist();       //初始化资源指标
        initSingleUserIndicatorlist();  //初始化单一资源指标
        getExceptionList();//异常
        $(".sortable-list").sortable({connectWith:".connectList"}).disableSelection();

        //单个指标一览编辑保存
        $("#templatesTopoEditor").click(function(){
            jp.open({
                type: 2,
                area: ['70%', '500px'],
                title:"编辑",
                content: "${ctx}/sys/templates/singleIndicator" ,
                btn: ['确定', '关闭'],
                yes: function(index, layero){
                    var iframe = layero.find('iframe')[0];
                    var checkIndicatorId = iframe.contentWindow.saveEditIndicatorid();
                    var title=iframe.contentWindow.getTitle();
                    saveSingleUserIndicator(checkIndicatorId,2,title);
                    jp.close(index);//关闭对话框。
                },
                cancel: function(index){
                }
            });
        });


		$("#indicators_list").click(function(){ //指标一览
			jp.open({
				type: 2,
				area: ['70%', '500px'],
				title:"编辑",
				content: "${ctx}/sys/templates/topoEditor" ,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
                    var iframeWin = layero.find('iframe')[0];
                    var id=iframeWin.contentWindow.getIds();
                    var title=iframeWin.contentWindow.getTitle();
                    saveUserIndicator(id,1,title);
					jp.close(index);//关闭对话框。
				},
				cancel: function(index){
				}
			});
		});
       // $("#topo-edit").hide();
        window.onresize = function() {
           /* topNChart.resize();*/
           /* healthChart.resize();*/
        };


	});

    function editTopo(){
        $("#topo-edit").slideDown();
    }
    function cancelTopo() {
        $("#topo-edit").slideUp();
    }


	function saveUserIndicator(id,type,title) {
		jp.loading();
		$.ajax({
			type : 'POST',
			async : true,
			url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/saveUserIndicator",
			data:{resourceIndicatorlistId:id,type:type,title:title},
			timeout:10*1000,    //超时时间 10s
			success : function(result) {
			   if(result){
				   initUserIndicatorlist();
			   }else{
				   alert("指标保存失败");
			   }
			},
			error:function(){
				alert("指标保存失败");

			},
			complete:function(XMLHttpRequest,status){
				if(status=='timeout'){
					alert("请求超时，请检查网络后重试");
				}
				jp.close();
			}
		});
	}

	function saveSingleUserIndicator(id,type,title) {
	jp.loading();
	$.ajax({
		type : 'POST',
		async : true,
		url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/saveUserIndicator",
		data:{resourceIndicatorlistId:id,type:type,title:title},
		timeout:10*1000,    //超时时间 10s
		success : function(result) {
			if(result){
				initSingleUserIndicatorlist();
			}else{
				alert("指标保存失败");
			}
		},
		error:function(){
			alert("指标保存失败");

		},
		complete:function(XMLHttpRequest,status){
			if(status=='timeout'){
				alert("请求超时，请检查网络后重试");
			}
			jp.close();
		}
	});
	}

	function initUserIndicatorlist() {
	$.ajax({
		type : 'POST',
		async : true,
		url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/getUserIndicator",
		data:{type:1},
		timeout:10*1000,    //超时时间 10s
		success : function(result) {
            $("#title").html(result["title"]);
            result=result["list"];
				var html = "";
				$("#indicatorsList").html(html);
            //console.log("==================");
            //console.log(result);
				for (var i = 0; i < result.length; i++) {
				    var value="-";
				    if(result[i].value){
                        value= result[i].value+ result[i].indicator.unit;
                    }
					html = html + "<tr><td>" + result[i].resource.name + "</td><td>" + result[i].indicator.name + "</td><td>" + value  + "</td></tr>";
				}
            //console.log(html);
				$("#indicatorsList").html(html);
		},
		error:function(){
		},
		complete:function(XMLHttpRequest,status){

		}
	});
	}

	function initSingleUserIndicatorlist() {
	$.ajax({
		type : 'POST',
		async : true,
		url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/getUserSingleIndicator",
		data:{type:2},
		timeout:10*1000,    //超时时间 10s
		success : function(result) {
		    if(result["title"]){
                $("#singTitle").html("("+result["title"]+")指标一览");
            }else{
                $("#singTitle").html("单一指标一览");
            }

            result=result["list"];
				var html = "";
				for (var i = 0; i < result.length; i++) {
                    var value="-";
                    if(result[i].value){
                        value= result[i].value+result[i].indicator.unit;
                    }

					html = html + "<tr><td>" + result[i].indicator.name + "</td><td style='text-align: right'>" + value + "</td></tr>";
				}
				$("#userSingleIndicator").html(html);
		},
		error:function(){
		},
		complete:function(XMLHttpRequest,status){

		}
	});
	}



     var exceptionData;
	//异常
	function getExceptionList() {
	$.ajax({
		type : 'POST',
		async : true,
		url :  "${ctx}/exception/exception/resourceException/exceptionList",
		data:{},
		timeout:10*1000,    //超时时间 10s
		success : function(result) {
            var html = "";
            exceptionData = result;
            console.log("==================");
            console.log(result);

			for (var i = 0; i < exceptionData.length; i++) {
				html = html + "<tr><td><a type='button' data-toggle='modal' data-target='#exceptionDetail'> <b onclick='showExceptionDetail("+i+")'>" + exceptionData[i].ename + "<b></a></td><td>"+ exceptionData[i].efrom +"</td><td>" + exceptionData[i].eclass + "</td>" +
					"<td><a class='' href='#' title='手动恢复' onclick='manualValidation(this)' attr-data='"+exceptionData[i].id+"' attr-data='"+exceptionData[i].estatus+"'><i class='fa fa-undo btn-lg'></i></a>\n" +
					"<a class='' href='#' title='删除' onclick='manualDelete(this)' attr-data='"+exceptionData[i].id+"'><i class='fa fa-trash btn-lg'></i></a></td></tr>";
			}
            $("#getExceptionList").html(html);
		},
		error:function(){
		},
		complete:function(XMLHttpRequest,status){
		}
	});
	}
	function manualValidation(obj){//手动恢复
		var manualValidationId = $(obj).attr("attr-data");

		var status = $(obj).attr("attr-status");
        $.ajax({
            type : 'POST',
            async : true,
            url :  "${ctx}/exception/exception/resourceException/indexManualRecovery",
            data:{id:manualValidationId,currentStatus:status},
            timeout:10*1000,    //超时时间 10s
            success : function(result) {
                getExceptionList();
            },
            error:function(){
            },
            complete:function(XMLHttpRequest,status){

            }
        });
	}
	function manualDelete(obj){//删除
        var manualValidationId = $(obj).attr("attr-data");
        jp.confirm('确认要删除该异常告警记录吗？', function(){
            jp.loading();
            jp.get("${ctx}/exception/exception/resourceException/delete?id="+manualValidationId, function(data){
                if(data.success){
                    getExceptionList();
                    jp.success(data.msg);
                }else{
                    jp.error(data.msg);
                }
            })
        });
	}



function showExceptionDetail(i) {
    $("#ename").html(exceptionData[i].ename);
    $("#efrom").html(exceptionData[i].efrom);
    $("#etotalNum").html(exceptionData[i].etotalNum);
    $("#eclass").html(exceptionData[i].eclass);
    $("#evalue").html(exceptionData[i].evalue);
    $("#estatus").html(exceptionData[i].estatus);
    $("#efirstTimeValue").html(exceptionData[i].efirstTimeValue);
    $("#eindicator").html(exceptionData[i].eindicator);
    $("#resourceType").html(exceptionData[i].resourceType);
    $("#elastTime").html(exceptionData[i].elastTime);
    $("#event").html(exceptionData[i].event);
    $("#econfirmStatus").html(exceptionData[i].econfirmStatus);
    $("#remarks").html(exceptionData[i].remarks);

    }


</script>

</body>
</html>