var rowBar;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
    //横向柱状图
    rowBar = echarts.init(document.getElementById('rowBar'));
    rowOption = {
        backgroundColor:'rgba(0,0,0,0)',
        title: {
            text: '当天流量情况top7',
            textStyle:{
                color:'rgba(255,255,255,0.9)',
                fontSize: 16,
                fontWeight:'normal'
            },
            left:'5%',
            top:'5px'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: null // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: '<div style="text-align: center;">当天流量情况top7</div>{b} : {c}'
        },
        grid: {
            left: '3%',
            right: '4%',
            top: '10%',
            height: 280, //设置grid高度
            containLabel: true
        },
        xAxis: [{
            type: 'value',
            axisLabel: {
                show: true,
                rotate: 0,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },
            axisLabel: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            splitLine: {
                show: false
            }

        }],
        yAxis: [{
            type: 'category',
            boundaryGap: true,
            axisTick: {
                show: true
            },
            axisLabel: {
               // interval: null,
                show: true,
                rotate: 0,
                textStyle: {
                    color: 'rgba(255,255,255,0.8)'
                }
            },
            data: ['合肥', '芜湖', '蚌埠', '滁州', '安庆', '铜陵', '淮南'],
            splitLine: {
                show: false
            }
        }],
        series: [{
            name: '流量',
            type: 'bar',
            barWidth: 20,
            label: {
                normal: {
                    show: true,
                    position: 'right'
                },
                textStyle: {
                    color: '#fff'
                },
            },
            itemStyle: {
                normal: {
                    color: 'rgba(38,130,157,0.6)',
                    borderWidth: 1,
                    borderColor: 'rgba(38,130,157,0.9)'

                }
            },
            data: [160, 170, 240, 264, 300, 520, 610]
        }]
    };
    rowBar.setOption(rowOption);
})

