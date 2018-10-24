<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var resourceTypeTreeTable=null;
		$(document).ready(function() {
			resourceTypeTreeTable=$('#resourceTypeTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/resourcetype/resourceType/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#resourceTypeTreeTableTpl").html();

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function(resourceTypeTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                resourceTypeTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function(resourceTypeTreeTable, id) {   
		            },  
		            afterExpand : function(resourceTypeTreeTable, id) {  
		            },  
		            beforeClose : function(resourceTypeTreeTable, id) {    
		            	
		            }  
		        });
		        
		        resourceTypeTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		});
		
		function del(con,id){  
			jp.confirm('确认要删除资源类型吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/resourcetype/resourceType/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			resourceTypeTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			resourceTypeTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="resourceTypeTreeTableTpl">
			<td><a  href="${ctx}/resourcetype/resourceType/form?id={{d.row.id}}">
				{{d.row.name === undefined ? "": d.row.name}}
			</a></td>
			<td>
				{{d.row.sort === undefined ? "": d.row.sort}}
			</td>
			<td>
				{{d.row.img === undefined ? "": "<i class='"+d.row.img+"'></i>"}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="resourcetype:resourceType:view">
						<li><a href="${ctx}/resourcetype/resourceType/form?id={{d.row.id}}"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="resourcetype:resourceType:edit">
		   				<li><a href="${ctx}/resourcetype/resourceType/form?id={{d.row.id}}"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="resourcetype:resourceType:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="resourcetype:resourceType:add">
						<li><a href="${ctx}/resourcetype/resourceType/form?parent.id={{d.row.id}}"><i class="fa fa-plus"></i> 添加下级资源类型</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>