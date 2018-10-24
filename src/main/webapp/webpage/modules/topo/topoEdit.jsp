
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
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/edit.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/jvalidate/jquery.jvalidate.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/topo/css/colorpicker/jquery.colorpicker.min.css" />
</head>
<body>
<div class="ui-layout-north"></div>
<div class="ui-layout-center">
    <div class="ui-layout-west">
        <div id="resourceOverlay"></div>
        <div id="resourceSearching">
            <img src="images/load/load_circle.gif" class="loading"/><br>
         <%--   <input id="stopSearchBtn" type="button" value=""/>--%>
        </div>
        <%--<div class="subhead"><input id="resourceSearcher" type="text" class="search gray"/></div>--%>
        <div id="treeArea" class="content">
            <div id="accordion"></div>
        </div>
    </div>
    <div class="ui-layout-svg">
        <div id="svgCanvasOverlay"></div>
        <div id="svgCanvas" class="content">
            <div id="svgContainer"></div>
        </div>
    </div>
    <div class="ui-layout-east">
        <div class="content">
            <div id="tabs"></div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/topo/js/jquery-1.10.2.min.js"></script>

<script src="${ctxStatic}/topo/js/jquery.hydra.min.js"></script>
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
<script src="${ctxStatic}/topo/js/ui/mousewheel/jquery.mousewheel.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/resize/jquery.ba-resize.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/iphonechkbox/jquery.iphonechkbox.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/msgwindow/jquery.msgwindow.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/colorpicker/jquery.colorpicker.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/datatables/jquery.dataTables.min.js"></script>

<script>
    var dex = "${ctxStatic}/";
    var ctx = "${ctx}";
    var ctxStaticTopo = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>'+"/"+dex;
    var queryParams = $.string.queryParams(window.location.search);
</script>

<script src="${ctxStatic}/topo/js/ui/tooltip/jquery.tooltip.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/propertypanel/jquery.propertypanel.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/contextmenu/jquery.contextmenu.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/contextmenu/jquery.ctxadapter.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/d3layout/d3forlayout.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/d3layout/layouttree.min.js"></script>
<script src="${ctxStatic}/topo/js/data.min.js"></script>
<script src="${ctxStatic}/topo/js/topology.min.js"></script>
<script src="${ctxStatic}/topo/js/ui/navmenu/jquery.menuswitch.min.js"></script>
<script src="${ctxStatic}/topo/js/edit.min.js"></script>


</body>
</html>