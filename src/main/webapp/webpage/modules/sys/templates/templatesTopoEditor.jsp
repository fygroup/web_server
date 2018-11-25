<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>个人信息</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/proTree.js"></script>
    <script src="${ctxStatic}/SimpleTree.js"></script>
    <%@include file="templatesEditor.js" %>
    <script>
        var data;
        var code="1";
        var type="up";
        var id="";
        var name="";
        var title;

        $.ajax({
            type : 'POST',
            async : true,
            url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/getUserIndicator",
            data:{type:'1'},
            timeout:10*1000,    //超时时间 10s
            success : function(result) {
                var value=result["list"];
                title=result["title"];
                //console.log("==================");
               // console.log(value);
                for(var i=0;i<value.length;i++){
                    id+=value[i].id+",";
                    name+="("+value[i].resource.name+")"+value[i].indicator.name +",";
                }
                initNameView();
            },
            error:function(){
            },
            complete:function(XMLHttpRequest,status){

            }
        });


        $(document).ready(function () {
            $.ajax({
                type : 'POST',
                async : true,
                url :  "${ctx}/resource/resource/dataSelect",
                timeout:50*1000,    //超时时间 10s
                success : function(result) {
                    data=result;
                    $("#optionView").html(data["select"]);
                    $(".selectbtn").show();
                    initSelect(null);
                },
                error:function(){
                    //alert("接口列表失败");
                },
                complete:function(XMLHttpRequest,status){
                    if(status=='timeout'){
                        alert("请求超时，请检查网络后重试");
                    }
                }
            });


        })

        function selectThis(obj) {
            var selectedId=$(obj).next().next().val();
            var selectedName=$(obj).next().val();
            if(checkSelected(selectedId)){ //选中
                $(obj).attr("data-select",false);
                $(obj).attr('src','${ctxStatic}/images/icon/unselected_icon.png');
                delSelected(selectedId,selectedName);
            }else{
                $(obj).attr('src','${ctxStatic}/images/icon/selected_icon.png');
                $(obj).attr("data-select",true);
                id=id+","+selectedId;
                name=name+","+selectedName;
            }
            initNameView();
        }
        function selectOption() {
            code=$('#resourceTypeSelect  option:selected').val();
            initSelect(null);
        }
        function initSelect(t) {
            if(t!=null){
                type=t;
            }
            if(data!=null){
                $("#innerUl").html(data[code]);
                $(".st_tree").SimpleTree({
                    click:function(a){
                    }
                });
                $(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
            }else{
                $("#innerUl").html("");
            }
            initImg();
        }

        function checkSelected(selectedId){
            var item=id.split(",");
            for(var i=0;i<item.length;i++){
                if(item[i]==selectedId){
                   return true;
                }
            }
            return false;
        }

        function delSelected(selectedId,selectedName){
            var item=id.split(",");
            id="";
            for(var i=0;i<item.length;i++){
                if(item[i]!=""&&item[i]!=selectedId){
                    id=id+item[i]+",";
                }
            }
            var num=0;
            var nameItem=name.split(",");
            name="";
            for(var i=0;i<nameItem.length;i++){
                if(nameItem[i]!=""&&nameItem[i]!=selectedName){
                    name=name+nameItem[i]+",";
                    num++;
                }
            }

            $("#selected_num").html("已选指标("+num+")");
        }

        function initNameView(){
            if(title){
                $("#editIndicator").val(title);
            }

            $("#selectedView").html("");
            var nameItem=name.split(",");
            var idItem = id.split(",");
            var html="";
            var num=0;
            for(var i=0;i<nameItem.length;i++){
              if(nameItem[i]!=""){
                  html=html+  "<tr> <td>"+nameItem[i]+"<input value='"+ idItem[i] +"' name='"+ idItem[i] +"' hidden = false></td></tr>";
                  num++;
              }
            }

            $("#selected_num").html("已选指标("+num+")");
            $("#selectedView").html(html);
        }

        function initImg(){
            $(".selectImg").each(function(){
                var thisId=$(this).next().next().val();
                var item=id.split(",");
                for(var i=0;i<item.length;i++){
                    if(item[i]==thisId){
                        $(this).attr('src','${ctxStatic}/images/icon/selected_icon.png');
                        break;
                    }
                }
            });
        }


        function getTitle(){
            var title=$("#editIndicator").val();
            if(title.trim()==""){
                title="指标一览";
            }
            return title;
        }

        function getIds(){
                return id;
        }
    </script>
    <style>
        .form-group{
            overflow: hidden;
        }
    </style>
</head>
<body class="bg-white">
<div class="wrapper wrapper-content">
    <div class="row">


        <div class="col-md-12">
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-2 col-lg-2 col-md-2 control-label">栏目标题：</label>
                    <div class="col-sm-6 col-lg-6 col-md-6">
                        <input class="form-control required" value="" id="editIndicator" placeholder="指标一览"/>
                    </div>
                </div>
                <div class="form-group" >
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th class="width-35">待选指标列表</th>
                            <th class="width-35" id="selected_num">已选指标(0)</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="width-35">
                                <div>
                                    <div id="optionView"></div>
                                    <div id="innerUl"></div>
                                </div>
                            </td>
                            <td class="width-35">
                                <table class="table table-striped table-bordered" id="selectedView">
                                </table>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <%--<label class="col-sm-2 control-label">拓扑名称：</label>
                    <div class="col-sm-6">
                        <select name="manufacturer" id="manufacturer" class="form-control" >
                            <option value="1">物理拓扑图</option>
                            <option value="1">逻辑拓扑图</option>
                        </select>
                    </div>--%>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>