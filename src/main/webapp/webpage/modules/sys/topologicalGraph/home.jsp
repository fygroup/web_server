<%--
  Created by IntelliJ IDEA.
  User: KL-DJ
  Date: 2017/10/28
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>实时加载折线图</title>
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/echarts.jsp"%>
    <style>
        @media screen and (min-width: 980px) {
            .gauge{
                width:100% !important;
                display: inline-block;
                margin:0;
            }
        }
        @media screen and (max-width: 980px) and (min-width: 500px) {
            .gauge{
                width:24% !important;
                display: inline-block;
                margin:0;
            }
        }
        @media screen and (max-width: 500px) {
            .gauge{
                width:100% !important;
                display: inline-block;
                margin:0;
            }
        }
        .gauge-box{
            display: flex;
            justify-content: space-around;
            flex-wrap:wrap;
        }
    </style>
</head>
<body  id="body-container" class="wrapper wrapper-content">
<div class="conter-wrapper home-container">
    <div class="row home-row">
        <div class="col-lg-8 col-md-9 perception-col">
            <div id="category" style="width:100%;height:600px"></div>
        </div>
        <div class="col-lg-4 col-md-3 perception-col gauge-box">
            <div class="gauge"><div style="width:100%;margin:0;background: #ed9c28" id="health"></div></div>
            <div class="gauge"><div style="width:100%;margin:0;background: #00C1B3" id="availability_calculation"></div></div>
            <div class="gauge"><div style="width:100%;margin:0;background: #00b7ee"></div></div>
            <div class="gauge"><div style="width:100%;margin:0;background: #ff0000"></div></div>
        </div>
    </div>
</div>
    <h1>实时加载折线图</h1>

    <div id="main" style="width:900px;height:600px"></div>
<script  type="text/javascript">
    $(function(){
        //折线图
        var category = echarts.init(document.getElementById('category'));
        window.onresize = category.resize;

        option = {
            backgroundColor: '#fff',
            title: {
                text: '监控',
                textStyle: {
                    fontWeight: 'normal',
                    fontSize: 16,
                    color: '#57617B'
                },
                left: '6%'
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
                data: ['健康度', '可用率', 'CPU利用率','MEM利用率'],
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
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: '#57617B'
                    }
                },
                data: ['13:00', '13:05', '13:10', '13:15', '13:20', '13:25', '13:30', '13:35', '13:40', '13:45', '13:50', '13:55','14:23','14:53','15:52']
            }],
            yAxis: [{
                type: 'value',
                name: '单位（%）',
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#57617B'
                    }
                },
                axisLabel: {
                    margin: 10,
                    textStyle: {
                        fontSize: 14
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                }
            }],
            series: [{
                name: '健康度',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(137, 189, 27, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(137, 189, 27, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgb(137,189,27)',
                        borderColor: 'rgba(137,189,2,0.27)',
                        borderWidth: 12

                    }
                },
                data: [22.0, 18.2, 32.0, 65.0, 50.0, 20.0, 11.0, 25.3, 45.6, 12.2, 65.0, 22,12.2, 65.0, 22]
            }, {
                name: '可用率',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(0, 136, 212, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(0, 136, 212, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgb(0,136,212)',
                        borderColor: 'rgba(0,136,212,0.2)',
                        borderWidth: 12

                    }
                },
                data: [36.3, 69.0, 25.5, 45, 12.2, 65, 12.2, 22.0,82, 91, 34, 50, 91, 34, 50]
            }, {
                name: 'CPU利用率',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(219, 50, 51, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(219, 50, 51, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {

                        color: 'rgb(219,50,51)',
                        borderColor: 'rgba(219,50,51,0.2)',
                        borderWidth: 12
                    }
                },
                data: [20, 82, 25, 45, 22, 91, 34, 50, 20, 10, 65, 22,10, 65, 22]
            }, {
                name: 'MEM利用率',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(135, 29, 165, 0.3)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(135, 29, 165, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                        shadowBlur: 10
                    }
                },
                itemStyle: {
                    normal: {

                        color: 'rgb(135, 29, 165)',
                        borderColor: 'rgba(135, 29, 165,0.2)',
                        borderWidth: 12
                    }
                },
                data: [68, 62, 28, 36, 82, 61, 34, 56, 20, 19, 45, 62,19, 45, 62]
            } ]
        };
        category.setOption(option);
        //折线图
        //仪表盘
        var health = document.getElementById("health");
        var healthChart = echarts.init(health);
        var healthOption = {
            backgroundColor: '#fff',
            title: {
                "text": 'MEM利用率',
                "x": '50%',
                "y": '45%',
                textAlign: "center",
                "textStyle": {
                    "fontWeight": 'normal',
                    "fontSize": 15
                },
                "subtextStyle": {
                    "fontWeight": 'bold',
                    "fontSize": 20,
                    "color": '#3ea1ff'
                }
            },
            series: [
                {
                    "name": ' ',
                    "type": 'pie',
                    "radius": ['50%', '70%'],
                    "avoidLabelOverlap": false,
                    "startAngle": 225,
                    "color": ["#871DA5", "transparent"],
                    "hoverAnimation": false,
                    "legendHoverLink": false,
                    "label": {
                        "normal": {
                            "show": false,
                            "position": 'center'
                        },
                        "emphasis": {
                            "show": true,
                            "textStyle": {
                                "fontSize": '20',
                                "fontWeight": 'bold'
                            }
                        }
                    },
                    "labelLine": {
                        "normal": {
                            "show": false
                        }
                    },
                    "data": [{
                        "value": 75,
                        "name": '1'
                    }, {
                        "value": 25,
                        "name": '2'
                    }]
                },
                {
                    "name": '',
                    "type": 'pie',
                    "radius": ['52%', '68%'],
                    "avoidLabelOverlap": false,
                    "startAngle": 317,
                    "color": ["#fff", "transparent"],
                    "hoverAnimation": false,
                    "legendHoverLink": false,
                    "clockwise": false,
                    "itemStyle":{
                        "normal":{
                            "borderColor":"transparent",
                            "borderWidth":"14"
                        },
                        "emphasis":{
                            "borderColor":"transparent",
                            "borderWidth":"14"
                        }
                    }
                    ,
                    "z":10,
                    "label": {
                        "normal": {
                            "show": false,
                            "position": 'center'
                        },
                        "emphasis": {
                            "show": true,
                            "textStyle": {
                                "fontSize": '14',
                                "fontWeight": 'bold'
                            }
                        }
                    },
                    "labelLine": {
                        "normal": {
                            "show": false
                        }
                    },
                    "data": [{
                        // "value": (100 - value1) * 266 / 360,
                        "name": ''
                    }, {
                        // "value": 100 - (100 - value1) * 266 / 360,
                        "name": ''
                    }
                    ]
                }

            ]
        };



        app.timeTicket = setInterval(function() {
            var value = parseInt(Math.random() * 55) + 30,
                    value_ = (100 - value) * 266 / 360;
            healthOption.title.subtext = value + "%";
            healthOption.series[1].data[0].value = value_;
            healthOption.series[1].data[1].value = 100 - value_;
            healthChart.setOption(healthOption, true);
        }, 1000);

        //实时数据
        var dom = document.getElementById("main");
        var myChart = echarts.init(dom);
        window.onresize = category.resize;
        var app = {};
        option = null;
        var base = +new Date(2014, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var date = [];

        var data = [Math.random() * 150];
        var now = new Date(base);

        function addData(shift) {
            now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/');
            date.push(now);
            data.push((Math.random() - 0.4) * 10 + data[data.length - 1]);

            if (shift) {
                date.shift();
                data.shift();
            }

            now = new Date(+new Date(now) + oneDay);
        }

        for (var i = 1; i < 100; i++) {
            addData();
        }

        option = {
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date
            },
            yAxis: {
                boundaryGap: [0, '50%'],
                type: 'value'
            },
            series: [
                {
                    name:'成交',
                    type:'line',
                    smooth:true,
                    symbol: 'none',
                    stack: 'a',
                    areaStyle: {
                        normal: {}
                    },
                    data: data
                }
            ]
        };

        setInterval(function () {
            addData(true);
            myChart.setOption({
                xAxis: {
                    data: date
                },
                series: [{
                    name:'成交',
                    data: data
                }]
            });
        }, 500);
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }

    })

</script>

</body>
<head>
    <title>Title</title>
</head>
</html>
