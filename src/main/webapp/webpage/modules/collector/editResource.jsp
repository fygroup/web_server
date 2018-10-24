<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/static/jquery/jquery-2.1.1.min.js"%>
<%@ include file="/static/jquery/bootstrap.min.js"%>
<html>
<head>
    <title>编辑采集资源列表</title>
    <meta name="decorator" content="ani"/>
    <style>
        .controls{
            margin-left: 50px;
        }
        #submitBtn{
            float: right;
            margin: 10px;
        }
    </style>
    <script>
        $(document).ready(function() {
            loadResource();
        })
        function loadResource() {
            $.ajax({
                url:"${ctxStatic}/test_collector.json",
                type: "get",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    $.each(data,function (k,v) {
                        console.log(v);
                        $.each(v,function (k1,v1) {
                            console.log(v1.name);
                            $("#box").append('<label class="checkbox"><input type="checkbox" value="'+v1.id+'">'+v1.name+'</label>')
                        })
                    })
                }
            })
        }

        function submitForm() {
            var data=$('editResourceForm').serialize();//序列化获得表单数据，结果为：user_id=12&user_name=John&user_age=20
            var submitData=decodeURIComponent(data,true);//submitData是解码后的表单数据，结果同上
            $.ajax({
                url:'',
                data:submitData,
                cache:false,//false是不缓存，true为缓存
                async:true,//true为异步，false为同步
                beforeSend:function(){
                    //请求前
                },
                success:function(result){
                    //请求成功时
                    window.location = "${ctx}/resource/resource/collector";
                },
                complete:function(){
                    //请求结束时
                },
                error:function(){
                    //请求失败时
                }
            })
        }
    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/resource/resource/collector"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form id="editResourceForm" class="form-horizontal">
                    <fieldset>
                        <div id="legend" class="">
                            <legend class="">请选择需要监控的资源</legend>
                        </div>
                        <div class="control-group">
                            <div id="box" class="controls">
                                <%--<label id="showLabel" class="checkbox">--%>
                                    <%--<input type="checkbox" value="0">全选--%>
                                <%--</label>--%>
                                <%--<label class="checkbox">--%>
                                    <%--<input type="checkbox" value="Option two">222--%>
                                <%--</label>--%>
                            </div>
                        </div>
                    </fieldset>
                        <button id="submitBtn" type="submit" class="btn btn-primary btn-lg" onclick="submitForm()">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
