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
            url: "${ctx}/oa/deviceList/data",
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
                if ($el.data("item") == "edit") {
                    edit(row.id);
                } else if ($el.data("item") == "delete") {
                    deleteAll(row.id);
                }
            },

            onClickRow: function(row, $el){},
            columns: [{
                checkbox: true
            }, {
                field: 'devName',
                title: '设备名称',
                sortable: true,
                formatter:function(value, row , index){
                    return "<a href='${ctx}/oa/deviceList/view?id="+row.id+"'>"+value+"</a>";
                }
            },  {
                field: 'devMoney',
                title: '价值',
                sortable: true
            }, {
                field: 'devSource',
                title: '设备来源',
                sortable: true
            },  {
                field: 'state',
                title: '状态',
                sortable: true,
                align: 'center',
                formatter:function(value, row , index){
                    if(value == '0'){
                        return '<font color="green">正常</font>';
                    }else if(value == '1') {
                        return '<font color="red">维修中</font>';
                    }else {
                        return "-";
                    }
                }
            },  {
                field: 'isUse',
                title: '是否使用',
                sortable: true,
                align: 'center',
                formatter:function(value, row , index){
                    if(value == '0'){
                        return '<font color="green">未使用</font>';
                    }else if(value == '1') {
                        return '<font color="red">使用</font>';
                    }else {
                        return "-";
                    }
                }
            },{
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
                    'click .resume': function (e, value, row, index) {
                        resume(row.id);
                    },
                    'click .stop': function (e, value, row, index) {
                        stop(row.id);
                    }
                },
                formatter:  function operateFormatter(value, row, index) {
                    return [
                        <shiro:hasPermission name="oa:deviceList:resume">
                            '<a href="#" class="resume" title="正常" >【正常】</a>',
                        </shiro:hasPermission>
                        <shiro:hasPermission name="oa:deviceList:stop">
                            '<a href="#" class="stop" title="维修">【维修】</a>'
                        </shiro:hasPermission>
                    ].join('');
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
        });

        $("#btnImport").click(function () {
            jp.open({
                type: 1,
                area: [500, 300],
                title: "导入数据",
                content: $("#importBox").html(),
                btn: ['下载模板', '确定', '关闭'],
                btn1: function (index, layero) {
                    window.location = '${ctx}/oa/deviceList/import/template';
                },
                btn2: function (index, layero) {
                    var inputForm = top.$("#importForm");
                    var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
                    inputForm.attr("target", top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
                    inputForm.onsubmit = function () {
                        jp.info('  正在导入，请稍等...');
                    }
                    inputForm.submit();
                    jp.close(index);
                },
                btn3: function (index) {
                    jp.close(index);
                }
            });
        });

        $("#search").click("click", function () {// 绑定查询按扭
            $('#table').bootstrapTable('refresh');
        });

        $("#reset").click("click", function () {// 绑定查询按扭
            $("#searchForm  input").val("");
            $("#searchForm  select").val("");
            $('#table').bootstrapTable('refresh');
        });

        $('#appTime').datetimepicker({
            format: "YYYY-MM-DD"
        });

    });

//获取id
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
};

//获取是否审核
function getIsauditSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.isaudit
    });
};

//获取是否交付
function getIsdeliverySelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.isdelivery
    });
};

//删除
function deleteAll(id) {
    if(!id){
        id = getIdSelections();
    }
    jp.confirm('确认要删除操作吗？', function () {
        jp.loading();
        jp.get("${ctx}/oa/deviceList/deleteAll?ids=" + id, function (data) {
            if (data.success) {
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
                jp.error(data.msg);
            }
        })
    })
};

//修改
function edit(id) {
    if(!id){
        id = getIdSelections();
    }
    if (getIsauditSelections() != 1 && getIsdeliverySelections() != 1) {
        window.location = "${ctx}/oa/deviceList/form?id=" + id;
    } else {
        layer.msg('请选择未审核或者未交付的数据', {
                icon: 2,
                anim: 6,
                time: 2500
            }
        );
    }
};

//查看
function view() {
    window.location = "${ctx}/oa/deviceList/view?id=" + getIdSelections();
};

//维修
function stop(id) {
    if (!id) {
        id = getIdSelections();
    }
    jp.confirm('确定要维修该设备？', function () {
        jp.loading();
        jp.get("${ctx}/oa/deviceList/stop?id=" + id, function (data) {
            if (data.success) {
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
                jp.error(data.msg);
            }
        })
    })
};

//正常
function resume(id) {
    if (!id) {
        id = getIdSelections();
    }
    jp.confirm('确定要正常该设备？', function () {
        jp.loading();
        jp.get("${ctx}/oa/deviceList/resume?id=" + id, function (data) {
            if (data.success) {
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
                jp.error(data.msg);
            }
        })
    })
};


</script>