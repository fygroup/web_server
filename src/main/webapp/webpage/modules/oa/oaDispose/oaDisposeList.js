<%@ page contentType="text/html;charset=UTF-8" %>
    <script>
    $(document).ready(function() {
        $('#table').bootstrapTable({

            //请求方法
            method: 'get',
            //类型json
            dataType: "json",
            //显示刷新按钮
            showRefresh: true,
            //显示切换手机试图按钮
            showToggle: true,
            //显示 内容列下拉框
            showColumns: true,
            //显示到处按钮
            showExport: true,
            //显示切换分页按钮
            showPaginationSwitch: true,
            //最低显示2行
            minimumCountColumns: 2,
            //是否显示行间隔色
            striped: true,
            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            cache: false,
            //是否显示分页（*）
            pagination: true,
            //排序方式
            sortOrder: "asc",
            //初始化加载第一页，默认第一页
            pageNumber:1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/oa/oaDispose/data",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams : function(params) {
                var searchParam = $("#searchForm").serializeJSON();
                searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
                searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                return searchParam;
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
            contextMenuTrigger:"right",//pc端 按右键弹出菜单
            contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
            contextMenu: '#context-menu',
            onContextMenuItem: function(row, $el){
                if($el.data("item") == "edit"){
                    window.location = "${ctx}/oa/oaDispose/form?id=" + row.id;
                } else if($el.data("item") == "delete"){
                    jp.confirm('确认要删除该问题处理记录吗？', function(){
                        jp.loading();
                        jp.get("${ctx}/oa/oaDispose/delete?id="+row.id, function(data){
                            if(data.success){
                                $('#table').bootstrapTable('refresh');
                                jp.success(data.msg);
                            }else{
                                jp.error(data.msg);
                            }
                        })

                    });

                }
            },

            onClickRow: function(row, $el){
            },
            columns: [{
                checkbox: true

            }
                ,{
                    field: 'oaIssueReturn.exception',
                    title: '问题',
                    sortable: true,
                    formatter:function(value, row , index) {
                        if(value == null){
                            return row.description;
                        }else{
                            return "<a href='${ctx}/oa/oaIssueReturn/exceptionForm?exceptionId=" + value.id + "'>" + value.indicatorName + "</a>";

                        }
                    }
                },{
                    field: 'name',
                    title: '处理人',
                    sortable: true

                },{
                    field: 'startTime',
                    title: '开始时间',
                    formatter:function (value, row, index) {
                        if(value==null){
                           return "-";
                        }else{
                            return jp.dateFormat(value, "yyyy-MM-dd hh:mm:ss");
                        }

                    }
                },{
                    field: 'endTime',
                    title: '结束时间',
                    formatter:function (value, row, index) {
                        if(value==null){
                            return "-";
                        }else{
                            return jp.dateFormat(value, "yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },{
                    field: 'problemstate',
                    title: '问题状态',
                    sortable: true,
                    formatter:function(value, row , index){
                        if(value == '0'){
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemstate'))}, value, "待接单");
                        }else if(value == '1') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemstate'))}, value, "接单");
                        }else if(value == '2')
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemstate'))}, value, "处理");
                        else if(value == '3'){
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemstate'))}, value, "完成");
                        }else{
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemstate'))}, value, "-");
                        }
                    }

                },{
                    field:'complete',
                    title:'完成时间',
                    formatter:function (value, row, index) {
                        if(value==null){
                            return "-";
                        }else{
                            return jp.dateFormat(value, "yyyy-MM-dd hh:mm:ss");
                        }
                    }
                },{
                    field:'declaretype',
                    title:'申报类型',
                    sortable:true,
                    formatter:function(value, row , index){
                        if(value == '0'){
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemtype'))}, value, "软件");
                        }else if(value == '1') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemtype'))}, value, "硬件");
                        }else if(value == '2'){
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemtype'))}, value, "网络");
                        }
                        else{
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_dispose_problemtype'))}, value, "-");
                        }
                    }
                },
                {
                    field:'plan',
                    title:'解决方案',
                    sortable:true
                },{
                    field:'cause',
                    title:'问题原因',
                    sortable:true
                },{
                    field:'remark',
                    title:'备注',
                    sortable:true
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        'click .order': function (e, value, row, index) {
                            jp.confirm('确认要接单吗？',function () {
                                jp.get("${ctx}/oa/oaDispose/order?id="+row.id,function (data) {
                                    if(data.success){
                                        jp.success(data.msg);
                                        $('#table').bootstrapTable('refresh');

                                    }else{
                                        jp.error(data.msg);
                                    }

                                })
                            })

                        },
                        'click .solve': function (e, value, row, index) {
                            jp.confirm('确认要处理吗？',function () {
                                jp.get("${ctx}/oa/oaDispose/solve?id="+row.id,function (data) {
                                    if(data.success){
                                        jp.success(data.msg);
                                        $('#table').bootstrapTable('refresh');

                                    }else{
                                        jp.error(data.msg);
                                    }

                                })
                            })

                        },
                        'click .edit': function (e, value, row, index) {
                            jp.openDialog('解决方案', '${ctx}/oa/oaDispose/form?id=' + row.id,'800px','500px');
                        },

                    },
                    formatter: function operateFormatter(value, row, index) {
                        var arra = new Array();
                        if(row['problemstate'] == '0'){
                            arra.push('<a  class="order" href="#">【接单】</a>');

                        }else if(row['problemstate'] == '1'){
                            arra.push('<a  class="solve" href="#">【处理】</a>');

                        }else if(row['problemstate'] == '2'){
                            arra.push('<a  class="edit" href="#">【完成】</a>');


                        }else {
                            arra.push('<a href="#">已处理</a>');

                        };
                        return arra.join("");

                    }
                }
            ]

        });


        if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


            $('#table').bootstrapTable("toggleView");
        }

        $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
        });



        $("#search").click("click", function() {// 绑定查询按扭
            $('#table').bootstrapTable('refresh');
        });

        $("#reset").click("click", function() {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $('#table').bootstrapTable('refresh');
        });

        $('#dateStart').datetimepicker({
            format: "YYYY-MM-DD"
        });
        $('#dateEnd').datetimepicker({
            format: "YYYY-MM-DD"
        });
    });



function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

function deleteAll(){

    jp.confirm('确认要删除巡检记录吗？', function(){
        jp.loading();
        jp.get("${ctx}/oa/oaDispose/deleteAll?ids=" + getIdSelections(), function(data){
            if(data.success){
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            }else{
                jp.error(data.msg);
            }
        })

    })
}
function edit(id){
    if(!id){
        id = getIdSelections();
    }
    jp.openDialog('解决方案', "${ctx}/oa/oaDispose/form?id=" + row.user.id,'800px', '500px');

}


</script>