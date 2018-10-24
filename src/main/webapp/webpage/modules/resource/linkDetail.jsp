
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>${resource.name}-资源详情</title>
	<style>
		*, *:before, *:after {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
		}

		html, body {
			height: 100%;
		}

		body {
			font: 14px/1 'Open Sans', sans-serif;
			color: #555;
			background: #eee;
		}

		h1 {
			padding: 50px 0;
			font-weight: 400;
			text-align: center;
		}

		p {
			margin: 0 0 20px;
			line-height: 1.5;
		}

		main {
			min-width: 320px;
			max-width: 800px;
			padding: 50px;
			margin: 0 auto;
			background: #fff;
		}

		section {
			display: none;
			padding: 20px 0 0;
			border-top: 1px solid #ddd;
		}

		input {
			display: none;
		}

		label {
			display: inline-block;
			margin: 0 0 -1px;
			padding: 15px 25px;
			font-weight: 600;
			text-align: center;
			color: #bbb;
			border: 1px solid transparent;
		}

		label:before {
			font-family: fontawesome;
			font-weight: normal;
			margin-right: 10px;
		}

		label[for*='1']:before {
			content: '\f1cb';
		}

		label[for*='2']:before {
			content: '\f17d';
		}

		label[for*='3']:before {
			content: '\f16b';
		}

		label[for*='4']:before {
			content: '\f1a9';
		}

		label:hover {
			color: #888;
			cursor: pointer;
		}

		input:checked + label {
			color: #555;
			border: 1px solid #ddd;
			border-top: 2px solid orange;
			border-bottom: 1px solid #fff;
		}

		#tab1:checked ~ #monitor,
		#tab2:checked ~ #content2,
		#tab3:checked ~ #content3,
		#tab4:checked ~ #content4 {
			display: block;
		}
		@media screen and (min-width: 992px ){
			.gauge-big{
				margin-top: 80px !important;
			}
		}
		@media screen and (max-width: 650px) {
			label {
				font-size: 0;
			}

			label:before {
				margin: 0;
				font-size: 18px;
			}
		}
		@media screen and (max-width: 400px) {
			label {
				padding: 15px;
			}
		}

		#monitorTop1 {
			float: left;
			width: 25%;
			background-color: #ffffff;
			padding: 20px;
			height: 700px;
		}
		#monitorTop2 {
			float: left;
			width: 25%;
			background-color: #ffffff;
			padding: 20px;
			height: 700px;
		}
		#monitorTop3 {
			background-color: #ffffff;
			float: right;
			width: 25%;
			padding: 20px;
			height: 700px;
		}
		#monitorTop4 {
			background-color: #ffffff;
			float: right;
			width: 25%;
			padding: 20px;
			height: 700px;
		}

		.cpu-content{
			width:100%;
			display: flex;
			flex-direction: row;
			justify-content: flex-start;
			flex-wrap: wrap;

		}
		.cpu-list{
			height:320px;
			flex: 1;
			margin:14px;
		}
		.tit{
			background: #fafafa;
		}
		.td-hover:hover{
			background: #fafafa;
		}
		td{
			min-width: 60px;
		}
		.fixed-table-body{
			padding:15px;
		}

	</style>

	<meta name="decorator" content="ani"/>
	<link rel="stylesheet" href="${ctxStatic}/common/css/main.css" type="text/css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/blueimp-gallery.min.css" type="text/css">
	<script src="${ctxStatic}/plugin/echarts3/echarts.min.js"></script>
	<script src="${ctxStatic}/common/js/jquery.blueimp-gallery.min.js"></script>
	<script type="text/javascript">
        
		$(document).ready(function() {

		});




	</script>
	<style>

		.fixed-table-body{
			border-bottom:1px solid #dadada;
		}
		.h-title{
			background: #fafafa;
		}
		.body{
			background: #fff;
		}

		@media screen and (min-width: 980px) {
			.gauge{
				width:100% !important;
				display: inline-block;
				margin:0;
			}
			.gauge-chart{
				width:48%;
				height:220px;
				margin:0;
				background: #fff;
				display: inline-block;
			}
		}
		@media screen and (max-width: 980px) and (min-width: 500px) {
			.gauge{
				width:48% !important;
				display: inline-block;
				margin:0;
			}
			.gauge-chart{
				width:48%;
				height:220px;
				margin:0;
				background: #fff;
				display: inline-block;
			}
		}
		@media screen and (max-width: 500px) {
			.gauge{
				width:100% !important;
				display: inline-block;
				margin:0;
			}
			.gauge-chart{
				width:48%;
				height:220px;
				margin:0;
				background: #fff;
				display: inline-block;
			}
		}
		.gauge-box{
			display: flex;
			justify-content: space-around;
			flex-wrap:wrap;
		}
	</style>
</head>
<body class="body">


<div class="panel panel-primary" style="border:0;">
	<input value="${resource.id}" id="resourceId" type="hidden">
	<input value="${resource.name}" id="resourceName" type="hidden">

	<input id="tab1" type="radio" name="tabs" checked>



	<section id="monitor" style="padding-top:0;">

		<div class="fixed-table-body">
			<h4 class="table-title">基本信息</h4>
			<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead><tr><th>名称</th><th>容量</th><th>上联设备</th><th>上连接接口</th><th>下联设备</th><th>下连接接口</th></tr></thead>
				<tbody>

			      <tr>
					  <th><a type="button" class="" data-toggle="modal" data-target="#informationNameEdit" class="btn edit tableEdit" id="resourceNameSection">${linkIndicator.resource.name}</a></th>
					  <th>${linkIndicator.capacity}</th>
					  <th>${linkIndicator.upResource.name}</th>
					  <th>${linkIndicator.upInterface.name}</th>
					  <th>${linkIndicator.downResource.name}</th>
					  <th>${linkIndicator.downInterface.name}</th>
				  </tr>

			</tbody>
			</table>
		</div>



		<div class="modal inmodal" id="informationNameEdit" tabindex="-1" role="dialog" aria-hidden="true" >
			<div class="modal-dialog" style="width:400px;">
				<div class="modal-content animated bounceInRight" style="width:400px;">
					<div class="modal-body" style="width:100%">
						<div id="editView" style="width:100%;height:120px">
							<h4>编辑资源名称</h4>
							<div style="margin-top: 32px;text-align: center">
								<input id="informationResourceName" value="${linkIndicator.resource.name}" class=" form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white"  data-dismiss="modal" onclick="saveResourceName()" >保存</button>
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>


		<script>
            function saveResourceName() {
                if($("#informationResourceName").val().trim().length==0){
                    return;
                }
                $.ajax({
                    type : 'POST',
                    timeout:10*1000,    //超时时间 10s
                    url :  "${ctx}/resource/resource/updateResourceName",
                    data : { resourceId: $("#resourceId").val(), name : $("#informationResourceName").val()},
                    success : function(result) {
                        if(result){
                            $("#resourceNameSection").html($("#informationResourceName").val());
                            window.opener.myRefresh();
                            //$("#titleId").html($("#informationResourceName").val()+"-资源详情");
                            // window.location.reload();
                        }else{
                            alert("保存失败");
                        }
                    },
                    error:function(){
                    }
                });
            }
		</script>




		<div class="fixed-table-body" >
			<h4 class="table-title">实时一览</h4>
			<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead><tr><th>状态</th><th>容量</th><th>上行速率</th><th>下行速率</th><th>上行利用率</th><th>下行利用率</th><th>健康度</th><th>可用率</th></tr></thead>
				<tbody>

					<tr>
						<td>${linkIndicator.status}</td>
						<td>${linkIndicator.capacity}</td>
						<td>${linkIndicator.upRate}</td>
						<td>${linkIndicator.downRate}</td>
						<td>${linkIndicator.upUsedRate}</td>
						<td>${linkIndicator.downUsedRate}</td>
						<td>${linkIndicator.healthDegree}</td>
						<td>${linkIndicator.availability}</td>
					</tr>

				</tbody>
			</table>
		</div>




			<div class="modal inmodal" id="realTimeAnalysisModal" tabindex="-1" role="dialog" aria-hidden="true" >
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content animated bounceInRight" style="width:800px;">
						<div class="modal-body" style="width:100%">
							<div id="realTimeAnalysisView" style="width:750px;height:340px"></div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>


			<script>

			</script>




	</section>



</div>

</body>
</html>

