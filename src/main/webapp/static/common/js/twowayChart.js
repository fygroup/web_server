var twowayChart;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
    twowayChart = echarts.init(document.getElementById('twowayChart'));

    var dataMap = {};
    var myData = ['合肥', '芜湖', '蚌埠', '滁州', '安庆', '铜陵', '淮南'];

    function dataFormatter(obj) {

        var temp;
        for (var year = 2002; year <= 2011; year++) {
            var max = 0;
            var sum = 0;
            temp = obj[year];
            for (var i = 0, l = temp.length; i < l; i++) {
                max = Math.max(max, temp[i]);
                sum += temp[i];
                obj[year][i] = {
                    name: myData[i],
                    value: temp[i]
                }
            }
            obj[year + 'max'] = Math.floor(max / 100) * 100;
            obj[year + 'sum'] = sum;
        }
        return obj;
    }

    dataMap.datanan = dataFormatter({
        //max : 60000,
        2011: [162, 113, 24, 112, 143, 153,256],
        2010: [142, 134, 44, 42, 143, 63,27],
        2009: [132, 14, 144, 142, 143, 163,207],
        2008: [182, 55, 12, 152, 149, 133,163],
        2007: [112, 255, 122, 192, 99, 83,43],
        2006: [182, 88, 122, 102, 109, 132,83],
        2005: [112, 95, 112, 42, 89, 143,23],
        2004: [92, 85, 152, 52, 59, 183,43],
        2003: [122, 85, 52, 82, 29, 83,63],
        2002: [182, 35, 13, 48, 149, 193,73]
    });

    dataMap.datanv = dataFormatter({
        //max : 4000,
        2011: [162, 103, 24, 112, 143, 153,256],
        2010: [142, 134, 44, 42, 143, 63,27],
        2009: [132, 14, 124, 142, 143, 163,207],
        2008: [182, 55, 12, 152, 149, 133,163],
        2007: [112, 255, 122, 192, 99, 83,43],
        2006: [182, 88, 122, 102, 109, 132,83],
        2005: [112, 95, 112, 42, 89, 143,23],
        2004: [92, 85, 152, 52, 59, 183,43],
        2003: [122, 85, 52, 82, 29, 83,63],
        2002: [182, 35, 13, 48, 149, 193,73]
    });


    option = {
        backgroundColor:'rgba(0,0,0,0)',
        baseOption: {
            timeline: {
                show: true, //隐藏坐标轴
                axisType: 'category',
                autoPlay: true,
                playInterval: 1000,
                data: ['2002-01-01', '2003-01-01', '2004-01-01', {
                    value: '2005-01-01',
                    tooltip: {
                        formatter: '{b}'
                    },
                    symbol: 'diamond',
                    symbolSize: 16
                }, '2006-01-01', '2007-01-01', '2008-01-01', '2009-01-01',
                    '2010-01-01', {
                        value: '2011-01-01',
                        tooltip: {
                            formatter: function(params) {
                                return params.name + '';
                            }
                        },
                        symbol: 'diamond',
                        symbolSize: 18
                    },
                ],
                label: {
                    formatter: function(s) {
                        return (new Date(s)).getFullYear();
                    }
                },
            },

            legend: {
                data: ['最大值', '平均值'],
                top: 22,
                right: '2%',
                textStyle: {
                    color: 'rgba(255,255,255,0.9)',
                    fontSize:14,
                    fontWeight:'normal'
                }
            },
            /*toolbox: {
                feature: {
                    dataView: {
                        show: true,
                        readOnly: false
                    },
                    restore: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }
                }
            },*/
            grid: [{
                show: false,
                left: '4%',
                top: 60,
                bottom: 30,
                containLabel: true,
                width: '46%',
            }, {
                show: false,
                left: '50.5%',
                top: 80,
                bottom: 30,
                width: '4%',
            }, {
                show: false,
                right: '4%',
                top: 60,
                bottom: 30,
                containLabel: true,
                width: '46%',
            }, ],

            xAxis: [{
                type: 'value',
                inverse: true,
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                position: 'top',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#B2B2B2',
                        fontSize: 12,
                    },
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#1F2022',
                        width: 1,
                        type: 'solid',
                    },
                },
            }, {
                gridIndex: 1,
                show: false,
            }, {
                gridIndex: 2,
                type: 'value',
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                position: 'top',
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#B2B2B2',
                        fontSize: 12,
                    },
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: '#1F2022',
                        width: 1,
                        type: 'solid',
                    },
                },
            }, ],
            yAxis: [{
                type: 'category',
                inverse: true,
                position: 'right',
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false,
                    margin: 8,
                    textStyle: {
                        color: '#9D9EA0',
                        fontSize: 12,
                    },

                },
                data: myData,
            }, {
                gridIndex: 1,
                type: 'category',
                inverse: true,
                position: 'left',
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    textStyle: {
                        color: '#9D9EA0',
                        fontSize: 12,
                    },

                },
                data: myData.map(function(value) {
                    return {
                        value: value,
                        textStyle: {
                            align: 'center',
                        }
                    }
                }),
            }, {
                gridIndex: 2,
                type: 'category',
                inverse: true,
                position: 'left',
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false,
                    textStyle: {
                        color: '#9D9EA0',
                        fontSize: 12,
                    },

                },
                data: myData,
            }, ],
            series: [{
                name: '',
                type: 'bar',
                barGap: 20,
                barWidth: 12,
                label: {
                    normal: {
                        show: false,
                    },
                    emphasis: {
                        show: true,
                        position: 'left',
                        offset: [0, 0],
                        textStyle: {
                            color: '#fff',
                            fontSize: 14,
                        },
                    },
                },
                itemStyle: {
                    normal: {
                        color: '#659F83',
                    },
                    emphasis: {
                        color: '#08C7AE',
                    },
                },
            }, {
                name: '',
                type: 'bar',
                barGap: 20,
                barWidth: 12,
                xAxisIndex: 2,
                yAxisIndex: 2,
                label: {
                    normal: {
                        show: false,
                    },
                    emphasis: {
                        show: true,
                        position: 'right',
                        offset: [0, 0],
                        textStyle: {
                            color: '#fff',
                            fontSize: 14
                        },
                    },
                },
                itemStyle: {
                    normal: {
                        color: '#F68989'
                    },
                    emphasis: {
                        color: '#F94646'
                    },
                },
            }, {
                name: '',
                type: 'pie',
                center: ['75%', '35%'],
                radius: '28%',
                z: 100
            }]
        },
        options: [{
            title: {
                text: '2002全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2002']
            }, {
                data: dataMap.datanv['2002']
            }, ]
        }, {
            title: {
                text: '2003全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2003']
            }, {
                data: dataMap.datanv['2003']
            }, ]
        }, {
            title: {
                text: '2004全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2004']
            }, {
                data: dataMap.datanv['2004']
            }, ]

        }, {
            title: {
                text: '2005全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2005']
            }, {
                data: dataMap.datanv['2005']
            }, ]
        }, {
            title: {
                text: '2006全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2006']
            }, {
                data: dataMap.datanv['2006']
            }, ]
        }, {
            title: {
                text: '全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2007']
            }, {
                data: dataMap.datanv['2007']
            }, ]
        }, {
            title: {
                text: '2008全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2008']
            }, {
                data: dataMap.datanv['2008']
            }, ]
        }, {
            title: {
                text: '2009全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2009']
            }, {
                data: dataMap.datanv['2009']
            }, ]
        }, {
            title: {
                text: '2010全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2010']
            }, {
                data: dataMap.datanv['2010']
            }, ]
        }, {
            title: {
                text: '2011全省资源分布'
            },
            series: [{
                data: dataMap.datanan['2011']
            }, {
                data: dataMap.datanv['2011']
            }, ]
        }]
    };
    twowayChart.setOption(option);
})

