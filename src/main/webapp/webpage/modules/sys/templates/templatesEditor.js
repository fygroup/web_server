<%@ page contentType="text/html;charset=UTF-8" %>
<script>

	  function add(){
		  jp.openDialog('新建模板', '${ctx}/sys/templates/addTemplate','800px', '500px')
	  }

	  function edit(id){
		  if(!id){
				id = getIdSelections();
			}
		  jp.openDialog('编辑角色', "${ctx}/sys/role/form?id=" + id,'800px', '500px')

	  }
		function del(con,id){
			jp.confirm('确认要删除机构吗？', function(){
				jp.loading();
				$.get("", function(data){
					if(data.success){
						$treeTable.del(id);
						jp.success(data.msg);
					}else{
						jp.error(data.msg);
					}
				})

			});
		}

		//指标一览信息

	var data;
	var code="1";
	var type="up";
	var id="";
	var name="";
	$(document).ready(function () {
		$.ajax({
			type : 'POST',
			async : true,
			url :  "${ctx}/resource/resource/dataSelect",
			timeout:50*1000,    //超时时间 10s
			success : function(result) {
				data=result;
				$("#optionView").html(data["select"]);
				$(".selectbtn").show();
			},
			error:function(){
				//alert("接口列表失败dfgdfgdfgfd");
			},
			complete:function(XMLHttpRequest,status){
				if(status=='timeout'){
					alert("请求超时，请检查网络后重试");
				}
			}
		});
	})

	function selectThis(obj) {
		var selectedId=$(obj).next().next().val();
		var selectedName=$(obj).next().val();
		if(checkSelected(selectedId)){ //选中
			$(obj).attr("data-select",false);
			$(obj).attr('src','${ctxStatic}/images/icon/unselected_icon.png');
			delSelected(selectedId,selectedName);
		}else{
			$(obj).attr('src','${ctxStatic}/images/icon/selected_icon.png');
			$(obj).attr("data-select",true);
			id=id+","+selectedId;
			name=name+","+selectedName;
		}
		initNameView();
	}
	function selectOption() {
		code=$('#resourceTypeSelect  option:selected').val();
		initSelect(null);
	}
	function initSelect(t) {
		if(t!=null){
			type=t;
		}
		if(data!=null){
			$("#innerUl").html(data[code]);
			$(".st_tree").SimpleTree({
				click:function(a){
				}
			});
			$(".selectImg").attr('src','${ctxStatic}/images/icon/unselected_icon.png');
		}else{
			$("#innerUl").html("");
		}
		initImg();
	}

	function checkSelected(selectedId){
		var item=id.split(",");
		for(var i=0;i<item.length;i++){
			if(item[i]==selectedId){
				return true;
			}
		}
		return false;
	}

	function delSelected(selectedId,selectedName){
		var item=id.split(",");
		id="";
		for(var i=0;i<item.length;i++){
			if(item[i]!=selectedId){
				id=id+item[i]+",";
			}
		}
		var nameItem=name.split(",");
		name="";
		for(var i=0;i<nameItem.length;i++){
			if(nameItem[i]!=selectedName){
				name=name+nameItem[i]+",";
			}
		}
	}

	function initNameView(){
		$("#selectedView").html("");
		var nameItem=name.split(",");
		var idItem = id.split(",");
		var selectedIdList = "";
		var html="";
		for(var i=0;i<nameItem.length;i++){
			if(nameItem[i]!=""){
				html=html+  "<tr> <td>"+nameItem[i]+"<input value='"+ idItem[i] +"' name='"+ idItem[i] +"' hidden = false></td></tr>";
				selectedIdList = selectedIdList +nameItem[i]+",";
			}
		}
		$("#selectedView").html(html);
		$("#selectedIdList").val(selectedIdList);
	}

	function initImg(){
		$(".selectImg").each(function(){
			var thisId=$(this).next().next().val();
			var item=id.split(",");
			for(var i=0;i<item.length;i++){
				if(item[i]==thisId){
					$(this).attr('src','${ctxStatic}/images/icon/selected_icon.png');
					break;
				}
			}
		});
	}
	function indicators_list(){
        jp.open({
            type: 2,
            area: ['70%', '500px'],
            title:"编辑",
            content: "${ctx}/sys/templates/topoEditor",
            btn: ['确定', '关闭'],
            yes: function(index, layero){
                var selectedIdList = $("#selectedIdList").val();
                //var selectedViewForm =top.$("#selectedForm").find("#selectedIdList").val();
                console.log(selectedIdList);
                $.ajax({
                    url:"",
                    type:'get',
                    async:false,
                    data:{},
                    timeout:100*1000,    //超时时间 100s
                    dataType:'json',    //返回的数据格式：
                    success:function(data,textStatus,jqXHR){

                    },
                    error:function(xhr,textStatus){

                    }
                });

                jp.close(index);//关闭对话框。
            },
            cancel: function(index){
            }
        });
	}
/*
$("#indicators_list").click(function(){
alert("666");
jp.open({
   type: 2,
   area: ['70%', '500px'],
   title:"编辑",
   content: "${ctx}/sys/templates/topoEditor",
   btn: ['确定', '关闭'],
   yes: function(index, layero){
       var selectedIdList = $("#selectedIdList").val();
       //var selectedViewForm =top.$("#selectedForm").find("#selectedIdList").val();
       console.log(selectedIdList);
       $.ajax({
           url:"",
           type:'get',
           async:false,
           data:{},
           timeout:100*1000,    //超时时间 100s
           dataType:'json',    //返回的数据格式：
           success:function(data,textStatus,jqXHR){

           },
           error:function(xhr,textStatus){

           }
       });

       jp.close(index);//关闭对话框。
   },
   cancel: function(index){
   }
});
})*/

		//指标一览信息


</script>