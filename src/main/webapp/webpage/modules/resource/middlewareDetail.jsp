
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
			min-width: 60px;
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

			//接口详情table列项伸缩
			$(".elastic-table").click(function(){
				$(".uncalled-for").toggleClass("uncalled-for-show");
			});
            getChart();
            initIndicatorList();
            findListByResource();

			//健康度
			var health = document.getElementById("health");
			var healthChart = echarts.init(health);
			var healthOption = {
				title: {
					text: '健康度',
					x: '50%',
					y: '45%',
					textAlign: "center",
					textStyle: {
						fontWeight: 'normal',
						fontSize: 16
					},
					subtextStyle: {
						fontWeight: 'bold',
						fontSize: 15,
						color: '#27C6C9'
					}
				},
				series: [{
					name: ' ',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#2EC7C9'
					}, {
						offset: 1,
						color: '#A0EDED'
					}]), "transparent"],
					hoverAnimation: false,
					legendHoverLink: false,
					itemStyle: {
						normal: {
							borderColor: "transparent",
							borderWidth: "10"
						},
						emphasis: {
							borderColor: "transparent",
							borderWidth: "10"
						}
					},
					z: 10,
					labelLine: {
						normal: {
							show: false
						}
					},
					data: [{
						value: 75
					}, {
						value: 25
					}]
				}, {
					name: '',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: ["#2EC7C9", "transparent"],
					labelLine: {
						normal: {
							show: false
						}
					},
					data: [{
						value: 75
					}, {
						value: 25
					}]
				}

				]
			};

			var value = $("#healthPercentValue").val(),
					value_ = 75 * value / 100;
			healthOption.title.subtext = value + "%";
			healthOption.series[0].data[0].value = value_;
			healthOption.series[0].data[1].value = 100 - value_;
			healthChart.setOption(healthOption, true);

			//可用率
			var availabilityCalculation = document.getElementById("availabilityCalculation");
			var acChart = echarts.init(availabilityCalculation);
			var acOption = {
				title: {
					text: '可用率',
					x: '50%',
					y: '45%',
					textAlign: "center",
					textStyle: {
						fontWeight: 'normal',
						fontSize: 16
					},
					subtextStyle: {
						fontWeight: 'bold',
						fontSize: 15,
						color: '#9B6FF0'
					}
				},
				series: [{
					name: ' ',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#A27CEA'
					}, {
						offset: 1,
						color: '#D1C4ED'
					}]), "transparent"],
					hoverAnimation: false,
					legendHoverLink: false,
					itemStyle: {
						normal: {
							borderColor: "transparent",
							borderWidth: "10"
						},
						emphasis: {
							borderColor: "transparent",
							borderWidth: "10"
						}
					},
					z: 10,
					labelLine: {
						normal: {
							show: false
						}
					},
					data: [{
						value: 75
					}, {
						value: 25
					}]
				}, {
					name: '',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: ["#B6A2DE", "transparent"],
					labelLine: {
						normal: {
							show: false
						}
					},
					data: [{
						value: 75
					}, {
						value: 25
					}]
				}

				]
			};

			var value = $("#availabilityPercentValue").val(),
					value_ = 75 * value / 100;
			acOption.title.subtext = value + "%";
			acOption.series[0].data[0].value = value_;
			acOption.series[0].data[1].value = 100 - value_;
			acChart.setOption(acOption, true);



			$(window).resize(function() {
				acChart.resize();
				healthChart.resize();
			});



		});
		//监控
        function getChart() {
            $.ajax({
                type : 'GET',
                url :  "${ctx}/resource/resource/getMiddlewareChart",
                timeout:10*1000,    //超时时间 10s
                data : {
                    resourceId : $("#resourceId").val()
                },
                success : function(result) {
                    var category = echarts.init(document.getElementById('category'));
					$(window).resize(function() {
						category.resize();
					});
                    var time = result.time;
                    var healthyRate = result.healthyRate;
                    var availabilityRate = result.availabilityRate;

					option = {
						tooltip: {
							trigger: 'axis',
							position: function (pt) {
								return [pt[0], '10%'];
							}
						},
						title: {
							left: 'center',
							text: '',
						},
						xAxis: {
							type: 'category',
							boundaryGap: false,
							data: time
						},
						yAxis: {
							type: 'value',
							name: '单位（%）',
							axisLine: {
								lineStyle: {
									color: '#57617B'
								}
							},
							axisLabel: {
								formatter: '{value}'
							}
						},
						dataZoom: [{
							type: 'inside',
							start: 0,
							end: 100
						}, {
							start: 0,
							end: 10,
							handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
							handleSize: '80%',
							handleStyle: {
								color: '#fff',
								shadowBlur: 3,
								shadowColor: 'rgba(0, 0, 0, 0.6)',
								shadowOffsetX: 2,
								shadowOffsetY: 2
							}
						}],
						series: [
							{
								name:'健康度',
								type:'line',
								smooth:true,
								symbol: 'none',
								sampling: 'average',
								itemStyle: {
									normal: {
										color: 'rgb(58,203,205)'
									}
								},
								data: healthyRate
							},
							{
								name:'可用率',
								type:'line',
								smooth:true,
								symbol: 'none',
								sampling: 'average',
								itemStyle: {
									normal: {
										color: 'rgb(166,130,234)'
									}
								},
								data: availabilityRate
							}
						]
					};
                    category.setOption(option);
                },
				error:function(){
                }

            });
        }


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
	<label for="tab1">监控</label>


	<input id="tab4" type="radio" name="tabs">
	<label for="tab4">故障列表</label>

	<section id="monitor" style="padding-top:0;">

		<div class="fixed-table-body" >
			<div class="row home-row">
				<div class="col-lg-8 col-md-7 perception-col">
					<div class="row">

					</div>
					<div id="category" style="width:100%;height:440px"></div>
				</div>
				<div class="col-lg-4 col-md-5 perception-col gauge-box">
					<div class="gauge gauge-big">
						<div class="gauge-chart" style="" id="health"></div>
						<input value="${healthPercent}" id="healthPercentValue" type="hidden"/>
						<div class="gauge-chart" style="" id="availabilityCalculation"></div>
						<input value="${availabilityPercent}" id="availabilityPercentValue" type="hidden"/>
					</div>
				</div>
			</div>
		</div>
		<div class="fixed-table-body">
			<h4 class="table-title pull-left">基本信息</h4>
			<table class="table" >
				<tbody>
				<tr>
					<td class="tit">名称</td><td class="td-hover"><a type="button" class="" data-toggle="modal" data-target="#informationNameEdit" class="btn edit tableEdit" id="resourceNameSection">${resource.name}</a></td>
					<td class="tit">资源类型</td><td class="td-hover">${resource.resourceType.name}</td>
					<td class="tit">归属法院</td><td class="td-hover">${resource.company.name}</td>
				</tr>
				<tr>
					<td class="tit">管理IP</td><td class="td-hover">${resource.ip}</td>
					<td class="tit">管理状态</td><td class="td-hover">${fns:getDictLabel(resource.status, 'resource_status', '')}</td>
					<td class="tit">端口</td><td class="td-hover">${resource.resourceBaseInfo.port}</td>
				</tr>

				<tr>
					<td class="tit">厂商</td><td class="td-hover">${resource.manufacturer.name}</td>
					<td class="tit">描述</td><td class="td-hover" colspan="6">${resource.description}</td>
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
								<input id="informationResourceName" value="${resource.name}" class=" form-control">
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
                        }else{
                            alert("保存失败");
                        }
                    },
                    error:function(){
                    }
                });
            }
		</script>







		<div class="fixed-table-body">
			<div class="h-title">
				<h4 class="table-title pull-left" id="indicatorsSizeView">指标列表</h4>
				<div class="pull-right ">
					<button  class="btn edit tableEdit" onclick="addIndicator()" >编辑</button>
					<div style="display: none;">
						<button  class="btn edit tableSave" onclick="" >保存</button>
						<button  class="btn edit tableCancel" onclick="" >取消</button>
					</div>
				</div>
			</div>
			<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead><tr><th>指标名称</th><th>事件类型</th><th>最新采集时间</th><th>当前值</th><th>操作</th></tr></thead>
				<tbody id="indicatorListView">

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
                function addIndicator() {
                    var url='${ctx}/indicator/indicator/showIndicatorList?resourceId='+$("#resourceId").val();             //转向网页的地址;
                    var name='添加指标';                  //网页名称，可为空;
                    var iWidth=1200;                          //弹出窗口的宽度;
                    var iHeight=800;                         //弹出窗口的高度;
                    //获得窗口的垂直位置
                    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                    //获得窗口的水平位置
                    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                    window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
                }

                function initIndicatorList() {
                    $.ajax({
                        type : 'GET',
                        url :  "${ctx}/resource/resource/initIndicatorList",
                        timeout:10*1000,    //超时时间 10s
                        data : {
                            resourceId : $("#resourceId").val()
                        },
                        success : function(result) {
                            var html="";
                            $("#indicatorsSizeView").html("指标列表("+result.length+")");
                            for(var i=0;i<result.length;i++){
                                html += "<tr><td>"+result[i].indicator.name+"</td>"+
                                    "<td>"+result[i].indicator.eventType+"</td>";
                                if(result[i].value){
                                    if(result[i].value=='-1') {
                                        html += "<td>" + result[i].updateDate + "</td><td><b style='color:red'>" + result[i].value + "" + result[i].indicator.unit + "<b/></td>";
                                    }else{
                                        html += "<td>" + result[i].updateDate + "</td><td>" + result[i].value + "" + result[i].indicator.unit + "</td>";
                                    }
                                }else{
                                   html +=  "<td>-</td><td>-</td>";
                                }
                                if((result[i].indicator.itemType == 41003||result[i].indicator.itemType == 42003||result[i].indicator.itemType == 51004||result[i].indicator.itemType == 52008)&&result[i].value){

                                    html +=   "<td> <a type='button' data-toggle='modal' data-target='#realTimeAnalysisModal'><i class='fa fa-bar-chart-o' title='实时分析' onclick='realTimeAnalysis("+result[i].indicator.itemType+")'></i></a></td>";

                                }else{
                                    html +=   "<td></td>";
                                }
                                html +=   "</tr>";
                            }
                            $("#indicatorListView").html("");
                            $("#indicatorListView").html(html);
                        },
                        error:function(){
                            $("#indicatorsSizeView").html("指标列表(0)");
                        }
                    });
                }
                function realTimeAnalysis(type){
                    $.ajax({
                        type : 'GET',
                        url :  "${ctx}/resource/resource/realTimeAnalysis",
                        timeout:10*1000,    //超时时间 10s
                        data : {
                            type:type,resourceId:$("#resourceId").val()
                        },
                        success : function(result) {
                            var view = echarts.init(document.getElementById('realTimeAnalysisView'));

                            var time = result.time;
                            var percent = result.percent;
                            var type = result.type;
                            var title="";
                            var unit="";
                            if(type==1){
                                title="平均CPU利用率";
                                unit="单位(%)"
                            }else if(type==2){
                                title="平均内存利用率";
                                unit="单位(%)"
                            }else if(type==9){
                                title="ICMP响应时间";
                                unit="单位(ms)"
                            }

                            option = {
                                tooltip: {
                                    trigger: 'axis',
                                    position: function (pt) {
                                        return [pt[0], '10%'];
                                    }
                                },
                                title: {
                                    left: 'center',
                                    text: title,
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: time
                                },
                                yAxis: {
                                    type: 'value',
                                    name: unit,
                                    axisLine: {
                                        lineStyle: {
                                            color: '#57617B'
                                        }
                                    },
                                    axisLabel: {
                                        formatter: '{value}'
                                    },
                                    boundaryGap: [0, '100%']
                                },
                                dataZoom: [{
                                    type: 'inside',
                                    start: 0,
                                    end: 100
                                }, {
                                    start: 0,
                                    end: 10,
                                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                                    handleSize: '80%',
                                    handleStyle: {
                                        color: '#fff',
                                        shadowBlur: 3,
                                        shadowColor: 'rgba(0, 0, 0, 0.6)',
                                        shadowOffsetX: 2,
                                        shadowOffsetY: 2
                                    }
                                }],
                                series: [
                                    {
                                        name:title,
                                        type:'line',
                                        smooth:true,
                                        symbol: 'none',
                                        sampling: 'average',
                                        itemStyle: {
                                            normal: {
                                                color: 'rgb(58,203,205)'
                                            }
                                        },
                                        areaStyle: {
                                            normal: {
                                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                                    offset: 0,
                                                    color: 'rgb(58,203,205)'
                                                }, {
                                                    offset: 1,
                                                    color: 'rgb(160,242,242)'
                                                }])
                                            }
                                        },
                                        data: percent
                                    }
                                ]
                            };
                            view.setOption(option);

                        },
                        error:function(){
                        }

                    });
                }
			</script>




		<%--承载业务--%>
        <%--  <div class="fixed-table-body">
            <div class="row">
                <div class="col-sm-12">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs">
                            <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">承载业务(0)</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div id="tab-4" class="tab-pane">
                                <table id="4" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>业务名称</th>
                                        <th>健康度</th>
                                        <th>异常等级</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>ApplicationFrameHost.exe</td>
                                        <td>42012</td>
                                        <td>0.00%</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>--%>

	</section>


	<section id="content4" style="padding: 20px;">
		<div class="result-lsist-contant">
			<div class="row search-container">
				<div class="col-xs-12 col-sm-6 col-md-2 search-list search-select">
					<div class="search-title">异常等级：</div>
					<div class="search-value">
						<select class="form-control" id="searchExceptionClass">
							<option value="">全部</option>
							<option value="0">提示</option>
							<option value="1">一般</option>
							<option value="2">较急</option>
							<option value="3">紧急</option>
						</select>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-3 search-list search-select">
					<a  onclick="findListByResource()" style="margin-right: 8px;" id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
					<a  onclick="reset()" id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
				</div>
			</div>
			<div class="clear">
				<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
					<tr>
						<th>指标名称</th>
						<th>异常来源</th>
						<th>异常等级</th>
						<th>当前指标</th>
						<th>第一次产生时间</th>
						<th>异常持续时间</th>
						<th>确认人</th>
						<th>地域</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody id="exceptionTbody">

					</tbody>
				</table>
			</div>
		</div>


		<script>
            function reset() {
                $("#searchExceptionClass").val("");
                findListByResource();
            }

            function findListByResource() {
                var exceptionClassValue=$("#searchExceptionClass").val();
                var html = "";
                $.ajax({
                    type : 'GET',
                    url :  "${ctx}/resource/resource/findListByResource",
                    timeout:10*1000,    //超时时间 10s
                    data : {
                        resourceId : $("#resourceId").val(), exceptionClass : exceptionClassValue
                    },
                    success : function(result) {
                        for(var i=0;i<result.length;i++){
                            html += "<tr><td>"+result[i].indicatorName+"</td><td>"+result[i].exceptionSource+"</td><td>"+result[i].exceptionClass+"</td>" +
                                "<td>"+result[i].currentValue+result[i].unit+"</td><td>"+result[i].firstTriggerTime+"</td>" +

									/*"<td>"+result[i].currentStatus+"</td>" +*/

                                "<td>"+result[i].duration+"</td><td>"+result[i].confirmUserName+"</td><td>"+result[i].area+"</td><td>";

                            var id='\"'+result[i].id+'\"';
                            if(result[i].currentStatusDict=='0'){
                                html+="<button class='btn btn-detail' onclick='manualRecovery("+id+")'>手动恢复</button>&nbsp;&nbsp;&nbsp;";
                            }
                            if(result[i].confirmStatusDict=='0'){
                                html+="<button class='btn btn-detail' onclick='confirmException("+id+")'>异常确认</button>&nbsp;&nbsp;&nbsp;";
                            }
                            html +="<button class='btn-detail btn' onclick='delException("+id+")'>删除</button></td></tr>";
                        }
                        $("#exceptionTbody").html(html)

                    },
                    error:function(){

                    },
                    complete:function(XMLHttpRequest,status){
                    }
                });
            }




            function manualRecovery(id) {
                $.ajax({
                    type : 'GET',
                    url :  "${ctx}/resource/resource/manualRecovery",
                    timeout:10*1000,    //超时时间 10s
                    data : {
                        resourceExceptionId : id
                    },
                    success : function(result) {
                        if(result) {
                            findListByResource();
                        }else{
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                    }

                });
            }
            function confirmException(id) {
                $.ajax({
                    type : 'GET',
                    url :  "${ctx}/resource/resource/confirmException",
                    timeout:10*1000,    //超时时间 10s
                    data : {
                        resourceExceptionId : id
                    },
                    success : function(result) {
                        if(result) {
                            findListByResource();
                        }else{
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                    }

                });
            }

            function delException(id) {
                $.ajax({
                    type : 'GET',
                    url :  "${ctx}/resource/resource/delException",
                    timeout:10*1000,    //超时时间 10s
                    data : {
                        resourceExceptionId : id
                    },
                    success : function(result) {
                        if(result) {
                            findListByResource();
                        }else{
                            alert("操作失败");
                        }
                    },
                    error:function(){
                        alert("操作失败");
                    }

                });
            }
		</script>
	</section>
</div>

</body>
</html>

