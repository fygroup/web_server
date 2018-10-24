<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#resourceExceptionTable').bootstrapTable({
		 
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
               url: "${ctx}/exception/exception/resourceException/data",
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
                   	window.location = "${ctx}/exception/exception/resourceException/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该异常告警记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/exception/exception/resourceException/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#resourceExceptionTable').bootstrapTable('refresh');
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
		        field: 'indicatorName',
		        title: '指标名称',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/exception/exception/resourceException/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'exceptionSource',
		        title: '异常来源',
		        sortable: true
		       
		    }
		   ,{
			   field: 'updateDate',
			   title: '更新时间',
			   sortable: true,
			   formatter:function(value, row , index){
					if(row.updateDate==null){
						return "-";
					}else{
						return jp.dateFormat(value,"YYYY-MM-dd hh:mm:ss");
					}

			   }

		   }
			,{
		        field: 'totalQuantity',
		        title: '累计次数',
		        sortable: true
		       
		    }
			,{
		        field: 'exceptionClass',
		        title: '异常等级',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, value, "-");
		        }
		       
		    }
			/*,{
		        field: 'currentValue',
		        title: '当前指标值',
		        sortable: true
		       
		    }*/
			,{
		        field: 'currentStatus',
		        title: '当前状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('exception_current_status'))}, value, "-");
		        }
		       
		    }
			/*,{
		        field: 'firstTriggerValue',
		        title: '第一次产生指标值',
		        sortable: true
		       
		    }*/
			/*,{
		        field: 'firstTriggerClass',
		        title: '第一次产生级别',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('exception_class'))}, value, "-");
		        }
		       
		    }*/
			,{
		        field: 'firstTriggerTime',
		        title: '第一次产生时间',
		        sortable: true,
			    formatter:function(value, row , index){
				   return jp.dateFormat(value,"YYYY-MM-dd hh:mm:ss");
			    }

		    }
			/*,{
		        field: 'resourceIndicator.name',
		        title: '资源指标id',
		        sortable: true
		       
		    }
			,{
		        field: 'lastTriggerTime',
		        title: '最后一次产生异常的时间',
		        sortable: true,
			   formatter:function(value, row , index){
				   return jp.dateFormat(value,"YYYY-MM-dd hh:mm:ss");
			   }
		       
		    }
			,{
		        field: 'resourceType.name',
		        title: '资源类型',
		        sortable: true
		       
		    }*/
			,{
		        field: 'eventType',
		        title: '指标事件类型',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('indicator_event_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'confirmStatus',
		        title: '确认状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('exception_confirm_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'confirmUser.name',
		        title: '确认人',
		        sortable: true
		       
		    }
				   /*,{
		        field: 'area',
		        title: '地域',
		        sortable: true
		       
		    }
			,{
		        field: 'office.name',
		        title: '归属部门',
		        sortable: true
		       
		    }
			,{
		        field: 'indicatorItem.name',
		        title: '资源列表项id（冗余）',
		        sortable: true
		       
		    }*/

				  , {
					field: 'id',
					title: '编辑',
					sortable: true
                       ,formatter:function(value, row , index){
						   var result="";

                           if(row.currentStatus==0){
                               result+="<a  href='${ctx}/exception/exception/resourceException/manualRecovery?id="+row.id+"' title='手动恢复' ><i class='fa fa-undo btn-lg' style=''></i></a>&nbsp;&nbsp;";

                               // result+="<a href='${ctx}/exception/exception/resourceException/manualRecovery?id="+row.id+"'>手动恢复</a>&nbsp;&nbsp;&nbsp;";
                           }

                           if(row.confirmStatus==0){
                               result+="<a  href='${ctx}/exception/exception/resourceException/confirmException?id="+row.id+"' title='异常确认' ><i class='glyphicon glyphicon-saved btn-lg' style=''></i></a>";

                               //  result+="<a href='${ctx}/exception/exception/resourceException/confirmException?id="+row.id+"'>异常确认</a>";
                           }


                           return result;
                       }
					}
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#resourceExceptionTable').bootstrapTable("toggleView");
		}
	  
	  $('#resourceExceptionTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#resourceExceptionTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#resourceExceptionTable').bootstrapTable('getSelections').length!=1);
        });
		  
		/*$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/exception/exception/resourceException/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});*/
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#resourceExceptionTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#resourceExceptionTable').bootstrapTable('refresh');
		});
		
		$('#beginFirstTriggerTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endFirstTriggerTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#lastTriggerTime').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
	});
		
  function getIdSelections() {
        return $.map($("#resourceExceptionTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该异常告警记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/exception/exception/resourceException/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#resourceExceptionTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/exception/exception/resourceException/form?id=" + getIdSelections();
  }
  
</script>