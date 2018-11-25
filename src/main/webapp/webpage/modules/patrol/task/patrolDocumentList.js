<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#downloadTable').bootstrapTable({
		 
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
               url: "${ctx}/monitor/patrolDocument/data",
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
                   	window.location = "${ctx}/monitor/scheduleJob/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该巡检任务记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/monitor/scheduleJob/delete?id="+row.id, function(data){
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
		        field: 'name',
		        title: '文件名称',
		        sortable: true
		       
		    },{
			    field: 'type',
			    title: '文件类型',
			    sortable: true
			}
			,
            {
                field: 'createDate',
                title: '生成时间',
                sortable: true
             }
			, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        // 'click .resume': function (e, value, row, index) {
    		        // 	resume(row.id);
    		        // },
    		        // 'click .stop': function (e, value, row, index) {
    		        // 	stop(row.id);
    		        // }
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [
                        '<a  href="'+row.documentUrls+'"><i class="fa fa-download"></i>下载 &nbsp;&nbsp;&nbsp;</a>',

                    ].join('');
    		    }
		    }]
	});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#downloadTable').bootstrapTable("toggleView");
		}
	  
	  $('#downloadTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#downloadTable').bootstrapTable('getSelections').length);
            $('#edit,#stop,#resume,#startNow,download').prop('disabled', $('#downloadTable').bootstrapTable('getSelections').length!=1);
        });
		  
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#downloadTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $('#downloadTable').bootstrapTable('refresh');
		});
    $('#createDate').datetimepicker({
        format: "YYYY-MM-DD"
    });
	});

  function getIdSelections() {
        return $.map($("#downloadTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

  //立即运行一次
  function startNow(){
	  jp.confirm('确定要立即运行一次该任务？', function(){
			jp.loading();
			jp.get("${ctx}/monitor/patrol/startNow?id=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})

		})
  	};
</script>