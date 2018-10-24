<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#resourceBaseInfoTable').bootstrapTable({
		 
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
               url: "${ctx}/resourcebaseinfo/resourceBaseInfo/data",
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
                   	window.location = "${ctx}/resourcebaseinfo/resourceBaseInfo/form?id=" + row.id;
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该资源访问信息记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/resourcebaseinfo/resourceBaseInfo/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#resourceBaseInfoTable').bootstrapTable('refresh');
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
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/resourcebaseinfo/resourceBaseInfo/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    }
			,{
		        field: 'port',
		        title: '端口',
		        sortable: true
		       
		    }
			,{
		        field: 'rdcommunity',
		        title: '读共同体',
		        sortable: true
		       
		    }
			,{
		        field: 'delay',
		        title: '超时时间',
		        sortable: true
		       
		    }
			,{
		        field: 'repeatnum',
		        title: '超时次数',
		        sortable: true
		       
		    }
			,{
		        field: 'userName',
		        title: '用户名',
		        sortable: true
		       
		    }
			,{
		        field: 'securityLeve',
		        title: '安全级别',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList(''))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'authenticationProtocol',
		        title: '认证协议',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList(''))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'authenticationPassword',
		        title: '认证密码',
		        sortable: true
		       
		    }
			,{
		        field: 'encryptionProtocol',
		        title: '加密协议',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList(''))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'encryptionPassword',
		        title: '加密密码',
		        sortable: true
		       
		    }
			,{
		        field: 'accessConfigType',
		        title: '访问类型',
		        sortable: true
		       
		    }
			,{
		        field: 'accessConfigPort',
		        title: '访问端口',
		        sortable: true
		       
		    }
			,{
		        field: 'accessConfigUserName',
		        title: '访问用户名',
		        sortable: true
		       
		    }
			,{
		        field: 'accessConfigPassword',
		        title: '访问密码',
		        sortable: true
		       
		    }
			,{
		        field: 'privilegedModeCommand',
		        title: ' 特权模式口令',
		        sortable: true
		       
		    }
			,{
		        field: 'privilegedModePassword',
		        title: '特权模式密码',
		        sortable: true
		       
		    }
			,{
		        field: 'commandPrompt',
		        title: '命令提示符',
		        sortable: true
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#resourceBaseInfoTable').bootstrapTable("toggleView");
		}
	  
	  $('#resourceBaseInfoTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#resourceBaseInfoTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#resourceBaseInfoTable').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/resourcebaseinfo/resourceBaseInfo/import/template';
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
		});
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#resourceBaseInfoTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#resourceBaseInfoTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#resourceBaseInfoTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该资源访问信息记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/resourcebaseinfo/resourceBaseInfo/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#resourceBaseInfoTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
	  window.location = "${ctx}/resourcebaseinfo/resourceBaseInfo/form?id=" + getIdSelections();
  }
  
</script>