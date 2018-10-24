<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="ani"/>
	<link rel="stylesheet" href="${ctxStatic}/common/css/main.css" type="text/css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/uploader-img.css" type="text/css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/blueimp-gallery.min.css" type="text/css">
	<script src="${ctxStatic}/plugin/echarts3/echarts.min.js"></script>
	<script src="${ctxStatic}/common/js/jquery.blueimp-gallery.min.js"></script>
	<script  src="${ctxStatic}/common/jedate.js"></script>
	<style>

		#body-container {
			margin-left: 0px !important;
			margin-top: 0px !important;
			overflow-x: hidden!important;
			transition: all 0.2s ease-in-out !important;
			height: 100% !important;
		}
	</style>
</head>
<body class="">
<div id="body-container">

	          <!-- 搜索 -->
				<form id="searchForm" class="form form-horizontal well clearfix" >
					<div class="col-xs-12 col-sm-6 col-md-4">
						<label class="label-item single-overflow pull-left" title="日期：">日期：</label>
						<div class="col-xs-12">
							<div class="col-xs-12 col-sm-5">
								<div class='input-group date' id='date' style="left: -10px;" >
									<input type='text' id="dateValue"  name="date" class="form-control"  />
									<span class="input-group-addon">
								                       <span class="glyphicon glyphicon-calendar"></span>
								    </span>
								</div>
							</div>

						</div>
					</div>

					<div class="col-xs-12 col-sm-6 col-md-4">
						<div style="margin-top:26px">
							<a   class="btn btn-primary btn-rounded  btn-bordered btn-sm" onclick="init()"><i class="fa fa-search"></i> 查询</a>
							<a  class="btn btn-primary btn-rounded  btn-bordered btn-sm" onclick="reset()"><i class="fa fa-refresh"></i> 重置</a>

							&nbsp;&nbsp;&nbsp;&nbsp;
							<a  class="btn btn-primary btn-rounded  btn-bordered btn-sm" onclick="exportExcel()"><i class=" glyphicon glyphicon-share-alt"></i> 导出</a>
						</div>
					</div>
				</form>

	<div class="conter-wrapper home-container" style="width: 100%;margin-top: 10px;">
		<div  style="width: 45%;height: 450px;display: inline-block;"  id="cpu-chart"></div>

		<div  style="width: 45%;height: 450px;display: inline-block;"  id="mem-chart"></div>
	</div>

</div>


<script>
    $(function() {
        $('#date').datetimepicker({
            format: "YYYY-MM-DD"
        });
        init();
    });


    function exportExcel(){
        window.open("${ctx}/resource/resource/exportNetworkData?data="+$("#dateValue").val());
    };

	function reset() {
	   $("#dateValue").val("");
	   init();
	}

    function init() {
        var myChartCPU = echarts.init(document.getElementById('cpu-chart'));
        $.ajax({
            type : 'GET',
            async : true,
            url :  "${ctx}/resource/resource/getCpuChartData",
            data:{data:$("#dateValue").val(),resourceTypeId:"0adbb9f2a71343c280fecb64c932b667"},
            timeout:10*1000,    //超时时间 10s
            success : function(result) {

                var name = result.name;
                var cpuAvg = result.cpuAvg;
                var cpuTop = result.cpuTop;

                var optionCpu = {
                    color: ['#4cabce', '#e5323e'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['CPU平均值', 'CPU最高值']
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            axisTick: {show: false},
                            data: name
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: 'CPU平均值',
                            type: 'bar',
                            barGap: 0,
                            data: cpuAvg
                        },
                        {
                            name: 'CPU最高值',
                            type: 'bar',
                            data: cpuTop
                        }
                    ]
                };
                myChartCPU.setOption(optionCpu);

            },
            error:function(){
            }
        });




        var myChartMEM = echarts.init(document.getElementById('mem-chart'));

        $.ajax({
            type : 'GET',
            async : true,
            url :  "${ctx}/resource/resource/getMemChartData",
            data:{data:$("#dateValue").val(),resourceTypeId:"0adbb9f2a71343c280fecb64c932b667"},
            timeout:10*1000,    //超时时间 10s
            success : function(result) {

                var name = result.name;
                var memAvg = result.memAvg;
                var memTop = result.memTop;


                var optionMem = {
                    color: ['#4cabce', '#e5323e'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['内存平均值', '内存最高值']
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    xAxis: [
                        {
                            type: 'category',
                            axisTick: {show: false},
                            data: name
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '内存平均值',
                            type: 'bar',
                            barGap: 0,
                            data: memAvg
                        },
                        {
                            name: '内存最高值',
                            type: 'bar',
                            data: memTop
                        }
                    ]
                };
                myChartMEM.setOption(optionMem);

            },
            error:function(){
            }
        });
    }

</script>

</body>
</html>