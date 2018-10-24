
$(document).ready(function() {
     healthChart = echarts.init(document.getElementById('health'));

    <!--仪表盘-->
    option = {
        "series": [
            {
                "center": [
                    "25.0%",
                    "50%"
                ],
                "radius": [
                    "49%",
                    "50%"
                ],
                "clockWise": false,
                "hoverAnimation": false,
                "type": "pie",
                "itemStyle": {
                    "normal": {
                        "label": {
                            "show": true,
                            "textStyle": {
                                "fontSize": 15,
                                "fontWeight": "bold"
                            },
                            "position": "center"
                        },
                        "labelLine": {
                            "show": false
                        },
                        "color": "#5886f0",
                        "borderColor": "#5886f0",
                        "borderWidth": 10
                    },
                    "emphasis": {
                        "label": {
                            "textStyle": {
                                "fontSize": 15,
                                "fontWeight": "bold"
                            }
                        },
                        "color": "#5886f0",
                        "borderColor": "#5886f0",
                        "borderWidth": 10
                    }
                },
                "data": [
                    {
                        "value": 52.7,
                        "name": "可用率\n52.7%"
                    },
                    {
                        "name": " ",
                        "value": 47.3,
                        "itemStyle": {
                            "normal": {
                                "label": {
                                    "show": false
                                },
                                "labelLine": {
                                    "show": false
                                },
                                "color": "#5886f0",
                                "borderColor": "#5886f0",
                                "borderWidth": 0
                            },
                            "emphasis": {
                                "color": "#5886f0",
                                "borderColor": "#5886f0",
                                "borderWidth": 0
                            }
                        }
                    }
                ]
            },
            {
                "center": [
                    "75.0%",
                    "50%"
                ],
                "radius": [
                    "49%",
                    "50%"
                ],
                "clockWise": false,
                "hoverAnimation": false,
                "type": "pie",
                "itemStyle": {
                    "normal": {
                        "label": {
                            "show": true,
                            "textStyle": {
                                "fontSize": 15,
                                "fontWeight": "bold"
                            },
                            "position": "center"
                        },
                        "labelLine": {
                            "show": false
                        },
                        "color": "#ee3a3a",
                        "borderColor": "#ee3a3a",
                        "borderWidth": 10
                    },
                    "emphasis": {
                        "label": {
                            "textStyle": {
                                "fontSize": 15,
                                "fontWeight": "bold"
                            }
                        },
                        "color": "#ee3a3a",
                        "borderColor": "#ee3a3a",
                        "borderWidth": 10
                    }
                },
                "data": [
                    {
                        "value": 47.3,
                        "name": "健康率\n47.3%"
                    },
                    {
                        "name": " ",
                        "value": 52.7,
                        "itemStyle": {
                            "normal": {
                                "label": {
                                    "show": false
                                },
                                "labelLine": {
                                    "show": false
                                },
                                "color": "#ee3a3a",
                                "borderColor": "#ee3a3a",
                                "borderWidth": 0
                            },
                            "emphasis": {
                                "color": "#ee3a3a",
                                "borderColor": "#ee3a3a",
                                "borderWidth": 0
                            }
                        }
                    }
                ]
            }
        ]
    };
    healthChart.setOption(option);
})