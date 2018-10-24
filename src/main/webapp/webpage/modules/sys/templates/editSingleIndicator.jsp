<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>个人信息</title>
    <meta name="decorator" content="ani"/>
    <script src="${ctxStatic}/proTree.js"></script>
    <script src="${ctxStatic}/SimpleTree.js"></script>
    <style>
        .form-group{
            overflow: hidden;
        }
    </style>
    <script>
        var data;
        var code="1";
        var type="up";
        var id="";
        var checkIndicatorId = "";
        var checkPropertyId = "";
        var title="";

        $(document).ready(function () {

            $.ajax({
                type : 'POST',
                async : true,
                url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/getSingleUserIndicator",
                timeout:10*1000,    //超时时间 10s
                success : function(result) {
                    id=result["selectedResourceId"];
                    $("#top-indicator-list").html(result["html"]);
                    code=result["code"];
                    checkIndicatorId=result["selected"];
                    if(result["title"]){
                        $("#editIndicator").val(result["title"]);
                    }
                },
                error:function(){
                },
                complete:function(XMLHttpRequest,status){
                }
            });


            $.ajax({
                type : 'POST',
                async : true,
                url :  "${ctx}/resource/resource/singleResourceSelect",
                timeout:50*1000,    //超时时间 10s
                success : function(result) {
                    data=result;

                    $("#optionView").html(data["select"]+"");
                    $(".selectbtn").show();
                    initSelect(null);
                    initSelected();
                },
                error:function(){
                    alert("接口列表失败");
                },
                complete:function(XMLHttpRequest,status){
                    if(status=='timeout'){
                        alert("请求超时，请检查网络后重试");
                    }
                }
            });


        })

        function selectThis(obj) {
            var selectedId=$(obj).next().val();
            if(checkSelected(selectedId)){ //选中
                $(obj).attr("data-select",false);

                $(obj).attr('src','${ctxStatic}/images/icon/unselected_icon.png');
                delSelected(selectedId);

            }else{
                $(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
                $(obj).attr('src','${ctxStatic}/images/icon/selected_icon.png');
                $(obj).attr("data-select",true);
                id=selectedId;
            }
            resourceIndicatorlist();
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
                if(data[code]){
                    $("#innerUl").html(data[code]+"");
                }

                $(".st_tree").SimpleTree({
                    click:function(a){
                    }
                });
                $(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
            }else{
                $("#innerUl").html("");
            }

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

        function delSelected(selectedId){
            var item=id.split(",");
            id="";
            for(var i=0;i<item.length;i++){
                if(item[i]!=selectedId){
                    id=id+item[i]+",";
                }
            }
        }


        function selectIndicatorList(){

        }

        function saveEditIndicator(){
            var editIndicator = $("#editIndicator").val();
            return editIndicator;
        }
        function getIds(){
            return checkIndicatorId;
        }

        function indicatorSelect(obj){
            var selectedId=$(obj).next().val();
            if(checkIndicatorSelect(selectedId)){ //选中
                $(obj).attr("data-select",false);
                $(obj).attr('src','${ctxStatic}/images/icon/unselected_icon.png');
                delIndicatorSelected(selectedId);
            }else{
                $(obj).attr('src','${ctxStatic}/images/icon/selected_icon.png');
                $(obj).attr("data-select",true);
                checkIndicatorId=checkIndicatorId+","+selectedId;
            }
        }
        function checkIndicatorSelect(selectedId){
            var item=checkIndicatorId.split(",");
            for(var i=0;i<item.length;i++){
                if(item[i]==selectedId){
                    return true;
                }
            }
            return false;
        }
        function delIndicatorSelected(selectedId){
            var item=checkIndicatorId.split(",");
            checkIndicatorId="";
            for(var i=0;i<item.length;i++){
                if(item[i]!=""&&item[i]!=selectedId){
                    checkIndicatorId=checkIndicatorId+item[i]+",";
                }
            }
        }


        function resourceIndicatorlist() {
            $.ajax({
                type : 'POST',
                async : true,
                url :  "${ctx}/resourceindicatorlist/resourceIndicatorlist/resourceIndicatorlist",
                data:{resourceId:id},
                timeout:10*1000,    //超时时间 10s
                success : function(result) {
                    var html = "";
                    for(var i=0;i<result.length;i++){
                       html +="<div><img src='/web/static/images/icon/unselected_icon.png' style='height: 15px;width: 15px' class='indicatorSelectImg' onclick='indicatorSelect(this)' ><input type='text' hidden value='"+ result[i].id +"'/><a href='#'> "+ result[i].indicator.name +" </a></div>";
                    }
                    $("#top-indicator-list").html(html);
                    checkIndicatorId="";
                },
                error:function(){
                },
                complete:function(XMLHttpRequest,status){

                }
            });
        }

        function saveEditIndicatorid(){
            return checkIndicatorId;
        }


        function getTitle(){

            return "";
        }






       function initSelected() {

           $(".selectImg").each(function () {
               if(id!=null&&id==$(this).next().val()){
                   $(this).attr('src','${ctxStatic}/images/icon/selected_icon.png');
               }else{
                   $(this).attr('src','${ctxStatic}/images/icon/unselected_icon.png');
               }

           });


       }


    </script>
    <style>
        .form-group{
            margin:8px auto;
            overflow: hidden;
        }
        .control-label{
            text-align: right;
            line-height: 28px;
        }
        td{
            padding:0px !important;
        }
        .top-property-title{
            border-top:1px solid #ddd;
        }
        .left-indicator-list{
            width:100%;
            height:100%;
            overflow: auto;
            padding:8px;
        }
    </style>
</head>
<body class="bg-white">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel-body">
             <%--   <div class="form-group">
                    <label class="col-sm-2 control-label"><font color="red">*</font>栏目标题：</label>
                    <div class="col-sm-8">
                        <input class="form-control required" value="" id="editIndicator" placeholder="单一资源指标一览"/>
                    </div>
                </div>--%>
                <div class="form-group" >
                    <table class="table table-striped table-bordered">
                        <tbody>
                        <tr>
                            <td class="width-35">
                                <div class="top-indicator-title">待选指标列表</div>
                                <div class="left-indicator-list">
                                    <div id="optionView"></div>
                                    <div id="innerUl"></div>
                                </div>
                            </td>
                            <td class="width-35">
                                <div class="top-indicator-contact">
                                    <div class="top-indicator-title">已选指标列表</div>
                                    <div id="checkIndicatorId"></div>
                                    <div class="top-indicator-list" id="top-indicator-list"></div>
                                </div>
                                <%--<div class="top-indicator-contact">
                                    <div class="top-indicator-title top-property-title">已选属性列表</div>
                                    <div class="top-indicator-list" id="top-property-list"></div>
                                </div>--%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>