<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>首页</title>
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/echarts.jsp"%>
    <script src="${ctxStatic}/common/js/anhui.js"></script>
    <script src="${ctxStatic}/common/js/panorama_drag.js"></script>
    <script src="${ctxStatic}/common/js/chinaMapChart.js"></script>
    <style type="text/css">
        body{
            background: url("${ctxStatic}/common/images/pan_background.jpg") no-repeat center;
            background-size: cover;
        }
        #box {
            width: 400px;
            height: 400px;
            cursor: move;
            position: absolute;
            top: 30px;
            left: 30px;
            /*background-color: #002a80;*/
            /**/
            /*border: 1px solid #CCCCCC;*/
            /*opacity: 0.4;*/
            /*-webkit-box-shadow: 10px 10px 25px #ccc;*/
            /*-moz-box-shadow: 10px 10px 25px #ccc;*/
            /*box-shadow: 10px 10px 25px #ccc;*/
        }
        /*#coor {*/
            /*width: 10px;*/
            /*height: 10px;*/
            /*overflow: hidden;*/
            /*cursor: se-resize;*/
            /*position: absolute;*/
            /*right: 0;*/
            /*bottom: 0;*/
            /*background-color: #09C;*/
        /*}*/
    </style>
</head>
<body>
<div id="box">
    <div id="chinaMap" style="width:100%;height:90%;"></div>
    <%--<div id="coor"></div>--%>
</div>
<script src="vendor/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="js/vendor.js"></script>

<script>
    var $box = $('#box').mousedown(function(e) {
        var offset = $(this).offset();

        this.posix = {'x': e.pageX - offset.left, 'y': e.pageY - offset.top};
        $.extend(document, {'move': true, 'move_target': this});
    });
        // .on('mousedown', '#coor', function(e) {
        // var posix = {
        //     'w': $box.width(),
        //     'h': $box.height(),
        //     'x': e.pageX,
        //     'y': e.pageY
        // };
    //
    //     $.extend(document, {'move': true, 'call_down': function(e) {
    //             $box.css({
    //                 'width': Math.max(30, e.pageX - posix.x + posix.w),
    //                 'height': Math.max(30, e.pageY - posix.y + posix.h)
    //             });
    //         }});
    //     return false;
    // });
</script>

</body>
</html>