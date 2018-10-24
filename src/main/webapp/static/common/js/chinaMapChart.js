var chinMapChart;
$(document).ready(function() {
    chinMapChart = echarts.init(document.getElementById('chinaMap'));
    //var myMounth = '一月';
    var myHosName = '资源';
    option = null;
    var geoCoordMap = {
        '宿州运维中心': [116.1162, 33.2004],
        '蚌埠运维中心': [117.3893, 32.8516],
        '芜湖运维中心': [118.1238, 31.1216],
        '阜阳运维中心': [115.9199, 32.8896],
        '淮南运维中心': [116.5313, 32.6273],
        '亳州运维中心': [115.9526, 33.7617],
        '安庆运维中心': [117.0865, 30.5465],
        '合肥运维中心': [117.2985, 32.0581],
        '铜陵运维中心': [117.4124, 30.4901],
        '六安运维中心': [116.9688, 31.368],
        '黄山运维中心': [118.4551, 30.2539],
        '淮北运维中心': [116.7946, 33.6633]
    };

    var BJData = [
        [{
            name: '合肥运维中心'
        }, {
            name: '宿州运维中心',
            value: 195
        }],
        [{
            name: '宿州运维中心'
        }, {
            name: '合肥运维中心',
            value: 90
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '蚌埠运维中心',
            value: 90
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '芜湖运维中心',
            value: 80
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '阜阳运维中心',
            value: 70
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '淮南运维中心',
            value: 70
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '亳州运维中心',
            value: 60
        }],
        [{
            name: '合肥运维中心'
        }, {
            name: '安庆运维中心',
            value: 70
        }],
        [{
            name: '亳州运维中心'
        }, {
            name: '铜陵运维中心',
            value: 40
        }],
        [{
            name: '亳州运维中心'
        }, {
            name: '六安运维中心',
            value: 130
        }],
        [{
            name: '亳州运维中心'
        }, {
            name: '黄山运维中心',
            value: 130
        }],
        [{
            name: '亳州运维中心'
        }, {
            name: '淮北运维中心',
            value: 200
        }]
    ];
    var planePath = "arrow";
    //var planePath = "path://M917.965523 917.331585c0 22.469758-17.891486 40.699957-39.913035 40.699957-22.058388 0-39.913035-18.2302-39.913035-40.699957l-0.075725-0.490164-1.087774 0c-18.945491-157.665903-148.177807-280.296871-306.821991-285.4748-3.412726 0.151449-6.751774 0.562818-10.240225 0.562818-3.450589 0-6.789637-0.410346-10.202363-0.524956-158.606321 5.139044-287.839661 127.806851-306.784128 285.436938l-1.014096 0 0.075725 0.490164c0 22.469758-17.854647 40.699957-39.913035 40.699957s-39.915082-18.2302-39.915082-40.699957l-0.373507-3.789303c0-6.751774 2.026146-12.903891 4.91494-18.531052 21.082154-140.712789 111.075795-258.241552 235.432057-312.784796C288.420387 530.831904 239.989351 444.515003 239.989351 346.604042c0-157.591201 125.33352-285.361213 279.924387-285.361213 154.62873 0 279.960203 127.770012 279.960203 285.361213 0 97.873098-48.391127 184.15316-122.103966 235.545644 124.843356 54.732555 215.099986 172.863023 235.808634 314.211285 2.437515 5.290493 4.01443 10.992355 4.01443 17.181311L917.965523 917.331585zM719.822744 346.679767c0-112.576985-89.544409-203.808826-199.983707-203.808826-110.402459 0-199.944821 91.232864-199.944821 203.808826s89.542362 203.808826 199.944821 203.808826C630.278335 550.488593 719.822744 459.256752 719.822744 346.679767z";
    //    简笔人2
    //    var  planePath="path://M621.855287 587.643358C708.573965 540.110571 768 442.883654 768 330.666667 768 171.608659 648.609267 42.666667 501.333333 42.666667 354.057399 42.666667 234.666667 171.608659 234.666667 330.666667 234.666667 443.22333 294.453005 540.699038 381.59961 588.07363 125.9882 652.794383 21.333333 855.35859 21.333333 1002.666667L486.175439 1002.666667 1002.666667 1002.666667C1002.666667 815.459407 839.953126 634.458526 621.855287 587.643358Z";

    var convertData = function(data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var dataItem = data[i];
            var fromCoord = geoCoordMap[dataItem[0].name];
            var toCoord = geoCoordMap[dataItem[1].name];
            if (fromCoord && toCoord) {
                res.push({
                    fromName: dataItem[0].name,
                    toName: dataItem[1].name,
                    coords: [fromCoord, toCoord]
                });
            }
        }
        return res;
    };

    var color = ['#a6c84c', '#ffa022', '#46bee9'];
    var mySeries = [];
    [
        [myHosName, BJData]
    ].forEach(function(item, i) {
        mySeries.push({ //线
            name: item[0],
            //                      name: item[0] + ' Top10',
            type: 'lines',
            zlevel: 1,
            effect: {
                show: true,
                period: 6,
                trailLength: 0.7,
                color: '#fff',
                symbolSize: 3
            },
            lineStyle: {
                normal: {
                    color: color[0],
                    width: 0,
                    curveness: 0.2
                }
            },
            data: convertData(item[1])
        }, { //移动 点
            name: item[0],
            //                      name: item[0] + ' Top10',
            type: 'lines',
            zlevel: 2,
            effect: {
                show: true,
                period: 6,
                trailLength: 0,
                symbol: planePath,
                symbolSize: 15
            },
            lineStyle: {
                normal: {
                    color: color[1],
                    width: 1,
                    opacity: 0.4,
                    curveness: 0.2
                }
            },
            data: convertData(item[1])
        }, { //省份圆点
            name: item[0],
            //                      name: item[0] + ' Top10',
            type: 'effectScatter',
            coordinateSystem: 'geo',
            zlevel: 2,
            rippleEffect: {
                brushType: 'stroke'
            },
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    formatter: '{b}'
                }
            },
            symbolSize: function(val) {
                return val[2] / 6;
            },
            itemStyle: {
                normal: {
                    color: function(params) {
                        var tmp = params.data.value[2]
                        if (tmp < 100) {
                            return 'blue';
                        } else if (tmp > 150) {
                            return '#ED5D79'
                        } else
                            return 'yellow';
                    }
                }
            },
            data: item[1].map(function(dataItem) {
                return {
                    name: dataItem[1].name,
                    value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
                };
            })
        });
    });

    option = {
        backgroundColor: 'rgba(0,0,0,0)',
        title: {
            text: '摩卡运维可视化平台',
            subtext: '',
            left: 'center',
            textStyle: {
                color: '#fff'
            },
            subtextStyle: {
                color: 'yellow',
                fontWeight: 'bold'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: function(params) {
                if (params.seriesIndex == 2 || params.seriesIndex == 5 || params.seriesIndex == 8) {
                    return params.name + '<br>' + params.seriesName + ':' + params.data.value[2] + ' ';
                } else if (params.seriesIndex == 1 || params.seriesIndex == 4 || params.seriesIndex == 7) {
                    return params.data.fromName + '→' + params.data.toName;
                }
            }
        },
        /*legend: {
            orient: 'vertical',
            top: '6%',
            left: 'center',
            data: [myHosName],
            textStyle: {
                color: '#fff'
            },
            selectedMode: 'single'
        },*/
        geo: {
            map: '安徽',
            label: {
                emphasis: {
                    show: false
                }
            },
            roam: true,
            itemStyle: {
                normal: {
                    areaColor: 'rgba(24,136,229,0.8)',
                    borderColor: '#0D2363'
                },
                emphasis: {
                    areaColor: 'rgba(24,136,229,0.95)'
                }
            }
        },
        series: mySeries
    };


    if (option && typeof option === "object") {
        chinMapChart.setOption(option, true);
    }

})

