<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/webpage/include/echarts.jsp"%>
<html>
<head>
    <title>${resource.name}</title>
    <meta name="decorator" content="ani"/>
    <link rel="stylesheet" href="${ctxStatic}/common/css/main.css" type="text/css">
    <link rel="stylesheet" href="${ctxStatic}/common/css/tab-label.css" type="text/css">
    <link rel="stylesheet" href="${ctxStatic}/common/css/blueimp-gallery.min.css" type="text/css">
    <script src="${ctxStatic}/common/js/jquery.blueimp-gallery.min.js"></script>
    <script src="${ctxStatic}/plugin/echarts3/echarts.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
            var pieChart;
            //在线人数
            pieChart = echarts.init(document.getElementById('pieChart'));
            var totalUser = [0];
            var totalUserUnit = "";
            var onlineUser = [0];
            var onlineUserUnit = "";
            if($("#totalUser").val()||$("#onlineUser").val()){
                $("#pieChart").attr("style","");
                if($("#totalUser").val()){
                    totalUser = [$("#totalUser").val()];
                    totalUserUnit=$("#totalUserUnit").val();
                }

                if($("#onlineUser").val()){
                    onlineUser = [$("#onlineUser").val()];
                     onlineUserUnit = $("#onlineUserUnit").val();
                }
                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: { // 坐标轴指示器，坐标轴触发有效
                            type: 'none' // 默认为直线，可选为：'line' | 'shadow'
                        },
                        formatter: function(data) {
                            console.log(data[0].dataIndex);
                            var _str = "";
                            _str += "总人数： " + totalUser[data[0].dataIndex] + totalUserUnit+"<br>";
                            _str += "在线人数： " + onlineUser[data[0].dataIndex] + onlineUserUnit+"<br>";
                            return _str;
                        }
                    },
                    legend: {
                        show: false,
                        top: 0,
                        data: ['总人数', '在线人数']
                    },
                    grid: {
                        left: '3%',
                        right: '6%',
                        bottom: '3%',
                        top: 0,
                        containLabel: true
                    },
                    xAxis: {
                        type: 'value',
                        show: true,
                        max: 150,
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: false
                        },
                        splitLine: {
                            show: false
                        }

                    },
                    yAxis: {
                        type: 'category',
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        data: ['']
                    },
                    series: [{
                        name: '总人数',
                        type: 'bar',
                        label: {
                            normal: {
                                show: true,
                                formatter: function(data) {
                                    return "总人数"+totalUser[data.dataIndex] + " "+totalUserUnit;
                                },
                                position: 'right',
                                textStyle: {
                                    color: '#000'
                                }
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#aaa',
                                borderWidth: 1,
                                borderColor: '#188333'
                            }
                        },
                        data: [100]
                    }, {
                        name: '在线人数',
                        type: 'bar',
                        barGap: '-100%',
                        label: {
                            normal: {
                                show: true,
                                formatter: function(data) {
                                    return "在线人数：" + onlineUser[data.dataIndex] +onlineUserUnit;
                                },
                                position: 'insideLeft'
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#188333'
                            }
                        },
                        data: onlineUser.map(function(item, index) {
                            return parseInt(item / totalUser[index] * 100)
                        })
                    }]
                };
                pieChart.setOption(option);
            }




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

            var value = 100,
                value_ = 75 * value / 100;
            healthOption.title.subtext = value + "%";
            healthOption.series[0].data[0].value = value_;
            healthOption.series[0].data[1].value = 100 - value_;
            healthChart.setOption(healthOption, true);





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

            var value = 90,
                value_ = 75 * value / 100;
            acOption.title.subtext = value + "%";
            acOption.series[0].data[0].value = value_;
            acOption.series[0].data[1].value = 100 - value_;
            acChart.setOption(acOption, true);

            if($("#database").val()){
                $("#db_view").attr("style","");
            }

            if($("#middleware").val()){
                $("#middleware_view").attr("style","");
            }

            //数据库
            if($("#database").val()) {
                //可用率
                var databaseAvailabilityCalculation = document.getElementById("databaseAvailabilityCalculation");
                var databaseChart = echarts.init(databaseAvailabilityCalculation);
                var databaseOption = {
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

                var value = 90,
                    value_ = 75 * value / 100;
                databaseOption.title.subtext = value + "%";
                databaseOption.series[0].data[0].value = value_;
                databaseOption.series[0].data[1].value = 100 - value_;
                databaseChart.setOption(databaseOption, true);


                //健康度
                var databaseHealth = document.getElementById("databaseHealth");
                var databaseHealthChart = echarts.init(databaseHealth);
                var databaseHealthOption = {
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

                var value = 100,
                    value_ = 75 * value / 100;
                databaseHealthOption.title.subtext = value + "%";
                databaseHealthOption.series[0].data[0].value = value_;
                databaseHealthOption.series[0].data[1].value = 100 - value_;
                databaseHealthChart.setOption(databaseHealthOption, true);
            }

            //中间件
            if($("#middleware").val()){
                    //健康度
                    var middlewareHealth = document.getElementById("middlewareHealth");
                    var middlewareHealthChart = echarts.init(middlewareHealth);
                    var middlewareHealthOption = {
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

                    var value = 100,
                        value_ = 75 * value / 100;
                    middlewareHealthOption.title.subtext = value + "%";
                    middlewareHealthOption.series[0].data[0].value = value_;
                    middlewareHealthOption.series[0].data[1].value = 100 - value_;
                    middlewareHealthChart.setOption(middlewareHealthOption, true);

                    //可用率
                    var middlewareavailabilityCalculation = document.getElementById("middlewareAvailabilityCalculation");
                    var middlewareacChart = echarts.init(middlewareavailabilityCalculation);
                    var middlewareacOption = {
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

                    var value = 90,
                        value_ = 75 * value / 100;
                    middlewareacOption.title.subtext = value + "%";
                    middlewareacOption.series[0].data[0].value = value_;
                    middlewareacOption.series[0].data[1].value = 100 - value_;
                    middlewareacChart.setOption(middlewareacOption, true);

            }

        });



        function detail(type,id){

            var id=id;
            var url='${ctx}/resource/resource/'+type+'?id='+id;
            var name='';                  //网页名称，可为空;
            var iWidth=document.body.scrollWidth;                          //弹出窗口的宽度;
            var iHeight=document.body.scrollHeight;                         //弹出窗口的高度;
            window.open(url, name, 'height='  + iHeight + ',width=' + iWidth + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
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
        .server-detail-title{
            width:100%;
            text-align: center;
        }
        .server-link{
            width:100%;
            text-align: center;
        }
        .server-link a{
            color: #3CAEE7;
        }
    </style>
</head>
<body class="body">
<div class="panel panel-primary" style="border:0;">
    <input value="${resource.id}" id="resourceId" type="hidden">
    <input value="${resource.name}" id="resourceName" type="hidden">
    <input value="${database}" id="database" type="">
    <input value="${middleware}" id="middleware" type="">

        <div class="fixed-table-body" >
            <div class="row home-row">
                <div class="">
                    <h3 class="server-detail-title">${resource.name}</h3>

                    <c:forEach items="${applicationIndicatorList}" var="item">
                       <c:if test="${item.type == 1}">
                           <div class="running-time">运行时长：<span>${item.applicationIndicatorValue.value}</span></div>
                       </c:if>

                        <c:if test="${item.type == 2}">
                            <input id="totalUser" value="${item.applicationIndicatorValue.value}">
                            <input id="totalUserUnit" value="${item.unit}" type="hidden">
                        </c:if>

                        <c:if test="${item.type == 3}">
                            <input id="onlineUser" value="${item.applicationIndicatorValue.value}">
                            <input id="onlineUserUnit" value="${item.unit}" type="hidden">
                        </c:if>

                    </c:forEach>
                    <div class="" id="pieChart" style="width: 320px;height: 160px;display: none"></div>
                </div>
            </div>
        </div>
        <div class="fixed-table-body">
            <h4 class="server-detail-title">所在服务器</h4>
            <div class="server-link"><a onclick="detail('serverDetail','${server.id}')">${server.name}</a></div>
            <div class="row home-row">
                <div class="col-lg-6 col-md-6 perception-col gauge-box">
                    <div class="gauge">
                        <div class="gauge-chart" style="" id="health"></div>
                        <div class="gauge-chart" style="" id="availabilityCalculation"></div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 perception-col gauge-box">
                    <div class="gauge">
                        <div class="gauge-chart" style="" id="CPUCalculation"></div>
                        <input value="${cpuUsedPercent}" id="cpuUsedPercentValue" type="hidden"/>
                        <div class="gauge-chart" style="" id="MEMCalculation"></div>
                        <input value="${memoryUsedPercent}" id="MEMCalculationValue" type="hidden"/>
                    </div>
                </div>
            </div>
        </div>
            <div class="fixed-table-body" >
                <div class="row home-row">
                    <c:if test="${database != null}">
                    <div class="col-lg-6 col-md-6 perception-col gauge-box" id="db_view">
                        <h4 class="server-detail-title">数据库</h4>
                        <div class="server-link"><a onclick="detail('dataBaseDetail','${database.id}')">${database.name}</a></div>
                        <div class="gauge gauge-big">
                            <div class="gauge-chart" style="" id="databaseHealth"></div>
                            <div class="gauge-chart" style="" id="databaseAvailabilityCalculation"></div>
                        </div>
                    </div>
                    </c:if>

                    <c:if test="${middleware!=null}">
                    <div class="col-lg-6 col-md-6 perception-col gauge-box" id="middleware_view">
                        <h4 class="server-detail-title">所在中间件</h4>
                        <div class="server-link"><a onclick="detail('middlewareDetail','${middleware.id}')">${middleware.name}</a></div>
                        <div class="gauge gauge-big">
                            <div class="gauge-chart" style="" id="middlewareHealth"></div>
                            <div class="gauge-chart" style="" id="middlewareAvailabilityCalculation"></div>
                        </div>
                    </div>
                    </c:if>
                </div>
            </div>


</div>

</body>
</html>








