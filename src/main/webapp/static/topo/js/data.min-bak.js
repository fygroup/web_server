var testData = {
    config: {
        global: {
            "topos": {"1": {"label": "网络拓扑", "usable": true}},
            "event_animate": "5",
            "kpi_cache_time": "300",
            "reload_time": "300",
            "auto_view_status": "1",
            "popup_window": "0",
            "resource_alter_valid": "5",
            "server_run_test": "1"
        },
        event4symbol: [{"in": "5", "type": "matrix", "values": "0 0 1.99 0.01 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0"}, {
            "in": "4",
            "type": "matrix",
            "values": "0 0 0 1 0 1 1 0.11 0 0 0 0 0 0 0 0 0 0 1 0"
        }, {"in": "3", "type": "matrix", "values": "0 0 0 0 1 0 1 1 0.33 0 0 0 0 0 0 0 0 0 1 0"}, {
            "in": "2",
            "type": "matrix",
            "values": "0 0 0 0 0 1 0 0 0 0 0 1 1 0.11 0 0 0 0 1 0"
        }, {"in": "1", "type": "matrix", "values": "0 0 0 0 0 1 1 1 0.11 0 0 0 0 0 0 0 0 0 1 0"}],
        event4line: [{"in": "5", "type": "matrix", "values": "0 0 1 0.66 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0"}, {
            "in": "4",
            "type": "matrix",
            "values": "0 1.99 0 0 0 0 0.22 1.99 0 0 0 0 0 0 0 0 0 0 1 0"
        }, {"in": "3", "type": "matrix", "values": "0 0 0 0 1 0 1 1 0.33 0 0 0 0 0 0 0 0 0 1 0"}, {
            "in": "2",
            "type": "matrix",
            "values": "0 0 0 0 0 1 0 0 0 0 0 1 1 0.11 0 0 0 0 1 0"
        }, {"in": "1", "type": "matrix", "values": "0 0 0 0 0 1 1 1 0.11 0 0 0 0 0 0 0 0 0 1 0"}],
        objectClass: {
            "3003": {"id": "3003", "name": "路由器"},
            "3004": {"id": "3004", "name": "交换机"},
            "3005": {"id": "3005", "name": "服务器"},
            "3006": {"id": "3006", "name": "链路"},
            "3015": {"id": "3015", "name": "服务器"},
            "3016": {"id": "3016", "name": "服务器"},
            "3017": {"id": "3017", "name": "服务器"},
            "3018": {"id": "3018", "name": "服务器"},
            "3019": {"id": "3019", "name": "服务器"},
            "3020": {"id": "3020", "name": "防火墙"},
            "3022": {"id": "3022", "name": "服务器"},
            "3023": {"id": "3023", "name": "交换机"},
            "3024": {"id": "3024", "name": "VPN"},
            "3027": {"id": "3027", "name": "负载均衡"},

            //当前我们本地系统的功能
            "11": {"id": "11", "name": "路由器"},
            "12": {"id": "12", "name": "交换机"},
            "21": {"id": "21", "name": "windows服务器"},
            "22": {"id": "22", "name": "Linux服务器"},
            "31": {"id": "31", "name": "网络链路"},
            "41": {"id": "41", "name": "mysql"},
            "42": {"id": "42", "name": "sqlserver"},
            "51": {"id": "51", "name": "tomcat"},
            "52": {"id": "52", "name": "nginx"},
            "61": {"id": "61", "name": "防火墙"},
            "62": {"id": "62", "name": "入侵检测设备"},
            "71": {"id": "71", "name": "存储设备1"},
            "81": {"id": "81", "name": "web应用服务"},
            "82": {"id": "82", "name": "邮件应用服务"},
            "83": {"id": "83", "name": "ftp应用服务"},

        },
        //配置每个资源的可操作项 和 整个拓扑图点击的可操作项目
        contextmenu4show: {
            "view": [/*{
                "id": "1.1",
                "text": "选择工具",
                "action": "$.lab.ui.setViewDragSelect",
                "available": "topology.ui.viewDragAction!='select'"
            },*/{
                "id": "1.4",
                "text": "指标挂载",
                "action": "$.lab.itsm.hangInfo",
                "separator": true
            }, {
                "id": "1.6",
                "text": "编辑",
                "action": "$.lab.direct.gotoEdit",
                "separator": true
            },{
                "id": "1.8",
                "text": "刷新",
                "action": "$.lab.topo.viewRefresh"
            }, {
                "id": "1.9",
                "text": "中心位置",
                "action": "$.lab.topo.viewPositionReset"
            },/*{
                "id": "1.12",
                "children": [{
                    "id": "1.12.2",
                    "text": "pdf文档",
                    "available": "topology.id==1"
                }, {
                    "id": "1.12.6",
                    "text": "png图片",
                    "available": "topology.id==1",
                    "separator": true
                }, {"id": "1.12.8", "text": "jpg图片", /!*"image": dex +  "images/icon/img-16.png",*!/ "available": "topology.id==1"}],
                "text": "导出…",
            }, */{
                "id": "1.14",
                "text": "全屏显示",
                "action": "$.lab.ui.fullScreen",
                "separator": true,
                "visible": "lab.testFullScreen()==false"
            }, {
                "id": "1.15",
                "text": "取消全屏",
                "action": "$.lab.ui.unfullScreen",
                "visible": "lab.testFullScreen()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            }],
            "symbol": [{
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                },],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            }, {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            }],
            "container": [{
                "id": "1.1",
                "text": "展开",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==false"
            }, {
                "id": "1.2",
                "text": "闭合",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                },],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            }, {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            }],
            "lseter": [{
                "id": "1.1",
                "text": "展开",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==false"
            }, {
                "id": "1.2",
                "text": "闭合",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            }],
            //链路配置
            "line": [{
                "id": "1.1",
                "text": "链路信息",
                //    "image": dex +  "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            },/*{
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },*/{"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            }],

            //路由器的可操作项目，下面分别配置
            "11": [
                {
                    "id": "1.0",
                    "text": "查看详情",
                    "action": "$.lab.itsm.gotoSymbolDetail",
                    "available": "lab.testSingleSelected()==true"
                }, {
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "12": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "21": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "22": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "31": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "41": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{//mysql
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "42": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{//sqlserver
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "51": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "52": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "61": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{//防火墙
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "62": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "71": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "81": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{//web应用服务
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "82": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],
            "83": [{
                "id": "1.0",
                "text": "查看详情",
                "action": "$.lab.itsm.gotoSymbolDetail",
                "available": "lab.testSingleSelected()==true"
            },{
                "id": "1.1",
                "text": "设备信息",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }
            ],

            //物理链路

            "3003": [{
                "id": "1.1",
                "text": "设备信息",
              //  "image":  dex + "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                //    "image": dex +  "images/icon/alert_active-16.png",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
             //   "image": dex +  "images/icon/alert-16.png",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                //  "image":  dex + "images/icon/information-16.png",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }, {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                 //   "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                 //   "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                //    "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                 //   "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
              //  "image": dex +  "images/icon/zoom-16.png"
            }],
            "3004": [{
                "id": "1.1",
                "text": "设备信息",
             //   "image": dex +  "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                //    "image": dex +  "images/icon/alert_active-16.png",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }/*, {
                    "id": "2.1.4",
                    "text": "历史告警",
                    "image": dex +  "images/icon/alert_history-16.png",
                    "action": "$.lab.event.historyAlarm",
                    "available": "topology.id==1"
                }*/],
                "text": "告警详情",
             //   "image": dex +  "images/icon/alert-16.png",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                //  "image":  dex + "images/icon/information-16.png",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            },{"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                //    "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                //    "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                 //   "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
             //   "image": dex +  "images/icon/zoom-16.png"
            }],
            "3005": [{
                "id": "1.1",
                "text": "设备信息",
             //   "image": dex +  "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                //    "image": dex +  "images/icon/alert_active-16.png",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }/*, {
                    "id": "2.1.4",
                    "text": "历史告警",
                    "image": dex +  "images/icon/alert_history-16.png",
                    "action": "$.lab.event.historyAlarm",
                    "available": "topology.id==1"
                }*/],
                "text": "告警详情",
            //    "image": dex +  "images/icon/alert-16.png",
                "available": "lab.testEvent()==true"
            }, {
                "id": "3.1",
                "text": "文本框显示",
                //  "image":  dex + "images/icon/information-16.png",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }, {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                //    "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                 //   "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                //    "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            //    "image": dex +  "images/icon/zoom-16.png"
            }],
            "3006": [{
                "id": "1.1",
                "text": "链路信息",
             //   "image": dex +  "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                //    "image": dex +  "images/icon/alert_active-16.png",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
            //    "image": dex +  "images/icon/alert-16.png",
                "available": "lab.testEvent()==true"
            },  {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                //    "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                //    "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                    "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
             //   "image": dex +  "images/icon/zoom-16.png"
            }],
            "3020": [{
                "id": "1.1",
                "text": "设备信息",
            //    "image": dex +  "images/icon/information-16.png",
                "action": "$.lab.itsm.detailInfo",
                "available": "lab.testSingleSelected()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "活动告警",
                //    "image": dex +  "images/icon/alert_active-16.png",
                    "action": "$.lab.event.activeAlarm",
                    "available": "lab.testActiveEvent()==true"
                }],
                "text": "告警详情",
             //   "image": dex +  "images/icon/alert-16.png",
                "available": "lab.testEvent()==true"
            },{
                "id": "3.1",
                "text": "文本框显示",
                //  "image":  dex + "images/icon/information-16.png",
                "action": "$.lab.itsm.InfoTextShow",
                "available": "lab.testSingleSelected()==true"
            }, {"id": "active", "action": "$.lab.menu.activeUrl"}, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                //    "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                //    "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                //    "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
            //    "image": dex +  "images/icon/zoom-16.png"
            }]
        },
        contextmenu4edit: {
            "view": [{
                "id": "1.1",
                "text": "属性面板",
                //"image": dex +  "images/icon/attribute-16.png",
                "action": "$.lab.topo.propertyPanel"
            }, /*{
                "id": "1.2",
                "text": "选择工具",
               // "image": dex +  "images/icon/selector-16.png",
                "action": "$.lab.ui.setViewDragSelect",
                "available": "topology.ui.viewDragAction!='select'"
            }, {
                "id": "1.3",
                "text": "平移工具",
                //"image": dex +  "images/icon/drag-16.png",
                "action": "$.lab.ui.setViewDragMove",
                "available": "topology.ui.viewDragAction!='move'"
            }, {
                "id": "1.4",
                "text": "图形脚本",
               // "image": dex +  "images/icon/script-16.png",
                "action": "$.lab.exp.svgXml"
            }, {
                "id": "1.6",
                "text": "粘贴",
               // "image": dex +  "images/icon/paste-16.png",
                "action": "$.lab.topo.pasteElement",
                "separator": true,
                "available": "lab.testPasteElement()==true"
            },*/ {
                "id": "1.8",
                "text": "保存",
               // "image": dex +  "images/icon/save-16.png",
                "action": "$.lab.topo.saveTopo",
                "separator": true,
                "available": "topology.id==1"
               // "available":true
            }, {
                "id": "1.9",
                "text": "返回",
                //"image": dex +  "images/icon/log_out-16.png",
                "action": "$.lab.direct.gotoMain"
            }, /*{
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "力学布局",
                   // "image": dex +  "images/icon/layout_force-16.png",
                    "action": "$.lab.topo.forceLayout"
                }, {
                    "id": "2.1.4",
                    "text": "树形布局",
                    //"image": dex +  "images/icon/layout_tree-16.png",
                    "action": "$.lab.topo.treeLayout",
                    "visible": "lab.testLayoutTreeVisible()==true"
                }],
                "text": "自动布局",
                //"image": dex +  "images/icon/layout-16.png"
            },*/ {
                "id": "1.13",
                "text": "全屏显示",
               // "image": dex +  "images/icon/fullscreen-16.png",
                "action": "$.lab.ui.fullScreen",
                "separator": true,
                "visible": "lab.testFullScreen()==false"
            }, {
                "id": "1.14",
                "text": "取消全屏",
                //"image": dex +  "images/icon/un_fullscreen-16.png",
                "action": "$.lab.ui.unfullScreen",
                "visible": "lab.testFullScreen()==true"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                   // "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                   // "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                //    "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
               // "image": dex +  "images/icon/zoom-16.png"
            }],
            "symbol": [{
                "id": "1.1",
                "text": "属性面板",
                //"image": dex +  "images/icon/attribute-16.png",
                "action": "$.lab.topo.propertyPanel"
            }, {
                "id": "1.2",
                "text": "绘制连线",
               // "image": dex +  "images/icon/draw_line-16.png",
                "action": "$.lab.topo.drawStraightLine",
                "visible": "lab.testDrawLine()==true"
            }, {
                "id": "1.3",
                "text": "拿出容器",
               // "image": dex +  "images/icon/takeout-16.png",
                "action": "$.lab.topo.removeSymbolsFromContainer",
                "visible": "parent!=null"
            },/* {
                "id": "1.5",
                "text": "复制",
               // "image": dex +  "images/icon/copy-16.png",
                "action": "$.lab.topo.copySelectedElement",
                "separator": true,
                "visible": "lab.testCopySelectedElement()==true"
            }, {
                "id": "1.6",
                "text": "粘贴",
               // "image": dex +  "images/icon/paste-16.png",
                "action": "$.lab.topo.pasteElement",
                "available": "lab.testPasteElement()==true"
            }, */{
                "id": "1.7",
                "text": "删除",
               // "image": dex +  "images/icon/del-16.png",
                "action": "$.lab.topo.deleteElement"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "置于顶层",
                   // "image": dex +  "images/icon/move_front-16.png",
                    "action": ""
                }, {"id": "2.1.4", "text": "置于底层", "image": dex +  "images/icon/move_back-16.png", "action": ""}, {
                    "id": "2.1.8",
                    "text": "上移一层",
                   // "image": dex +  "images/icon/move_forwards-16.png",
                    "action": "",
                    "separator": true
                }, {"id": "2.1.10", "text": "下移一层", "image": dex +  "images/icon/move_backwards-16.png", "action": ""}],
                "text": "图层操作",
                //"image": dex +  "images/icon/layers-16.png",
                "visible": "false",
                "available": "false"
            }, {
                "id": "2.1",
                "children": [ {
                    "id": "2.1.4",
                    "text": "树形布局",
                   // "image": dex +  "images/icon/layout_tree-16.png",
                    "action": "$.lab.topo.treeLayout",
                    "visible": "lab.testLayoutTreeVisible()==true"
                }],
                "text": "自动布局",
                //"image": dex +  "images/icon/layout-16.png"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                  //  "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                  //  "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                  //  "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                  //  "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
              //  "image": dex +  "images/icon/zoom-16.png"
            }],
            "container": [{
                "id": "1.1",
                "text": "属性面板",
              //  "image": dex +  "images/icon/attribute-16.png",
                "action": "$.lab.topo.propertyPanel"
            }, {
                "id": "1.2",
                "text": "展开",
              //  "image": dex +  "images/icon/expand-16.png",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==false"
            }, {
                "id": "1.3",
                "text": "闭合",
              //  "image": dex +  "images/icon/collapse-16.png",
                "action": "$.lab.topo.elementExpandCollapseToggle",
                "visible": "this.isExpand==true"
            }, /*{
                "id": "1.5",
                "text": "复制",
              //  "image": dex +  "images/icon/copy-16.png",
                "action": "$.lab.topo.copySelectedElement",
                "separator": true,
                "visible": "lab.testCopySelectedElement()==true"
            }, {
                "id": "1.6",
                "text": "粘贴",
              //  "image": dex +  "images/icon/paste-16.png",
                "action": "$.lab.topo.pasteElement",
                "available": "lab.testPasteElement()==true"
            },*/ {
                "id": "1.8",
                "text": "删除",
              //  "image": dex +  "images/icon/del-16.png",
                "action": "$.lab.topo.deleteElement",
                "separator": true
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "置于顶层",
                  //  "image": dex +  "images/icon/move_front-16.png",
                    "action": ""
                }, {"id": "2.1.4", "text": "置于底层", "image": dex +  "images/icon/move_back-16.png", "action": ""}, {
                    "id": "2.1.8",
                    "text": "上移一层",
                  //  "image": dex +  "images/icon/move_forwards-16.png",
                    "action": "",
                    "separator": true
                }, {"id": "2.1.10", "text": "下移一层", "image": dex +  "images/icon/move_backwards-16.png", "action": ""}],
                "text": "图层操作",
              //  "image": dex +  "images/icon/layers-16.png",
                "visible": "false",
                "available": "false"
            },/* {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "力学布局",
                  //  "image": dex +  "images/icon/layout_force-16.png",
                    "action": "$.lab.topo.forceLayout"
                }, {
                    "id": "2.1.4",
                    "text": "树形布局",
                  //  "image": dex +  "images/icon/layout_tree-16.png",
                    "action": "$.lab.topo.treeLayout",
                    "visible": "lab.testLayoutTreeVisible()==true"
                }],
                "text": "自动布局",
               // "image": dex +  "images/icon/layout-16.png"
            }, */{
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                  //  "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                   // "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                   // "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                   // "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
                "image": dex +  "images/icon/zoom-16.png"
            }],
            "line": [{
                "id": "1.1",
                "text": "属性面板",
               // "image": dex +  "images/icon/attribute-16.png",
                "action": "$.lab.topo.propertyPanel"
            }, {
                "id": "1.2",
                "text": "横平竖直",
              //  "image": dex +  "images/icon/poly_line_format-16.png",
                "action": "$.lab.topo.rightAnglePolyLine",
                "visible": "lab.testPolyPoints()==true"
            }, {
                "id": "1.4",
                "text": "删除",
             //   "image": dex +  "images/icon/del-16.png",
                "action": "$.lab.topo.deleteElement",
                "separator": true
            }, {
                "id": "1.7",
                "text": "修改链路",
              //  "image": dex +  "images/icon/link-16.png",
                "action": "$.lab.itsm.modifyLink",
                "visible": "this.objectClass==3006"
            }, {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                  //  "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                  //  "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                  //  "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                  //  "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
              //  "image": dex +  "images/icon/zoom-16.png"
            }],
            "lseter": [{
                "id": "1.1",
                "text": "属性面板",
              //  "image": dex +  "images/icon/attribute-16.png",
                "action": "$.lab.topo.propertyPanel"
            }, {
                "id": "1.2",
                "text": "横平竖直",
              //  "image": dex +  "images/icon/poly_line_format-16.png",
                "action": "$.lab.topo.rightAnglePolyLine",
                "visible": "this.points.length>2"
            }, /*{
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "力学布局",
                  //  "image": dex +  "images/icon/layout_force-16.png",
                    "action": "$.lab.topo.forceLayout"
                }, {
                    "id": "2.1.4",
                    "text": "树形布局",
                 //   "image": dex +  "images/icon/layout_tree-16.png",
                    "action": "$.lab.topo.treeLayout",
                    "visible": "lab.testLayoutTreeVisible()==true"
                }],
                "text": "自动布局",
              //  "image": dex +  "images/icon/layout-16.png"
            },*/ {
                "id": "2.1",
                "children": [{
                    "id": "2.1.2",
                    "text": "满屏尺寸",
                //    "image": dex +  "images/icon/zoom_fit-16.png",
                    "action": "$.lab.topo.viewFitToCanvas"
                }, {
                    "id": "2.1.4",
                    "text": "放大尺寸",
                //    "image": dex +  "images/icon/zoom_in-16.png",
                    "action": "$.lab.topo.viewZoomIn"
                }, {
                    "id": "2.1.6",
                    "text": "缩小尺寸",
                //    "image": dex +  "images/icon/zoom_out-16.png",
                    "action": "$.lab.topo.viewZoomOut"
                }, {
                    "id": "2.1.8",
                    "text": "原始尺寸",
                //    "image": dex +  "images/icon/zoom_original-16.png",
                    "action": "$.lab.topo.viewOrigiZoom"
                }],
                "text": "图形缩放",
              //  "image": dex +  "images/icon/zoom-16.png"
            }]
        },
        convertor: {
            "TEXT": {"id": "TEXT", "zhname": "文本", "func": "text_disp"},
            "INTEGER": {"id": "INTEGER", "zhname": "整数", "func": "integer_disp"},
            "BYTE": {"id": "BYTE", "zhname": "Byte", "func": "bytes_disp"},
            "KBYTE": {"id": "KBYTE", "zhname": "KB", "func": "kbytes_disp"},
            "BYTEPS": {"id": "BYTEPS", "zhname": "Byte/秒", "func": "byteps_disp"},
            "KBYTEPS": {"id": "KBYTEPS", "zhname": "KB/秒", "func": "kbyteps_disp"},
            "BITPS": {"id": "BITPS", "zhname": "bit/秒", "func": "bitps_disp"},
            "BOOLEAN2TEXT": {"id": "BOOLEAN2TEXT", "zhname": "是否(文本)", "func": "bool2Text"},
            "BOOLEAN2SYMBOL": {"id": "BOOLEAN2SYMBOL", "zhname": "是否(√与×)", "func": "bool2Symbol"},
            "CONNECT": {"id": "CONNECT", "zhname": "连通状态(通断)", "func": "connect"},
            "LCD": {"id": "LCD", "zhname": "LCD数字字体", "func": "LCD"},
            "IP": {"id": "IP", "zhname": "IP地址", "func": "ip_disp"},
            "PERCENT2BAR": {"id": "PERCENT2BAR", "zhname": "百分比(条状)", "func": "percent2Bar"},
            "REVERSE_PERCENT2BAR": {"id": "REVERSE_PERCENT2BAR", "zhname": "逆向百分比(条状)", "func": "reversePercent2Bar"},
            "PERCENT2LCD": {"id": "PERCENT2LCD", "zhname": "百分比(LCD)", "func": "percent2LCD"},
            "REVERSE_PERCENT2LCD": {"id": "REVERSE_PERCENT2LCD", "zhname": "逆向比分比(LCD)", "func": "reversePercent2LCD"},
            "PERCENT2GAUGE": {"id": "PERCENT2GAUGE", "zhname": "百分比(仪表盘)", "func": "percent2Gauge"},
            "REVERSE_PERCENT2GAUGE": {
                "id": "REVERSE_PERCENT2GAUGE",
                "zhname": "逆向比分比(仪表盘)",
                "func": "reversePercent2Gauge"
            },
            "ALARM": {"id": "ALARM", "zhname": "告警", "func": "alarm_disp"},
            "RUNTIME": {"id": "RUNTIME", "zhname": "运行时长", "func": "runtime"},
            "TEMPERATURE": {"id": "TEMPERATURE", "zhname": "温度", "func": "temperature"},
            "SUPPORT": {"id": "SUPPORT", "zhname": "支持", "func": "support"}
        },
        navmenu4show: [/*{
            "id": "1",
            "text": "操作",
            "disabled":false,
            //"icon": dex + "/images/icon/system-32.png",
           // "disicon": dex + "images/icon_dis/system-32.png",
            "children": [{
                "id": "1.1",
                "text": "选择工具",
              //  "icon": dex + "images/icon/selector-16.png",
              //  "disicon": dex + "images/icon_dis/selector-16.png",
                "action": $.lab.ui.setViewDragSelect,
                "available": "topology.ui.viewDragAction!='select'",
                "disabled": true
            }, {
                "id": "1.2",
                "text": "平移工具",
               // "icon": dex + "images/icon/drag-16.png",
               // "disicon": dex + "images/icon_dis/drag-16.png",
                "action": $.lab.ui.setViewDragMove,
                "available": "topology.ui.viewDragAction!='move'"
            }, {"separator": true}, {
                "id": "1.3",
                "text": "批量选择",
               // "icon": dex + "images/icon/group-16.png",
               // "disicon": dex + "images/icon_dis/group-16.png",
                "action": $.lab.topo.selectElemtByCondition,
                "available": "true"
            }, {
                "id": "1.4",
                "text": "设为默认",
               //"icon": dex + "images/icon/eye-16.png",
               // "disicon": dex + "images/icon_dis/eye-16.png",
                "action": $.lab.topo.setUserDefaultView,
                "available": "topology.permission==2||topology.permission==3||topology.permission==4",
                "disabled": true
            }, {"separator": true}, {
                "id": "1.5",
                "text": "导出...",
               // "icon": dex + "images/icon/camera-16.png",
               // "disicon": dex + "images/icon_dis/camera-16.png",
                "children": [{
                    "id": "1.5.1",
                    "text": "PDF文档",
                   // "icon": dex + "images/icon/acrobat-16.png",
                   // "disicon": dex + "images/icon_dis/acrobat-16.png",
                    "disabled": true,
                    "action": $.lab.exp.exportPdf
                }, {
                    "id": "1.5.2",
                    "text": "PNG图片0",
                   // "icon": dex + "images/icon/map-16.png",
                   // "disicon": dex + "images/icon_dis/map-16.png",
                    "disabled": false,
                    "action": $.lab.exp.exportPng
                }, {
                    "id": "1.5.3",
                    "text": "JPG图片",
                    //"icon": dex + "images/icon/img-16.png",
                   // "disicon": dex + "images/icon_dis/img-16.png",
                    "disabled": true,
                    "action": $.lab.exp.exportJpg
                }]
            }]
        },*/
            {
            "id": "2",
            "text": "视图",
            "disabled":false,
            "children": [{
                "id": "2.1",
                "text": "位置还原",
                //"icon": dex + "images/icon/back_location-16.png",
               // "disicon": dex + "images/icon_dis/back_location-16.png",
                "action": $.lab.topo.viewPositionReset
            }, {"separator": true}, {
                "id": "2.2",
                "text": "窗口...",
                "children": [{
                    "id": "2.2.4",
                    "text": "鹰眼窗",
                    "action": $.lab.ui.eagleEyeDisplayToggle
                }]
            }, {"separator": true}, {
                "id": "2.3",
                "text": "满屏尺寸",
                "action": $.lab.topo.viewFitToCanvas
            }, {
                "id": "2.4",
                "text": "放大尺寸",
                "action": $.lab.topo.viewZoomIn
            }, {
                "id": "2.5",
                "text": "缩小尺寸",
                "action": $.lab.topo.viewZoomOut
            }, {
                "id": "2.6",
                "text": "原始尺寸",
                "action": $.lab.topo.viewOrigiZoom
            }]
        },/* {
            "id": "3",
            "text": "分析",
            "disabled": false,
            "children": [{
                "id": "3.1",
                "text": "指标挂载",
                "action": $.lab.itsm.hangInfo
            }]
        },*/ {
            "id": "4",
            "text": "编辑",
            "action": $.lab.direct.gotoEdit,
            "disabled": false
        },
           {
         "id": "6",
         "text": "全屏",
         "action": $.lab.ui.fullScreen,
         "available": "lab.testFullScreen()==false",
         "disabled": false
         }/*,{
                "id": "5",
                "text": "新建",
                "action": $.lab.direct.addView,
                "available": "topology.permission==3||topology.permission==4",
                "disabled": false
            }*/
            ,{
                "id": "6",
                "text": "返回",
                "action": $.lab.direct.returnTopoList,
                "available": "",
                "disabled": false
            }
            ],
        navmenu4edit: [{
            "id": "1",
            "text": "操作",
            "children": [{
                "id": "1.1",
                "text": "导入节点",
                "action": $.lab.topo.importSymbols
            }, /*{"separator": true}, {
                "id": "1.2",
                "text": "选择工具",
                "action": $.lab.ui.setViewDragSelect,
                "available": "topology.ui.viewDragAction!='select'",
                "disabled": true
            }, {
                "id": "1.3",
                "text": "平移工具",
                "action": $.lab.ui.setViewDragMove,
                "available": "topology.ui.viewDragAction!='move'"
            },*/{"separator": true}, {
                "id": "1.4",
                "text": "批量选择",
                "action": $.lab.topo.selectElemtByCondition,
                "available": "true"
            },{"separator": true}]
        }, {
            "id": "2",
            "text": "绘图",
            "children": [{
                "id": "2.1",
                "text": "形状...",
                "children": [{
                    "id": "2.1.1",
                    "text": "节点",
                    "action": $.lab.topo.addVitualSymbol
                }, {"separator": true}]
            }, {
                "id": "2.3",
                "text": "线条...",
                "children": [{
                    "id": "2.3.1",
                    "text": "直线",
                    "action": $.lab.topo.drawStraightLine
                }]
            },{"separator": true}, {
                "id": "2.5",
                "text": "布局...",
                "children": [{
                    "id": "2.5.2",
                    "text": "树形布局",
                    "action": $.lab.topo.treeLayout,
                    "available": "true"
                }]
            }]
        },/* {
            "id": "4",
            "text": "高级",
            "children": [{
                "id": "4.4",
                "text": "网格与吸附",
                "action": $.lab.ui.gridAndSnap,
                "available": "lab.testCanvasGridSupport()==true"
            },{
                "id": "4.5",
                "text": "图形脚本",
                "action": $.lab.exp.svgXml
            }]
        },*/ {
            "id": "5",
            "text": "保存",
            "available": "true",
            "disabled": false,
            "action":$.lab.topo.saveTopo
        }, {
            "id": "6",
            "text": "返回",
            "action": $.lab.direct.gotoMain
        }, {
            "id": "7",
            "text": "全屏",
            "action": $.lab.ui.fullScreen,
            "available": "lab.testFullScreen()==false",
            "disabled": false
        }],
        plugin: {//绘制悬浮框的显示
            "11": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "12": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "21": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },



            "22": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "31": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },

            "41": {//mysql
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "42": {//sqlserver
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "51": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "52": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "61": {//防火墙
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "62": {//入侵检测设备
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },

            "71": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "81": {//web应用服务
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },


            "line": {
                "CLICK": "alarmTable",
                "DBL_CLICK": "lineseterTransform",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },



            "3003": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3004": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3005": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3006": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3015": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3016": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3017": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3018": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3019": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3020": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3021": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3022": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3023": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3024": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "3027": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "symbol": {
                "CLICK": "alarmTable",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "container": {
                "CLICK": "alarmTable",
                "DBL_CLICK": "containerTransform",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            },
            "lineseter": {
                "CLICK": "alarmTable",
                "DBL_CLICK": "lineseterTransform",
                "RIGHT_CLICK": "contextMenu",
                "MOUSE_OVER": "showTooltip",
                "MOUSE_OUT": "hideTooltip",
                "MOUSE_MOVE": "moveTooltip"
            }
        },
        context: {
            "2":{
                //物理链路
                "TOOLTIP": {//容量，上连接接口，下连接接口，上行速率，下行速率，上行利用率,下行利用率,健康度，可用率
                    "kpis": "LinkCapacity,UpInterface,DownInterface,UpLinkRate,DownLinkRate,upUsedRate,downUsedRate,healthDegree,availability",
                    "operations": ",,MAX,MAX,MAX,MAX,MAX,MAX,MAX,MAX,MAX",
                    "converters": ",,,,,,,,,",//控制输入的格式转换器
                    "kpiTexts":"LinkCapacity,UpInterface,DownInterface"
                },
                "INFORMATION": {//容量，上连接接口，下连接接口，上行速率，下行速率，上行利用率,下行利用率,健康度，可用率
                    "kpis": "LinkCapacity,UpInterface,DownInterface,UpLinkRate,DownLinkRate,upUsedRate,downUsedRate,healthDegree,availability",
                    "operations": ",,MAX,MAX,MAX,MAX,MAX,MAX,MAX,MAX,MAX",
                    "converters": ",,,,,,,,",//控制输入的格式转换器
                    "kpiTexts":"LinkCapacity,UpInterface,DownInterface"
                }
                // "HANG": {"kpis": "LinkBwUsedPer", "operations": "MAX", "converters": "PERCENT2LCD"}
            },

            "11": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "ICMPTime", "operations": "", "converters": ""}
            },
            "12": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "", "operations": "", "converters": ""}
            },
            "21": {//windows服务器
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "ICMPTime", "operations": "", "converters": ""}
            },
            "22": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "ICMPTime", "operations": "", "converters": ""}
            },
            "31": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,EquipModel,SysDescr",
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },
            "41": {//mysql
                "TOOLTIP": {
                    "kpis": "EventTotal,EventLevel,ZhLabel,ResourceType,Port,EquipmentIP,CurrentConnNum",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "INTEGER,ALARM,,,INTEGER,IP,,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,SysDescr,Port,CurrentConnNum",
                    "kpiTexts":"IpAddr,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,INTERGER,,"
                },
                "HANG": {"kpis": "CurrentConnNum", "operations": "", "converters": ""}
            },
            "42": {//sqlserver
                "TOOLTIP": {
                    "kpis": "EventTotal,EventLevel,ZhLabel,ResourceType,Port,EquipmentIP,CurrentConnNum",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "INTEGER,ALARM,,,INTEGER,IP,,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,SysDescr,Port,CurrentConnNum",
                    "kpiTexts":"IpAddr,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,INTERGER,,"
                },
                "HANG": {"kpis": "CurrentConnNum", "operations": "", "converters": ""}
            },
            "51": {//Tomcat
                "TOOLTIP": {
                    "kpis": "EventTotal,EventLevel,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "INTEGER,ALARM,,,INTEGER,IP"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,SysDescr,Port",
                    "kpiTexts":"IpAddr,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,INTERGER"
                },
                //"HANG": {"kpis": "", "operations": "SUM", "converters": "BITPS"}
            },
            "52": {
                "TOOLTIP": {
                    "kpis": "EventTotal,EventLevel,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "INTEGER,ALARM,,,INTEGER,IP"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,SysDescr,Port",
                    "kpiTexts":"IpAddr,ZhLabel,ResourceType,Port,EquipmentIP",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,,,INTERGER"
                },
               // "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },
            "61": {//防火墙
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "ICMPTime", "operations": "", "converters": ""}
            },
            "62": {//入侵检测设备
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime",//悬浮框
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,,,,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysName,SnmpIp,Vendor,EquipModel,SysDescr,ResourceType,ICMPTime",//设备信息
                    "kpiTexts":"IpAddr,CpuRate,MemRate,ResourceType",//文本框
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER,RUNTIME"
                },
                "HANG": {"kpis": "ICMPTime", "operations": "", "converters": ""}
            },
            "71": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,EquipModel,SysDescr",
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,,"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },
            "81": {//web应用服务
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,ZhLabel,SysUpTime,OnlinePeople",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,,,,RUNTIME,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SysUpTime,SysTotalPeople,OnlinePeople,SysDescr",
                    "kpiTexts":"IpAddr,ZhLabel,SysUpTime,SysTotalPeople,OnlinePeople",
                    "operations": "IP,,,,",
                    "converters": "IP,,,RUNTIME,,,,"
                },
                "HANG": {"kpis": "OnlinePeople", "operations": "", "converters": ""}
            },
            "82": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,,,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,EquipModel,SysDescr",//Geography,IsSwitch,IsRouter,PortCnt,PhyPortCnt
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },
            "83": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,PERCENT2BAR,PERCENT2BAR,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,EquipModel,SysDescr",//Geography,IsSwitch,IsRouter,PortCnt,PhyPortCnt
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },


            //物理链路
            "wrap_7a9887aa227e4d52a7e0deb8715ac697":{
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,,,"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Vendor,EquipModel,SysDescr",//Geography,IsSwitch,IsRouter,PortCnt,PhyPortCnt
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },

            "3003": {
                "TOOLTIP": {
                    "kpis": "IpAddr,EventTotal,EventLevel,Availability,CpuRate,MemRate",
                    "operations": ",,,,MAX,MAX,",
                    "converters": "IP,INTEGER,ALARM,REVERSE_PERCENT2BAR,PERCENT2BAR,PERCENT2BAR,RUNTIME"
                },
                "INFORMATION": {
                    "kpis": "IpAddr,ZhLabel,SnmpIp,Geography,Vendor,EquipModel,SysDescr,IsSwitch,IsRouter,PortCnt,PhyPortCnt",
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "operations": ",,,,,,,,,,,",
                    "converters": ",,,,,,,,SUPPORT,SUPPORT,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "IfRate", "operations": "SUM", "converters": "BITPS"}
            },
            "3006": {
                "TOOLTIP": {
                    "kpis": "SrcNode,SrcPort,DstNode,DstPort,EventTotal,EventLevel,LinkSpeed,LinkRate,LinkInRate,LinkOutRate,LinkBwUsedPer,LinkInBwUsedPer,LinkOutBwUsedPer",
                    "operations": ",,,,,,MAX,MAX,MAX,MAX,MAX,MAX,MAX",
                    "converters": ",,,,INTEGER,ALARM,BITPS,BITPS,BITPS,BITPS,PERCENT2BAR,PERCENT2BAR,PERCENT2BAR"
                },
                "INFORMATION": {
                    "kpis": "ZhLabel,SrcNode,DstNode,SrcPort,DstPort,SrcIndex,DstIndex",
                    "operations": ",,,,,,",
                    "kpiTexts":"IpAddr,CpuRate,MemRate",
                    "converters": ",,,,,INTEGER,INTEGER"
                },
                "HANG": {"kpis": "LinkBwUsedPer", "operations": "MAX", "converters": "PERCENT2LCD"}
            },
            "line": {
                "TOOLTIP": {
                    "kpis": "EventTotal,EventLevel,LinkSpeed,LinkRate,LinkInRate,LinkOutRate,LinkBwUsedPer,LinkInBwUsedPer,LinkOutBwUsedPer",
                    "operations": ",,MAX,MAX,MAX,MAX,MAX,MAX,MAX",
                    "converters": "INTEGER,ALARM,,,,,,,"
                }, "HANG": {"kpis": "LinkBwUsedPer", "operations": "MAX", "converters": "PERCENT2LCD"}
            }
        },
        //设备详情信息配置
        kpi: {
            "11": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "12": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "21": {//windows服务器
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "22": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "31": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "41": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
               // "ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "ContinuousWorkingTime": {"type": "DYNAMIC", "zhname": "连续工作时间", "unit": "Mb/秒"},
                "CurrentonCnectionumber": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": "个"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                "EquipmentIP": {"type": "DYNAMIC", "zhname": "所在设备IP", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "CurrentConnNum": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": ""},
            },
            "42": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                // "ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "ContinuousWorkingTime": {"type": "DYNAMIC", "zhname": "连续工作时间", "unit": "Mb/秒"},
                "CurrentonCnectionumber": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": "个"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                "EquipmentIP": {"type": "DYNAMIC", "zhname": "所在设备IP", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "CurrentConnNum": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": ""},
            },
            "51": {//tomcat
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                // "ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "ContinuousWorkingTime": {"type": "DYNAMIC", "zhname": "连续工作时间", "unit": "Mb/秒"},
                "CurrentonCnectionumber": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": "个"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                "EquipmentIP": {"type": "DYNAMIC", "zhname": "所在设备IP", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
            },
            "52": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                // "ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "ContinuousWorkingTime": {"type": "DYNAMIC", "zhname": "连续工作时间", "unit": "Mb/秒"},
                "CurrentonCnectionumber": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": "个"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                "EquipmentIP": {"type": "DYNAMIC", "zhname": "所在设备IP", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
            },
            "61": {//防火墙
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "62": {//入侵检测设备
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                /*"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},*/
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "设备类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "71": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
               // "ZhObjCls": {"type": "STATIC", "zhname": "类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "81": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
               // "ZhObjCls": {"type": "STATIC", "zhname": "类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Server": {"type": "DYNAMIC", "zhname": "所在服务器", "unit": "%"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
                "SysTotalPeople": {"type": "DYNAMIC", "zhname": "系统总人数", "unit": ""},
                "OnlinePeople": {"type": "DYNAMIC", "zhname": "在线人数", "unit": ""},
            },
            "82": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                //"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },
            "83": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                //"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                // "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                // "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                // "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                // "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "Health": {"type": "DYNAMIC", "zhname": "健康度", "unit": "%"},
            },


            "3003": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "Availability": {"type": "DYNAMIC", "zhname": "可用率", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                //"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SnmpIp": {"type": "STATIC", "zhname": "SNMP地址", "unit": ""},
                //"ZhObjCls": {"type": "STATIC", "zhname": "类型", "unit": ""},
                "Geography": {"type": "STATIC", "zhname": "地理位置", "unit": ""},
                "Vendor": {"type": "STATIC", "zhname": "厂商", "unit": ""},
                "EquipModel": {"type": "STATIC", "zhname": "型号", "unit": ""},
                "SysDescr": {"type": "STATIC", "zhname": "描述", "unit": ""},
                "IsSwitch": {"type": "STATIC", "zhname": "支持网桥", "unit": ""},
                "IsRouter": {"type": "STATIC", "zhname": "支持路由", "unit": ""},
                "PortCnt": {"type": "STATIC", "zhname": "端口总数", "unit": "个"},
                "PhyPortCnt": {"type": "STATIC", "zhname": "物理端口数", "unit": "个"},
                "IfRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"}
            },
            "3006": {
                "SrcNode": {"type": "STATIC", "zhname": "源设备", "unit": ""},
                "SrcPort": {"type": "STATIC", "zhname": "源端口", "unit": ""},
                "DstNode": {"type": "STATIC", "zhname": "目的设备", "unit": ""},
                "DstPort": {"type": "STATIC", "zhname": "目的端口", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkSpeed": {"type": "DYNAMIC", "zhname": "带宽", "unit": "Mb/秒"},
                "LinkRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "LinkInRate": {"type": "DYNAMIC", "zhname": "入速率", "unit": "Mb/秒"},
                "LinkOutRate": {"type": "DYNAMIC", "zhname": "出速率", "unit": "Mb/秒"},
                "LinkBwUsedPer": {"type": "DYNAMIC", "zhname": "带宽利用率", "unit": "%"},
                "LinkInBwUsedPer": {"type": "DYNAMIC", "zhname": "入带宽利用率", "unit": "%"},
                "LinkOutBwUsedPer": {"type": "DYNAMIC", "zhname": "出带宽利用率", "unit": "%"},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SrcIndex": {"type": "STATIC", "zhname": "源索引", "unit": ""},
                "DstIndex": {"type": "STATIC", "zhname": "目的索引", "unit": ""}
            },
            //物理链路
            "2": {
                // "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                // "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkCapacity": {"type": "DYNAMIC", "zhname": "容量", "unit": ""},
                "UpLinkRate": {"type": "DYNAMIC", "zhname": "上行速率", "unit": ""},
                "UpInterface": {"type": "STATIC", "zhname": "上连接接口", "unit": ""},
                "DownInterface": {"type": "STATIC", "zhname": "下连接接口", "unit": ""},
                "DownLinkRate": {"type": "DYNAMIC", "zhname": "下行速率", "unit": ""},
                "upUsedRate": {"type": "DYNAMIC", "zhname": "上行利用率", "unit": ""},
                "downUsedRate": {"type": "DYNAMIC", "zhname": "下行利用率", "unit": ""},
                "healthDegree":{"type": "DYNAMIC", "zhname": "健康度", "unit": ""},
                "availability":{"type": "DYNAMIC", "zhname": "可用率", "unit": ""}
            },
            "line": {
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkSpeed": {"type": "DYNAMIC", "zhname": "带宽", "unit": "Mb/秒"},
                "LinkRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "LinkInRate": {"type": "DYNAMIC", "zhname": "入速率", "unit": "Mb/秒"},
                "LinkOutRate": {"type": "DYNAMIC", "zhname": "出速率", "unit": "Mb/秒"},
                "LinkBwUsedPer": {"type": "DYNAMIC", "zhname": "带宽利用率", "unit": "%"},
                "LinkInBwUsedPer": {"type": "DYNAMIC", "zhname": "入带宽利用率", "unit": "%"},
                "LinkOutBwUsedPer": {"type": "DYNAMIC", "zhname": "出带宽利用率", "unit": "%"}
            }
        },//配置编辑图标规格

        //悬浮窗的文本显示???有问题
        kpiText: {
            "11": {

                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "12": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "21": {//windows服务器IpAddr,EventTotal,EventLevel,ZhLabel,SysName,CpuRate,MemRate,ResourceType,ICMPTime
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "22": {

                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "31": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "41": {
                "EquipmentIP": {"type": "STATIC", "zhname": "所在设备IP", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                "CurrentConnNum": {"type": "DYNAMIC", "zhname": "当前连接数", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "42": {
                "EquipmentIP": {"type": "STATIC", "zhname": "所在设备IP", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
                //"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "51": {
                "EquipmentIP": {"type": "STATIC", "zhname": "所在设备IP", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
            },
            "52": {
                "EquipmentIP": {"type": "STATIC", "zhname": "所在设备IP", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "Port": {"type": "DYNAMIC", "zhname": "端口", "unit": ""},
            },
            "61": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},

            },
            "62": {//入侵检测设备
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "SysName": {"type": "STATIC", "zhname": "系统名称", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "ResourceType": {"type": "DYNAMIC", "zhname": "资源类型", "unit": ""},
                "ICMPTime": {"type": "DYNAMIC", "zhname": "ICMP响应时间", "unit": ""},
                // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "71": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "ZhLabel": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                //"SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "81": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
                "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
                "SysTotalPeople": {"type": "DYNAMIC", "zhname": "系统总人数", "unit": ""},
                "OnlinePeople": {"type": "DYNAMIC", "zhname": "在线人数", "unit": ""},
            },
            "82": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "83": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "3003": {
                "IpAddr": {"type": "STATIC", "zhname": "IP地址", "unit": ""},
                "CpuRate": {"type": "DYNAMIC", "zhname": "CPU使用率", "unit": ""},
                "MemRate": {"type": "DYNAMIC", "zhname": "内存使用率", "unit": ""},
               // "SysUpTime": {"type": "DYNAMIC", "zhname": "连续运行时长", "unit": ""},
            },
            "3006": {
                "SrcNode": {"type": "STATIC", "zhname": "源设备", "unit": ""},
                "SrcPort": {"type": "STATIC", "zhname": "源端口", "unit": ""},
                "DstNode": {"type": "STATIC", "zhname": "目的设备", "unit": ""},
                "DstPort": {"type": "STATIC", "zhname": "目的端口", "unit": ""},
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkSpeed": {"type": "DYNAMIC", "zhname": "带宽", "unit": "Mb/秒"},
                "LinkRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "LinkInRate": {"type": "DYNAMIC", "zhname": "入速率", "unit": "Mb/秒"},
                "LinkOutRate": {"type": "DYNAMIC", "zhname": "出速率", "unit": "Mb/秒"},
                "LinkBwUsedPer": {"type": "DYNAMIC", "zhname": "带宽利用率", "unit": "%"},
                "LinkInBwUsedPer": {"type": "DYNAMIC", "zhname": "入带宽利用率", "unit": "%"},
                "LinkOutBwUsedPer": {"type": "DYNAMIC", "zhname": "出带宽利用率", "unit": "%"},
                "ZhLabel": {"type": "STATIC", "zhname": "名称", "unit": ""},
                "SrcIndex": {"type": "STATIC", "zhname": "源索引", "unit": ""},
                "DstIndex": {"type": "STATIC", "zhname": "目的索引", "unit": ""}
            },
            "2": {
                // "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                // "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkSpeed": {"type": "DYNAMIC", "zhname": "容量", "unit": ""},
                "UpLinkRate": {"type": "STATIC", "zhname": "上行速率", "unit": ""},
                "UpInterface": {"type": "STATIC", "zhname": "上连接接口", "unit": ""},
                "DownInterface": {"type": "DYNAMIC", "zhname": "下连接接口", "unit": ""},
                "DownLinkRate": {"type": "DYNAMIC", "zhname": "下行速率", "unit": ""},
                "upUsedRate": {"type": "DYNAMIC", "zhname": "上行利用率", "unit": ""},
                "downUsedRate": {"type": "DYNAMIC", "zhname": "下行利用率", "unit": ""},
                "healthDegree":{"type": "DYNAMIC", "zhname": "健康度", "unit": ""},
                "availability":{"type": "DYNAMIC", "zhname": "可用率", "unit": ""}
            },
            "line": {
                "EventTotal": {"type": "DYNAMIC", "zhname": "告警总数", "unit": "个"},
                "EventLevel": {"type": "DYNAMIC", "zhname": "告警级别", "unit": ""},
                "LinkSpeed": {"type": "DYNAMIC", "zhname": "带宽", "unit": "Mb/秒"},
                "LinkRate": {"type": "DYNAMIC", "zhname": "速率", "unit": "Mb/秒"},
                "LinkInRate": {"type": "DYNAMIC", "zhname": "入速率", "unit": "Mb/秒"},
                "LinkOutRate": {"type": "DYNAMIC", "zhname": "出速率", "unit": "Mb/秒"},
                "LinkBwUsedPer": {"type": "DYNAMIC", "zhname": "带宽利用率", "unit": "%"},
                "LinkInBwUsedPer": {"type": "DYNAMIC", "zhname": "入带宽利用率", "unit": "%"},
                "LinkOutBwUsedPer": {"type": "DYNAMIC", "zhname": "出带宽利用率", "unit": "%"}
            }
        },

        //资源图标配置
        graph: {
            "11": {"type": "ICON", "icon":  "h3c/h3_router_general.svg", "width": "57", "height": "41", "": ""},
            "12": {"type": "ICON", "icon":  "h3c/h3_switch_general.svg", "width": "55", "height": "40", "": ""},
            "21": {"type": "ICON", "icon":  "cisco/blue/c_b_server.svg", "width": "40", "height": "55", "": ""},
            "22": {"type": "ICON", "icon":  "cisco/blue/c_b_server.svg", "width": "40", "height": "55", "": ""},
            "31": {"type": "ICON", "icon":  "cisco/blue/c_b_access_point.svg", "width": "57", "height": "41", "": ""},
            "41": {"type": "ICON", "icon":  "virtual/v_host_db.svg", "width": "57", "height": "41", "": ""},
            "42": {"type": "ICON", "icon":  "virtual/v_host_db.svg", "width": "57", "height": "41", "": ""},
            "51": {"type": "ICON", "icon":  "virtual/v_host_control.svg", "width": "57", "height": "41", "": ""},
            "52": {"type": "ICON", "icon":  "virtual/v_host_web.svg", "width": "57", "height": "41", "": ""},
            "61": {"type": "ICON", "icon":  "cisco/blue/c_b_firewall.svg", "width": "14", "height": "40", "": ""},
            "62": {"type": "ICON", "icon":  "h3c/h3_network_security.svg", "width": "35", "height": "43", "": ""},
            "71": {"type": "ICON", "icon":  "virtual/v_datastore.svg", "width": "57", "height": "41", "": ""},
            "81": {"type": "ICON", "icon":  "virtual/v_host_web.svg", "width": "40", "height": "55", "": ""},
            "82": {"type": "ICON", "icon":  "virtual/v_host_control.svg", "width": "57", "height": "41", "": ""},
            "83": {"type": "ICON", "icon":  "virtual/v_host_control.svg", "width": "57", "height": "41", "": ""},
            "3003": {"type": "ICON", "icon":  "h3c/h3_router_general.svg", "width": "57", "height": "41", "": ""},
            "3004": {"type": "ICON", "icon":  "h3c/h3_switch_general.svg", "width": "55", "height": "40", "": ""},
            "3005": {"type": "ICON", "icon":  "virtual/v_host_basic.svg", "width": "40", "height": "55", "": ""},
            "3015": {"type": "ICON", "icon":  "virtual/v_host_basic.svg", "width": "40", "height": "55", "": ""},
            "3016": {"type": "ICON", "icon":  "virtual/v_host_web.svg", "width": "40", "height": "55", "": ""},
            "3017": {"type": "ICON", "icon":  "virtual/v_host_web.svg", "width": "40", "height": "55", "": ""},
            "3018": {"type": "ICON", "icon":  "virtual/v_host_control.svg", "width": "40", "height": "59", "": ""},
            "3019": {"type": "ICON", "icon":  "virtual/v_host_control.svg", "width": "40", "height": "59", "": ""},
            "3020": {"type": "ICON", "icon":  "cisco/blue/c_b_firewall.svg", "width": "14", "height": "40", "": ""},
            "3021": {"type": "ICON", "icon":  "h3c/h3_ids.svg", "width": "57", "height": "42", "": ""},
            "3022": {"type": "ICON","icon":  "cisco/blue/c_b_network_security.svg","width": "35","height": "43","": ""},
            "3023": {"type": "ICON", "icon":  "h3c/h3_switch_csu.svg", "width": "55", "height": "40", "": ""},
            "3024": {"type": "ICON", "icon":  "h3c/h3_vpn.svg", "width": "55", "height": "40", "": ""},
            "3027": {"type": "ICON", "icon": "h3c/h3_splitter.svg", "width": "55", "height": "40", "": ""},
            "symbol": {"type": "ICON", "icon":  "h3c/h3_void.svg", "width": "47", "height": "35", "": ""},
            "container": {"type": "ICON", "icon":  "h3c/h3_cloud.svg", "width": "60", "height": "35", "": ""}
        },
        icon: [{
            "label": "Cisco蓝色",
            "icons": [{
                "name": "c_b_server.svg",
                "path": "cisco/blue/",
                "desc": "服务器",
                "width": "31",
                "height": "48"
            }, {
                "name": "c_b_switch_router.svg",
                "path": "cisco/blue/",
                "desc": "交换机功能路由器",
                "width": "40",
                "height": "42"
            }, {
                "name": "c_b_route_switch_processor.svg",
                "path": "cisco/blue/",
                "desc": "路由交换处理器",
                "width": "40",
                "height": "54"
            }, {
                "name": "c_b_remote_switch_lay_2.svg",
                "path": "cisco/blue/",
                "desc": "二层远程交换机",
                "width": "53",
                "height": "27"
            }, {
                "name": "c_b_multilayer_switch.svg",
                "path": "cisco/blue/",
                "desc": "多层交换机",
                "width": "40",
                "height": "37"
            }, {
                "name": "c_b_workgroup_switch.svg",
                "path": "cisco/blue/",
                "desc": "工作组交换机",
                "width": "60",
                "height": "30"
            }, {
                "name": "c_b_router.svg",
                "path": "cisco/blue/",
                "desc": "路由器",
                "width": "54",
                "height": "32"
            }, {
                "name": "c_b_router_netflow.svg",
                "path": "cisco/blue/",
                "desc": "NetFlow路由器",
                "width": "57",
                "height": "34"
            }, {
                "name": "c_b_wireless_router.svg",
                "path": "cisco/blue/",
                "desc": "无线路由器",
                "width": "45",
                "height": "46"
            }, {
                "name": "c_b_router_7500.svg",
                "path": "cisco/blue/",
                "desc": "路由器(7500系列)",
                "width": "52",
                "height": "46"
            }, {
                "name": "c_b_router_10700.svg",
                "path": "cisco/blue/",
                "desc": "路由器(10700系列)",
                "width": "49",
                "height": "32"
            }, {
                "name": "c_b_router_iptel.svg",
                "path": "cisco/blue/",
                "desc": "IP电话路由器",
                "width": "52",
                "height": "45"
            }, {
                "name": "c_b_router_content_service.svg",
                "path": "cisco/blue/",
                "desc": "内容服务路由器",
                "width": "46",
                "height": "32"
            }, {
                "name": "c_b_ip.svg",
                "path": "cisco/blue/",
                "desc": "IP网络",
                "width": "38",
                "height": "46"
            }, {
                "name": "c_b_10ge_fcoe.svg",
                "path": "cisco/blue/",
                "desc": "以太网光纤通道(10GE)",
                "width": "32",
                "height": "42"
            }, {
                "name": "c_b_hub.svg",
                "path": "cisco/blue/",
                "desc": "集线器",
                "width": "48",
                "height": "39"
            }, {
                "name": "c_b_100baset_hub.svg",
                "path": "cisco/blue/",
                "desc": "集线器(100Baset)",
                "width": "43",
                "height": "18"
            }, {
                "name": "c_b_access_point.svg",
                "path": "cisco/blue/",
                "desc": "网络桥接器",
                "width": "49",
                "height": "22"
            }, {
                "name": "c_b_bridge.svg",
                "path": "cisco/blue/",
                "desc": "网桥",
                "width": "42",
                "height": "29"
            }, {
                "name": "c_b_wireless_bridge.svg",
                "path": "cisco/blue/",
                "desc": "无线网桥",
                "width": "50",
                "height": "40"
            }, {
                "name": "c_b_firewall.svg",
                "path": "cisco/blue/",
                "desc": "防火墙",
                "width": "20",
                "height": "46"
            }, {
                "name": "c_b_firewall_h.svg",
                "path": "cisco/blue/",
                "desc": "防火墙",
                "width": "46",
                "height": "20"
            }, {
                "name": "c_b_router_firewall.svg",
                "path": "cisco/blue/",
                "desc": "路由防火墙",
                "width": "48",
                "height": "36"
            }, {
                "name": "c_b_ace.svg",
                "path": "cisco/blue/",
                "desc": "负载均衡设备",
                "width": "56",
                "height": "38"
            }, {
                "name": "c_b_atm_switch.svg",
                "path": "cisco/blue/",
                "desc": "ATM交换机",
                "width": "37",
                "height": "36"
            }, {
                "name": "c_b_broadband_router.svg",
                "path": "cisco/blue/",
                "desc": "无线宽带路由器",
                "width": "41",
                "height": "44"
            }, {
                "name": "c_b_carrier_routor.svg",
                "path": "cisco/blue/",
                "desc": "高端路由器",
                "width": "47",
                "height": "49"
            }, {
                "name": "c_b_sgw.svg",
                "path": "cisco/blue/",
                "desc": "信令网关",
                "width": "37",
                "height": "37"
            }, {
                "name": "c_b_network_security.svg",
                "path": "cisco/blue/",
                "desc": "网络安全设备",
                "width": "35",
                "height": "43"
            }, {
                "name": "c_b_services.svg",
                "path": "cisco/blue/",
                "desc": "服务",
                "width": "41",
                "height": "40"
            }, {"name": "c_b_cloud.svg", "path": "cisco/blue/", "desc": "云", "width": "69", "height": "42"}]
        },
            {
            "label": "Cisco灰色",
            "icons": [{
                "name": "c_g_host.svg",
                "path": "cisco/grey/",
                "desc": "主机",
                "width": "41",
                "height": "26"
            }, {
                "name": "c_g_switch_router.svg",
                "path": "cisco/grey/",
                "desc": "交换机功能路由器",
                "width": "38",
                "height": "39"
            }, {
                "name": "c_g_route_switch_processor.svg",
                "path": "cisco/grey/",
                "desc": "路由交换处理器",
                "width": "41",
                "height": "32"
            }, {
                "name": "c_g_remote_switch_lay_2.svg",
                "path": "cisco/grey/",
                "desc": "二层远程交换机",
                "width": "49",
                "height": "24"
            }, {
                "name": "c_g_multilayer_switch.svg",
                "path": "cisco/grey/",
                "desc": "多层交换机",
                "width": "31",
                "height": "31"
            }, {
                "name": "c_g_workgroup_switch.svg",
                "path": "cisco/grey/",
                "desc": "工作组交换机",
                "width": "48",
                "height": "24"
            }, {
                "name": "c_g_router.svg",
                "path": "cisco/grey/",
                "desc": "路由器",
                "width": "38",
                "height": "26"
            }, {
                "name": "c_g_wireless_router.svg",
                "path": "cisco/grey/",
                "desc": "无线路由器",
                "width": "38",
                "height": "39"
            }, {
                "name": "c_g_router_7500.svg",
                "path": "cisco/grey/",
                "desc": "路由器(7500系列)",
                "width": "38",
                "height": "37"
            }, {
                "name": "c_g_router_7600.svg",
                "path": "cisco/grey/",
                "desc": "路由器(7600系列)",
                "width": "35",
                "height": "32"
            }, {
                "name": "c_g_router_10700.svg",
                "path": "cisco/grey/",
                "desc": "路由器(10700系列)",
                "width": "38",
                "height": "26"
            }, {
                "name": "c_g_router_iptel.svg",
                "path": "cisco/grey/",
                "desc": "IP电话路由器",
                "width": "37",
                "height": "33"
            }, {
                "name": "c_g_router_content_service.svg",
                "path": "cisco/grey/",
                "desc": "内容服务路由器",
                "width": "38",
                "height": "25"
            }, {
                "name": "c_g_ip.svg",
                "path": "cisco/grey/",
                "desc": "IP网络",
                "width": "31",
                "height": "35"
            }, {
                "name": "c_g_10ge_fcoe.svg",
                "path": "cisco/grey/",
                "desc": "以太网光纤通道(10GE)",
                "width": "30",
                "height": "42"
            }, {
                "name": "c_g_hub.svg",
                "path": "cisco/grey/",
                "desc": "集线器",
                "width": "32",
                "height": "27"
            }, {
                "name": "c_g_100baset_hub.svg",
                "path": "cisco/grey/",
                "desc": "集线器(100Baset)",
                "width": "43",
                "height": "22"
            }, {
                "name": "c_g_mobile_access_router.svg",
                "path": "cisco/grey/",
                "desc": "移动接入路由器",
                "width": "52",
                "height": "30"
            }, {
                "name": "c_g_access_point.svg",
                "path": "cisco/grey/",
                "desc": "网络桥接器",
                "width": "37",
                "height": "16"
            }, {
                "name": "c_g_bridge.svg",
                "path": "cisco/grey/",
                "desc": "网桥",
                "width": "37",
                "height": "30"
            }, {
                "name": "c_g_wireless_bridge.svg",
                "path": "cisco/grey/",
                "desc": "无线网桥",
                "width": "36",
                "height": "30"
            }, {
                "name": "c_g_firewall.svg",
                "path": "cisco/grey/",
                "desc": "防火墙",
                "width": "15",
                "height": "33"
            }, {
                "name": "c_g_router_firewall.svg",
                "path": "cisco/grey/",
                "desc": "路由防火墙",
                "width": "37",
                "height": "30"
            }, {
                "name": "c_g_ace.svg",
                "path": "cisco/grey/",
                "desc": "负载均衡设备",
                "width": "44",
                "height": "32"
            }, {
                "name": "c_g_acs.svg",
                "path": "cisco/grey/",
                "desc": "AAA服务器",
                "width": "36",
                "height": "26"
            }, {
                "name": "c_g_atm_3800.svg",
                "path": "cisco/grey/",
                "desc": "ATM(3800系列)",
                "width": "33",
                "height": "38"
            }, {
                "name": "c_g_atm_router.svg",
                "path": "cisco/grey/",
                "desc": "ATM路由器",
                "width": "38",
                "height": "26"
            }, {
                "name": "c_g_atm_switch.svg",
                "path": "cisco/grey/",
                "desc": "ATM交换机",
                "width": "28",
                "height": "29"
            }, {
                "name": "c_g_broadband_router.svg",
                "path": "cisco/grey/",
                "desc": "无线宽带路由器",
                "width": "38",
                "height": "38"
            }, {
                "name": "c_g_carrier_routor.svg",
                "path": "cisco/grey/",
                "desc": "高端路由器",
                "width": "33",
                "height": "33"
            }, {
                "name": "c_g_sgw.svg",
                "path": "cisco/grey/",
                "desc": "信令网关",
                "width": "28",
                "height": "28"
            }, {
                "name": "c_g_fax.svg",
                "path": "cisco/grey/",
                "desc": "传真机",
                "width": "60",
                "height": "29"
            }, {
                "name": "c_g_printer.svg",
                "path": "cisco/grey/",
                "desc": "打印机",
                "width": "57",
                "height": "22"
            }, {
                "name": "c_g_front_end_processor.svg",
                "path": "cisco/grey/",
                "desc": "前端处理机",
                "width": "22",
                "height": "28"
            }, {
                "name": "c_g_mainframe.svg",
                "path": "cisco/grey/",
                "desc": "普通服务器",
                "width": "24",
                "height": "34"
            }, {
                "name": "c_g_web_server.svg",
                "path": "cisco/grey/",
                "desc": "Web服务器",
                "width": "38",
                "height": "26"
            }, {
                "name": "c_g_super_computer.svg",
                "path": "cisco/grey/",
                "desc": "超级计算机",
                "width": "43",
                "height": "51"
            }, {
                "name": "c_g_database.svg",
                "path": "cisco/grey/",
                "desc": "数据库",
                "width": "32",
                "height": "26"
            }, {
                "name": "c_g_network_security.svg",
                "path": "cisco/grey/",
                "desc": "网络安全设备",
                "width": "23",
                "height": "29"
            }, {
                "name": "c_g_services.svg",
                "path": "cisco/grey/",
                "desc": "服务",
                "width": "27",
                "height": "27"
            }, {
                "name": "c_g_sun_workstation.svg",
                "path": "cisco/grey/",
                "desc": "SUN工作站",
                "width": "37",
                "height": "32"
            }, {
                "name": "c_g_vpn.svg",
                "path": "cisco/grey/",
                "desc": "VPN",
                "width": "44",
                "height": "24"
            }, {
                "name": "c_g_subnet.svg",
                "path": "cisco/grey/",
                "desc": "子网",
                "width": "87",
                "height": "53"
            }, {"name": "c_g_cloud.svg", "path": "cisco/grey/", "desc": "云", "width": "48", "height": "32"}]
        }, {
            "label": "H3C",
            "icons": [{
                "name": "h3_router_general.svg",
                "path": "h3c/",
                "desc": "常规路由器",
                "width": "57",
                "height": "41"
            }, {
                "name": "h3_router_core.svg",
                "path": "h3c/",
                "desc": "核心路由器",
                "width": "55",
                "height": "52"
            }, {
                "name": "h3_router_ne16.svg",
                "path": "h3c/",
                "desc": "路由器(NE16)",
                "width": "67",
                "height": "83"
            }, {
                "name": "h3_router_ne20.svg",
                "path": "h3c/",
                "desc": "路由器(NE20)",
                "width": "98",
                "height": "67"
            }, {
                "name": "h3_router_ne40.svg",
                "path": "h3c/",
                "desc": "路由器(NE40)",
                "width": "70",
                "height": "102"
            }, {
                "name": "h3_router_ne80.svg",
                "path": "h3c/",
                "desc": "路由器(NE80)",
                "width": "64",
                "height": "122"
            }, {
                "name": "h3_router_r1700.svg",
                "path": "h3c/",
                "desc": "路由器(R1700)",
                "width": "94",
                "height": "53"
            }, {
                "name": "h3_router_r2500e.svg",
                "path": "h3c/",
                "desc": "路由器(R2500E)",
                "width": "102",
                "height": "60"
            }, {
                "name": "h3_router_r2600e.svg",
                "path": "h3c/",
                "desc": "路由器(R2600E)",
                "width": "103",
                "height": "52"
            }, {
                "name": "h3_router_r3600e.svg",
                "path": "h3c/",
                "desc": "路由器(R3600E)",
                "width": "97",
                "height": "56"
            }, {
                "name": "h3_router_ar18.svg",
                "path": "h3c/",
                "desc": "路由器(AR18)",
                "width": "88",
                "height": "54"
            }, {
                "name": "h3_router_ar28.svg",
                "path": "h3c/",
                "desc": "路由器(AR28)",
                "width": "106",
                "height": "54"
            }, {
                "name": "h3_router_ar46.svg",
                "path": "h3c/",
                "desc": "路由器(AR46)",
                "width": "92",
                "height": "60"
            }, {
                "name": "h3_router_msr.svg",
                "path": "h3c/",
                "desc": "MSR",
                "width": "59",
                "height": "45"
            }, {
                "name": "h3_router_msr20.svg",
                "path": "h3c/",
                "desc": "路由器(MSR20)",
                "width": "96",
                "height": "52"
            }, {
                "name": "h3_router_msr30.svg",
                "path": "h3c/",
                "desc": "路由器(MSR30)",
                "width": "98",
                "height": "58"
            }, {
                "name": "h3_router_msr50.svg",
                "path": "h3c/",
                "desc": "路由器(MSR50)",
                "width": "100",
                "height": "58"
            }, {
                "name": "h3_router_ipv6.svg",
                "path": "h3c/",
                "desc": "IPv6路由器",
                "width": "58",
                "height": "42"
            }, {
                "name": "h3_pad.svg",
                "path": "h3c/",
                "desc": "PAD",
                "width": "58",
                "height": "44"
            }, {
                "name": "h3_network_equipment.svg",
                "path": "h3c/",
                "desc": "网络设备",
                "width": "56",
                "height": "42"
            }, {
                "name": "h3_switch_general.svg",
                "path": "h3c/",
                "desc": "常规交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_core.svg",
                "path": "h3c/",
                "desc": "核心交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_l2.svg",
                "path": "h3c/",
                "desc": "二层交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_l3.svg",
                "path": "h3c/",
                "desc": "三层交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_dis.svg",
                "path": "h3c/",
                "desc": "分路交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_edge.svg",
                "path": "h3c/",
                "desc": "边缘交换机",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_dce.svg",
                "path": "h3c/",
                "desc": "DCE",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_csu.svg",
                "path": "h3c/",
                "desc": "CSU",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_pse.svg",
                "path": "h3c/",
                "desc": "PSE",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_wap.svg",
                "path": "h3c/",
                "desc": "WAP",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_switch_4200.svg",
                "path": "h3c/",
                "desc": "交换机(4200)",
                "width": "112",
                "height": "58"
            }, {
                "name": "h3_switch_4400.svg",
                "path": "h3c/",
                "desc": "交换机(4400)",
                "width": "110",
                "height": "56"
            }, {
                "name": "h3_switch_4540.svg",
                "path": "h3c/",
                "desc": "交换机(4540)",
                "width": "108",
                "height": "60"
            }, {
                "name": "h3_switch_4900.svg",
                "path": "h3c/",
                "desc": "交换机(4900)",
                "width": "106",
                "height": "55"
            }, {
                "name": "h3_switch_s1000.svg",
                "path": "h3c/",
                "desc": "交换机(S1000)",
                "width": "112",
                "height": "56"
            }, {
                "name": "h3_switch_s2000.svg",
                "path": "h3c/",
                "desc": "交换机(S2000)",
                "width": "110",
                "height": "56"
            }, {
                "name": "h3_switch_s3000.svg",
                "path": "h3c/",
                "desc": "交换机(S3000)",
                "width": "108",
                "height": "52"
            }, {
                "name": "h3_switch_s3500.svg",
                "path": "h3c/",
                "desc": "交换机(S3500)",
                "width": "108",
                "height": "59"
            }, {
                "name": "h3_switch_s3900.svg",
                "path": "h3c/",
                "desc": "交换机(S3900)",
                "width": "113",
                "height": "58"
            }, {
                "name": "h3_switch_s5000.svg",
                "path": "h3c/",
                "desc": "交换机(S5000)",
                "width": "110",
                "height": "57"
            }, {
                "name": "h3_switch_s6500.svg",
                "path": "h3c/",
                "desc": "交换机(S6500)",
                "width": "78",
                "height": "75"
            }, {
                "name": "h3_switch_s8500.svg",
                "path": "h3c/",
                "desc": "交换机(S8500)",
                "width": "71",
                "height": "81"
            }, {
                "name": "h3_splitter.svg",
                "path": "h3c/",
                "desc": "Splitter",
                "width": "46",
                "height": "34"
            }, {
                "name": "h3_vpn.svg",
                "path": "h3c/",
                "desc": "虚拟专用网",
                "width": "65",
                "height": "48"
            }, {
                "name": "h3_ids.svg",
                "path": "h3c/",
                "desc": "入侵检测",
                "width": "57",
                "height": "42"
            }, {
                "name": "h3_network_security.svg",
                "path": "h3c/",
                "desc": "安全防护",
                "width": "35",
                "height": "43"
            }, {
                "name": "h3_hub.svg",
                "path": "h3c/",
                "desc": "集线器",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_bridge.svg",
                "path": "h3c/",
                "desc": "网桥",
                "width": "55",
                "height": "40"
            }, {
                "name": "h3_server.svg",
                "path": "h3c/",
                "desc": "服务器",
                "width": "49",
                "height": "75"
            }, {
                "name": "h3_printer.svg",
                "path": "h3c/",
                "desc": "打印机",
                "width": "72",
                "height": "76"
            }, {
                "name": "h3_mult_device.svg",
                "path": "h3c/",
                "desc": "多功能设备",
                "width": "79",
                "height": "71"
            }, {
                "name": "h3_fax.svg",
                "path": "h3c/",
                "desc": "传真机",
                "width": "80",
                "height": "72"
            }, {
                "name": "h3_copier.svg",
                "path": "h3c/",
                "desc": "复印机",
                "width": "76",
                "height": "74"
            }, {
                "name": "h3_firewall.svg",
                "path": "h3c/",
                "desc": "防火墙",
                "width": "60",
                "height": "74"
            }, {
                "name": "h3_server_cluster.svg",
                "path": "h3c/",
                "desc": "服务集群",
                "width": "128",
                "height": "73"
            }, {
                "name": "h3_net_pf.svg",
                "path": "h3c/",
                "desc": "网络管理平台",
                "width": "130",
                "height": "76"
            }, {
                "name": "h3_database.svg",
                "path": "h3c/",
                "desc": "数据库",
                "width": "64",
                "height": "70"
            }, {
                "name": "h3_wireless_switch.svg",
                "path": "h3c/",
                "desc": "无线交换机",
                "width": "58",
                "height": "43"
            }, {
                "name": "h3_wireless_bridge.svg",
                "path": "h3c/",
                "desc": "无线网桥",
                "width": "58",
                "height": "43"
            }, {
                "name": "h3_wireless_router_wa1208e.svg",
                "path": "h3c/",
                "desc": "无线路由器(WA1208E)",
                "width": "97",
                "height": "76"
            }, {
                "name": "h3_wireless_switch_wx1200.svg",
                "path": "h3c/",
                "desc": "无线交换机(WX1200)",
                "width": "106",
                "height": "56"
            }, {
                "name": "h3_internet.svg",
                "path": "h3c/",
                "desc": "Internet",
                "width": "70",
                "height": "46"
            }, {
                "name": "h3_mware.svg",
                "path": "h3c/",
                "desc": "mware",
                "width": "56",
                "height": "42"
            }, {"name": "h3_disk.svg", "path": "h3c/", "desc": "磁盘", "width": "62", "height": "40"}, {
                "name": "h3_cpu.svg",
                "path": "h3c/",
                "desc": "CPU",
                "width": "44",
                "height": "35"
            }, {"name": "h3_cloud.svg", "path": "h3c/", "desc": "云", "width": "60", "height": "35"}, {
                "name": "h3_void.svg",
                "path": "h3c/",
                "desc": "无",
                "width": "47",
                "height": "35"
            }]
        }, {
            "label": "华为",
            "icons": [{"name": "hw_router.svg", "path": "huawei/", "desc": "路由器", "width": "128", "height": "32"}]
        }, {
            "label": "其它",
            "icons": [{
                "name": "v_data_center.svg",
                "path": "virtual/",
                "desc": "数据中心",
                "width": "81",
                "height": "76"
            }, {
                "name": "v_filesystem.svg",
                "path": "virtual/",
                "desc": "文件系统",
                "width": "76",
                "height": "73"
            }, {
                "name": "v_datastore.svg",
                "path": "virtual/",
                "desc": "数据存储",
                "width": "80",
                "height": "81"
            }, {
                "name": "v_datastore_cluster.svg",
                "path": "virtual/",
                "desc": "数据存储集群",
                "width": "66",
                "height": "68"
            }, {
                "name": "v_system.svg",
                "path": "virtual/",
                "desc": "系统",
                "width": "68",
                "height": "70"
            }, {
                "name": "v_earth.svg",
                "path": "virtual/",
                "desc": "地球",
                "width": "70",
                "height": "70"
            }, {
                "name": "v_port.svg",
                "path": "virtual/",
                "desc": "端口",
                "width": "52",
                "height": "49"
            }, {
                "name": "v_physical_machine.svg",
                "path": "virtual/",
                "desc": "物理机",
                "width": "58",
                "height": "103"
            }, {
                "name": "v_cluster.svg",
                "path": "virtual/",
                "desc": "集群物理机",
                "width": "71",
                "height": "75"
            }, {
                "name": "v_virtual_machine.svg",
                "path": "virtual/",
                "desc": "虚拟机",
                "width": "46",
                "height": "95"
            }, {
                "name": "v_pool.svg",
                "path": "virtual/",
                "desc": "池",
                "width": "91",
                "height": "55"
            }, {
                "name": "v_router.svg",
                "path": "virtual/",
                "desc": "路由器",
                "width": "53",
                "height": "34"
            }, {
                "name": "v_switch.svg",
                "path": "virtual/",
                "desc": "交换机",
                "width": "54",
                "height": "42"
            }, {
                "name": "v_host_basic.svg",
                "path": "virtual/",
                "desc": "普通服务器",
                "width": "40",
                "height": "55"
            }, {
                "name": "v_host_control.svg",
                "path": "virtual/",
                "desc": "应用服务器",
                "width": "40",
                "height": "59"
            }, {
                "name": "v_host_db.svg",
                "path": "virtual/",
                "desc": "数据库服务器",
                "width": "40",
                "height": "55"
            }, {
                "name": "v_host_web.svg",
                "path": "virtual/",
                "desc": "Web服务器",
                "width": "40",
                "height": "55"
            }, {
                "name": "v_server.svg",
                "path": "virtual/",
                "desc": "大型机",
                "width": "44",
                "height": "56"
            }, {
                "name": "v_user.svg",
                "path": "virtual/",
                "desc": "用户",
                "width": "34",
                "height": "45"
            }, {
                "name": "v_load_balancing.svg",
                "path": "virtual/",
                "desc": "负载均衡",
                "width": "88",
                "height": "24"
            }, {
                "name": "v_disks.svg",
                "path": "virtual/",
                "desc": "磁盘阵列",
                "width": "74",
                "height": "42"
            }, {
                "name": "v_interface.svg",
                "path": "virtual/",
                "desc": "接口",
                "width": "26",
                "height": "20"
            }, {
                "name": "v_disk.svg",
                "path": "virtual/",
                "desc": "磁盘",
                "width": "57",
                "height": "36"
            }, {
                "name": "v_app.svg",
                "path": "virtual/",
                "desc": "应用",
                "width": "25",
                "height": "25"
            }, {
                "name": "v_database.svg",
                "path": "virtual/",
                "desc": "数据库",
                "width": "48",
                "height": "28"
            }, {
                "name": "v_file.svg",
                "path": "virtual/",
                "desc": "文档",
                "width": "24",
                "height": "32"
            }, {
                "name": "v_cpu.svg",
                "path": "virtual/",
                "desc": "CPU",
                "width": "44",
                "height": "35"
            }, {
                "name": "v_memory.svg",
                "path": "virtual/",
                "desc": "内存",
                "width": "77",
                "height": "18"
            }, {
                "name": "v_filesys.svg",
                "path": "virtual/",
                "desc": "文件",
                "width": "44",
                "height": "34"
            }, {
                "name": "v_middleware.svg",
                "path": "virtual/",
                "desc": "中间件",
                "width": "46",
                "height": "46"
            }, {"name": "v_wire.svg", "path": "virtual/", "desc": "信号", "width": "27", "height": "33"}]
        }],
        panel: [{"id": "1", "text": "设备类型"}/*, {"id": "2", "text": "最新发现"}*/],
        help: {
            "help4view_location": {
                "head": "图标与拓扑图的位置关系",
                "body": "图标与拓扑图的位置关系分为<label class='help-important'>相对定位</label>与<label class='help-important'>绝对定位</label>两种。<br><br><label class='help-important'>相对定位</label>通常是指图标与拓扑图的背景图存在地理位置上的关联关系，当背景图进行自适应或者尺寸变更时，图标与背景图的相对位置不会产生变化。<label class='help-important'>绝对定位</label>则正好相反，它只关注图标所在的坐标系位置，背景或视觉区域的尺寸变更均不会对图标所在位置造成影响。<br><br>当您定义的拓扑图强调地域性时（比如全国性的骨干网络拓扑），习惯性的会按照地域或区域的规则摆放节点，并希望节点不要偏离所在区域位置。这时建议您将本开关置为开启状态，拓扑将会自动为您提供最佳的浏览效果。"
            },
            "help4view_hidden_nav": {
                "head": "拓扑导航树中关联的子拓扑图太多、太乱",
                "body": "在某些特殊场景下，我们可能不希望拓扑图出现在导航树中。比如，当前拓扑图可能只是一张子图，它的存在只是为了更好的表达拓扑图的层次关系，它通常是从父级拓扑图的某个节点上关联打开的。<br><br>如果您当前编辑的拓扑图正属于上述范畴，那么为了保证拓扑导航树的整洁与使用的方便，建议您将本开关置为开启状态，让这类并不需要直接打开查看的拓扑图从导航树中隐藏掉。"
            },
            "help4ne_reflash": {"head": "自动同步信息", "body": "开启此功能后，自动将相关联网元或图形的信息同步到当前图形。"},
            "help4container_permit": {
                "head": "容器的展开与合并",
                "body": "顾名思义，容器是一种可以展开、合并的图形元素，其默认也允许进行这两种操作。但如果您希望容器不再带有双击展开或合并的功能，那么就可以将本开关置为关闭状态。"
            },
            "help4container_autoresize": {
                "head": "容器的展开尺寸自适应",
                "body": "当容器的展开尺寸自适应设置为“开”时自动更具容器内元素调整尺寸；如果设置为“关”时手动设置展开尺寸。"
            },
            "help4fontfamily": {
                "head": "字体",
                "body": "由于字体的显示是依靠浏览器支持的，所以建议您选用现代浏览器。<br>另外，带有<label class='help-important'>*</label>号的字体是不能保证兼容性的，随着浏览器逐步升级，可能才会被兼容。"
            },
            "help4link": {
                "head": "定义关联功能",
                "body": "在浏览拓扑图时，如果您希望在当前图形的右键菜单中自定义一些打开其它功能的超链接，那么就可以在这里进行配置。当然，自定义的关联功能是存在数量限制的，您不能配置得过多，目前我们希望您最多配置5个。"
            },
            "help4event_tfrstatus": {
                "head": "告警状态的传递",
                "body": "子节点把自己的告警状态传递给父亲过程中，进行升降级变化。如果子节点对父节点的影响较大，则进行升级，否则进行降级。"
            },
            "help4event_tfrmin": {
                "head": "告警传递时的最小级别",
                "body": "这个参数同时影响告警消息和告警状态的传递。对子节点的一个告警，如果告警级别大于或等于“最小传递级别”，则把告警传递给父节点，否则不进行传递；如果子节点的告警状态大于或等于“最小传递级别”，则传递告警状态。"
            }
        }
    },
    views:[{
        "id": 8685,
        "name": "Test",
        "descr": "",
        "type": 1,
        "timestamp": 201602021420036,
        "isInstance": true,
        "isCache": true,
        "isHidden": true,
        "style": "",
        "config": "",
        "options": "",
        "parentViewId": -1,
        "orderCode": 0,
        "relSymbolId": -1,
    }],
    view: {
        8685: {
            "symbols": [{
                "id": 9087,
                "name": "DLQ8",
                "x": 182,
                "y": 460,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 964448097,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 9088,
                "name": "7609",
                "x": 259,
                "y": 361,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1058273819,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_core.svg;icon-width:55;icon-height:52;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 9091,
                "name": "X9RN",
                "x": 341,
                "y": 373,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1669027637,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_core.svg;icon-width:55;icon-height:52;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 59,
                "name": "8S7R",
                "x": 361,
                "y": 175,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 220474473,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8687,
                "name": "520B",
                "x": 425,
                "y": 301,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1582729651,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8798;render:animation;"
            }, {
                "id": 8814,
                "name": "220B",
                "x": 425,
                "y": 442,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 425012547,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8848;render:animation;"
            }, {
                "id": 8686,
                "name": "SR24",
                "x": 452,
                "y": 528,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1654264441,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8755;render:animation;"
            }, {
                "id": 52,
                "name": "14EA",
                "x": 459,
                "y": 592,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1104023844,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8696,
                "name": "BB20",
                "x": 475,
                "y": 192,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 111910029,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8752;render:animation;"
            }, {
                "id": 9089,
                "name": "RM3A",
                "x": 475,
                "y": 367,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1806646443,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 54,
                "name": "HJRR",
                "x": 493,
                "y": 104,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1140138694,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8708,
                "name": "BIQKJ",
                "x": 508,
                "y": 304,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 691477938,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8806;render:animation;"
            }, {
                "id": 9239,
                "name": "WOV1",
                "x": 516,
                "y": 447,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 215194754,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:9251;render:animation;"
            }, {
                "id": 48,
                "name": "AOIC",
                "x": 526,
                "y": 662,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 369372872,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 9243,
                "name": "C7FM",
                "x": 563,
                "y": 255,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 447495993,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:9254;render:animation;"
            }, {
                "id": 51,
                "name": "O2NJ",
                "x": 570,
                "y": 489,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 368053119,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8689,
                "name": "98FM",
                "x": 580,
                "y": 590,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 415244369,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8753;render:animation;"
            }, {
                "id": 8812,
                "name": "83JV",
                "x": 582,
                "y": 189,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1031245938,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8843;render:animation;"
            }, {
                "id": 8694,
                "name": "47AK",
                "x": 629,
                "y": 381,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1526455592,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_core.svg;icon-width:55;icon-height:52;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8757;render:animation;"
            }, {
                "id": 8691,
                "name": "03MF",
                "x": 633,
                "y": 259,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1071134035,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8748;render:animation;"
            }, {
                "id": 8710,
                "name": "VMWOI",
                "x": 650,
                "y": 539,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 270071870,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8794;render:animation;"
            }, {
                "id": 55,
                "name": "UV9Q",
                "x": 657,
                "y": 656,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1159018287,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8816,
                "name": "9ENV",
                "x": 687,
                "y": 461,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1673414679,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8841;render:animation;"
            }, {
                "id": 8734,
                "name": "VIW82",
                "x": 699,
                "y": 174,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 81447521,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8756;render:animation;"
            }, {
                "id": 8716,
                "name": "09372",
                "x": 706,
                "y": 321,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 913640993,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8754;render:animation;"
            }, {
                "id": 8712,
                "name": "V8W9",
                "x": 720,
                "y": 238,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1839692122,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8799;render:animation;"
            }, {
                "id": 49,
                "name": "832HC",
                "x": 750,
                "y": 86,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1925419503,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "render:animation;"
            }, {
                "id": 8699,
                "name": "SCOQ",
                "x": 753,
                "y": 498,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 820625114,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8755;render:animation;"
            }, {
                "id": 8823,
                "name": "V9382",
                "x": 761,
                "y": 586,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 52626305,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8841;render:animation;"
            }, {
                "id": 8688,
                "name": "2339 M",
                "x": 787,
                "y": 267,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1764447560,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8798;render:animation;"
            }, {
                "id": 8690,
                "name": "CW7R",
                "x": 831,
                "y": 191,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1737600624,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8753;render:animation;"
            }, {
                "id": 8693,
                "name": "09PQ",
                "x": 838,
                "y": 410,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 932962660,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_core.svg;icon-width:55;icon-height:52;text-display:1;font-size:10;font-color:#999999;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8757;render:animation;"
            }, {
                "id": 9241,
                "name": "BSRA",
                "x": 839,
                "y": 509,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1739518876,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:9254;render:animation;"
            }, {
                "id": 8813,
                "name": "RGE9 M",
                "x": 870,
                "y": 249,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 451137351,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;text-shadow:;text-shadow-color:;shadow:bottom;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8848;render:animation;"
            }, {
                "id": 8810,
                "name": "LGM1",
                "x": 921,
                "y": 496,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1052865150,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8843;render:animation;"
            }, {
                "id": 8736,
                "name": "FJ84",
                "x": 927,
                "y": 595,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 2123880115,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8752;render:animation;"
            }, {
                "id": 8692,
                "name": "CD09",
                "x": 937,
                "y": 298,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1668666759,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8748;render:animation;"
            }, {
                "id": 8717,
                "name": "23HJ",
                "x": 957,
                "y": 195,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1269204163,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8806;render:animation;"
            }, {
                "id": 8711,
                "name": "ZI328",
                "x": 985,
                "y": 359,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 9822605,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8794;render:animation;"
            }, {
                "id": 8707,
                "name": "V9E8",
                "x": 991,
                "y": 434,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1725101526,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8754;render:animation;"
            }, {
                "id": 8739,
                "name": "C092",
                "x": 1032,
                "y": 528,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 734896262,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8756;render:animation;"
            }, {
                "id": 9242,
                "name": "LK1J",
                "x": 1046,
                "y": 276,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 154893590,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-color:#999999;font-size:10;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:9251;render:animation;"
            }, {
                "id": 8713,
                "name": "V8327",
                "x": 1077,
                "y": 387,
                "type": 0,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1092038600,
                "objectClass": "3003",
                "instanceId": null,
                "style": "icon:h3c/h3_router_general.svg;icon-width:47;icon-height:33;text-display:1;font-size:10;font-color:#999999;shadow:bottom;opacity:1;text-stroke-color:;text-border-color:;text-border-fill-color:;text-border-radius:0;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:8799;render:animation;"
            }],
            "lines": [{
                "id": 8815,
                "name": "02OX->47AK",
                "path": "M 448 458 L 656 407",
                "type": 0,
                "srcSymbol": 8814,//
                "dstSymbol": 8694,// 47AK ID
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1977547298,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8817;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 46,
                "name": "47AK->520B",
                "path": "M 656 407 L 448 317",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8687,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 194575916,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 50,
                "name": "47AK->O2NJ",
                "path": "M 656 407 L 593 505",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 51,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 591837581,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8828,
                "name": "47AK->02LY",
                "path": "M 656 407 L 710 477",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8816,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1646660403,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8834;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8728,
                "name": "47AK->09ZQ",
                "path": "M 656 407 L 722 190",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8734,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 573600437,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8765;render:animation;"
            }, {
                "id": 8826,
                "name": "47AK->20CC",
                "path": "M 656 407 L 603 606",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8689,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 2008506904,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8835;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8830,
                "name": "47AK->21AR",
                "path": "M 656 407 L 498 208",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8696,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1314935984,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8825;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8730,
                "name": "47AK->28PN",
                "path": "M 656 407 L 475 544",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8686,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 684861886,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8776;render:animation;"
            }, {
                "id": 9240,
                "name": "47AK->32BB",
                "path": "M 656 407 L 586 271",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 9243,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1881365274,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:9244;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8839,
                "name": "47AK->43HH",
                "path": "M 656 407 L 605 205",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8812,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 187543740,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8827;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8743,
                "name": "47AK->44LS",
                "path": "M 656 407 L 729 337",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8716,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1459950614,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8775;render:animation;"
            }, {
                "id": 8720,
                "name": "47AK->51WX",
                "path": "M 656 407 L 531 320",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8708,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 2083004177,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8749;render:animation;"
            }, {
                "id": 9247,
                "name": "47AK->67XO",
                "path": "M 656 407 L 539 463",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 9239,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1357958793,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:9250;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8721,
                "name": "47AK->73XL",
                "path": "M 656 407 L 743 254",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8712,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 2097109983,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8791;render:animation;"
            }, {
                "id": 8729,
                "name": "47AK->92ZI",
                "path": "M 656 407 L 673 555",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8710,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 897663740,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8793;render:animation;"
            }, {
                "id": 9090,
                "name": "48AK->09CO",
                "path": "M 286 387 L 368 399",
                "type": 0,
                "srcSymbol": 9088,
                "dstSymbol": 9091,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1632119261,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8838,
                "name": "58RM->10IE",
                "path": "M 865 436 L 893 265",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8813,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 247598343,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8824;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8695,
                "name": "58RM->33HH",
                "path": "M 865 436 L 1008 375",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8711,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1991730296,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8792;render:animation;"
            }, {
                "id": 8726,
                "name": "58RM->47AK",
                "path": "M 865 436 L 656 407",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8694,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 251550478,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8738,
                "name": "58RM->68XL",
                "path": "M 865 436 L 1014 450",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8707,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1307664403,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8774;render:animation;"
            }, {
                "id": 8703,
                "name": "09PQ->C092",
                "path": "M 865 436 L 1055 544",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8739,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1743027470,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8763;render:animation;"
            }, {
                "id": 8697,
                "name": "09PQ->FJ84",
                "path": "M 865 436 L 950 611",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8736,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1977172725,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8768;render:animation;"
            }, {
                "id": 8740,
                "name": "09PQ->SCOQ",
                "path": "M 865 436 L 776 514",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8699,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1262801852,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8777;render:animation;"
            }, {
                "id": 8727,
                "name": "09PQ->CD09",
                "path": "M 865 436 L 960 314",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8692,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 626904647,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8773;render:animation;"
            }, {
                "id": 8701,
                "name": "09PQ->2339 M",
                "path": "M 865 436 L 810 283",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8688,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1856336713,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8795;render:animation;"
            }, {
                "id": 8731,
                "name": "09PQ->CW7R",
                "path": "M 865 436 L 854 207",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8690,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1167704871,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8766;render:animation;"
            }, {
                "id": 9246,
                "name": "09PQ->BSRA",
                "path": "M 865 436 L 862 525",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 9241,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 851070110,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:9249;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8818,
                "name": "09PQ->V9382",
                "path": "M 865 436 L 784 602",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8823,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1395053401,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8820;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 9245,
                "name": "09PQ->LK1J",
                "path": "M 865 436 L 1069 292",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 9242,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 13942890,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:9248;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8741,
                "name": "09PQ->23HJ",
                "path": "M 865 436 L 980 211",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8717,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1485974753,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8797;render:animation;"
            }, {
                "id": 8829,
                "name": "09PQ->LGM1",
                "path": "M 865 436 L 944 512",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8810,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1513409543,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-line:8833;tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8706,
                "name": "09PQ->V8327",
                "path": "M 865 436 L 1100 403",
                "type": 0,
                "srcSymbol": 8693,
                "dstSymbol": 8713,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1627005723,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8796;render:animation;"
            }, {
                "id": 58,
                "name": "VIW82->832HC",
                "path": "M 722 190 L 773 102",
                "type": 0,
                "srcSymbol": 8734,
                "dstSymbol": 49,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": "3006",
                "objectClass": "-1",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 57,
                "name": "BB20->8S7R",
                "path": "M 498 208 L 384 191",
                "type": 0,
                "srcSymbol": 8696,
                "dstSymbol": 59,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": "3006",
                "objectClass": "-1",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 60,
                "name": "BB20->HJRR",
                "path": "M 498 208 L 516 120",
                "type": 0,
                "srcSymbol": 8696,
                "dstSymbol": 54,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": "3006",
                "objectClass": "-1",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 45,
                "name": "RM3A->47AK",
                "path": "M 498 383 L 656 407",
                "type": 0,
                "srcSymbol": 9089,
                "dstSymbol": 8694,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 691817581,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 8705,
                "name": "03MF->47AK",
                "path": "M 656 407 L 656 275",
                "type": 0,
                "srcSymbol": 8694,
                "dstSymbol": 8691,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1696070841,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;tfr-to-line:8779;render:animation;"
            }, {
                "id": 47,
                "name": "98FM->AOIC",
                "path": "M 603 606 L 549 678",
                "type": 0,
                "srcSymbol": 8689,
                "dstSymbol": 48,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 134859911,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 56,
                "name": "98FM->14EA",
                "path": "M 603 606 L 482 608",
                "type": 0,
                "srcSymbol": 8689,
                "dstSymbol": 52,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": "3006",
                "objectClass": "-1",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 53,
                "name": "98FM->UV9Q",
                "path": "M 603 606 L 680 672",
                "type": 0,
                "srcSymbol": 8689,
                "dstSymbol": 55,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": "3006",
                "objectClass": "-1",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 9092,
                "name": "7609->DLQ8",
                "path": "M 286 387 L 205 476",
                "type": 0,
                "srcSymbol": 9088,
                "dstSymbol": 9087,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 141320285,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }, {
                "id": 9094,
                "name": "X9RN->RM3A",
                "path": "M 368 399 L 498 383",
                "type": 0,
                "srcSymbol": 9091,
                "dstSymbol": 9089,
                "timestamp": 201602021520023,
                "viewId": 8685,
                "parentId": -1,
                "objectId": 1389086655,
                "objectClass": "3006",
                "instanceId": "-1",
                "style": "dashed:;border:0;border-color:;shadow:;shadow-color:;",
                "options": "custom-label:1;",
                "url": null,
                "alarm": "tfr-to-symbol:-1;render:animation;"
            }],
            "view": {
                "id": 8685,
                "name": "Test",
                "descr": "",
                "type": 1,
                "userId": 1,
                "timestamp": 201602021520023,
                "isInstance": true,
                "isCache": true,
                "isHidden": false,
                "style": "background-image:;width:1366;height:768;font-family:null;font-size:0;font-color:null;background-color:#ffffff;",
                "config": "",
                "options": "adapter:0;position:0;hidden-nav:0;node-hang-display:0;line-hang-display:0;hang-name-display:0;node-hang-adapter:0;node-hang-position:4;",
                "parentViewId": -1,
                "orderCode": 2,
                "relSymbolId": -1
            }
        },


    },
    kpi: {
        8685: {
            "8694": {
                "MemRate": "7.13",
                "SysUpTime": "585860157",
                "CpuRate": "79.25",
                "ZhLabel": "47AK",
                "IsRouter": "0",
                "IpAddr": "37.32.248.10",
                "SnmpIp": "37.32.248.10",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "48": {
                "MemRate": "13.34",
                "SysUpTime": "262191400",
                "CpuRate": "2.00",
                "ZhLabel": "AOIC",
                "IsRouter": "0",
                "IpAddr": "37.32.248.10",
                "SnmpIp": "37.32.248.10",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "49": {
                "SysUpTime": "1191400",
                "ZhLabel": "832HC",
                "IsRouter": "0",
                "IpAddr": "37.104.248.14",
                "SnmpIp": "37.104.248.14",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "51": {
                "SysUpTime": "5848727507",
                "ZhLabel": "O2NJ",
                "IsRouter": "0",
                "IpAddr": "37.136.128.10",
                "SnmpIp": "37.136.128.10",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0",
                "Health":"100%",
                "Availability":"100%",
            },
            "52": {
                "MemRate": "43.19",
                "SysUpTime": "3481191400",
                "CpuRate": "62.11",
                "ZhLabel": "14EA",
                "IsRouter": "0",
                "IpAddr": "37.32.248.6",
                "SnmpIp": "37.32.248.6",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "54": {
                "MemRate": "10.55",
                "SysUpTime": "493845719",
                "CpuRate": "58.45",
                "ZhLabel": "HJRR",
                "IsRouter": "0",
                "IpAddr": "37.48.248.34",
                "SnmpIp": "37.48.248.34",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "55": {
                "MemRate": "81.40",
                "SysUpTime": "2571191400",
                "CpuRate": "29.47",
                "ZhLabel": "UV9Q",
                "IsRouter": "0",
                "IpAddr": "37.32.248.2",
                "SnmpIp": "37.32.248.2",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "59": {
                "MemRate": "50.17",
                "SysUpTime": "2571191400",
                "CpuRate": "48.59",
                "ZhLabel": "8S7R",
                "IsRouter": "0",
                "IpAddr": "37.48.248.10",
                "SnmpIp": "37.48.248.10",
                "PhyPortCnt": "1",
                "PortCnt": "1",
                "Vendor": "Reserved",
                "SysDescr": "",
                "EquipModel": "",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8686": {
                "SysUpTime": "647138457",
                "CpuRate": "84.92",
                "ZhLabel": "SR24",
                "IsRouter": "1",
                "IpAddr": "37.196.224.1",
                "SnmpIp": "37.0.248.46",
                "PhyPortCnt": "11",
                "PortCnt": "14",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "30584.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8687": {
                "MemRate": "71.69",
                "SysUpTime": "2442967010",
                "CpuRate": "18.51",
                "ZhLabel": "520B",
                "IsRouter": "1",
                "IpAddr": "37.40.224.1",
                "SnmpIp": "37.40.224.1",
                "PhyPortCnt": "7",
                "PortCnt": "25",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8688": {
                "MemRate": "70.16",
                "SysUpTime": "59834195",
                "CpuRate": "35.19",
                "ZhLabel": "2339 M ",
                "IsRouter": "1",
                "IpAddr": "37.40.224.5",
                "SnmpIp": "37.40.224.5",
                "PhyPortCnt": "10",
                "PortCnt": "26",
                "Vendor": "Start Network Technology Co., Ltd.",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8689": {
                "MemRate": "41.69",
                "SysUpTime": "2571191400",
                "CpuRate": "35.69",
                "ZhLabel": "98FM",
                "IsRouter": "1",
                "IpAddr": "37.32.224.1",
                "SnmpIp": "37.32.224.1",
                "PhyPortCnt": "11",
                "PortCnt": "14",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "0.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
              //  "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8690": {
                "MemRate": "91.79",
                "SysUpTime": "473894192",
                "ZhLabel": "CW7R",
                "IsRouter": "1",
                "IpAddr": "37.32.224.5",
                "SnmpIp": "37.32.224.5",
                "PhyPortCnt": "10",
                "PortCnt": "19",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "1896.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG2205BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8691": {
                "MemRate": "46.11",
                "SysUpTime": "235494423",
                "CpuRate": "32.10",
                "ZhLabel": "03MF",
                "IsRouter": "1",
                "IpAddr": "37.88.224.1",
                "SnmpIp": "37.0.248.42",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "48544.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8692": {
                "MemRate": "40.61",
                "SysUpTime": "235487411",
                "CpuRate": "71.72",
                "ZhLabel": "CD09",
                "IsRouter": "1",
                "IpAddr": "37.88.224.5",
                "SnmpIp": "37.0.244.42",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "4400.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
              //  "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8693": {
                "MemRate": "100.00",
                "SysUpTime": "222440400",
                "CpuRate": "59.16",
                "ZhLabel": "09PQ",
                "IsRouter": "1",
                "IpAddr": "172.16.1.38",
                "SnmpIp": "37.0.244.21",
                "PhyPortCnt": "106",
                "PortCnt": "109",
                "Vendor": "Maipu Electric Industrial Co., Ltd",
                "IfRate": "83656.00",
                "SysDescr": "MyPower (R) Operating System Software\nMP7500 version 6.3.6(integrity), compiled on Oct 16 2012, 02:41:18\nCopyright (C) 1999 Maipu Communication Technology Co., Ltd. All Rights Reserved.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8696": {
                "MemRate": "26.11",
                "SysUpTime": "295462072",
                "CpuRate": "58.15",
                "ZhLabel": "BB20",
                "IsRouter": "1",
                "IpAddr": "37.48.224.1",
                "SnmpIp": "37.0.248.22",
                "PhyPortCnt": "7",
                "PortCnt": "14",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "23184.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8697": {
                "LinkInBwUsedPer": "15.41",
                "LinkOutBwUsedPer": "12.67",
                "LinkOutRate": "76182.00",
                "LinkSpeed": "4000000.00",
                "LinkBwUsedPer": "14.62",
                "LinkRate": "858365.00",
                "LinkInRate": "768704.00"
            },
            "8699": {
                "MemRate": "81.43",
                "SysUpTime": "372671241",
                "CpuRate": "17.63",
                "ZhLabel": "SCOQ",
                "IsRouter": "1",
                "IpAddr": "37.196.224.5",
                "SnmpIp": "37.0.244.46",
                "PhyPortCnt": "10",
                "PortCnt": "16",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "0.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG2205BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8701": {
                "LinkInBwUsedPer": "16.38",
                "LinkOutBwUsedPer": "11.00",
                "LinkOutRate": "73382.00",
                "LinkSpeed": "4000000.00",
                "LinkBwUsedPer": "18.00",
                "LinkRate": "884336.00",
                "LinkInRate": "779704.00"
            },
            "8703": {
                "LinkOutRate": "76632.00",
                "LinkBwUsedPer": "10.27",
                "SrcIndex": "19",
                "DstIndex": "1670",
                "LinkRate": "821336.00",
                "ZhLabel": "09PQ->C092",
                "SrcNode": "09PQ",
                "SrcPort": "multilink11",
                "LinkInBwUsedPer": "18.62",
                "LinkOutBwUsedPer": "1.92",
                "DstNode": "C092",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "744704.00",
                "DstPort": "Mp-group0"
            },
            "8706": {
                "LinkOutRate": "81584.00",
                "LinkBwUsedPer": "6.14",
                "SrcIndex": "22",
                "DstIndex": "2054",
                "LinkRate": "491304.00",
                "ZhLabel": "09PQ->V8327",
                "SrcNode": "09PQ",
                "SrcPort": "multilink14",
                "LinkInBwUsedPer": "10.24",
                "LinkOutBwUsedPer": "2.04",
                "DstNode": "V8327",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "409720.00",
                "DstPort": "Mp-group0"
            },
            "8707": {
                "MemRate": "13.34",
                "SysUpTime": "10087942",
                "CpuRate": "32.06",
                "ZhLabel": "V9E8",
                "IsRouter": "1",
                "IpAddr": "37.136.224.5",
                "SnmpIp": "37.0.244.66",
                "PhyPortCnt": "10",
                "PortCnt": "26",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "11368.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8708": {
                "MemRate": "69.11",
                "SysUpTime": "148257386",
                "CpuRate": "94.17",
                "ZhLabel": "BIQKJ",
                "IsRouter": "1",
                "IpAddr": "37.16.224.1",
                "SnmpIp": "37.0.248.6",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "18016.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8710": {
                "MemRate": "43.19",
                "SysUpTime": "656033179",
                "CpuRate": "62.11",
                "ZhLabel": "VMWOI",
                "IsRouter": "1",
                "IpAddr": "37.112.224.1",
                "SnmpIp": "37.112.224.1",
                "PhyPortCnt": "7",
                "PortCnt": "25",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "9584.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8711": {
                "MemRate": "23.19",
                "SysUpTime": "1427220401",
                "CpuRate": "45.68",
                "ZhLabel": "ZI328",
                "IsRouter": "1",
                "IpAddr": "37.112.224.5",
                "SnmpIp": "37.112.224.5",
                "PhyPortCnt": "10",
                "PortCnt": "27",
                "Vendor": "Start Network Technology Co., Ltd.",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8712": {
                "MemRate": "43.19",
                "SysUpTime": "27304020",
                "CpuRate": "16.70",
                "ZhLabel": "V8W9",
                "IsRouter": "1",
                "IpAddr": "37.128.224.1",
                "SnmpIp": "37.0.248.62",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "17568.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8713": {
                "MemRate": "37.10",
                "SysUpTime": "27295206",
                "CpuRate": "76.99",
                "ZhLabel": "V8327",
                "IsRouter": "1",
                "IpAddr": "37.128.224.5",
                "SnmpIp": "37.0.244.62",
                "PhyPortCnt": "10",
                "PortCnt": "16",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "5400.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG2205BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8716": {
                "SysUpTime": "110079584",
                "ZhLabel": "09372",
                "IsRouter": "1",
                "IpAddr": "37.136.224.1",
                "SnmpIp": "37.0.248.66",
                "PhyPortCnt": "7",
                "PortCnt": "24",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "31216.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8717": {
                "MemRate": "16.66",
                "SysUpTime": "148251093",
                "CpuRate": "9.14",
                "ZhLabel": "23HJ",
                "IsRouter": "1",
                "IpAddr": "37.16.224.5",
                "SnmpIp": "37.0.244.6",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "8384.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8727": {
                "LinkOutRate": "176584.00",
                "LinkBwUsedPer": "7.20",
                "SrcIndex": "17",
                "DstIndex": "1670",
                "LinkRate": "575984.00",
                "ZhLabel": "09PQ->CD09",
                "SrcNode": "09PQ",
                "SrcPort": "multilink9",
                "LinkInBwUsedPer": "9.99",
                "LinkOutBwUsedPer": "4.41",
                "DstNode": ">CD09",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "399400.00",
                "DstPort": "Mp-group0"
            },
            "8731": {
                "LinkInBwUsedPer": "2.60",
                "LinkOutBwUsedPer": "1.74",
                "LinkOutRate": "34760.00",
                "LinkSpeed": "2000000.00",
                "LinkBwUsedPer": "2.17",
                "LinkRate": "86760.00",
                "LinkInRate": "52000.00"
            },
            "8734": {
                "MemRate": "25.27",
                "SysUpTime": "275125015",
                "CpuRate": "24.61",
                "ZhLabel": "VIW82",
                "IsRouter": "1",
                "IpAddr": "37.104.224.1",
                "SnmpIp": "37.0.248.50",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "47744.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8736": {
                "MemRate": "100.00",
                "SysUpTime": "25723974",
                "CpuRate": "96.58",
                "ZhLabel": "FJ84",
                "IsRouter": "1",
                "IpAddr": "37.48.224.5",
                "SnmpIp": "37.48.224.5",
                "PhyPortCnt": "9",
                "PortCnt": "28",
                "Vendor": "Start Network Technology Co., Ltd.",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8739": {
                "MemRate": "25.61",
                "SysUpTime": "95119162",
                "CpuRate": "91.66",
                "ZhLabel": "C092",
                "IsRouter": "1",
                "IpAddr": "37.104.224.5",
                "SnmpIp": "37.0.244.50",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "8888.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8740": {
                "LinkOutRate": "170792.00",
                "LinkBwUsedPer": "5.32",
                "SrcIndex": "18",
                "DstIndex": "2054",
                "LinkRate": "425320.00",
                "ZhLabel": "09PQ->SCOQ",
                "SrcNode": "09PQ",
                "SrcPort": "multilink10",
                "LinkInBwUsedPer": "6.36",
                "LinkOutBwUsedPer": "4.27",
                "DstNode": "SCOQ",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "254528.00",
                "DstPort": "Mp-group0"
            },
            "8741": {
                "LinkOutRate": "115344.00",
                "LinkBwUsedPer": "8.39",
                "SrcIndex": "9",
                "DstIndex": "1670",
                "LinkRate": "671544.00",
                "ZhLabel": "09PQ->23HJ",
                "SrcNode": "09PQ",
                "SrcPort": "multilink1",
                "LinkInBwUsedPer": "13.91",
                "LinkOutBwUsedPer": "2.88",
                "DstNode": "23HJ",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "556200.00",
                "DstPort": "Mp-group0"
            },
            "8810": {
                "MemRate": "27.14",
                "SysUpTime": "3405161702",
                "CpuRate": "60.14",
                "ZhLabel": "LGM1",
                "IsRouter": "1",
                "IpAddr": "37.24.224.5",
                "SnmpIp": "37.0.244.10",
                "PhyPortCnt": "10",
                "PortCnt": "13",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "4144.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8812": {
                "MemRate": "94.01",
                "SysUpTime": "4044179646",
                "CpuRate": "72.60",
                "ZhLabel": "83JV",
                "IsRouter": "1",
                "IpAddr": "37.24.224.1",
                "SnmpIp": "37.0.248.10",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "34312.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8813": {
                "MemRate": "60.91",
                "SysUpTime": "1530900973",
                "CpuRate": "97.00",
                "ZhLabel": "RGE9 M",
                "IsRouter": "1",
                "IpAddr": "37.64.224.5",
                "SnmpIp": "37.64.224.5",
                "PhyPortCnt": "10",
                "PortCnt": "26",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "4600.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8814": {
                "MemRate": "79.24",
                "SysUpTime": "1530912629",
                "CpuRate": "10.64",
                "ZhLabel": "220B",
                "IsRouter": "1",
                "IpAddr": "37.64.224.1",
                "SnmpIp": "37.64.224.1",
                "PhyPortCnt": "7",
                "PortCnt": "24",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "22080.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8816": {
                "MemRate": "13.34",
                "SysUpTime": "216130849",
                "CpuRate": "58.21",
                "ZhLabel": "9ENV",
                "IsRouter": "1",
                "IpAddr": "37.120.224.1",
                "SnmpIp": "37.0.248.58",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "0.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "8818": {
                "LinkOutRate": "223008.00",
                "LinkBwUsedPer": "11.13",
                "SrcIndex": "21",
                "DstIndex": "11",
                "LinkRate": "1335096.00",
                "ZhLabel": "09PQ->V9382",
                "SrcNode": "09PQ",
                "SrcPort": "multilink13",
                "LinkInBwUsedPer": "18.53",
                "LinkOutBwUsedPer": "3.72",
                "DstNode": "V9382",
                "LinkSpeed": "6000000.00",
                "LinkInRate": "1112088.00",
                "DstPort": "multilink 1"
            },
            "8823": {
                "MemRate": "30.58",
                "SysUpTime": "42344197",
                "CpuRate": "79.53",
                "ZhLabel": "V9382",
                "IsRouter": "1",
                "IpAddr": "37.120.224.5",
                "SnmpIp": "37.0.244.58",
                "PhyPortCnt": "10",
                "PortCnt": "13",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "7480.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "8829": {
                "LinkOutRate": "300968.00",
                "LinkBwUsedPer": "11.89",
                "SrcIndex": "10",
                "DstIndex": "11",
                "LinkRate": "1426456.00",
                "ZhLabel": "09PQ->LGM1",
                "SrcNode": "09PQ",
                "SrcPort": "multilink2",
                "LinkInBwUsedPer": "18.76",
                "LinkOutBwUsedPer": "5.02",
                "DstNode": "LGM1",
                "LinkSpeed": "6000000.00",
                "LinkInRate": "1125488.00",
                "DstPort": "multilink 1"
            },
            "9087": {
                "MemRate": "24.00",
                "SysUpTime": "2571191400",
                "CpuRate": "22.10",
                "ZhLabel": "DLQ8",
                "IsRouter": "1",
                "IpAddr": "10.192.188.66",
                "SnmpIp": "10.192.188.66",
                "PhyPortCnt": "5",
                "PortCnt": "10",
                "Vendor": "Maipu Electric Industrial Co., Ltd",
                "IfRate": "1024.00",
                "SysDescr": "MyPower (R) Operating System Software\nMP3840 version 6.3.6(integrity), compiled on Oct 16 2012, 04:52:26\nCopyright (C) 1999 Maipu Communication Technology Co., Ltd. All Rights Reserved.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "9088": {
                "MemRate": "68.49",
                "SysUpTime": "1923873969",
                "CpuRate": "34.05",
                "ZhLabel": "7609",
                "IsRouter": "1",
                "IpAddr": "10.192.255.4",
                "SnmpIp": "10.192.255.4",
                "PhyPortCnt": "16",
                "PortCnt": "153",
                "Vendor": "ciscoSystems",
                "IfRate": "6110744.00",
                "SysDescr": "Cisco IOS Software, c7600rsp72043_rp Software (c7600rsp72043_rp-ADVIPSERVICESK9-M), Version 15.2(4)S6, RELEASE SOFTWARE (fc1)\nTechnical Support: http://www.cisco.com/techsupport\nCopyright (c) 1986-2014 by Cisco Systems, Inc.\nCompiled Fri 08-Aug-14 05:1",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "9089": {
                "MemRate": "18.00",
                "SysUpTime": "2571089200",
                "CpuRate": "2.00",
                "ZhLabel": "RM3A",
                "IsRouter": "1",
                "IpAddr": "10.192.187.66",
                "SnmpIp": "10.192.187.66",
                "PhyPortCnt": "4",
                "PortCnt": "15",
                "Vendor": "Maipu Electric Industrial Co., Ltd",
                "IfRate": "1753160.00",
                "SysDescr": "MyPower (R) Operating System Software\nMP3780 version 6.1.191(NAT)(integrity), compiled on May 25 2012, 17:38:47\nCopyright (C) 1999 Maipu Communication Technology Co., Ltd. All Rights Reserved.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "9091": {
                "MemRate": "68.49",
                "SysUpTime": "1922966102",
                "CpuRate": "48.10",
                "ZhLabel": "X9RN",
                "IsRouter": "1",
                "IpAddr": "10.192.255.3",
                "SnmpIp": "10.192.255.3",
                "PhyPortCnt": "22",
                "PortCnt": "135",
                "Vendor": "ciscoSystems",
                "IfRate": "79146984.00",
                "SysDescr": "Cisco IOS Software, c7600rsp72043_rp Software (c7600rsp72043_rp-ADVIPSERVICESK9-M), Version 15.2(4)S6, RELEASE SOFTWARE (fc1)\nTechnical Support: http://www.cisco.com/techsupport\nCopyright (c) 1986-2014 by Cisco Systems, Inc.\nCompiled Fri 08-Aug-14 05:1",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "9092": {
                "LinkOutRate": "1387392.00",
                "LinkBwUsedPer": "13.88",
                "SrcIndex": "30",
                "DstIndex": "5",
                "LinkRate": "4442128.00",
                "ZhLabel": "7609->DLQ8",
                "SrcNode": "7609",
                "SrcPort": "GigabitEthernet3/0/0.9",
                "LinkInBwUsedPer": "19.09",
                "LinkOutBwUsedPer": "8.67",
                "DstNode": "DLQ8",
                "LinkSpeed": "16000000.00",
                "LinkInRate": "3054736.00",
                "DstPort": "gigaethernet3"
            },
            "9094": {
                "LinkOutRate": "4478520.00",
                "LinkBwUsedPer": "33.82",
                "SrcIndex": "129",
                "DstIndex": "14",
                "LinkRate": "16912344.00",
                "ZhLabel": "X9RN->RM3A",
                "SrcNode": "X9RN",
                "SrcPort": "GigabitEthernet2/1/1.20",
                "LinkInBwUsedPer": "49.74",
                "LinkOutBwUsedPer": "17.91",
                "DstNode": "RM3A",
                "LinkSpeed": "25000000.00",
                "LinkInRate": "12433824.00",
                "DstPort": "atm1/0"
            },
            "9239": {
                "MemRate": "19.70",
                "SysUpTime": "3380119733",
                "CpuRate": "52.89",
                "ZhLabel": "WOV1",
                "IsRouter": "1",
                "IpAddr": "37.72.224.1",
                "SnmpIp": "37.0.248.34",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "13432.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "9241": {
                "MemRate": "69.79",
                "SysUpTime": "3945900196",
                "CpuRate": "41.76",
                "ZhLabel": "BSRA",
                "IsRouter": "1",
                "IpAddr": "37.80.224.5",
                "SnmpIp": "37.0.244.38",
                "PhyPortCnt": "7",
                "PortCnt": "13",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "2440.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG5120BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
               // "ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "9242": {
                "MemRate": "81.11",
                "SysUpTime": "2375127163",
                "CpuRate": "15.10",
                "ZhLabel": "LK1J",
                "IsRouter": "1",
                "IpAddr": "37.72.224.5",
                "SnmpIp": "37.0.244.34",
                "PhyPortCnt": "10",
                "PortCnt": "16",
                "Vendor": "HUAWEI Technology Co.,Ltd",
                "IfRate": "2496.00",
                "SysDescr": "Huawei Versatile Routing Platform\nSoftware Version: VRP (R) software, Version 5.30 USG2205BSR V100R005C00SPC300\nCopyright (c) 2008-2011 Huawei Technologies Co., Ltd.",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "1"
            },
            "9243": {
                "MemRate": "80.71",
                "SysUpTime": "122908294",
                "CpuRate": "25.08",
                "ZhLabel": "C7FM",
                "IsRouter": "1",
                "IpAddr": "37.80.224.1",
                "SnmpIp": "37.0.248.38",
                "PhyPortCnt": "9",
                "PortCnt": "12",
                "Vendor": "Start Network Technology Co., Ltd.",
                "IfRate": "16296.00",
                "SysDescr": "Ruijie Router (RSR20-24) by Ruijie Networks",
                "EquipModel": ".",
                "Geography": "",
                //"ZhObjCls": "路由器",
                "IsSwitch": "0"
            },
            "9245": {
                "LinkOutRate": "52792.00",
                "LinkBwUsedPer": "2.03",
                "SrcIndex": "15",
                "DstIndex": "2054",
                "LinkRate": "162256.00",
                "ZhLabel": "09PQ->LK1J",
                "SrcNode": "09PQ",
                "SrcPort": "multilink7",
                "LinkInBwUsedPer": "2.74",
                "LinkOutBwUsedPer": "1.32",
                "DstNode": "LK1J",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "109464.00",
                "DstPort": "Mp-group0"
            },
            "9246": {
                "LinkOutRate": "210648.00",
                "LinkBwUsedPer": "9.17",
                "SrcIndex": "16",
                "DstIndex": "1670",
                "LinkRate": "733224.00",
                "ZhLabel": "09PQ->BSRA",
                "SrcNode": "09PQ",
                "SrcPort": "multilink8",
                "LinkInBwUsedPer": "13.06",
                "LinkOutBwUsedPer": "5.27",
                "DstNode": "BSRA",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "522576.00",
                "DstPort": "Mp-group0"
            },
            "9247": {
                "LinkOutRate": "37068718.00",
                "LinkBwUsedPer": "41.17",
                "SrcIndex": "16",
                "DstIndex": "1670",
                "LinkRate": "624184.00",
                "ZhLabel": "47AK->WOV1",
                "SrcNode": "47AK",
                "SrcPort": "multilink8",
                "LinkInBwUsedPer": "18.15",
                "LinkOutBwUsedPer": "6.14",
                "DstNode": "WOV1",
                "LinkSpeed": "4000000.00",
                "LinkInRate": "564558.00",
                "DstPort": "Mp-group0"
            },
            "46": {
                "SrcIndex": "16",
                "DstIndex": "1670",
                "ZhLabel": "47AK->520B",
                "SrcNode": "47AK",
                "SrcPort": "multilink8",
                "DstNode": "520B",
                "LinkSpeed": "4000000.00",
                "DstPort": "Mp-group52"
            }
        }
    },
    event: {
        8685: {
            "lineEventStatus": {"8703": 5},
            "events": {
                "2475237837575691665165149831673125481": {
                    "text": "IP 37.136.128.10的设备持续PING不通,超过115days 1hours..",
                    "transfer": "0",
                    "oname": "O2NJ",
                    "at": "1447204273000",
                    "as": "2",
                    "lt": "1433744705000",
                    "type": "NetPoller",
                    "au": "root",
                    "id": "2475237837575691665165149831673125481",
                    "title": "设备ping不通",
                    "level": "5",
                    "am": "",
                    "oid": "368053119",
                    "ft": "1433702948000",
                    "ds": "-1"
                },
                "29674698919313348538571474991217275111": {
                    "text": "10.192.255.3失去响应后5分钟内恢复响应.",
                    "transfer": "0",
                    "oname": "X9RN",
                    "at": "1447204273000",
                    "as": "2",
                    "lt": "1411695458000",
                    "type": "NetPoller",
                    "au": "root",
                    "id": "29674698919313348538571474991217275111",
                    "title": "暂时掉线, 已恢复",
                    "level": "2",
                    "am": "",
                    "oid": "1669027637",
                    "ft": "1409800005000",
                    "ds": "-1"
                },
                "4184389757415947168811944124513157152444": {
                    "text": "37.48.248.34持续PING不通,超过40days 0hours..",
                    "transfer": "0",
                    "oname": "HJRR",
                    "at": "1447204273000",
                    "as": "2",
                    "lt": "1411695952000",
                    "type": "NetPoller",
                    "au": "root",
                    "id": "4184389757415947168811944124513157152444",
                    "title": "设备ping不通",
                    "level": "5",
                    "am": "",
                    "oid": "1140138694",
                    "ft": "1408236593000",
                    "ds": "-1"
                },
                "361167444231973356291452981742937228294": {
                    "text": "37.104.248.14持续PING不通,超过1days 16hours..",
                    "transfer": "0",
                    "oname": "832HC",
                    "at": "1447204273000",
                    "as": "1",
                    "lt": "1410399243000",
                    "type": "NetPoller",
                    "au": "root",
                    "id": "361167444231973356291452981742937228294",
                    "title": "设备ping不通",
                    "level": "5",
                    "am": "",
                    "oid": "1925419503",
                    "ft": "1410254474000",
                    "ds": "-1"
                },
                "312867235211189717583878141112716192116": {
                    "text": "中断",
                    "transfer": "0",
                    "oname": "09PQ->C092",
                    "at": "1447204273000",
                    "as": "2",
                    "lt": "1433745005000",
                    "type": "linkdevaleres",
                    "au": "root",
                    "id": "312867235211189717583878141112716192116",
                    "title": "链路09PQ->C092中断",
                    "level": "5",
                    "am": "",
                    "oid": "1743027470",
                    "ft": "1433703005000",
                    "ds": "-1"
                },
                "2291387855262686111836189181531379516388": {
                    "text": "37.32.248.10持续PING不通,超过41days 16hours..",
                    "transfer": "0",
                    "oname": "AOIC",
                    "at": "1447900687000",
                    "as": "2",
                    "lt": "1411695661000",
                    "type": "NetPoller",
                    "au": "root",
                    "id": "2291387855262686111836189181531379516388",
                    "title": "设备ping不通",
                    "level": "5",
                    "am": "",
                    "oid": "369372872",
                    "ft": "1408094369000",
                    "ds": "-1"
                }
            },
            "lineEventIds": {"8703": ["312867235211189717583878141112716192116"]},
            "symbolEventStatus": {"48": 5, "49": 5, "51": 5, "54": 5, "9091": 2},
            "symbolEventIds": {
                "48": ["2291387855262686111836189181531379516388"],
                "49": ["361167444231973356291452981742937228294"],
                "51": ["2475237837575691665165149831673125481"],
                "54": ["4184389757415947168811944124513157152444"],
                "9091": ["29674698919313348538571474991217275111"]
            }
        }
    },
    resourceNodes:[]
};



//获取拓扑图中路由器的资源文件
$.ajax({
    type : 'GET',
    url :  ctx+"/resource/resource/topoResource",
    async:false,
    data : {
    },
    success : function(result) {
        if(result.success){
            testData["resourceNodes"] = result.body.resourceNodes;
        }
    },
    error:function (error) {

    }

});


//获取拓扑图中所有的警告信息
$.ajax({
    type : 'GET',
    async : false,
    dataType:"json",
    url :  ctx+"/topoview/topoView/findExceptionListByTopo",
    data:{
        exceptionClass:"",
        topoViewId:queryParams.id

    },
    timeout:10*1000,    //超时时间 10s
    success : function(result) {
       // console.log("exception");
       // console.log(result);
        //交换机id 642803bcbd8b45b1bce3cffcd77300d8
        //
        if(result){
            for (viewId in result){
                testData.event[viewId] = {};
                var dataArr = {};
                var eventDataArr = result[viewId];
                console.log("eventDataArr");
                console.log(eventDataArr);
                var eventArr = {};
                var symbolEventStatusArr = {};
                var symbolEventIdsArr = {};
                for(var i =0;i<eventDataArr.length;i++){
                    var model = eventDataArr[i];
                   // console.log("model");
                   // console.log(model);
                    var text ="";
                    var oname = "";
                    var firstTime = "";
                    var lastTime = "";
                    var title = "";
                    var exceptionlevel = "0";// 当前系统是【1 正常 绿色 2 通知 蓝色  3 警告 黄色 4 主要 橙色 5 严重 红色】 后台字典传递过来是 【异常等级（0，提示 1，一般 2，较急 3，紧急）】
                    var currentStatus = "0";//当前状态 【当前状态（0，未恢复 1，手动恢复 2，已恢复）】
                    var type = "";//告警类型

                    if(model.indicatorName){
                        text = model.indicatorName;
                    }

                    if(model.exceptionSource){
                        oname = model.exceptionSource;
                    }

                    if(model.indicatorEventType){
                        type = model.indicatorEventType;
                    }

                    if(model.firstTriggerTime){
                        firstTime = model.firstTriggerTime;
                    }
                    if(model.lastTriggerTime){
                        lastTime = model.lastTriggerTime;
                    }

                    if(model.exceptionClass){
                        exceptionlevel = (parseInt(model.exceptionClass)+2).toString();//告警程度+2 为了匹配当前系统
                    }

                    //当前状态
                    if(model.currentStatus){
                        currentStatus = model.currentStatus;
                    }
                    eventArr[model.id] = {
                        "text":text,//告警正文
                        "currentStatus":currentStatus,
                        "transfer": "0",//产生方式
                        "oname": oname,//告警网元名称
                        "at": "1447204273000",//确认时间
                        "as": "2",
                        "lt": lastTime,//最近告警时间
                        "type": type,//告警类型
                        "au": "root",
                        "id": model.id,
                        "title": text,//告警标题
                        "level": exceptionlevel,//告警严重程度
                        "am": "",
                        "oid": "368053119",
                        "ft": firstTime,//开始告警时间
                        "ds": "-1"
                    }
                    ////设置当前资源的警告程度
                    if(model.topoSymbolId){
                        symbolEventStatusArr[model.topoSymbolId] = exceptionlevel;
                    }
                    //设置当前资源的警告事件,可能有多个
                    var sEventIdsArr = [];
                    for(var j =0;j<eventDataArr.length;j++) {
                        var evetnModel = eventDataArr[j];
                        if(evetnModel.topoSymbolId == model.topoSymbolId){
                            sEventIdsArr.push(evetnModel.id);
                        }
                    }
                    symbolEventIdsArr[model.topoSymbolId] = sEventIdsArr;
                }
                dataArr["events"] = eventArr;
                dataArr["symbolEventStatus"]= symbolEventStatusArr;
                dataArr["symbolEventIds"] = symbolEventIdsArr;
                testData.event[viewId] = dataArr;
                console.log("testData-dujin");
                console.log(testData.event[viewId].symbolEventIds);
            }
        }


    },
    error:function(){
    },
    complete:function(XMLHttpRequest,status){
        if(status=='timeout'){
            alert("请求超时，请检查网络后重试");
        }
    }
});

//获取当前拓扑图中所有资源信息 topoResouceInfo
$.ajax({
    type : 'GET',
    async : false,
    dataType:"json",
    url :  ctx+"/topoview/topoView/topoResouceInfo",
    data:{
        topoViewId:queryParams.id
    },
    timeout:10*1000,    //超时时间 10s
    success : function(result) {
        var data = result.body.data;
        if (data) {
            for (var viewId in data) {
                var dataList = data[viewId];
                testData.kpi[viewId] = {};
                var dataArr = {};
                for (var i = 0; i < dataList.length; i++) {
                    var symbolData = dataList[i];
                    var _sef = {};
                    var memRate = "暂无数据";
                    var cpuRate = "暂无数据";
                    // var geography = "";
                    var sysDesc = "";
                    var model = "";
                    var phyPortCnt = "暂无数据";
                    var portCnt = "暂无数据";
                    var geography = "暂无数据";
                    var isSwitch = "";
                    var memorySurplus = "暂无数据";
                    var server = "暂无数据";
                    var currentonCnectionumber = "暂无数据";
                    var continuousWorkingTime = "暂无数据";
                    var resourceType = "暂无数据";
                    var ICMPTime = "暂无数据";
                    var SysName = "暂无数据";
                    var health = "暂无数据";
                    var Port = "暂无数据";
                    var EquipmentIP = "暂无数据";
                    var CurrentConnNum = "暂无数据";

                    if(symbolData.indicators && symbolData.indicators.currentConnNum){
                        CurrentConnNum = symbolData.indicators.currentConnNum;
                    }
                    if(symbolData.resource && symbolData.resource.ip){
                        EquipmentIP = symbolData.resource.ip;
                    }
                    if(symbolData.resource && symbolData.resource.resourceBaseInfo && symbolData.resource.resourceBaseInfo.port){
                        Port = symbolData.resource.resourceBaseInfo.port;
                    }
                    if(symbolData.resource && symbolData.resource.ip){
                        EquipmentIP = symbolData.resource.ip;
                    }

                    if(symbolData.resource && symbolData.resource.sysName){
                        SysName = symbolData.resource.sysName;
                    }
                    if(symbolData.indicators && symbolData.indicators.icmp){
                        ICMPTime = symbolData.indicators.icmp +"毫秒";
                    }
                    if(symbolData.resourceType){
                        resourceType = symbolData.resourceType;
                    }
                    if(symbolData.currentonCnectionumber){
                        currentonCnectionumber = symbolData.currentonCnectionumber;
                    }
                    if(symbolData.continuousWorkingTime){
                        continuousWorkingTime = symbolData.continuousWorkingTime;
                    }
                    if(symbolData.server){
                        server = symbolData.server;
                    }
                    if(symbolData.memorySurplus){
                        memorySurplus = symbolData.memorySurplus;
                    }
                    if(symbolData.isSwitch){
                        isSwitch = symbolData.isSwitch;
                    }
                    if(symbolData.geography){
                        geography = symbolData.geography;
                    }
                    if(symbolData.portCnt){
                        portCnt = symbolData.portCnt;
                    }
                    if(symbolData.phyPortCnt){
                        phyPortCnt = symbolData.phyPortCnt;
                    }
                    if(symbolData.indicators && symbolData.indicators.memRate){
                        memRate = symbolData.indicators.memRate +"%";
                    }
                    if(symbolData.indicators && symbolData.indicators.cpuRate){
                        cpuRate = symbolData.indicators.cpuRate +"%";
                    }

                    if(symbolData.sysDesc){
                        sysDesc = symbolData.sysDesc;
                    }

                    if(symbolData.model){
                        model = symbolData.model;
                    }
                  //资源图元的详情数据
                 if(symbolData.topoSymbolId){
                    dataArr[symbolData.topoSymbolId] = {
                        "MemRate": memRate,
                       // "SysUpTime": symbolData.sysUpTime,
                        "CpuRate": cpuRate,
                        "ZhLabel": symbolData.resourceName,
                        // "IsRouter": "0",
                        "IpAddr": symbolData.ip,
                        "SnmpIp": symbolData.ip,
                        // "PhyPortCnt": phyPortCnt,
                        // "PortCnt": portCnt,
                        "Vendor": symbolData.vendor,
                        "SysDescr": sysDesc,
                        "EquipModel": model,
                        //"Geography": geography,
                        //"ZhObjCls": symbolData.resourceType,
                        "Health":health,
                        "Availability":"100%",
                         //"IsSwitch": isSwitch,
                        "MemorySurplus":memorySurplus,
                        "Server":server,
                        "CurrentonCnectionumber":currentonCnectionumber,
                        "ContinuousWorkingTime":continuousWorkingTime,
                        "ResourceType":resourceType,
                        "ICMPTime":ICMPTime,
                        "SysName":SysName,
                        "Port":Port,
                        "EquipmentIP":EquipmentIP,
                        "SysTotalPeople":"",
                        "OnlinePeople":"",
                        "CurrentConnNum":CurrentConnNum,
                    };
               }else{
                  //物理链路的信息
                     //设置数据源
                     var linkIndicator = symbolData.linkIndicator;
                     var upUsedRate = "暂无数据",downUsedRate = "暂无数据",DownInterface = "暂无数据",LinkCapacity = "暂无数据",DownLinkRate = "暂无数据",upLinkRate = "暂无数据",UpInterface = "暂无数据",healthDegree = "暂无数据",availability = "暂无数据";
                     if(linkIndicator!=null&&linkIndicator.upUsedRate){
                         upUsedRate = linkIndicator.upUsedRate +"%";
                     }

                     if(linkIndicator!=null&&linkIndicator.downUsedRate){
                         downUsedRate = linkIndicator.downUsedRate +"%";
                     }

                     if(linkIndicator!=null&&linkIndicator.downInterface&&linkIndicator.downInterface.name){
                         DownInterface = linkIndicator.downInterface.name;
                     }

                     if(linkIndicator!=null&&linkIndicator.upInterface&&linkIndicator.upInterface.name){
                         UpInterface = linkIndicator.upInterface.name;
                     }

                     if(linkIndicator!=null&&linkIndicator.capacity){
                         LinkCapacity = linkIndicator.capacity;
                     }


                     if(linkIndicator!=null&&linkIndicator.upRate != null){
                         upLinkRate = linkIndicator.upRate;
                     }


                     if(linkIndicator!=null&&linkIndicator.downRate){
                         DownLinkRate = linkIndicator.downRate;
                     }

                     if(linkIndicator!=null&&linkIndicator.healthDegree){
                         healthDegree = linkIndicator.healthDegree+"%";
                     }

                     if(linkIndicator!=null&&linkIndicator.availability){
                         availability = linkIndicator.availability +"%";
                     }
                     dataArr[symbolData.id] =
                                {
                                    "upUsedRate": upUsedRate,//上行利用率
                                    "downUsedRate": downUsedRate,//下行利用率
                                    "DownInterface": DownInterface,//下连接接口
                                    "UpInterface": UpInterface,//上连接接口
                                    "LinkCapacity": LinkCapacity,//容量
                                    "DownLinkRate": DownLinkRate,//下行速率
                                    "UpLinkRate": upLinkRate,//上行速率
                                    "healthDegree":healthDegree,
                                    "availability":availability
                                }
                     }
                }
                testData.kpi[viewId] = dataArr;
            }
        }
    },
    error:function(){
    },
    complete:function(XMLHttpRequest,status){
        if(status=='timeout'){
            alert("请求超时，请检查网络后重试");
        }
    }
});



//获取当前拓扑图中所有拓扑图
$.ajax({
    type : 'GET',
    async : false,
    dataType:"json",
    url :  ctx+"/topoview/topoView/getTopoViewList",
    data:{
        topoViewId:queryParams.id
    },
    timeout:10*1000,    //超时时间 10s
    success : function(result) {
         if (result.length == 0){
             for(var i = 0;i<testData.config.navmenu4show.length-1;i++){//数据为空，操作，视图，分析，编辑 4个操作不能操作
                 testData.config.navmenu4show[i].disabled=true;
             }
         }else {
             $("#treeEditBtn").css("display","");
         }
         for(var i = 0;i<result.length;i++){
             testData.views.push(result[i]);
             //设置view，kpi 和events
             var symbolsArr = new Array();
             if(result[i].topoSymbolsList != null){
                 for(var j = 0;j<result[i].topoSymbolsList.length;j++){
                     var data = result[i].topoSymbolsList[j];
                     symbolsArr.push({
                         "id": data.id,
                         "name": data.name,
                         "x": data.x,
                         "y": data.y,
                         "type": 0,
                         "timestamp": data.timestamp,
                         "viewId": data.view.id,
                         "parentId": -1,
                         "objectId": data.objectId,
                         "objectClass": data.objectClass,
                         "instanceId": "",
                         "style": data.style,
                         "options": "",
                         "url": "",
                         "alarm": ""
                     });
                 }
             }

             var linesArr = new Array();
             if(result[i].topoLineList != null){
                 for(var j = 0;j<result[i].topoLineList.length;j++){
                     var data = result[i].topoLineList[j];
                     linesArr.push({
                         "id": data.id,
                         "name": data.name,
                         "path":data.path,
                         "type": 0,
                         "srcSymbol": data.srcSymbol,
                         "dstSymbol": data.dstSymbol,
                         "timestamp": data.timestamp,
                         "viewId": data.view.id,
                         "parentId": -1,
                         "objectId": data.objectId,
                         "objectClass": data.objectClass,
                         "instanceId": "",
                         "style": data.style,
                         "options": "",
                         "url": "",
                         "alarm": ""
                     });
                 }
             }
             testData.view[result[i].id] = {
                 "symbols": symbolsArr,
                 "lines": linesArr,
                 "view": {
                     "id": result[i].id,
                     "name": result[i].name,
                     "descr": "",
                     "type": 1,
                     "timestamp": result[i].timeStamp,
                     "isInstance": true,
                     "isCache": true,
                     "isHidden": false,
                     "style": "background-color:#fff;width:1366;height:768;",
                     "config": "",
                     "options": "",
                     "parentViewId": -1,
                     "orderCode": 2,
                     "relSymbolId": -1
                 }
             };

             if(!testData.event[result[i].id]){
                 //没有获取到拓扑图异常事件则初始化下，防止报错无法取得数据
                 testData.event[result[i].id]={};
             }
         }
        console.log(testData);
    },
    error:function(){
    },
    complete:function(XMLHttpRequest,status){
        if(status=='timeout'){
            alert("请求超时，请检查网络后重试");
        }
    }
});