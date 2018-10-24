/**
 * Created by KL-DJ on 2017/10/24.
 */
var gaugeChart;
$(document).ready(function() {
        gaugeChart = echarts.init(document.getElementById('dashboard'));
        function GetRandomNum(Min, Max) {
            var Range = Max - Min;
            var Rand = Math.random();
            return (Min + Math.round(Rand * Range));
        }

        var gauge_value = 66;


        if (gauge_value <= 55) {
            gauge_value = 0.6 * gauge_value + 14;
        } else if (gauge_value <= 70) {
            gauge_value = 2.4 * gauge_value - 85;
        } else {
            gauge_value = 0.48 * gauge_value + 49.4;
        }

        option = {
            title: {
                //show:false,
                x: "center",
                bottom: 20,
                //text:'AAA',
                subtext: '健康等级',
                textStyle:{
                    color:'#fff'
                }
            },
            tooltip: {
                show: true,
                // formatter: "{a} <br/>{b} {c}",
                backgroundColor: '#F7F9FB',
                borderColor: '#92DAFF',
                borderWidth: '1px',
                textStyle: {
                    color: 'black'
                },
                formatter: function (param) {
                    return '<em style="color:' + param.color + ';">' + param.value + '</em> 分'
                }

            },
            series: [{
                name: '信用分',
                type: 'gauge',
                // startAngle: 180,
                // endAngle: 0,
                min: 35,
                max: 95,
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 12,
                        shadowBlur: 0,
                        color: [
                            [0.2, '#ED4648'],
                            [0.4, '#F76C3E'],
                            [0.6, '#EDBF50'],
                            [0.8, '#1FC474'],
                            [1, '#138EFA']
                        ]
                    }
                },
                axisTick: {
                    show: true,
                    splitNumber: 1
                },
                splitLine: {
                    show: true,
                    length: 12,
                    lineStyle: {
                        //color:'black'
                    },
                },
                axisLabel: {
                    formatter: function (e) {
                        switch (e + "") {
                            case "41":
                                return "较差";
                            //return "";
                            case "47":
                                return "55";

                            case "53":
                                return "中等";
                            //return "";
                            case "59":
                                return "60";

                            case "65":
                                return "良好";
                            //return "";
                            case "71":
                                return "65";

                            case "77":
                                return "优秀";
                            //return "";
                            case "83":
                                return "70";

                            case "89":
                                return "极好";
                            //return "";
                            default:
                                return e;
                        }
                    },
                    textStyle: {
                        fontSize: 12,
                        fontWeight: ""
                    }
                },
                pointer: {
                    show: true,
                },
                detail: {
                    //show:false,
                    formatter: function (param) {
                        var level = '';
                        if (param < 47) {
                            level = '较差'
                        } else if (param < 59) {
                            level = '中等'
                        } else if (param < 71) {
                            level = '良好'
                        } else if (param < 83) {
                            level = '优秀'
                        } else if (param <= 95) {
                            level = '极好'
                        } else {
                            level = '暂无';
                        }
                        return level;
                    },
                    offsetCenter: [0, 140],
                    textStyle: {
                        fontSize: 20
                    }
                },
                data: [{
                    name: "",
                    value: Math.floor(gauge_value)
                }]
            }]
        };

        setInterval(function () {
            gauge_value = GetRandomNum(10, 95);
            // console.log(gauge_value);
            if (gauge_value <= 55) {
                gauge_value = 0.6 * gauge_value + 14;
            } else if (gauge_value <= 70) {
                gauge_value = 2.4 * gauge_value - 85;
            } else {
                gauge_value = 0.48 * gauge_value + 49.4;
            }
            // console.log(gauge_value);
            option = {
                title: {
                    //show:false,
                    x: "center",
                    bottom: 20,
                    //text:'AAA',
                    subtext: '',
                    textStyle:{
                        color:'#fff',
                        fontSize: 16
                    }
                },
                tooltip: {
                    show: true,
                    //  formatter: "{a} <br/>{b} {c}",
                    backgroundColor: '#F7F9FB',
                    borderColor: '#92DAFF',
                    borderWidth: '1px',
                    textStyle: {
                        color: 'black'
                    },
                    formatter: function (param) {
                        return '<em style="color:' + param.color + ';">' + param.value + '</em>'
                    }

                },
                series: [{
                    name: '',
                    type: 'gauge',
                    // startAngle: 180,
                    // endAngle: 0,
                    min: 10,
                    max: 95,
                    axisLine: {
                        show: true,
                        lineStyle: {
                            width: 12,
                            shadowBlur: 0,
                            color: [
                                [0.2, '#ED4648'],
                                [0.4, '#F76C3E'],
                                [0.6, '#EDBF50'],
                                [0.8, '#1FC474'],
                                [1, '#138EFA']
                            ]
                        }
                    },
                    axisTick: {
                        show: true,
                        splitNumber: 1
                    },
                    splitLine: {
                        show: true,
                        length: 12,
                        lineStyle: {
                            //color:'black'
                        },
                    },
                    axisLabel: {
                        formatter: function (e) {
                            switch (e + "") {
                                case "41":
                                    return "严重";
                                //return "";
                                case "47":
                                    return "55";

                                case "53":
                                    return "一般";
                                //return "";
                                case "59":
                                    return "60";

                                case "65":
                                    return "良好";
                                //return "";
                                case "71":
                                    return "65";

                                case "77":
                                    return "优秀";
                                //return "";
                                case "83":
                                    return "70";

                                case "89":
                                    return "极好";
                                //return "";
                                default:
                                    return e;
                            }
                        },
                        textStyle: {
                            fontSize: 12,
                            fontWeight: "nurmal"
                        }
                    },
                    pointer: {
                        show: true,
                    },
                    detail: {
                        //show:false,
                        formatter: function (param) {
                            var level = '';
                            if (param < 47) {
                                level = '较差'
                            } else if (param < 59) {
                                level = '中等'
                            } else if (param < 71) {
                                level = '良好'
                            } else if (param < 83) {
                                level = '优秀'
                            } else if (param <= 95) {
                                level = '极好'
                            } else {
                                level = '暂无';
                            }
                            return level;
                        },
                        offsetCenter: [0, 140],
                        textStyle: {
                            fontSize: 20
                        }
                    },
                    data: [{
                        name: "",
                        value: Math.floor(gauge_value)
                    }]
                }]
            };

            gaugeChart.setOption(option);
        }, 1000);
    })

