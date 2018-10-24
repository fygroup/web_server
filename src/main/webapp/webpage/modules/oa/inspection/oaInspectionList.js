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
               url: "${ctx}/oa/oaInspection/data",
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
                   	window.location = "${ctx}/oa/oaInspection/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该通知记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/oa/oaInspection/delete?id="+row.id, function(data){
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
		        title: '检查人员',
		        sortable: true
		    }
		    ,{
		        field: 'date',
		        title: '巡检日期',
		        sortable: true
		    }, {
                field: 'isflag',
                title: '是否正常',
                formatter:function(value, row , index){
                   return value=="0" ? '<font color="red">不正常</font>':'<font color="green">正常</font>';
                }
            },{
               field: 'pagename',
               title: '内容',
               sortable: true
            },{
		        field: 'remark',
		        title: '备注',
		        sortable: true
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
		  $('#table').bootstrapTable("toggleView");
		}
	  
	  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
            $('#exportWord').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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

        function getwordcontents() {//获取导出的word 内容
          return $.map($("#table").bootstrapTable('getSelections'), function (row) {
              return row.contents;
        });
    }

     function getwordname() {//获取导出的word名称
         return $.map($("#table").bootstrapTable('getSelections'), function (row) {
             return row.pagename;
        });
     }

    function deleteAll(){
        jp.confirm('确认要删除巡检记录吗？', function(){
            jp.loading();
            jp.get("${ctx}/oa/oaInspection/deleteAll?ids=" + getIdSelections(), function(data){
                if(data.success){
                    $('#table').bootstrapTable('refresh');
                    jp.success(data.msg);
                }else{
                    jp.error(data.msg);
                }
            })

        })
    }

  function edit(){
	  window.location = "${ctx}/oa/oaInspection/form?id=" + getIdSelections();
  }

    {/*window.onload=function()*/}
    {/*{*/}
        {/*document.getElementById('export_word').innerHTML='111';*/}
    {/*}*/}


  function methodName(){
    var a = getwordcontents();
    var dom = document.getElementById("export_word");
      dom["innerHTML"in dom?"innerHTML":"htmlContent"]=a;
      $("#export_word").wordExport(getwordname());
        return false;

}


</script>