/**
 * Created by KL-DJ on 2017/10/24.
 */
//var topNChart;
$(document).ready(function() {
    topNChart = echarts.init(document.getElementById('topN'));
    topNChartOption = {
        color: ['#3398DB'], //可选色：'#86D560', '#AF89D6', '#59ADF3', '#FF999A', '#FFCC67'
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['asus', '交换机', '服务器', '交换机', 'AUSDS', 'asusA', '交换机1', '服务器1A', '交换机2', 'AUSDS'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'',
                type:'bar',
                barWidth: '60%',
                data:[10, 52, 200, 334, 390, 330, 220, 390, 330, 220]
            }
        ]
    };
    topNChart.setOption(topNChartOption);

})

