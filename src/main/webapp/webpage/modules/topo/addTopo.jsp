
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="decorator" content="ani"/>
    <title></title>
    <style>
        .container{
            width:100%;
            padding:34px;
        }
        .body-container{
            background: #fff;

        }
    </style>
</head>
<body class="body-container">

<div class="container">
    <div  class="form-group">
        <span class="topo-title"></span>
        <label class="col-sm-2 control-label">拓扑图类型</label>
        <div class="col-sm-10">
            <select class="form-control m-b" name="account">
                <option>网络拓扑图</option>
            </select>
        </div>
    </div>
    <div class="hr-line-dashed"></div>
    <div  class="form-group">
        <span class="topo-title"></span>
        <label class="col-sm-2 control-label">拓扑图名称111</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="topoName">
        </div>
    </div>

    <button class="btn btn-primary pull-right" onclick="addTopo()"><strong>确  定</strong>
    </button>
</div>

<%--<script src="${ctxStatic}/topo/js/topology.min.js"></script>--%>
<script>
    var dex = "${ctxStatic}/";
    var ctx = "${ctx}";
    var ctxStaticTopo = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() %>'+"/"+dex;
    function addTopo(){
        alert("sure");
        $.ajax({
            type : 'POST',
//            dataType:"json",
            url :  "${ctx}/topoview/topoView/topoResouceSave",
            data:{name:$("#topoName").val(),type:"1"},
            timeout:3*1000,    //超时时间 10s
            success : function(result) {
               alert(result);
            },
            error:function(){
                alert("失败");
            },
            complete:function(XMLHttpRequest,status){
                if(status=='timeout'){
                    alert("请求超时，请检查网络后重试");
                }
            }
        });
    }
</script>

</body>
</html>