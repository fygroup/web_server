var pieChart;
$(document).ready(function() {
    //柱状图
    // 基于准备好的dom，初始化echarts实例
    //横向柱状图
    pieChart = echarts.init(document.getElementById('pieChart'));
    var data = [
        {value: 335,name: '畅通'},
        {value: 335,name: '预警'},
        {value: 315,name: '拥塞'},
        {value: 200,name: '不可用'},
    ];

    option = {
        backgroundColor: 'rgba(0,0,0,0)',
        title: {
            text: '资源利用率',
            textStyle:{
                color:'rgba(255,255,255,0.9)',
                fontSize: 16,
                fontWeight:'nurmal',
                top:'-10px'
            },
            x:'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        color: ['#fd9173', '#f36f8a', '#5f71d2', '#42a4eb', '#4ac7f5'],
        legend: { //图例组件，颜色和名字
            left: '4%',
            bottom: '0%',
            orient: 'horizontal',
            itemGap: 12, //图例每项之间的间隔
            itemWidth: 10,
            itemHeight: 10,
            icon: 'rect',
            data: ['畅通', '预警', '拥塞','不可用'],
            textStyle: {
                color: [],
                fontStyle: 'normal',
                fontFamily: '微软雅黑',
                fontSize: 12,
            }
        },
        series: [{
            name: '利用率',
            type: 'pie',
            clockwise: false, //饼图的扇区是否是顺时针排布
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ['50%', '50%'], //饼图的中心（圆心）坐标
            radius: [54, 96], //饼图的半径
            avoidLabelOverlap: true, ////是否启用防止标签重叠
            itemStyle: { //图形样式
                normal: {
                    borderColor: '#1e2239',
                    borderWidth: 1.5,
                },
            },
            label: { //标签的位置
                normal: {
                    show: true,
                    position: 'inside', //标签的位置
                    formatter: "{d}%",
                    textStyle: {
                        color: '#fff',
                    }
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontWeight: 'bold'
                    }
                }
            },
            data: data
        }, {
            name: '',
            type: 'pie',
            clockwise: false,
            silent: true,
            minAngle: 20, //最小的扇区角度（0 ~ 360）
            center: ['50%', '50%'], //饼图的中心（圆心）坐标
            radius: [0, 49], //饼图的半径
            itemStyle: { //图形样式
                normal: {
                    borderColor: '#1e2239',
                    borderWidth: 1.5,
                    opacity: 0.21,
                }
            },
            label: { //标签的位置
                normal: {
                    show: false,
                }
            },
            data: data
        }]
    };
    pieChart.setOption(option);
})

