
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>${resource.name}-资源详情</title>
	<meta name="decorator" content="ani"/>
	<link rel="stylesheet" href="${ctxStatic}/common/css/main.css" type="text/css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/uploader-img.css" type="text/css">
	<link rel="stylesheet" href="${ctxStatic}/common/css/blueimp-gallery.min.css" type="text/css">
	<script src="${ctxStatic}/plugin/echarts3/echarts.min.js"></script>
	<script src="${ctxStatic}/common/js/jquery.blueimp-gallery.min.js"></script>
	<script  src="${ctxStatic}/common/jedate.js"></script>
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


		/*img*/



	</style>

	<script type="text/javascript">

        var virtualTotal="0GB";
        var virtualUserd="0GB";
        var virtualUsedPercent=0;

        var physicalTotal="0GB";
        var physicalUserd="0GB";
        var physicalUsedPercent=0;
        
		$(document).ready(function() {


            initNetworkInterfaceListView();

			//接口详情table列项伸缩
			$(".elastic-table").click(function(){
				$(".uncalled-for").toggleClass("uncalled-for-show");
			});

            getCpuInfo();
            getChart();
            initIndicatorList();

            findListByResource(); //异常列表

            $(".virtualValue").each(function (index,obj) {
                var thisVal=0;
                if($(this).val()!=null&&$(this).val()!=''){
                    thisVal=$(this).val();
                }
                if(index==0){
                    virtualTotal=thisVal+"GB";
				}else if(index==1){
                    virtualUserd=thisVal+"GB";
                }
                else if(index==2){
                    virtualUsedPercent=thisVal;
                }
            })

            $(".physicalValue").each(function (index,obj) {
                var thisVal=0;
                if($(this).val()!=null&&$(this).val()!=''){
                    thisVal=$(this).val();
				}


                if(index==0){
                    physicalTotal=thisVal+"GB";
                }else if(index==1){
                    physicalUserd=thisVal+"GB";
                }
                else if(index==2){
                    physicalUsedPercent=thisVal;
                }
            })

            //物理内存
            var physical = echarts.init(document.getElementById('physicalMemory'));

			window.onresize = function() {
				physical.resize();
			 };
			phoption = {
				title: {
					text: '物理内存'+physicalTotal,
					subtext: '',
					left: 'center',
					padding: [20, 0],
					link: ''
				},
				backgroundColor: '',
				tooltip: {
					formatter: '<div style="text-align: center;">已使用物理内存</div>{b} : {c}'
				},
				/*toolbox: {
				 feature: {
				 restore: {},
				 saveAsImage: {}
				 }
				 },*/
				series: [{
					axisLine: {
						show: true,
						lineStyle: {
							color: [
								[0.166, '#91c7ae'],
								[0.5, '#63869e'],
								[0.835, '#EFC631'],
								[1, '#c23531']
							],
							width: 8
						}
					},
					axisTick: {            // 坐标轴小标记
						length:8,        // 属性length控制线长
						lineStyle: {       // 属性lineStyle控制线条样式
							color: 'auto'
						}
					},
					splitLine: {           // 分隔线
						length:12,         // 属性length控制线长
						lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
							color: 'auto'
						}
					},
					axisLabel: {
						distance: 6,
						textStyle: {
							color: 'auto'
						}
					},
					itemStyle: {
						normal: {
							color: 'auto'
						}
					},
					radius: '60%',
					pointer: {
						width: 10
					},
					title: {
						offsetCenter: [0, '90%']
					},
					detail: {
						textStyle: {
							fontWeight: 'bolder',
							fontSize: 23,
							color: 'black'
						},
						offsetCenter: [0, '58%'],
						formatter: '{value}'
					},
					min: 0,
					max: 100,
					name: '物理内存',
					type: 'gauge',
					show: false,
					splitNumber: 10,
					data: [{
						value: physicalUsedPercent,
						name: '已使用物理内存'+physicalUserd
					}]
				}]
			};


			physical.setOption(phoption, true);

			//虚拟内存
			var virtual = echarts.init(document.getElementById('virtualMemory'));

			window.onresize = function() {
				virtual.resize();
			};

			vmoption = {
				title: {
					text: '虚拟内存'+virtualTotal,
					subtext: '',
					left: 'center',
					padding: [20, 0],
					link: ''
				},
				backgroundColor: '',
				tooltip: {
					formatter: '<div style="text-align: center;">已使用虚拟内存</div>{b} : {c}'
				},
				/*toolbox: {
				 feature: {
				 restore: {},
				 saveAsImage: {}
				 }
				 },*/
				series: [{
					axisLine: {
						show: true,
						lineStyle: {
							color: [
								[0.166, '#91c7ae'],
								[0.5, '#63869e'],
								[0.835, '#EFC631'],
								[1, '#c23531']
							],
							width: 8
						}
					},
					axisTick: {            // 坐标轴小标记
						length:8,        // 属性length控制线长
						lineStyle: {       // 属性lineStyle控制线条样式
							color: 'auto'
						}
					},
					splitLine: {           // 分隔线
						length:12,         // 属性length控制线长
						lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
							color: 'auto'
						}
					},
					axisLabel: {
						distance: 6,
						textStyle: {
							color: 'auto'
						}
					},
					itemStyle: {
						normal: {
							color: 'auto'
						}
					},
					radius: '60%',
					pointer: {
						width: 10
					},
					title: {
						offsetCenter: [0, '90%']
					},
					detail: {
						textStyle: {
							fontWeight: 'bolder',
							fontSize: 23,
							color: 'black'
						},
						offsetCenter: [0, '58%'],
						formatter: '{value}'
					},
					min: 0,
					max: 100,
					name: '虚拟内存',
					type: 'gauge',
					show: false,
					splitNumber: 10,
					data: [{
						value: virtualUsedPercent,
						name: '已使用虚拟内存'+virtualUserd
					}]
				}]
			};
			virtual.setOption(vmoption, true);



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


			//CPUCalculation
			var CPUCalculation = document.getElementById("CPUCalculation");

			var CPUChart = echarts.init(CPUCalculation);
			var CPUOption = {
				title: {
					text: 'CPU利用率',
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
						color: '#3ea1ff'
					}
				},
				series: [{
					name: ' ',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#5AB1EF'
					}, {
						offset: 1,
						color: '#A7D6F5'
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
					color: ["#AAD8FC", "transparent"],
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


				var value = $("#cpuUsedPercentValue").val(),
						value_ = 75 * value / 100;
				CPUOption.title.subtext = value + "%";
				CPUOption.series[0].data[0].value = value_;
				CPUOption.series[0].data[1].value = 100 - value_;
				CPUChart.setOption(CPUOption, true);




			//MEMCalculation
			var calculation = document.getElementById("MEMCalculation");
			var MEMChart = echarts.init(calculation);
			var MEMOption = {
				title: {
					text: 'MEM利用率',
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
						color: '#F59B4D'
					}
				},
				series: [{
					name: ' ',
					type: 'pie',
					radius: ['50%', '70%'],
					startAngle: 225,
					color: [new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: '#F79B52'
					}, {
						offset: 1,
						color: '#F5D6BC'
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
					color: ["#FAD6BC", "transparent"],
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
				var value = $("#MEMCalculationValue").val(),
						value_ = 75 * value / 100;
				MEMOption.title.subtext = value + "%";
				MEMOption.series[0].data[0].value = value_;
				MEMOption.series[0].data[1].value = 100 - value_;
				MEMChart.setOption(MEMOption, true);

			$(window).resize(function() {
				MEMChart.resize();
				CPUChart.resize();
				acChart.resize();
				healthChart.resize();
			});



		});



		//CPU信息
		function getCpuInfo() {
            $("#cpuInfoParent").empty();
            $("#cpuInfoParent").html("<div id='cpuInfo' style='height: 360px;'></div>");

            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/resource/resource/getCpuInfo",
                timeout:10*1000,    //超时时间 10s
                data : {
                    id : $("#resourceId").val()
                },
                success : function(result) {
                   var cpus = echarts.init(document.getElementById('cpuInfo'));
                    window.onresize = function() {
                        cpus.resize();
                    };
                    var cpuIndex = result.cpuIndex;
                    var cpuValue = result.cpuValue;
                    var cpuNum = result.cpuNum;
                    var startNum;
                    var endNum;
                    if(cpuNum[0]<=8){
                        startNum = 0;
                        endNum = 100;
					}else if(cpuNum[0] < 32 ){
                        startNum = 10;
                        endNum = 60;
					}else{
                        startNum = 10;
                        endNum = 40;
					}
                    var title="CPU("+cpuNum[0]+"核心)";

                    option = {
                        backgroundColor:"#fff",
                        title: {
                            text: title,
                            subtext: '单位（%）',
                            sublink: ''
                        },
                        color: ['#C03A38'],
                        tooltip : {
                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '40px',
                            containLabel: true
                        },
                        xAxis : [
                            {
                                type : 'category',
                                data : cpuIndex,
                                axisTick: {
                                    alignWithLabel: true
                                }
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
								max: 100,
								min: 0,
								scale: true
                            }
                        ],
                        dataZoom: [{ // 这个dataZoom组件，默认控制x轴。
                            type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
							width: '70%',
							right:'15%',
							left:'15%',
                            start: startNum, // 左边在 10% 的位置。
                            end: endNum // 右边在 60% 的位置。
                        }],
                        series : [
                            {
                                name:'CPU',
                                type:'bar',
                                barWidth: '20px',
                                itemStyle: {
                                    "normal": {
                                        "color": "#C03A38",
                                        "barBorderRadius": 0,
                                        "label": {
                                            "show": true,
                                            "position": "top",
                                            formatter: function(p) {
                                                return p.value > 0 ? (p.value) : '';
                                            }
                                        }
                                    }
                                },
                                data:cpuValue
                            }
                        ]
                    };
                    cpus.setOption(option);
                }

            });
        }





		//监控
        function getChart() {
            $.ajax({
                type : 'GET',
                async : true,
                url :  "${ctx}/resource/resource/getChart",
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
                    var cpuUsedRate = result.cpuUsedRate;
                    var memoryUsedRate = result.memoryUsedRate;
                    var availabilityRate = result.availabilityRate;
					/*option = {
						backgroundColor: '#fff',
						color: ['#2EC7C9','#B6A2DE','#5AB1EF' ,'#FFB980'],
						title: {
							text: $("#resourceName").val()+'-监控',
							textStyle: {
								fontWeight: 'normal',
								fontSize: 16,
								color: '#57617B'
							},
							left: '6%'
						},
						tooltip: {
							trigger: 'axis',
							axisPointer: { // 坐标轴指示器，坐标轴触发有效
								type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
							}
						},
						tooltip: {
							trigger: 'axis',
							axisPointer: {
								lineStyle: {
									color: '#57617B'
								}
							}
						},
						legend: {
							icon: 'rect',
							itemWidth: 14,
							itemHeight: 5,
							itemGap: 13,
							data: [ '健康度','可用率','CPU利用率', 'MEM利用率'],
							right: '4%',
							textStyle: {
								fontSize: 12,
								color: '#57617B'
							}
						},
						grid: {
							left: '3%',
							right: '4%',
							bottom: '3%',
							containLabel: true
						},
						xAxis: [{
							type: 'category',
							axisLine: {
								lineStyle: {
									color: '#57617B'
								}
							},
							data: time
						}],
						yAxis: [{
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
						}],
						series: [{
							name: '健康度',
							type: 'bar',
							data: healthyRate
						}, {
							name: '可用率',
							type: 'bar',
							data: availabilityRate
						}, {
							name: 'CPU利用率',
							type: 'bar',
							data: cpuUsedRate
						}, {
							name: 'MEM利用率',
							type: 'bar',
							data: memoryUsedRate
						}]
					};*/


					option = {
						tooltip: {
							trigger: 'axis',
							position: function (pt) {
								return [pt[0], '10%'];
							}
						},
						title: {
							text: "",
							textStyle: {
								fontWeight: 'normal',
								fontSize: 16,
								color: '#57617B'
							},
							left: '4%',
							top:'0'
						},
						/*toolbox: {
							feature: {
								dataZoom: {
									yAxisIndex: 'none'
								},
								restore: {},
								saveAsImage: {}
							}
						},*/
						/*grid: [
							{x: '4%', y: '15%', width: '95%', height: '70%'},
						],*/
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
							},
							//boundaryGap: [0, '100%']
						},
						dataZoom: [{
							type: 'inside',
							start: 0,
							end: 100
						}, {
							start: 0,
							end: 100,
							handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
							handleSize: '100%',
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
								/*areaStyle: {
									normal: {
										color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
											offset: 0,
											color: 'rgb(58,203,205)'
										}, {
											offset: 1,
											color: 'rgb(160,242,242)'
										}])
									}
								},*/
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
								/*areaStyle: {
									normal: {
										color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
											offset: 0,
											color: 'rgb(166,130,234)'
										}, {
											offset: 1,
											color: 'rgb(255, 255, 255)'
										}])
									}
								},*/
								data: availabilityRate
							},
							{
								name:'CPU利用率',
								type:'line',
								smooth:true,
								symbol: 'none',
								sampling: 'average',
								itemStyle: {
									normal: {
										color: '#5AB1EF'
									}
								},
								/*areaStyle: {
									normal: {
										color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
											offset: 0,
											color: 'rgb(155, 58, 68)'
										}, {
											offset: 1,
											color: 'rgb(255, 70, 131)'
										}])
									}
								},*/
								data: cpuUsedRate
							},
							{
								name:'MEM利用率',
								type:'line',
								smooth:true,
								symbol: 'none',
								sampling: 'average',
								itemStyle: {
									normal: {
										color: '#F79B52'
									}
								},
								/*areaStyle: {
									normal: {
										color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
											offset: 0,
											color: 'rgb(155, 58, 68)'
										}, {
											offset: 1,
											color: 'rgb(255, 70, 131)'
										}])
									}
								},*/
								data: memoryUsedRate
							}
						]
					};
                    category.setOption(option);
                },
				error:function(){
                }

            });
        }



		function getSpeedChart(netInterfaceId,type) {
			$.ajax({
				type : 'GET',
				async : true,
				url :  "${ctx}/resource/resource/getNetInterfaceRateChart",
                timeout:10*1000,    //超时时间 10s
				data : {
					netInterfaceId : netInterfaceId,type:type
				},
				success : function(result) {
					var category = echarts.init(document.getElementById('netInterfaceRateView'));

					var time = result.time;
					var rate = result.rate;

					option = {
						tooltip: {
							trigger: 'axis',
							position: function (pt) {
								return [pt[0], '10%'];
							}
						},
						title: {
							left: 'center',
							text: '接口速率',
						},
						/*	toolbox: {
						 feature: {
						 dataZoom: {
						 yAxisIndex: 'none'
						 },
						 restore: {},
						 saveAsImage: {}
						 }
						 },*/
						xAxis: {
							type: 'category',
							boundaryGap: false,
							data: time
						},
						yAxis: {
							type: 'value',
							name: '单位（Kbps）',
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
								name:'接口速率',
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
								data: rate
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

	<input id="tab2" type="radio" name="tabs">
	<label for="tab2">CMDB</label>

	<%--<input id="tab3" type="radio" name="tabs">
	<label for="tab3">接口详情</label>--%>

	<input id="tab4" type="radio" name="tabs">
	<label for="tab4">故障列表</label>

	<section id="monitor" style="padding-top:0;">
		<%--<div style="background: #fff;">
			<div id="monitorTop1">
			</div>
			<div id="monitorTop2" style="text-align:center; "><img src="${ctxStatic}/test.png"/></div>
		     <div id="monitorTop3" style="text-align:center; "><img  src="${ctxStatic}/test.png"/></div>
		     <div id="monitorTop4" style="text-align:center; "><img  src="${ctxStatic}/test.png"/></div>
		</div>--%>

		<div class="fixed-table-body" >
			<div class="row home-row">
				<div class="col-lg-8 col-md-7 perception-col">
					<div class="row">

					</div>
					<div id="category" style="width:100%;height:440px"></div>
				</div>
				<div class="col-lg-4 col-md-5 perception-col gauge-box">
					<div class="gauge">
						<div class="gauge-chart" style="" id="health"></div>
						<input value="${healthPercent}" id="healthPercentValue" type="hidden"/>
						<div class="gauge-chart" style="" id="availabilityCalculation"></div>
						<input value="${availabilityPercent}" id="availabilityPercentValue" type="hidden"/>
					</div>
					<div class="gauge">
						<div class="gauge-chart" style="" id="CPUCalculation"></div>
						<input value="${cpuUsedPercent}" id="cpuUsedPercentValue" type="hidden"/>
					    <div class="gauge-chart" style="" id="MEMCalculation"></div>
						<input value="${memoryUsedPercent}" id="MEMCalculationValue" type="hidden"/>
					</div>
				</div>
			</div>
		</div>
		<div class="fixed-table-body">
			<h4 class="table-title pull-left">基本信息</h4>
			<table class="table" >
				<tbody>
				<tr>
					<td class="tit">名称</td><td class="td-hover" id="resourceNameSection">${resource.name}</td>
					<td class="tit">资源类型</td><td class="td-hover">${resource.resourceType.name}</td>
					<td class="tit">归属公司</td><td class="td-hover">${resource.company.name}</td>
				</tr>
				<tr>
					<td class="tit">管理IP</td><td class="td-hover">${resource.ip}</td>
					<td class="tit">端口</td><td class="td-hover">${resource.resourceBaseInfo.port}</td>
					<td class="tit">读共同体</td><td class="td-hover" id="resourceRdcommunitySection" >${resource.resourceBaseInfo.rdcommunity}</td>

				</tr>
				<tr>
					<td class="tit">管理方式</td><td class="td-hover">SNMPV${resource.resourceBaseInfo.managerType}</td>
					<td class="tit">系统名称</td><td class="td-hover">${resource.sysName}</td>
					<td class="tit">子网掩码</td><td class="td-hover">${resource.subnetmask}</td>
				</tr>

				<tr>
					<td class="tit">管理状态</td><td class="td-hover">${fns:getDictLabel(resource.status, 'resource_status', '')}</td>
					<td class="tit">厂商</td><td class="td-hover">${resource.manufacturer.name}</td>
					<td class="tit">操作系统</td><td class="td-hover">
					<c:if test="${resource.operatingSystemId != null}">
						${operatingSystemId.name}
					</c:if>
				     </td>
				</tr>

				<tr>
					<td class="tit">系统OID</td><td class="td-hover">${resource.sysOid}</td>
					<td class="tit">MAC地址</td><td class="td-hover">${resource.mac}</td>
					<td class="tit">型号</td><td class="td-hover">${resource.model}</td>
				</tr>
				<tr>
					<td class="tit">描述</td><td class="td-hover" colspan="6">${resource.description}</td>
				</tr>
				</tbody>
			</table>
		</div>


		<div class="fixed-table-body">
			<h4 class="table-title">CPU、MEM</h4>
			<div class="row">
				<div id="cpuInfoParent" class="col-lg-5 col-md-5 col-sm-12"></div>

                 <c:forEach items="${virtualPhysicalMemoryList}" var="item" varStatus="index">
					 <c:if test="${item.type =='2'}">
						 <input class="virtualValue" type="hidden" value="${item.total}"/>
						 <input class="virtualValue" type="hidden" value="${item.used}"/>
						 <input class="virtualValue" type="hidden" value="${item.usedRate}"/>
					 </c:if>
					 <c:if test="${item.type =='1'}">
						 <input class="physicalValue" type="hidden" value="${item.total}"/>
						 <input class="physicalValue" type="hidden" value="${item.used}"/>
						 <input class="physicalValue" type="hidden" value="${item.usedRate}"/>
					 </c:if>
				 </c:forEach>
				<div id="physicalMemory" class="col-lg-3 col-md-3 col-sm-6" style="height: 320px;display: inline-block"></div>
				<div id="virtualMemory" class="col-lg-3 col-md-3 col-sm-6" style="height: 320px;display: inline-block"></div>
			</div>
		</div>

			<div class="fixed-table-body">
				<div class="h-title">
					<h4 class="table-title pull-left" id="indicatorsSizeView"></h4>
					<div class="pull-right">
						<button  class="tableEdit" onclick="addIndicator()" >编辑</button>
						<div style="display: none;" >
							<button  class="tableSave" onclick="" >保存</button>
							<button  class="tableCancel" onclick="" >取消</button>
						</div>
					</div>
				</div>
				<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead><tr><th>指标名称</th><th>事件类型</th><th>最新采集时间</th><th>当前值</th><th>操作</th></tr></thead>
					<tbody id="indicatorListView">
					<%--<c:forEach items="${indicatorList}" var="item">
						<tr>
							<td>${item.indicator.name}</td>
							<td>${fns:getDictLabel(item.indicator.eventType, 'indicator_event_type', '')}</td>
							<td><fmt:formatDate value="${item.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							<td>${item.value}${item.indicator.unit}</td>
							<td><i class="fa fa-bar-chart-o" title="实时分析"></i></td>
						</tr>
					</c:forEach>--%>
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


			<div class="modal inmodal" id="processShowModal" tabindex="-1" role="dialog" aria-hidden="true" >
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content animated bounceInRight" style="width:800px;">
						<div class="modal-body" style="width:100%">
							<div id="processView" style="width:750px;"></div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>


			<div class="modal inmodal" id="softwareModal" tabindex="-1" role="dialog" aria-hidden="true" >
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content animated bounceInRight" style="width:800px;">
						<div class="modal-body" style="width:100%">
							<div id="softwareView" style="width:750px;"></div>
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
                        async : true,
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

                                if(result[i].value!=null&&result[i].value!="null"){
                                        html += "<td>"+result[i].updateDate+"</td>";
                                    if(result[i].indicator.itemType == '11') {       //进程列表
                                        html +=  "<td><a type='button'  data-toggle='modal' data-target='#processShowModal'> <b onclick='showProcess()'>点击查看进程列表</b></a> </td>";
                                    }else if(result[i].indicator.itemType == '12'){  //软件列表
                                        html +=  "<td><a type='button'  data-toggle='modal' data-target='#softwareModal'> <b onclick='showSoftware()'>点击查看进程列表</b></a> </td>";
                                    }else{
                                        if(result[i].value=='-1') {
                                            html += "<td><b style='color:red'>" + result[i].value + "" + result[i].indicator.unit + "<b/></td>";
                                        }else{
                                            html += "<td>" + result[i].value + "" + result[i].indicator.unit + "</td>";
                                        }
                                    }

                                    if((result[i].indicator.itemType == 1||result[i].indicator.itemType == 2||result[i].indicator.itemType == 9)&&result[i].value){
                                        html +=   "<td> <a type='button' data-toggle='modal' data-target='#realTimeAnalysisModal'><i class='fa fa-bar-chart-o' title='实时分析' onclick='realTimeAnalysis("+result[i].indicator.itemType+")'></i></a></td>";
                                    }else{
                                        html +=   "<td></td>";
                                    }
                                }else{
                                    html += "<td>-</td><td>-</td><td></td>";
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
                       async : true,
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

				function showProcess(){
                  $("#processView").html($("#processName").html());
				}

                function showSoftware(){
                    $("#softwareView").html($("#softwareName").html());
                }

			</script>







		<c:if test="${diskList != null}">
		<div class="fixed-table-body" >
			<h4 class="table-title pull-left">磁盘列表(${diskList.size()})</h4>
			<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead><tr><th>磁盘名称</th><th>总量</th><th>使用量</th><th>剩余量</th><th>利用率</th></tr></thead>
				<tbody>

			<c:forEach items="${diskList}" var="disk">
				<tr>
					<td>${disk.name}</td>
					<td>${disk.total}</td>
					<td>${disk.used}</td>
					<td>${disk.free}</td>
					<td>${disk.usedRate}%</td>
				</tr>
			</c:forEach>
				</tbody>
			</table>
		</div>
		</c:if>

		<div class="fixed-table-body" >
			<h4 class="table-title pull-left" id="networkInterfaceNum">网络接口(0)</h4>
			<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
				<tr>
					<th>接口名称</th>
					<th>容量</th>
					<%--<th>异常等级</th>
					<th>接口状态</th>--%>
					<th>输入速率</th>
					<th>输出速率</th>
					<%--<th>模板</th>
					<th>操作</th>--%>
				</tr>
				</thead>
				<tbody id="networkInterfaceListView">
				<%--<c:forEach items="${networkInterfaceList}" var="networkInterface">
					<tr>
						<td>${networkInterface.name}</td>
						<td>${networkInterface.capacity}</td>
					&lt;%&ndash;	<td></td>
						<td></td>&ndash;%&gt;
						<td>&lt;%&ndash;<b onclick="showList('${networkInterface.id}','input')">${networkInterface.intRate.rate}</b>&ndash;%&gt;
							<div class="text-center">
								<a type="button" class="" data-toggle="modal" data-target="#myModal">
									<b onclick="getSpeedChart('${networkInterface.id}','input')">${networkInterface.inRate.rate}</b>
								</a>
							</div>
						</td>
						<td>&lt;%&ndash;<b onclick="showList('${networkInterface.id}','output')">${networkInterface.outRate.rate}</b>&ndash;%&gt;
							<div class="text-center">
								<a type="button" class="" data-toggle="modal" data-target="#myModal">
									<b onclick="getSpeedChart('${networkInterface.id}','output')">${networkInterface.outRate.rate}</b>
								</a>
							</div>
						</td>
					&lt;%&ndash;	<td></td>
						<td></td>&ndash;%&gt;

					</tr>
				</c:forEach>--%>
				</tbody>
			</table>
		</div>

			<script>
                function initNetworkInterfaceListView() {
                    var html = "";
                    $.ajax({
                        type : 'GET',
                        url :  "${ctx}/resource/resource/getNetworkInterfaceList",
                        timeout:50*1000,    //超时时间 50s
                        data : {
                            resourceId : $("#resourceId").val()
                        },
                        success : function(result) {
                            $("#networkInterfaceNum").html("网络接口("+result.length+")");
                            for(var i=0;i<result.length;i++) {
                                html += "<tr><td>" + result[i].name + "</td>";
                                html += "<td>" + result[i].capacity + "</td><td><div class='text-center'><a type='button' class='' data-toggle='modal' data-target='#myModal'>";
                                var intRate='';
                                if(result[i].inRate!=null&&result[i].inRate.rate!=null){
                                    intRate=result[i].inRate.rate;
                                }
                                html += "<b onclick=\"getSpeedChart('" + result[i].id + "','input')\">" + intRate + "</b></a></div></td>";
                                html += "<td><div class='text-center'><a type='button'' class='' data-toggle='modal' data-target='#myModal'>";

                                var outRate='';
                                if(result[i].outRate!=null&&result[i].outRate.rate!=null){
                                    outRate=result[i].outRate.rate;
                                }

                                html +="<b onclick=\"getSpeedChart('" + result[i].id + "','output')\">" + outRate + "</b></a></div></td></tr>";

                            }
                            $("#networkInterfaceListView").html(html);

                        },
                        error:function(){

                        },
                        complete:function(XMLHttpRequest,status){
                        }
                    });
                }
			</script>


			<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content animated bounceInRight" style="width:800px;">
						<div class="modal-body" style="width:100%">
							<div id="netInterfaceRateView" style="width:750px;height:340px"></div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
        <div class="fixed-table-body">
            <div class="row">
                <div class="col-sm-12">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 进程(${processList.size()})</a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">已安装软件(${softwareList.size()})</a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false" id="bearingApplicationNum">承载应用(0)</a>
                            </li>
                            <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false" id="bearingServerNum" >承载业务(0)</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <table id="1" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>进程名称</th>
                                        <th>PID</th>
                                        <th>CPU</th>
                                        <th>MEM</th>
                                        <th style="max-width: 260px;overflow: auto">路径</th>
                                        <th>初始化参数</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                   <c:forEach items="${processList}" var="process">
										 <tr>
											<td width="">${process.name}</td>
											<td>${process.pid}</td>
											<td>${process.cpuUsedPercent}</td>
											<td>${process.memory}</td>
											<td style="min-width: 200px;max-width: 260px;word-break: break-all">${process.path}</td>
											<td style="min-width: 200px;max-width: 260px;word-break: break-all">${process.initParameter}</td>
										 </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

								<div id="processName" style="display: none">
									<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
										<thead>
										<tr>
											<th>进程名称</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${processList}" var="process">
											<tr>
												<td width="">${process.name}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>

                            </div>
                            <div id="tab-2" class="tab-pane">
                                <table id="2" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>软件名称</th>
                                        <th>修改时间</th>
                                        <th>软件类型</th>
                                    </tr>
                                    </thead>
                                    <tbody>
										<c:forEach items="${softwareList}" var="software">
										  <tr>
											<td>${software.name}</td>
											<td>${software.modifyTime}</td>
											  <td><c:if test="${software.type == 4}">应用程序</c:if><c:if test="${software.type != 4}">其他</c:if></td>
										  </tr>
                                      </c:forEach>
                                    </tbody>
                                </table>

								<div id="softwareName" style="display: none">
									<table  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
										<thead>
										<tr>
											<th>软件名称</th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${softwareList}" var="software">
											<tr>
												<td>${software.name}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
                            </div>
                            <div id="tab-3" class="tab-pane">
                                <table id="3" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>应用名称</th>
                                        <th>健康度</th>
                                     <%--   <th>异常等级</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody id="bearingApplicationView">
									<%--  <tr >
                                        <td>ApplicationFrameHost.exe</td>
                                         <td>42012</td>--%>
                                      <%--  <td>0.00%</td>
                                    </tr>--%>
                                    </tbody>
                                </table>
                            </div>
                            <div id="tab-4" class="tab-pane">
                                <table id="4" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                                    <thead>
                                    <tr>
                                        <th>业务名称</th>
                                        <th>健康度</th>
                                       <%-- <th>异常等级</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody id="bearingServerView">
									<%--<tr >
                                      <td>ApplicationFrameHost.exe</td>
                                       <td>42012</td>--%>
                                      <%--  <td>0.00%</td>
                                    </tr>--%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

			<script>
                $(document).ready(function () {
                    $.ajax({
                        type : 'POST',
                        url :  "${ctx}/resource/resource/serverBearing",
                        data:{id:$("#resourceId").val()},
                        timeout:10*1000,    //超时时间 10s
                        success : function(result) {

                            if(result["bearingApplication"]){
                                var bearingApplication=result["bearingApplication"];
                                $("#bearingApplicationNum").html("承载应用("+bearingApplication.length+")");

                                var html="";
                                for(var i=0;i<bearingApplication.length;i++){
                                    var parm="\""+bearingApplication[i].id+"\"";
                                    html+= "<tr><td><a href='#' onclick='detail("+parm+")'>"+bearingApplication[i].name+"</a></td><td>100%</td></tr>";
                                }
                                $("#bearingApplicationView").html(html);

                            }



                            if(result["bearingServer"]){
                                var bearingServer=result["bearingServer"];
                                $("#bearingServerNum").html("承载服务("+bearingServer.length+")");
                                var html="";
                                for(var i=0;i<bearingServer.length;i++){
                                    var parm="\""+bearingServer[i].id+"\"";
                                    html+= "<tr><td ><a href='#' onclick='detail("+parm+")'>"+bearingServer[i].name+"</a></td><td>100%</td></tr>";
                                }
                                $("#bearingServerView").html(html);
                            }





                           /* */
                        },
                        error:function(){
                        },
                        complete:function(XMLHttpRequest,status){

                        }
                    });

                })

                function detail(id){
                    var url='${ctx}/resource/resource/getInfo?id='+id;             //转向网页的地址;
                    var name='';                  //网页名称，可为空;
                    var iWidth=document.body.scrollWidth;                          //弹出窗口的宽度;
                    var iHeight=document.body.scrollHeight;                         //弹出窗口的高度;
                    window.open(url, name, 'height='  + iHeight + ',width=' + iWidth + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
                }

			</script>
	</section>

	<section id="content2" style="padding:0  20px;">
		<div class="row">
			<div class="h-title">
				<h4 class="table-title pull-left">基本信息</h4>
			<%--	<div class="pull-right ">
					<a  class="btn edit" href="" title="resource"> 编辑</a>
				</div>--%>
			</div>
			<table class="table information-table" >
				<tbody>
				<tr>
					<input id="resourceBaseInfoId" value="${resource.resourceBaseInfo.id}" type="hidden">
					<td class="td-default">资源名称</td><td class="td-hover">
					<a type="button" class="" data-toggle="modal" data-target="#informationNameEdit" class="btn edit tableEdit" id="resourceNameSection2">${resource.name}</a></td>
					<td class="td-default">读共同体</td><td class="td-hover">
					<a type="button" class="" data-toggle="modal" data-target="#informationRdcommunityEdit" class="btn edit tableEdit" id="resourceRdcommunitySection2">${resource.resourceBaseInfo.rdcommunity}</a></td>
					<td class="td-default">端口</td><td class="td-hover">${resource.resourceBaseInfo.port}</td>
				</tr>
				<tr>
					<td class="td-default">超时时间(ms)</td><td class="td-hover">${resource.resourceBaseInfo.delay}</td>
					<td class="td-default">超时次数</td><td class="td-hover">${resource.resourceBaseInfo.repeatnum}</td>
					<td class="td-default">用户名</td><td class="td-hover">-</td>

				</tr>
				<tr>
					<td class="td-default">安全级别</td><td class="td-hover">-</td>
					<td class="td-default">认证协议</td><td class="td-hover">-</td>
					<td class="td-default">认证密码</td><td class="td-hover">-</td>

				</tr>
				<tr>
					<td class="td-default">加密协议</td><td class="td-hover">-</td>
					<td class="td-default">加密密码</td><td class="td-hover">-</td>
					<td class="td-default">访问类型</td><td class="td-hover">-</td>

				</tr>
				<tr>
					<td class="td-default">访问端口</td><td class="td-hover"></td>
					<td class="td-default">访问用户名</td><td class="td-hover">-</td>
					<td class="td-default">访问密码</td><td class="td-hover">-</td>

				</tr>
				<tr>
					<td class="td-default">特权模式口令</td><td class="td-hover">-</td>
					<td class="td-default">特权模式密码</td><td class="td-hover">-</td>
					<td class="td-default">命令提示符</td><td class="td-hover" colspan="4">-</td>
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



		<div class="modal inmodal" id="informationRdcommunityEdit" tabindex="-1" role="dialog" aria-hidden="true" >
			<div class="modal-dialog" style="width:400px;">
				<div class="modal-content animated bounceInRight" style="width:400px;">
					<div class="modal-body" style="width:100%">
						<div  style="width:100%;height:120px">
							<h4>编辑读共同体</h4>
							<div style="margin-top: 32px;text-align: center">
								<input id="informationResourceRdcommunity" value="${resource.resourceBaseInfo.rdcommunity}" class=" form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white"  data-dismiss="modal" onclick="saveResourceRdcommunity()" >保存</button>
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
                            $("#resourceNameSection2").html($("#informationResourceName").val());
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


            function saveResourceRdcommunity() {
                if($("#informationResourceRdcommunity").val().trim().length==0){
                    return;
                }
                $.ajax({
                    type : 'POST',
                    timeout:10*1000,    //超时时间 10s
                    url :  "${ctx}/resource/resource/updateResourceRdcommunity",
                    data : { resourceBaseInfoId: $("#resourceBaseInfoId").val(), rdcommunity : $("#informationResourceRdcommunity").val()},
                    success : function(result) {
                        if(result){
                            $("#resourceRdcommunitySection").html($("#informationResourceRdcommunity").val());
                            $("#resourceRdcommunitySection2").html($("#informationResourceRdcommunity").val());
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



	<%--服务器信息--%>
		<div class="space-solid"></div>
		<div class="row">
			<div class="h-title">
				<h4 class="table-title pull-left">服务器信息</h4>
				<div class="pull-right ">
					<button  class="tableEdit" onclick="showInformationEditView(this)" id="informationEditBtn">编辑</button>
					<div style="display: none;" id="informationEditView">
						<button  class="tableSave" onclick="informationEditSave(this)" >保存</button>
						<button  class=" tableCancel" onclick="hideInformationEditView()" >取消</button>
					</div>
					<input  id="informationEditType" value="1">
				</div>
			</div>
			<table class="table information-table" id="informationFormEdit">
				<tbody>
				<form:form id="informationForm" action="${ctx}/resourceinformation/resourceInformation/edit"  method="post"  class="form-horizontal">
					<input name="id" id="resourceInformationId" value="${resourceInformation.id}">
					<input name="resourceId" value="${resource.id}">
						<tr>
							<td class="td-default">设备大类</td><td class="td-hover"><input  name="equipmentCategory" id="informationEquipmentCategory" class="informationClass" value="${resourceInformation.equipmentCategory}" /></td>
							<td class="td-default">设备小类</td><td class="td-hover"><input  name="equipmentType" id="informationEquipmentType" class="informationClass" value="${resourceInformation.equipmentType}" /></td>
							<td class="td-default">系统网址</td><td class="td-hover"><input  name="sysUrl" id="informationSysUrl" class="informationClass" value="${resourceInformation.sysUrl}" /></td>
						</tr>
						<tr>
							<td class="td-default">运维厂商</td><td class="td-hover"><input  name="opsFirm" id="informationOpsFirm" class="informationClass" value="${resourceInformation.opsFirm}" /></td>
							<td class="td-default">运维人员</td><td class="td-hover"><input  name="opsPerson" id="informationOpsPerson" class="informationClass" value="${resourceInformation.opsPerson}" /></td>
							<td class="td-default">运维人员联系方式</td><td class="td-hover"><input  name="opsContact" id="informationOpsContact" class="informationClass" value="${resourceInformation.opsContact}" /></td>
						</tr>
						<tr>
							<td class="td-default">设备供应商</td><td class="td-hover"><input  name="equipmentSupplier" id="informationEquipmentSupplier" class="informationClass" value="${resourceInformation.equipmentSupplier}" /></td>
							<td class="td-default">采购金额</td><td class="td-hover"><input  name="purchaseSum" id="informationPurchaseSum" class="informationClass" value="${resourceInformation.purchaseSum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							<td class="td-default">上架日期</td><td class="td-hover">
							<input class="informationClass" id="addedDateView" <c:if test="${resourceInformation.addedDate ==null}">  value=""  </c:if> <c:if test="${resourceInformation.addedDate !=null}">  value="<fmt:formatDate value="${resourceInformation.addedDate}" pattern="yyyy-MM-dd HH:mm:ss"/> "  </c:if> >
							<input style="display: none" class="datainp informationClass" id="informationAddedDate" name="addedDate" type="text" placeholder="请选择"  readonly <c:if test="${resourceInformation.addedDate ==null}">  value=""  </c:if> <c:if test="${resourceInformation.addedDate !=null}">  value="<fmt:formatDate value="${resourceInformation.addedDate}" pattern="yyyy-MM-dd HH:mm:ss"/> "  </c:if> >
						    </td>
						</tr>
						<tr>
							<td class="td-default">维保年限</td><td class="td-hover"><input  name="maintenancePeriod" id="informationMaintenancePeriod" class="informationClass" value="${resourceInformation.maintenancePeriod}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							<td class="td-default">所属机构编码</td><td class="td-hover"><input  name="courtId" id="informationCourtId" class="informationClass" value="${resourceInformation.courtId}" /></td>
							<td class="td-default">优先级</td><td class="td-hover"><input  name="priority" id="informationPriority" class="informationClass" value="${resourceInformation.priority}" /></td>
						</tr>
						<tr>
							<td class="td-default">所在服务器</td><td class="td-hover"><input  name="server" id="informationServer" class="informationClass" value="${resourceInformation.server}" /></td>
							<td class="td-default">数据库端口</td><td class="td-hover"><input  name="dbPort" id="informationDbPort" class="informationClass" value="${resourceInformation.dbPort}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							<td class="td-default">数据库版本</td><td class="td-hover"><input  name="dbEdition" id="informationDbEdition" class="informationClass" value="${resourceInformation.dbEdition}" /></td>
						</tr>
						<tr>
							<td class="td-default">操作系统</td><td class="td-hover"><input  name="os" id="informationOs" class="informationClass" value="${resourceInformation.os}" /></td>
							<td class="td-default">系统版本</td><td class="td-hover" colspan="4"><input  name="osEdition" id="informationOsEdition" class="informationClass" value="${resourceInformation.osEdition}" /></td>
						</tr>
				</form:form>
				</tbody>
			</table>
		</div>
		<div class="space-solid"></div>



		<%--服务器信息--%>
		<style>
			.informationClass{
				width: 100%;
				display: block;
			}
		</style>
		<script>

            var addedDate;
            jeDate({
                dateCell:"#informationAddedDate",
                format:"YYYY-MM-DD hh:mm:ss",
                isinitVal:false,
                isTime:true,
                minDate:"1970-01-01 00:00:00",
                okfun:function(val){}
            })


            $(function () {
                $(".informationClass").attr("readonly","readonly");
            })

            function informationEditSave(obj) {
                $.ajax({
                    type : 'POST',
                    url :  "${ctx}/resourceinformation/resourceInformation/edit",
                    timeout:10*1000,    //超时时间 10s
                    data:$("#informationForm").serialize(),
                    success : function(result) {
                        //alert("保存成功");
                        addedDate=$("#informationAddedDate").val();
                        $("#addedDateView").val($("#informationAddedDate").val());
                        $("#resourceInformationId").val(result);
                        $("#informationEditBtn").attr("style","display:");
                        $("#informationEditView").attr("style","display:none");
                        $("#informationEditType").val("1");
                        $(".informationClass").attr("readonly","readonly");
						$("#informationFormEdit").find("input").toggleClass("editInset");
                        $("#informationAddedDate").attr("style","display:none");
                        $("#addedDateView").attr("style","display:");
                    },
                    error:function(){
                        alert("保存失败");
                    },
                    complete:function(XMLHttpRequest,status){
                        if(status=='timeout'){
                            alert("请求超时，请检查网络后重试");
                        }
                    }
                });
            }


            function showInformationEditView(obj) {
                addedDate= $("#addedDateView").val();
                $("#addedDateView").attr("style","display:none");
                $("#informationAddedDate").attr("style","display:");

                $("#informationEditBtn").attr("style","display:none");
                $("#informationEditView").attr("style","display:");
                $("#informationEditType").val("2");
                $(".informationClass").removeAttr("readonly");
				$("#informationFormEdit").find("input").toggleClass("editInset");
            }

            function hideInformationEditView() {
                $.ajax({
                    type : 'POST',
                    async : true,
                    dataType:"json",
                    url :  "${ctx}/resourceinformation/resourceInformation/getResourceInformation",
                    data:{id:$("#resourceInformationId").val()},
                    timeout:10*1000,    //超时时间 10s
                    success : function(result) {
                        var resourceInformation = result.body.resourceInformation;

                        $("#informationAddedDate").val(addedDate);
                        if(addedDate=="请选择"){
                            addedDate="";
                        }
                        $("#addedDateView").val(addedDate);

                        $("#informationEquipmentCategory").val(resourceInformation.equipmentCategory);
                        $("#informationEquipmentType").val(resourceInformation.equipmentType);
                        $("#informationSysUrl").val(resourceInformation.sysUrl);

                        $("#informationOpsFirm").val(resourceInformation.opsFirm);
                        $("#informationOpsPerson").val(resourceInformation.opsPerson);
                        $("#informationOpsContact").val(resourceInformation.opsContact);

                        $("#informationEquipmentSupplier").val(resourceInformation.equipmentSupplier);
                        $("#informationPurchaseSum").val(resourceInformation.purchaseSum);

                        $("#informationMaintenancePeriod").val(resourceInformation.maintenancePeriod);
                        $("#informationCourtId").val(resourceInformation.courtId);
                        $("#informationPriority").val(resourceInformation.priority);

                        $("#informationServer").val(resourceInformation.server);
                        $("#informationDbPort").val(resourceInformation.dbPort);
                        $("#informationDbEdition").val(resourceInformation.dbEdition);

                        $("#informationOs").val(resourceInformation.os);
                        $("#informationOsEdition").val(resourceInformation.osEdition);


                        $("#informationEditBtn").attr("style","display:");
                        $("#informationEditView").attr("style","display:none");
                        $("#informationEditType").val("1");
                        $(".informationClass").attr("readonly","readonly");

                        $("#informationAddedDate").attr("style","display:none");
                        $("#addedDateView").attr("style","display:");

						$("#informationFormEdit").find("input").toggleClass("editInset");
                    },
                    error:function(){
                        alert("刷新失败");
                    },
                    complete:function(XMLHttpRequest,status){
                        if(status=='timeout'){
                            alert("请求超时，请检查网络后重试");
                        }
                    }
                });
            }

		</script>















		<%--配置信息--%>
		<div class="row">
			<div class="h-title">
				<h4 class="table-title pull-left">配置信息</h4>
				<div class="pull-right ">
					<button  class=" tableEdit" onclick="showConfigInfoEditView(this)" id="configInfoEditBtn">编辑</button>
					<div style="display: none;" id="configInfoEditView">
						<button  class="tableSave" onclick="configInfoEditSave(this)" >保存</button>
						<button  class="tableCancel" onclick="hideConfigInfoEditView()" >取消</button>
					</div>
					<input  id="configInfoEditType" value="1">
				</div>
			</div>
			<table class="table information-table" id="configInfoFormEdit">
				<tbody>
              <form:form id="configInfoForm" action="${ctx}/resourceconfiginfo/resourceConfigInfo/edit"  method="post"  class="form-horizontal">
							<input name="id" id="resourceConfigInfoId" value="${resourceConfigInfo.id}">
				            <input name="resourceId" value="${resource.id}">

							<tr>
								<td class="td-default">CPU 个数</td><td class="td-hover"><input  name="cpuNum" id="configInfoCpuNum" class="configInfoClass" value="${resourceConfigInfo.cpuNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">单个 CPU 型号</td><td class="td-hover"><input  name="singleCpuType" id="configInfoSingleCpuType" class="configInfoClass" value="${resourceConfigInfo.singleCpuType}" /></td>
								<td class="td-default">单个 CPU 频率</td><td class="td-hover"><input  name="singleCpuRate" id="configInfoSingleCpuRate" class="configInfoClass" value="${resourceConfigInfo.singleCpuRate}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							</tr>
							<tr>
								<td class="td-default">单个 CPU 核数</td><td class="td-hover"><input  name="singleCpuCorenum" id="configInfoSingleCpuCorenum" class="configInfoClass" value="${resourceConfigInfo.singleCpuCorenum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">内存</td><td class="td-hover"><input  name="memory" id="configInfoMemory" class="configInfoClass" value="${resourceConfigInfo.memory}" /></td>
								<td class="td-default">内存条个数</td><td class="td-hover"><input  name="memoryNum" id="configInfoMemoryNum" class="configInfoClass" value="${resourceConfigInfo.memoryNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							</tr>
							<tr>
								<td class="td-default">内存条容量</td><td class="td-hover"><input  name="memorycCapacity" id="configInfoMemorycCapacity" class="configInfoClass" value="${resourceConfigInfo.memorycCapacity}" /></td>
								<td class="td-default">硬盘个数</td><td class="td-hover"><input  name="diskNum" id="configInfoDiskNum" class="configInfoClass" value="${resourceConfigInfo.diskNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">硬盘容量</td><td class="td-hover"><input  name="diskCapacity" id="configInfoDiskCapacity" class="configInfoClass" value="${resourceConfigInfo.diskCapacity}" /></td>
							</tr>
							<tr>
								<td class="td-default">内置硬盘类型</td><td class="td-hover"><input  name="diskType" id="configInfoDiskType" class="configInfoClass" value="${resourceConfigInfo.diskType}" /></td>
								<td class="td-default">单个内置硬盘容量</td><td class="td-hover"><input  name="singleDiskCapacity" id="configInfoSingleDiskCapacity" class="configInfoClass" value="${resourceConfigInfo.singleDiskCapacity}" /></td>
								<td class="td-default">内置硬盘可用容量</td><td class="td-hover"><input  name="diskAvailableCapacity" id="configInfoDiskAvailableCapacity" class="configInfoClass" value="${resourceConfigInfo.diskAvailableCapacity}" /></td>
							</tr>
							<tr>
								<td class="td-default">内置硬盘是否RAID级别</td><td class="td-hover"><input  name="diskIfRaid" id="configInfoDiskIfRaid" class="configInfoClass" value="${resourceConfigInfo.diskIfRaid}" /></td>
								<td class="td-default">网卡个数</td><td class="td-hover"><input  name="netcardNum" id="configInfoNetcardNum" class="configInfoClass" value="${resourceConfigInfo.netcardNum}" /></td>
								<td class="td-default">U数</td><td class="td-hover"><input  name="UNum" id="configInfoUNum" class="configInfoClass" value="${resourceConfigInfo.UNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							</tr>
							<tr>
								<td class="td-default">控制器数量</td><td class="td-hover"><input  name="controllerNum" id="configInfoControllerNum" class="configInfoClass" value="${resourceConfigInfo.controllerNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">电源模块数量</td><td class="td-hover"><input  name="elepowerModuleNum" id="configInfoElepowerModuleNum" class="configInfoClass" value="${resourceConfigInfo.elepowerModuleNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">单个电源模块功率</td><td class="td-hover"><input  name="singlePowermodulePower" id="configInfoSinglePowermodulePower" class="configInfoClass" value="${resourceConfigInfo.singlePowermodulePower}" /></td>
							</tr>
							<tr>
								<td class="td-default">已用接口数量</td><td class="td-hover"><input  name="usedInterfaceNum" id="configInfoUsedInterfaceNum" class="configInfoClass" value="${resourceConfigInfo.usedInterfaceNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">可用接口数量</td><td class="td-hover"><input  name="avaliableInterfaceNum" id="configInfoAvaliableInterfaceNum" class="configInfoClass" value="${resourceConfigInfo.avaliableInterfaceNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
								<td class="td-default">特殊板卡数量</td><td class="td-hover"><input  name="specialBoardNum" id="configInfoSpecialBoardNum" class="configInfoClass" value="${resourceConfigInfo.specialBoardNum}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
							</tr>
							<tr>
								<td class="td-default">特殊板卡类型</td><td class="td-hover"><input  name="specialBoardType" id="configInfoSpecialBoardType" class="configInfoClass" value="${resourceConfigInfo.specialBoardType}" /></td>
								<td class="td-default">电源模块功率</td><td class="td-hover" colspan="4"><input  name="elepowerModulePower" id="configInfoElepowerModulePower" class="configInfoClass" value="${resourceConfigInfo.elepowerModulePower}" /></td>
							</tr>
                  </form:form>
				</tbody>
			</table>
		</div>

		<%--配置信息操作相关--%>
		<style>
			.configInfoClass{
				width: 100%;
				display: block;
			}
		</style>
		<script>
            $(function () {
                $(".configInfoClass").attr("readonly","readonly");
            })

            function configInfoEditSave(obj) {
                $.ajax({
                    type : 'POST',
                    url :  "${ctx}/resourceconfiginfo/resourceConfigInfo/edit",
                    timeout:10*1000,    //超时时间 10s
                    data:$("#configInfoForm").serialize(),
                    success : function(result) {
                       // alert("保存成功");
                        $("#resourceConfigInfoId").val(result);
                        $("#configInfoEditBtn").attr("style","display:");
                        $("#configInfoEditView").attr("style","display:none");
                        $("#configInfoEditType").val("1");
                        $(".configInfoClass").attr("readonly","readonly");
						$("#configInfoFormEdit").find("input").toggleClass("editInset");
                    },
                    error:function(){
                        alert("保存失败");
                    },
                    complete:function(XMLHttpRequest,status){
                        if(status=='timeout'){
                            alert("请求超时，请检查网络后重试");
                        }
                    }
                });
            }


            function showConfigInfoEditView(obj) {
                $("#configInfoEditBtn").attr("style","display:none");
                $("#configInfoEditView").attr("style","display:");
                $("#configInfoEditType").val("2");
                $(".configInfoClass").removeAttr("readonly");
				$("#configInfoFormEdit").find("input").toggleClass("editInset");
            }

            function hideConfigInfoEditView() {
                $.ajax({
                    type : 'POST',
                    dataType:"json",
                    url :  "${ctx}/resourceconfiginfo/resourceConfigInfo/getResourceConfigInfo",
                    data:{id:$("#resourceConfigInfoId").val()},
                    timeout:10*1000,    //超时时间 10s
                    success : function(result) {
                        var resourceConfigInfo = result.body.resourceConfigInfo;
                             $("#configInfoCpuNum").val(resourceConfigInfo.cpuNum);
                             $("#configInfoSingleCpuType").val(resourceConfigInfo.singleCpuType);
                             $("#configInfoSingleCpuRate").val(resourceConfigInfo.singleCpuRate);

							 $("#configInfoSingleCpuCorenum").val(resourceConfigInfo.singleCpuCorenum);
							 $("#configInfoMemory").val(resourceConfigInfo.memory);
							 $("#configInfoMemoryNum").val(resourceConfigInfo.memoryNum);

							 $("#configInfoMemorycCapacity").val(resourceConfigInfo.memorycCapacity);
							 $("#configInfoDiskNum").val(resourceConfigInfo.diskNum);
							 $("#configInfoDiskCapacity").val(resourceConfigInfo.diskCapacity);


							 $("#configInfoDiskType").val(resourceConfigInfo.diskType);
							 $("#configInfoSingleDiskCapacity").val(resourceConfigInfo.singleDiskCapacity);
							 $("#configInfoDiskAvailableCapacity").val(resourceConfigInfo.diskAvailableCapacity);

                             $("#configInfoDiskIfRaid").val(resourceConfigInfo.diskIfRaid);
                             $("#configInfoNetcardNum").val(resourceConfigInfo.netcardNum);
                             $("#configInfoUNum").val(resourceConfigInfo.unum);

                             $("#configInfoControllerNum").val(resourceConfigInfo.controllerNum);
                             $("#configInfoElepowerModuleNum").val(resourceConfigInfo.elepowerModuleNum);
                             $("#configInfoSinglePowermodulePower").val(resourceConfigInfo.singlePowermodulePower);

                             $("#configInfoUsedInterfaceNum").val(resourceConfigInfo.usedInterfaceNum);
                             $("#configInfoAvaliableInterfaceNum").val(resourceConfigInfo.avaliableInterfaceNum);
                             $("#configInfoSpecialBoardNum").val(resourceConfigInfo.specialBoardNum);

                             $("#configInfoSpecialBoardType").val(resourceConfigInfo.specialBoardType);
                             $("#configInfoElepowerModulePower").val(resourceConfigInfo.elepowerModulePower);

                        $("#configInfoEditBtn").attr("style","display:");
                        $("#configInfoEditView").attr("style","display:none");
                        $("#configInfoEditType").val("1");
                        $(".configInfoClass").attr("readonly","readonly");

						$("#configInfoFormEdit").find("input").toggleClass("editInset");
                    },
                    error:function(){
                        alert("刷新失败");
                    },
                    complete:function(XMLHttpRequest,status){
                        if(status=='timeout'){
                            alert("请求超时，请检查网络后重试");
                        }
                    }
                });
            }

		</script>













		<%--   物理信息--%>
		<div class="space-solid"></div>
		<div class="row">
			<div class="h-title">
				<h4 class="table-title pull-left">物理信息</h4>

				<div class="pull-right ">
					<button  class="tableEdit" onclick="showPhysicInfoEditView(this)" id="physicInfoEditBtn">编辑</button>
					<div style="display: none;" id="physicInfoEditView">
						<button  class="tableSave" onclick="physicInfoEditSave(this)" >保存</button>
						<button  class="tableCancel" onclick="hidePhysicInfoEditView()" >取消</button>
					</div>

					<input  id="physicInfoEditType" value="1">
				</div>
			</div>

			     <table class="table information-table" id="physicInfoFormForm">
				<tbody>
                 <form:form id="physicInfoForm" action="${ctx}/resourcephysicinfo/resourcePhysicInfo/edit"  method="post"  class="form-horizontal">
					<input name="id"  id="resourcePhysicInfoId" value="${resourcePhysicInfo.id}">
					<input name="resourceId" value="${resource.id}">
					<tr>
						<td class="td-default">高度(CM)</td><td class="td-hover"> <input  name="height" id="physicInfoHeight" class="physicInfoClass" value="${resourcePhysicInfo.height}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
						<td class="td-default">功耗(W)</td><td class="td-hover"> <input  name="powerDissipation" id="physicInfoPowerDissipation" class="physicInfoClass" value="${resourcePhysicInfo.powerDissipation}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/> </td>
						<td class="td-default">发热(J)</td><td class="td-hover"><input  name="heat" class="physicInfoClass" id="physicInfoHeat"  value="${resourcePhysicInfo.heat}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/> </td>
					</tr>
					<tr>
						<td class="td-default">重量(KG)</td><td class="td-hover"><input  name="weight" id="physicInfoWeight" class="physicInfoClass" value="${resourcePhysicInfo.weight}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/> </td>
						<td class="td-default">建筑</td><td class="td-hover"><input  name="building" id="physicInfoBuilding" class="physicInfoClass" value="${resourcePhysicInfo.building}" /> </td>
						<td class="td-default">机房</td><td class="td-hover"><input  name="machineroom" id="physicInfoMachineroom"  class="physicInfoClass" value="${resourcePhysicInfo.machineroom}" /></td>
					</tr>
					<tr>
						<td class="td-default">机柜</td><td class="td-hover"><input  name="cabinet" id="physicInfoCabinet" class="physicInfoClass" value="${resourcePhysicInfo.cabinet}" /> </td>
						<td class="td-default">柜内编号</td><td class="td-hover"><input name="cabinetNo" id="physicInfoCabinetNo" class="physicInfoClass" value="${resourcePhysicInfo.cabinetNo}" /> </td>
						<td class="td-default">机柜容量</td><td class="td-hover"><input  name="cabinetCapacity" id="physicInfoCabinetCapacity"  class="physicInfoClass" value="${resourcePhysicInfo.cabinetCapacity}" /> </td>
					</tr>
					<tr>
						<td class="td-default">所属位置</td><td class="td-hover"><input  name="locate" id="physicInfoLocate" class="physicInfoClass"  value="${resourcePhysicInfo.locate}" /> </td>
						<td class="td-default">详细地址</td><td class="td-hover"  colspan="3"><input name="datailedAddress" id="physicInfoDatailedAddress"  class="physicInfoClass" value="${resourcePhysicInfo.datailedAddress}" /> </td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="lightBoxGallery resource-img-container">
								<div class="lightBoxGallery-img-contact">
									<div class="lightBoxGallery-img-list">
										<a id="resourceImga1" <c:if test="${resourceImg0 != '' }"> href="${resourceImg0}" </c:if> <c:if test="${resourceImg0 == '' }"> href="${ctxStatic}/common/images/server1.png" </c:if> title="${resource.name}" data-gallery="">
											<img class="resource-img" id="resourceImg1" <c:if test="${resourceImg0 != '' }"> src="${resourceImg0}" </c:if> <c:if test="${resourceImg0 == '' }"> src="${ctxStatic}/common/images/server1.png" </c:if>>
										</a>
										<div class="uploadResourceImg" onclick="uploadResourceImg(1)">上传</div>
									</div>
									<div class="lightBoxGallery-img-list">
										<a id="resourceImga2"  <c:if test="${resourceImg1 != '' }"> href="${resourceImg1}" </c:if> <c:if test="${resourceImg1 == '' }"> href="${ctxStatic}/common/images/server2.png" </c:if> title="${resource.name}" data-gallery=""><img class="resource-img" id="resourceImg2" <c:if test="${resourceImg1 != '' }"> src="${resourceImg1}" </c:if> <c:if test="${resourceImg1 == '' }"> src="${ctxStatic}/common/images/server2.png" </c:if>></a>
										<div class="uploadResourceImg" onclick="uploadResourceImg(2)">上传</div>
									</div>
									<div class="lightBoxGallery-img-list">
										<a id="resourceImga3"  <c:if test="${resourceImg2 != '' }"> href="${resourceImg2}" </c:if> <c:if test="${resourceImg2 == '' }"> href="${ctxStatic}/common/images/server3.png" </c:if> title="${resource.name}" data-gallery=""><img class="resource-img" id="resourceImg3" <c:if test="${resourceImg2 != '' }"> src="${resourceImg2}" </c:if> <c:if test="${resourceImg2 == '' }"> src="${ctxStatic}/common/images/server3.png" </c:if>></a>
										<div class="uploadResourceImg" onclick="uploadResourceImg(3)">上传</div>
									</div>
									<div class="lightBoxGallery-img-list">
										<a id="resourceImga4"  <c:if test="${resourceImg3 != '' }"> href="${resourceImg3}" </c:if> <c:if test="${resourceImg3 == '' }"> href="${ctxStatic}/common/images/server4.png" </c:if> title="${resource.name}" data-gallery=""><img class="resource-img" id="resourceImg4" <c:if test="${resourceImg3 != '' }"> src="${resourceImg3}" </c:if> <c:if test="${resourceImg3 == '' }"> src="${ctxStatic}/common/images/server4.png" </c:if>></a>
										<div class="uploadResourceImg" onclick="uploadResourceImg(4)">上传</div>
									</div>
								</div>
								<div id="blueimp-gallery" class="blueimp-gallery">
									<div class="slides"></div>
									<h3 class="title"></h3>
									<a class="prev">‹</a>
									<a class="next">›</a>
									<a class="close">×</a>
									<a class="play-pause"></a>
									<ol class="indicator"></ol>
								</div>
							</div>

						</td>
					</tr>
              </form:form>
				</tbody>
			</table>

		</div>

       <%--   物理信息操作相关--%>
		<style>
			.physicInfoClass{
				width: 100%;
				display: block;
			}
		</style>

		<script>
            $(function(){
                $(".uploadResourceImg").attr("style","display:none");
                $("input[type=file]").change(function(){$(this).parents(".uploader-img").find(".uploader-img-filename").val($(this).val());});
                $("input[type=file]").each(function(){
                    if($(this).val()==""){$(this).parents(".uploader-img").find(".uploader-img-filename").val("");}
                });
            });

            function uploadResourceImg(num){
                var url='${ctx}/resource/resource/imageEdit?num='+num+"&resourceId="+$("#resourceId").val();             //转向网页的地址;
                var name='';                  //网页名称，可为空;
                var iWidth=1000;                          //弹出窗口的宽度;
                var iHeight=500;                         //弹出窗口的高度;
                //获得窗口的垂直位置
                var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
                //获得窗口的水平位置
                var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
                window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
            }


		function setPic(path,num){
            if(num==1){
                $("#resourceImg1").attr('src',path);
                $("#resourceImga1").attr('href',path);
            }else if(num==2){
                    $("#resourceImg2").attr('src',path);
                    $("#resourceImga2").attr('href',path);
            }else if(num==3){
                    $("#resourceImg3").attr('src',path);
                    $("#resourceImga3").attr('href',path);
            }else if(num==4){
                    $("#resourceImg4").attr('src',path);
                    $("#resourceImga4").attr('href',path);
            }
        }



			$(function () {
				$(".physicInfoClass").attr("readonly","readonly");
            })

            function physicInfoEditSave(obj) {
                $(".uploadResourceImg").attr("style","display:none");
			$.ajax({
					 type : 'POST',
					 url :  "${ctx}/resourcephysicinfo/resourcePhysicInfo/edit",
                     timeout:10*1000,    //超时时间 10s
					 data:$("#physicInfoForm").serialize(),
					 success : function(result) {
						 //alert("保存成功");
                         $("#resourcePhysicInfoId").val(result);
						 $("#physicInfoEditBtn").attr("style","display:");
						 $("#physicInfoEditView").attr("style","display:none");
						 $("#physicInfoEditType").val("1");
						 $(".physicInfoClass").attr("readonly","readonly");
						 $("#physicInfoFormForm").find("input").toggleClass("editInset");
					 },
					 error:function(){
                         alert("保存失败");
					 },
					 complete:function(XMLHttpRequest,status){
						if(status=='timeout'){
							alert("请求超时，请检查网络后重试");
						}
					}
				 });
            }


            function showPhysicInfoEditView(obj) {
                $("#physicInfoEditBtn").attr("style","display:none");
                $("#physicInfoEditView").attr("style","display:");
                $("#physicInfoEditType").val("2");
                $(".physicInfoClass").removeAttr("readonly");
				$("#physicInfoFormForm").find("input").toggleClass("editInset");
                $(".uploadResourceImg").attr("style","display:");

            }

            function hidePhysicInfoEditView(id) {
                $(".uploadResourceImg").attr("style","display:none");
                $.ajax({
                    type : 'POST',
                    dataType:"json",
                    url :  "${ctx}/resourcephysicinfo/resourcePhysicInfo/getResourcePhysicInfo",
                    data:{id:$("#resourcePhysicInfoId").val()},
                    timeout:10*1000,    //超时时间 10s
                    success : function(result) {
                        var resourcePhysicInfo = result.body.resourcePhysicInfo;
                        $("#physicInfoHeight").val(resourcePhysicInfo.height);
                        $("#physicInfoPowerDissipation").val(resourcePhysicInfo.powerDissipation);
                        $("#physicInfoHeat").val(resourcePhysicInfo.heat);

                        $("#physicInfoWeight").val(resourcePhysicInfo.weight);
                        $("#physicInfoBuilding").val(resourcePhysicInfo.building);
                        $("#physicInfoMachineroom").val(resourcePhysicInfo.machineroom);

                        $("#physicInfoCabinet").val(resourcePhysicInfo.cabinet);
                        $("#physicInfoCabinetNo").val(resourcePhysicInfo.cabinetNo);
                        $("#physicInfoCabinetCapacity").val(resourcePhysicInfo.cabinetCapacity);

                        $("#physicInfoLocate").val(resourcePhysicInfo.locate);
                        $("#physicInfoDatailedAddress").val(resourcePhysicInfo.datailedAddress);

                        $("#physicInfoEditBtn").attr("style","display:");
                        $("#physicInfoEditView").attr("style","display:none");
                        $("#physicInfoEditType").val("1");
                        $(".physicInfoClass").attr("readonly","readonly");
						$("#physicInfoFormForm").find("input").toggleClass("editInset");
                    },
                    error:function(){
                        alert("刷新失败");
                    },
                    complete:function(XMLHttpRequest,status){
                        if(status=='timeout'){
                            alert("请求超时，请检查网络后重试");
                        }
                    }
                });
            }

		</script>
	</section>



	<%--<section id="content3" style="padding: 0 20px;">
		<div class="fixed-table-body">
			<div class="elastic-table">操作</div>
			<div>
				<label>接口模板</label>
				<select class="form-control"></select>
			</div>
		</div>
		<div class="row">
			<table id="" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
				<tr>
					<th>接口名称</th>
					<th>所属VLAN</th>
					<th>接口别名</th>
					<th>MAC地址</th>
					<th>连接设备</th>
					<th>接口类型</th>
					<th>容量</th>
					<th>异常等级</th>
					<th>接口状态</th>
					<th>输入速率</th>
					<th>输出速率</th>
					<th class="uncalled-for uncalled-for-show">输入利用率</th>
					<th class="uncalled-for uncalled-for-show">输出利用率</th>
					<th class="uncalled-for uncalled-for-show">地域</th>
					<th class="uncalled-for uncalled-for-show">模板</th>
					<th class="uncalled-for uncalled-for-show">特性</th>
				</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="uncalled-for uncalled-for-show"></td>
						<td class="uncalled-for uncalled-for-show"></td>
						<td class="uncalled-for uncalled-for-show"></td>
						<td class="uncalled-for uncalled-for-show"></td>
						<td class="uncalled-for uncalled-for-show"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>--%>

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
				<%--<div class="col-xs-12 col-sm-6 col-md-3 search-list search-select">
					<div class="search-title">时间：</div>
					<div class="search-value">
						<div class="col-xs-12">
							<div class="col-xs-12 col-sm-5">
								<div class='input-group date' id='beginDate' style="left: -10px;" >
									<input type='text'  name="beginDate" class="form-control"  />
									<span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
								</div>
							</div>
							<div class="col-xs-12 col-sm-1">
								~
							</div>
							<div class="col-xs-12 col-sm-5">
								<div class='input-group date' id='endDate' style="left: -10px;" >
									<input type='text'  name="endDate" class="form-control" />
									<span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
								</div>
							</div>
						</div>
					</div>
				</div>--%>

				<div class="col-xs-12 col-sm-6 col-md-3 search-list search-select">
					<%--<div class="search-title">关键字：</div>
					<div class="search-value">
						<input value="" type="text" class="form-control">
					</div>--%>
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
						<%--<th>恢复状态</th>--%>
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

