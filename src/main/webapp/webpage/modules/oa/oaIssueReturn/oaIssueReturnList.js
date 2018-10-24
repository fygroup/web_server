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
            pageNumber: 1,
            //每页的记录行数（*）
            pageSize: 10,
            //可供选择的每页的行数（*）
            pageList: [10, 25, 50, 100],
            //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
            url: "${ctx}/oa/oaIssueReturn/data",
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
                    window.location = "${ctx}/oa/oaIssueReturn/form?id=" + row.id;
                } else if ($el.data("item") == "delete") {
                    jp.confirm('确认要删除该进出记录吗？', function () {
                        jp.loading();
                        jp.get("${ctx}/oa/oaIssueReturn/delete?id=" + row.id, function (data) {
                            if (data.success) {
                                $('#table').bootstrapTable('refresh');
                                jp.success(data.msg);
                            } else {
                                jp.error(data.msg);
                            }
                        })

                    });

                }
            },

            onClickRow: function (row, $el) {
            },
            columns: [{
                checkbox: true
            }
                , {
                    field: 'name',
                    title: '申报人',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value != null) {
                            return row.name;
                        } else {
                            return row.applicant;
                        }
                    }
                }, {
                    field: 'place',
                    title: '申报地点',
                    sortable: true
                }
                , {
                    field: 'detailplace',
                    title: '详细地址',
                    sortable: true
                }
                , {
                    field: 'description',
                    title: '问题描述',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == null) {
                            return "<a href='${ctx}/oa/oaIssueReturn/exceptionForm?exceptionId=" + row.exception.id + "'>" + row.exception.indicatorName + "</a>";
                        } else {
                            return row.description;
                        }
                    }

                }, {
                    field: 'problemstate',
                    title: '问题状态',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return '<font color="red">' + jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_problemstate'))}, value, "创建") + '</font>';
                        } else if (value == '1') {
                            return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_problemstate'))}, value, "派单") + '</font>';
                        } else if (value == '2') {
                            return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_problemstate'))}, value, "处理中") + '</font>';
                        } else if (value == '3') {
                            return '<font color="green">' + jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_problemstate'))}, value, "完成") + '</font>';
                        } else {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_problemstate'))}, value, "-");

                        }
                    }

                }, {
                    field: 'exception',
                    title: '等级',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == null) {
                            if (row.problemClass == '3') {
                                return '<font color="red">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.problemClass, "-") + '</font>';
                            } else if (row.problemClass == '2') {
                                return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.problemClass, "-") + '</font>';
                            } else if (row.problemClass == '1') {
                                return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.problemClass, "-") + '</font>';
                            } else if (row.problemClass == '0') {
                                return '<font color="green">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.problemClass, "-") + '</font>';
                            }

                        } else {
                            if (row.exception.exceptionClass == '3') {
                                return '<font color="red">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.exception.exceptionClass, "-") + '</font>';
                            } else if (row.exception.exceptionClass == '2') {
                                return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.exception.exceptionClass, "-") + '</font>';
                            } else if (row.exception.exceptionClass == '1') {
                                return '<font color="orange">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.exception.exceptionClass, "-") + '</font>';
                            } else if (row.exception.exceptionClass == '0') {
                                return '<font color="green">' + jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, row.exception.exceptionClass, "-") + '</font>';
                            }

                        }
                    }

                }
                , {
                    field: 'ediid',
                    title: '申报方式',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "电话");
                        } else if (value == '1') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "现场");
                        } else if (value == '2') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "巡检");
                        } else if (value == '3') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "系统");
                        } else if (value == '4') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "其它");
                        } else {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_ediid'))}, value, "-");
                        }

                    }

                }, {
                    field: 'date',
                    title: '申报时间',
                    sortable: true
                }, {
                    field: 'declaretype',
                    title: '申报类型',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '0') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_declaretype'))}, value, "软件");
                        } else if (value == '1') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_declaretype'))}, value, "硬件");
                        } else if (value == '2') {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_declaretype'))}, value, "网络");
                        } else {
                            return jp.getDictLabel(${fns:toJson(fns:getDictList('oa_issueReturn_declaretype'))}, value, "-");
                        }
                    }
                },{
                    field: 'opsname',
                    title: '处理人',
                    sortable: true
                }
            ]

        });


        if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端


        $('#table').bootstrapTable("toggleView");
    }

        $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $('#deleteAll,#sends').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
        $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
    });

        $("#search").click("click", function() {// 绑定查询按扭
        $('#table').bootstrapTable('refresh');
    });

        $("#reset").click("click", function() {// 绑定查询按扭
        $("#searchForm  input").val("");
        $("#searchForm  select").val("");
        $("#searchForm  .select-item").html("");
        $('#table').bootstrapTable('refresh');
    });

        $('#dateStart').datetimepicker({
        format: "YYYY-MM-DD"
    });
        $('#dateEnd').datetimepicker({
        format: "YYYY-MM-DD"
    });
    });

        /*获取所选记录的id*/
        function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
    }
        /*获取问题状态*/
        function getProblemstateSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.problemstate
    });
    }

        /*获取问题申报方式*/
        function getEdiidSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
        return row.ediid
    });
    }


        /*删除*/
        function deleteAll(){
        if (getProblemstateSelections()!=0) {
        layer.msg('已进行的工单不能删除', {
        icon: 2,
        anim: 6,
        time: 2500}
        );
        return;
        }
        jp.confirm('确认要删除问题申报吗？', function () {
        jp.loading();
        jp.get("${ctx}/oa/oaIssueReturn/deleteAll?ids=" + getIdSelections(), function (data) {
        if (data.success) {
        $('#table').bootstrapTable('refresh');
        jp.success(data.msg);
    } else {
        jp.error(data.msg);
    }
    })

    })
    }



        /*批量派单*/
        function sends(){
        if (getProblemstateSelections()!=0) {
        layer.msg('已进行的工单不能再次派单', {
        icon: 2,
        anim: 6,
        time: 2500}
        );
        return;
        }

        jp.confirm('确认要派单吗？', function () {
            jp.loading();
            jp.get("${ctx}/oa/oaIssueReturn/sends?ids=" + getIdSelections(), function (data) {
                if (data.success) {
                    $('#table').bootstrapTable('refresh');
                    jp.success(data.msg);
                } else {
                    jp.error(data.msg);
                }
            })

        })
    }


        /*修改*/
        function edit(){
        if (getProblemstateSelections()==0) {
        if(getEdiidSelections()!=3){
        window.location = "${ctx}/oa/oaIssueReturn/form?id=" + getIdSelections();
    }else{
        layer.msg('系统工单不能修改', {
        icon: 2,
        anim: 6,
        time: 2500}
        );
    }

    }else{
        layer.msg('请选择未进行的工单修改', {
        icon: 2,
        anim: 6,
        time: 2500}
        );
    }
    }


    </script>