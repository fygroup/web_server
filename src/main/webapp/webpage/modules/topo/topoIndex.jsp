<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Topology</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/layout/complex.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/jqueryui/jquery-ui-1.10.3.custom.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/navmenu/jquery.navmenu.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/msgwindow/msgwindow.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/tooltip/jquery.tooltip.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/ztree/zTreeStyle.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/contextmenu/jquery.contextmenu.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/datatables/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/percentbar/jquery.percentbar.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/iphonechkbox/jquery.iphonechkbox.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/main.css" />
    <style>
        #topo_list_content{
            width: 100%;
            height: 100%;
            overflow: auto;
        }
    </style>
</head>
<body id="body" style="min-width: 960px;min-height: 720px">
<%--<div id="topo_list_content" style="position: absolute;width: 100%;height: 100%;z-index: 10000;background: #d0e5f5;top: 0;left: 0;">--%>
    <%--<div  style=""><ul id="topo_list"></ul></div>--%>
<%--</div>--%>

    <div  class="topo-list ui-layout-north"></div>
    <div  class="topo-list ui-layout-center">
        <div class="ui-layout-svg">
            <div id="svgCanvas" class="content">
                <div class="topoTitleLeft">
                    <div class="topoTitleRight">
                        <div id="topoTitle"></div>
                    </div>
                </div>
                <div id="svgContainer"></div>
                <div id="eagleEye">
                    <div id="eagleEye-header">
                        <div id="eagleEye-zoom"></div>
                        <div id="eagleEye-close"></div>
                    </div>
                    <div id="eagleEye-content">
                        <div id="eagleEye-thumbnail-wrap"></div>
                        <div id="eagleEye-box"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui-layout-alarm">
            <div class="content">
                <div id="alarmTableContainer"></div>
            </div>
        </div>
    </div>
    <div  id="topo-list treeHighlightOverlay" class="layoutOverlay" ></div>
    <div  class="topo-list ui-layout-west">
        <div id="relViewRsPanelCover">
            <div id="relViewRsPanel"></div>
            <div class="layoutOverlay"><img src="${ctxStatic}/images/load/load_circle.gif" class="loading"/></div>
        </div>
        <div id="resourceOverlay" class="layoutOverlay"></div>
        <div id="resourceSearching">
            <img src="${ctxStatic}/images/load/load_circle.gif" class="loading"/><br>
            <input id="stopSearchBtn" type="button" value=""/>
        </div>
        <div class="subhead" id="subhead-index" style="">
            <input id="resourceSearcher" type="text" class="search gray" style="opacity: 0;height:0"/>
            <%--<img  src="${ctxStatic}/images/icon/write-16.png" title="编辑"/>--%>
            <span id="treeEditBtn" style="float:right;color:#888;margin-top: 8px;">编 辑</span>
        </div>
        <div id="treeArea" class="content"></div>
        <div class="footer"></div>
    </div>

<script src="${ctxStatic}/topo/js/jquery-1.10.2.min.js"></script>
<script src="${ctxStatic}/topo/js/jquery.hydra.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/propertypanel/jquery.propertypanel.min.js"></script>
<script src="${ctxStatic}/topo/js/jquery.lab.min.js"></script>
<script src="${ctxStatic}/topo/js/jquery.svg.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/jquery.easing.1.3.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/jquery.hoverintent.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/jqueryui/jquery-ui-1.10.3.custom.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/hotkeys/jquery.hotkeys.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/layout/jquery.layout-latest.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/jvalidate/jquery.jvalidate.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/navmenu/jquery.navmenu.min.js"></script>
<%--<script src="${ctxStatic}/topo/js/ui/contextmenu/jquery.contextmenu.min.js"></script>--%>
<script src="${ctxStatic}/topo/js/ui/contextmenu/jquery.ctxadapter.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/mousewheel/jquery.mousewheel.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/resize/jquery.ba-resize.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/iphonechkbox/jquery.iphonechkbox.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/msgwindow/jquery.msgwindow.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/datatables/jquery.dataTables.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/tooltip/jquery.tooltip.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/jscounter/jscounter.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/percentbar/jquery.percentbar.min.js"></script>
<script>
    var dex = "${ctxStatic}/";
    var ctx = "${ctx}";
    var ctxStaticTopo = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>'+"/"+dex;
    var queryParams = $.string.queryParams(window.location.search);
</script>
<script src="${ctxStatic}/topo/js/data.min.js"></script>
<script src="${ctxStatic}/topo/js/topology.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/contextmenu/jquery.contextmenu.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/tooltip/jquery.adapter4perf.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/tooltip/jquery.adapter4hang.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/alarmtable/jquery.alarmtable.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/navmenu/jquery.menuswitch.min.js"></script>
<script src="${ctxStatic}/topo/js/main.min.js"></script>
</body>
</html>