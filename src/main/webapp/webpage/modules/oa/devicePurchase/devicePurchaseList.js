<%@ page contentType ="text/html;charset=UTF-8"%>
 <script type="text/javascript">
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
            url: "${ctx}/oa/devicePurchase/data",
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
                if ($el.data("item") == "edit") {
                    edit(row.id);
                } else if ($el.data("item") == "delete") {
                    deleteAll(row.id);
                }
            },

            onClickRow: function (row, $el) {
            },
            columns: [{
                checkbox: true
            }, {
                field: 'devName',
                title: '设备名称',
                sortable: true,
                formatter: function (value, row, index) {
                    return "<a href='${ctx}/oa/devicePurchase/view?id=" + row.id + "'>" + value + "</a>";
                }
            }, {
                field: 'devSource',
                title: '设备来源',
                sortable: true
            }, {
                field: 'purTime',
                title: '采购时间',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return jp.dateFormat(value, "yyyy-MM-dd");
                    }
                }
            }, {
                field: 'purMoney',
                title: '采购金额',
                sortable: true
            }, {
                field: 'isflag',
                title: '是否审核',
                sortable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == '0') {
                        return '<font color="red">未审核</font>';
                    } else if (value == '1') {
                        return '<font color="green">已审核</font>';
                    } else {
                        return "-";
                    }
                }
            }, {
                field: 'audName',
                title: '审核人',
                sortable: true
            }, {
                field: 'isorder',
                title: '是否下单',
                sortable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == '0') {
                        return '<font color="red">未下单</font>';
                    } else if (value == '1') {
                        return '<font color="green">已下单</font>';
                    } else {
                        return "-";
                    }
                }
            }, {
                field: 'ordTime',
                title: '下单时间',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return jp.dateFormat(value, "yyyy-MM-dd");
                    }
                }
            }, {
                field: 'isarrival',
                title: '是否到货',
                sortable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == '0') {
                        return '<font color="red">未到货</font>';
                    } else if (value == '1') {
                        return '<font color="green">已到货</font>';
                    }
                }
            }, {
                field: 'arriTime',
                title: '到货时间',
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == null) {
                        return "-";
                    } else {
                        return jp.dateFormat(value, "yyyy-MM-dd");
                    }
                }
            },{
                field: 'purName',
                title: '采购人',
                sortable: true
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
            $('#order').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
            $('#arrival').prop('disabled', $('#table').bootstrapTable('getSelections').length != 1);
        });

        $("#btnImport").click(function() {
            jp.open({
                type: 1,
                area: [500, 300],
                title: "导入数据",
                content: $("#importBox").html(),
                btn: ['下载模板', '确定', '关闭'],
                btn1: function (index, layero) {
                    window.location = '${ctx}/oa/devicePurchase/import/template';
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

        $('#purTime').datetimepicker({
            format: "YYYY-MM-DD hh:mm"
        });


    });

//获取id
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

//获取是否审核
function getIsauditSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.isflag
    });
}

//获取是否下单
function getIsorderSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.isorder
    });
}

//获取是否到货
function getIsarrivalSelections() {
    return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.isarrival
    });
}

//删除
function deleteAll(id) {
    if(!id){
        id = getIdSelections();
    }
    if (getIsauditSelections()!=0) {
        layer.msg('已审核不能删除', {
            icon: 2,
            anim: 6,
            time: 2500}
        );
        return  ;
    }
    jp.confirm('确认要删除操作吗？', function () {
        jp.loading();
        jp.get("${ctx}/oa/devicePurchase/deleteAll?ids=" + id, function (data) {
            if (data.success) {
                $('#table').bootstrapTable('refresh');
                jp.success(data.msg);
            } else {
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
    if (getIsauditSelections() == 0 && getIsorderSelections() == 0 && getIsarrivalSelections() == 0) {
       window.location = "${ctx}/oa/devicePurchase/form?id=" + id;
    } else {
        layer.msg('请选择未审核，未下单或未到货设备！', {
                icon: 2,
                anim: 6,
                time: 3000
            }
        );
    }
}

//查看
function view() {
    window.location = "${ctx}/oa/devicePurchase/view?id=" + getIdSelections();
}

//审核
function audit() {
    if (getIsauditSelections() == 0) {
        jp.confirm('确认要审核操作吗？', function () {
            jp.loading();
            window.location = "${ctx}/oa/devicePurchase/audit?id=" + getIdSelections();
        })
    } else {
        layer.msg('错误提示：已审核的数据', {
                icon: 2,
                anim: 6,
                time: 3000
            }
        );
    }
}

//下单
function order() {
    if (getIsorderSelections() == 0 && getIsauditSelections() == 1) {
        jp.confirm('确认要下单操作吗？', function () {
            jp.loading();
            window.location = "${ctx}/oa/devicePurchase/order?id=" + getIdSelections();
        })
    } else {
        layer.msg('错误提示：未审核或者已下单', {
                icon: 2,
                anim: 6,
                time: 3000
            }
        );
    }
}

//到货
function arrival() {
    if (getIsarrivalSelections() == 0 && getIsorderSelections() == 1 && getIsauditSelections() == 1) {
        jp.confirm('确认要到货操作吗？', function () {
            jp.loading();
            window.location = "${ctx}/oa/devicePurchase/arrival?id=" + getIdSelections();
        })
    } else {
        layer.msg('错误提示：未下单或者已到货', {
                icon: 2,
                anim: 6,
                time: 3000
            }
        );
    }
}

</script>