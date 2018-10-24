<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    $(document).ready(function () {
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
            pageNumber: 1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/oa/mailList/data",
            //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
            //queryParamsType:'',
            ////查询参数,每次调用是会带上这个参数，可自定义
            queryParams: function (params) {
                var searchParam = $("#searchForm").serializeJSON();
                searchParam.pageNo = params.limit === undefined ? "1" : params.offset / params.limit + 1;
                searchParam.pageSize = params.limit === undefined ? -1 : params.limit;
                searchParam.orderBy = params.sort === undefined ? "" : params.sort + " " + params.order;
                return searchParam;
            },
            //分页方式：client客户端分页，server服务端分页（*）
            sidePagination: "server",
            contextMenuTrigger: "right",//pc端 按右键弹出菜单
            contextMenuTriggerMobile: "press",//手机端 弹出菜单，click：单击， press：长按。
            contextMenu: '#context-menu',
            onContextMenuItem: function (row, $el) {
                if($el.data("item") == "edit"){
                    edit(row.id);
                } else if($el.data("item") == "remove"){
                    deleteAll(row.id);
                } else if($el.data("item") == "view"){
                    view(row.id);
                }

            },

            onClickRow: function (row, $el) {
            },
            columns: [{
                checkbox: true
            }, {
                field: 'name',
                title: '姓名',
                sortable: true
            }, {
                field: 'sex',
                title: '性别',
                align: 'center',
                sortable: true,
                formatter:function(value, row , index){
                    return jp.getDictLabel(${fns:toJson(fns:getDictList('sex'))}, value, "-");
                }
            }, {
                field: 'phone',
                title: '电话',
                align: 'center',
                sortable: true
            }, {
                field: 'orgName',
                title: '部门',
                sortable: true
            }, {
                field: 'address',
                title: '地址',
                sortable: true
            }, {
                field: 'establishTime',
                title: '创建时间',
                align: 'center',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return jp.dateFormat(value, "yyyy-MM-dd hh:mm");
                    }
                }
            }
            ]
        });


        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端
            $('#table').bootstrapTable("toggleView");
        }

        $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' + 'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', !$('#table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
            $('#view').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
            $('#audit').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
            $('#delivery').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
        });

        $("#search").click("click", function () {// 绑定查询按扭
            $('#table').bootstrapTable('refresh');
        });

        $("#reset").click("click", function () {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $('#table').bootstrapTable('refresh');
        });
    });

//获取id
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//删除
function deleteAll(ids) {
    if(!ids){
        ids =  getIdSelections();
    }
    jp.confirm('确认要删除选中数据吗？',  function(){
        jp.loading();
        $.get("${ctx}/oa/mailList/deleteAll?ids=" +ids, function(data){
            if(data.success){
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            }else{
                jp.error(data.msg);
            }
        })
    })

}

//修改
function edit(id) {
    if(!id){
        id = getIdSelections();
    }
    jp.openDialog('编辑用户', "${ctx}/oa/mailList/form?id=" + id,'800px', '680px');
}

//查看
function view(id) {
    // window.location = "${ctx}/oa/mailList/view?id=" + getIdSelections();
    if(!id){
        id = getIdSelections();
    }
    jp.openDialog('查看用户', "${ctx}/oa/mailList/view?id=" + id,'800px', '680px');
}

</script>