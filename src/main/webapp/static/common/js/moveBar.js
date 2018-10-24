var barGraph;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
     barGraph = echarts.init(document.getElementById('moveBar'));

    array=[10, 100, 80, 170, 90, 30,20,30,50,15];
    var array1=[647, 647, 647, 647,647, 647,450,480,540,620];
    option = {
        backgroundColor: 'rgba(0,0,0,0)',
        animation: false,
        title:{
            text:'故障统计',
            textStyle:{
                color:'rgba(255,255,255,0.9)',
                fontSize: 16,
                fontWeight:'normal'
            },
            left:'5%',
            top:'5px',

        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow',
            }
        },
        grid: {
            top: 60,
            bottom: 30
        },
        xAxis: {
            type: 'value',
            position: 'bottom',
            splitLine: {
                lineStyle: {
                    type: 'solid',
                    color: 'rgba(90,103,214,0.4)'
                }
            },
            axisLabel: {
                show: true,
                rotate: 0,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },
        },
        yAxis: {
            splitNumber: 25,
            type: 'category',
            axisLine: {
                lineStyle: {
                    color: 'rgba(90,103,214,0.4)',
                    type: 'solid'
                }
            },
            axisLabel: {
                show: true,
                rotate: 0,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },
            axisTick: {
                show: true
            },
            splitLine: {
                lineStyle: {
                    type: 'solid',
                    color: 'rgba(90,103,214,0.4)'
                }
            },
            data: ['合肥', '芜湖', '蚌埠', '淮南', '马鞍山', '淮北', '铜陵', '安庆', '黄山', '阜阳']
        },
        series: [{
            name: ' ',
            type: 'bar',
            barGap: '-100%',
            label: {
                normal: {
                    textStyle: {
                        color: '#fff'
                    },
                    position: 'left',
                    show: false,
                    formatter: '{b}'
                }
            },
            itemStyle: {
                normal: {
                    color: 'rgba(255,255,255,0.2)',
                    borderWidth: 1,
                    borderColor: 'rgba(90,103,214,0.8)'

                }
            },
            data: array1
        }, {
            type: 'bar',
            silent: true,
            barGap: '-100%',
            data: array,
            itemStyle: {
                normal: {
                    color: 'rgba(90,103,214,0.8)',

                }
            },

        }]
    }

    barGraph.setOption(option);
})

