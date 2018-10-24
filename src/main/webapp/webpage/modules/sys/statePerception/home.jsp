<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="ani"/>
    <!-- 引入 echarts.js -->
    <%@ include file="/webpage/include/echarts.jsp"%>
    <script src="${ctxStatic}/common/js/gaugeChart.js"></script>
    <script src="${ctxStatic}/common/js/china.js"></script>
    <script src="${ctxStatic}/common/js/anhui.js"></script>
    <script src="${ctxStatic}/common/js/chinaMapChart.js"></script>
    <script src="${ctxStatic}/common/js/moveBar.js"></script>
    <script src="${ctxStatic}/common/js/rowBar.js"></script>
    <script src="${ctxStatic}/common/js/pieChart.js"></script>
    <script src="${ctxStatic}/common/js/upDownBar.js"></script>
<%--
    <script src="${ctxStatic}/common/js/twowayChart.js"></script>
--%>
    <script src="${ctxStatic}/common/js/downBar.js"></script>
    <style type="text/css">

        #body-container {
            margin-left: 0px !important;
            /**padding: 10px;*/
            margin-top: 0px !important;
            overflow-x: hidden!important;
            transition: all 0.2s ease-in-out !important;
            height: 100% !important;
        }
        .pereption-body{
            background: url("${ctxStatic}/common/images/perception.jpg") no-repeat center;
            background-size: cover;
        }
       /* #body-container{
            background: url("${ctxStatic}/common/images/perception.jpg") no-repeat center;
            background-size: cover;
        }*/
        .panel,.box,.top-right-chart,a.stat,.top-left-chart,.chart-container{
            background-color: rgba(0,0,0,0) !important;
        }
        .dashboard-title{
            width:100%;
            text-align: center;
            padding:16px 0 0;
            font-size: 14px;
            color:rgba(255,255,255,0.8);
        }
        .perception-box{
            margin:auto;
        }
    </style>
</head>
<body class="pereption-body">
<%--<img src="images/perception.png">--%>
<div id="body-container" class="wrapper wrapper-content">
    <div class="conter-wrapper home-container">
        <div class="row home-row">
            <div class="col-lg-3 col-md-3 perception-col">
                <div class=" panel panel-danger perception-box">
                    <div class="dashboard-title">健康等级</div>
                    <%--<div class="dashboard-title">订单状态</div>--%>
                    <div id="dashboard" style="width: 100%;height: 180px;"></div>
                </div>
                <div class=" panel panel-danger perception-box">
                    <div id="moveBar" style="width: 100%;height: 320px;"></div>
                </div>
                <div class=" panel panel-danger perception-box">
                    <div id="rowBar" style="width: 100%;height: 300px;"></div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 perception-col">
                <div class="map-container box padder perception-box">
                    <%--<div id="world-map" style="width: 100%; height: 480px"></div>--%>
                    <div id="chinaMap" style="width:100%;height:800px;"></div>
                    <%--<div id="anhui-map" style="width: 100%; height: 480px"></div>--%>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 perception-col">
                <div class="todo-container panel panel-danger perception-box">
                    <div class="panel-body">
                        <div class="home-charts-right">
                            <div class="top-right-chart">
                                <div id="pieChart" style="height:260px; padding-top:7px;"></div>
                            </div>
                            <div class="bottom-right-chart">
                                <div class="top-left-chart clearfix">
                                    <div class="row">
                                        <div class="col-sm-12">
<%--
                                            <div id="twowayChart" style="height:360px;width:100%; padding-top:7px;"></div>
--%>
                                            <div id="downBar" style="min-height:260px;width:100%; padding-top:7px;"></div>
                                            <div id="upDownBar" style="min-height:260px;width:100%; padding-top:7px;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--<div class="row home-row">
            <div class="col-md-4 col-lg-3">
                <div class="home-stats">
                    <a href="" class="stat hvr-wobble-horizontal  perception-box">
                        <div class=" stat-icon">
                            <i class="fa fa-cloud-upload fa-4x text-info "></i>
                        </div>
                        <div class=" stat-label">
                            <div class="label-header">
                                88%
                            </div>
                            <div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
                                <div class="progress-bar progress-bar-info" role="progressbar"
                                     aria-valuenow="88" aria-valuemin="0" aria-valuemax="100"  style="width: 88%;">
                                </div>
                            </div>
                            <div class="clearfix stat-detail">
                                <div class="label-body">
                                    <i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>服务正常运行时间
                                </div>
                            </div>
                        </div>
                    </a>
                    <a href="" class="stat hvr-wobble-horizontal  perception-box">
                        <div class=" stat-icon">
                            <i class="fa fa-heartbeat fa-4x text-success "></i>
                        </div>
                        <div class=" stat-label">
                            <div class="label-header">
                                94%
                            </div>
                            <div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
                                <div class="progress-bar progress-bar-success" role="progressbar"
                                     aria-valuenow="94" aria-valuemin="0" aria-valuemax="100"  style="width: 94%;">
                                </div>
                            </div>
                            <div class="clearfix stat-detail">
                                <div class="label-body">
                                    <i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>积极反馈
                                </div>
                            </div>
                        </div>
                    </a>
                    <a href="" class="stat hvr-wobble-horizontal perception-box">
                        <div class=" stat-icon">
                            <i class="fa fa-flag fa-4x text-danger "></i>
                        </div>
                        <div class=" stat-label">
                            <div class="label-header">
                                88%
                            </div>
                            <div class="progress-sm progress ng-isolate-scope" value="progressValue" type="info">
                                <div class="progress-bar progress-bar-danger" role="progressbar"
                                     aria-valuenow="88" aria-valuemin="0" aria-valuemax="100"  style="width: 88%;">
                                </div>
                            </div>
                            <div class="clearfix stat-detail">
                                <div class="label-body">
                                    <i class="fa fa-arrow-circle-o-right pull-right text-muted"></i>机器负载
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-md-4 col-lg-6">
                <div class="home-charts-middle">
                    <div class="chart-container perception-box">
                        &lt;%&ndash;<div class="chart-comment clearfix">
                            <div class="text-primary pull-left">
                                <span class="comment-header">55%</span><br />
                                <span class="comment-comment">搜素引擎</span>
                            </div>
                            <div class="text-success pull-left m-l">
                                <span class="comment-header">25%</span><br />
                                <span class="comment-comment">自主访问</span>
                            </div>
                            <div class="text-warning pull-left m-l">
                                <span class="comment-header">20%</span><br />
                                <span class="comment-comment">友情链接</span>
                            </div>
                        </div>&ndash;%&gt;
                        <div id="lineChart" style="height:250px"></div>
                        <div id="CPUChart" style="height:250px"></div>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-lg-3">
                <div class="home-charts-right">
                    <div class="top-right-chart perception-box">
                        <div id="cbar" style="height:150px; padding-top:7px;"></div>
                    </div>
                    <div class="bottom-right-chart">
                        <div class="top-left-chart clearfix perception-box">
                            <div class="row">
                                <div class="col-sm-6 text-left">
                                    <div class="padder">
                                        <span class="heading">本周访问人数 : </span><br />
                                        <big class="text-primary">22068</big>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div id="pie"  style="height:125px; padding-top:8px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>--%>
    </div>
</div>
<script src="vendor/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="js/vendor.js"></script>

<script>
    $(document).ready(function(){
        window.onresize = function() {
            chinMapChart.resize();
            downBar.resize();
            gaugeChart.resize();
            barGraph.resize();
            pieChart.resize();
            rowBar.resize();
            twowayChart.resize();
            upDownBar.resize();
        }
    })

    $(function(){
        $('#calendar2').fullCalendar({
            eventClick: function(calEvent, jsEvent, view) {
                alert('Event: ' + calEvent.title);
                alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
                alert('View: ' + view.name);
            }
        });

        $('#rtlswitch').click(function() {
            console.log('hello');
            $('body').toggleClass('rtl');

            var hasClass = $('body').hasClass('rtl');

            $.get('/api/set-rtl?rtl='+ (hasClass ? 'rtl': ''));

        });
        $('.theme-picker').click(function() {
            changeTheme($(this).attr('data-theme'));
        });
        $('#showMenu').click(function() {
            $('body').toggleClass('push-right');
        });

    });
    function changeTheme(the) {
        $("#current-theme").remove();
        $('<link>')
            .appendTo('head')
            .attr('id','current-theme')
            .attr({type : 'text/css', rel : 'stylesheet'})
            .attr('href', '/css/app-'+the+'.css');
    }
</script>

</body>
</html>