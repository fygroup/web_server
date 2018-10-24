
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/static/jquery/jquery-2.1.1.min.js"%>
<%@ include file="/static/jquery/bootstrap.min.js"%>
<html>
<head>
    <title>采集器</title>
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <script>
        function getCollector(){
            $('#collectorList').bootstrapTable({
                <%--url: "${ctxStatic}/test_collector.json",--%>
                method: 'get',
                dataType: "json",
                cache: false,//是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,//是否显示分页（*）
                sortOrder: "asc",//排序方式
                uniqueId:"id",
                pageNumber:1,//初始化加载第一页，默认第一页
                pageSize: 10,//每页的记录行数（*）
                pageList: [10, 25, 50, 100],//可供选择的每页的行数（*）
                data  :[{
                    "id"    : "4564" ,
                    "name"  : "lafeng",
                    "status" : "online" ,
                    "monitoringResource" : [{
                        "id" : "nk56",
                        "name" : "CPU"
                    },{
                        "id" : "dfsh84",
                        "name" : "内存"
                    }]
                },{
                    "id"    : "sdjnk" ,
                    "name"  : "xijing",
                    "status" : "outline" ,
                    "monitoringResource" : [{
                        "id" : "nk56",
                        "name" : "CPU"
                    },{
                        "id" : "dfsh84",
                        "name" : "内存"
                    },{
                        "id" : "465465",
                        "name" : "硬盘"
                    }]
                },{
                    "id"    : "45748" ,
                    "name"  : "厉害了我的哥",
                    "status" : "outline" ,
                    "monitoringResource" : [{
                        "id" : "nk56",
                        "name" : "CPU"
                    },{
                        "id" : "dfsh84",
                        "name" : "内存"
                    },{
                        "id" : "465465",
                        "name" : "硬盘"
                    }]
                },{
                    "id"    : "sdjnk" ,
                    "name"  : "xijing",
                    "status" : "outline" ,
                    "monitoringResource" : [{
                        "id" : "nk56",
                        "name" : "CPU"
                    },{
                        "id" : "dfsh84",
                        "name" : "内存"
                    },{
                        "id" : "465465",
                        "name" : "硬盘"
                    }]
                }],
                //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
                //queryParamsType:'',
                ////查询参数,每次调用是会带上这个参数，可自定义
                // queryParams : function(params) {
                //     var searchParam = $("#searchForm").serializeJSON();
                //     searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
                //     searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                //     searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                //     return searchParam;
                // },
                //分页方式：client客户端分页，server服务端分页（*）
                sidePagination: "server",
                contextMenuTrigger:"right",//pc端 按右键弹出菜单
                // onClickRow: function(row, $el){
                // },

                //处理json数据需要配置此项
                 responseHandler: function (res) {
                        return res.rows;
                    },
                columns: [{
                        field: 'id',
                        title: '资源id',
                        sortable: true

                    }
                    ,{
                        field: 'name',
                        title: '名称',
                        sortable: true

                    }
                    ,{
                        field: 'status',
                        title: '状态',
                        sortable: true

                    }
                    ,{
                        field: 'monitoringResource',
                        title: '监控资源管理',
                        align: 'center',
                        events: monitoringResource,
                        formatter: function(value, row, index) {
                            return [
                                '<button id="editResource" type="button" class="btn btn-primary glyphicon glyphicon-pencil">编辑</button>'
                            ].join("");
                        }
                    }
                ]

            });
        }

        window.monitoringResource = {
            // 编辑按钮事件
            "click #editResource": function (e,value,row,index) {
                console.log(e,value,row,index);
                window.location = "${ctx}/resource/resource/editResource";// + row.id;
            }
        }


        $(document).ready(function() {
            getCollector();
        })
    </script>
</head>
<style>
    #tableContainer{
        margin: 10px;
    }
    #tableHeader{
        margin-top: 10px;
    }
</style>

<body>
    <div>
        <div style="background-color: #fff; width: 98%;margin-top: 10px;margin-left: 1%; padding: 10px">
            <h4 id="tableHeader">采集器列表</h4>
            <div id="tableContainer" class="row">
                <table id="collectorList" class="table table-bordered"  data-toolbar="#toolbar"></table>
            </div>
        </div>
    </div>


</body>

</html>
