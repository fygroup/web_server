var downBar;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
    downBar = echarts.init(document.getElementById('downBar'));
    var xAxisData =['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    var data2 = [15,-2,-3,-10,-5,-6,-1];
    var data3 = [10,10,10,10,10,10,10];
    var MAX = 20;

    downBarOption = {
        backgroundColor: 'rgba(0,0,0,0)',
        title:{
            text:'预警',
            left:'4%',
            textStyle:{
                color:'rgba(255,255,255,0.9)',
                fontSize: 16,
                fontWeight:'normal'
            },
        },
        legend: {
            data: ['压力','预警下限'],
            align: 'right',
            right: '0%',
            top: '5px',
            textStyle: {
                color: 'rgba(255,255,255,0.9)',
                fontStyle: 'normal',
                fontFamily: '微软雅黑',
                fontSize: 12,
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: function (params) {
                return  params.seriesName+'</br>'+
                    params.name+':'+  (params.data+MAX)+'</br>';
            }
        },

        //tooltip : {
        //    trigger: 'axis',
        //    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
        //        type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
        //    },
        //    formatter: function (params) {
        //        return  params[0].name+'</br>'+
        //        params[0].seriesName+':'+  (params[0].data+MAX)+'</br>'+
        //        params[1].seriesName+':'+  (params[1].data+MAX);
        //    }
        //},

        xAxis: {
            data: xAxisData,
            type :"category",
            position:"top",
            boundaryGap:true,
            axisLabel: {
                show: true,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },
            splitLine: {
                lineStyle: {
                    type: 'solid',
                    color: 'rgba(90,103,214,0.4)'
                }
            },
        },
        yAxis: {
            interval:5,
            type:'value',
            max:0,
            min:-35,
            axisLabel: {
                show: true,
                rotate: 0,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                },
                formatter: function (value) {
                    return value + MAX;
                }
            },
            splitLine: {
                lineStyle: {
                    type: 'solid',
                    color: 'rgba(90,103,214,0.4)'
                }
            },
        },
        series: [{
            name: '压力',
            type: 'bar',
            data: data2.map(function (val) {
                return val - MAX;
            }),
            label: {
                normal: {
                    show: true,
                    rotate: 0,
                    textStyle: {
                        color: 'rgba(255,255,255,0.8)'
                    },
                    formatter: function (params) {
                        return params.value + MAX;
                    }
                }
            },
            itemStyle: {
                normal: {
                    color: 'rgba(38,130,157,0.6)',
                    borderWidth: 1,
                    borderColor: 'rgba(38,130,157,0.9)'

                }
            },
        },
            {
                name: '预警下限',
                type: 'line',
                data: data3.map(function (val) {
                    return val - MAX;
                }),
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255,255,255,0.8)'
                        },
                        show: true,
                        formatter: function (params) {
                            return params.value + MAX;
                        }
                    }
                }
            }
        ]
    };

    downBar.setOption(downBarOption);

})

