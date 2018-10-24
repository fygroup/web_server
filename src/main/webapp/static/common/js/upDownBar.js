var upDownBar;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
    upDownBar = echarts.init(document.getElementById('upDownBar'));

    var itemStyle = {
        normal: {

        },
        emphasis: {
            barBorderWidth: 1,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0,0,0,0.5)'
        }
    };

    upDownBarOption = {
        backgroundColor: 'rgba(0,0,0,0)',
        title: {
            text:'异常处理',
            left:'4%',
            textStyle:{
                color:'rgba(255,255,255,0.9)',
                fontSize: 16,
                fontWeight:'normal'
            }
        },
        legend: {
            data: ['未处理', '已处理'],
            right: '0%',
            top: '5px',
            textStyle: {
                color: 'rgba(255,255,255,0.9)',
                fontStyle: 'normal',
                fontFamily: '微软雅黑',
                fontSize: 12,
            }
        },
        tooltip: {
            formatter: function(params) {
                if (params.value < 0) {
                    return params.name + '<br/>已处理:' + -params.value+"%"
                } else {
                    return params.name + '<br/>未处理:' + params.value+"%"
                }
            }
        },
        xAxis: {
            name: '',
            silent: false,
            type: 'category',
            data: [],
            axisLabel: {
                show: true,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },

        },
        yAxis: [{
            position :"right",
            inverse: true,
            name: '',
            splitArea: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                },
                formatter: function(value) {
                    if (value < 0) {
                        return -value+"%"
                    } else {
                        return value+"%"
                    }
                }
            }

        }, {
            inverse: true,
            name: '',
            position: 'right',
            nameLocation: 'start',
            splitArea: {
                show: false
            }
        }],
        grid: {

        },
        series: [{
            name: '未处理',
            type: 'bar',
            stack: 'one',
            itemStyle: itemStyle,
            data: [{
                name: "异常",
                value: "30",
                itemStyle: {
                    normal: {
                        color: "rgba(237,70,72,0.6)"
                    }
                }
            }, {
                name: "异常",
                value: "80",
                itemStyle: {
                    normal: {
                        color: "rgba(237,70,72,0.6)"
                    }
                }
            }, {
                name: "异常",
                value: "40",
                itemStyle: {
                    normal: {
                        color: "rgba(237,70,72,0.6)"
                    }
                }
            }]
        }, {
            name: '已处理',
            type: 'bar',
            stack: 'one',
            itemStyle: itemStyle,
            data: [{
                name: "异常",
                value: "-70",
                itemStyle: {
                    normal: {
                        color: "rgba(38,130,157,0.6)"
                    }
                }
            }, {
                name: "异常",
                value: "-20",
                itemStyle: {
                    normal: {
                        color: "rgba(38,130,157,0.6)"
                    }
                }
            }, {
                name: "异常",
                value: "-60",
                itemStyle: {
                    normal: {
                        color: "rgba(38,130,157,0.6)"
                    }
                }
            }]
        }]
    };
    upDownBar.setOption(upDownBarOption);
})

