<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务代理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		var validateForm;
		var $table; // 父页面table表格id
		var $topIndex;//弹出窗口的 index
		function doSubmit(table, index){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $table = table;
			  $topIndex = index;
			  jp.loading();
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}

		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					jp.post("${ctx}/test/onetomanydialog/testDataMainDialog/save",$('#inputForm').serialize(),function(data){
						if(data.success){
	                    	$table.bootstrapTable('refresh');
	                    	jp.success(data.msg);
	                    	jp.close($topIndex);//关闭dialog

	                    }else{
            	  			jp.error(data.msg);
	                    }
					})
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
	        $('#inDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="testDataMainDialog" action="${ctx}/test/onetomanydialog/testDataMainDialog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属用户：</label></td>
					<td class="width-35">
						<sys:userselect id="tuser" name="tuser.id" value="${testDataMainDialog.tuser.id}" labelName="tuser.name" labelValue="${testDataMainDialog.tuser.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${testDataMainDialog.office.id}" labelName="office.name" labelValue="${testDataMainDialog.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属区域：</label></td>
					<td class="width-35">
						<sys:treeselect id="area" name="area.id" value="${testDataMainDialog.area.id}" labelName="area.name" labelValue="${testDataMainDialog.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>性别：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>加入日期：</label></td>
					<td class="width-35">
						<p class="input-group">

			            </p>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">火车票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">飞机票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">汽车票：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild11List', testDataChild11RowIdx, testDataChild11Tpl);testDataChild11RowIdx = testDataChild11RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th><font color="red">*</font>出发时间</th>
						<th><font color="red">*</font>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild11List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild11Tpl">//<!--
				<tr id="testDataChild11List{{idx}}">
					<td class="hide">
						<input id="testDataChild11List{{idx}}_id" name="testDataChild11List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild11List{{idx}}_delFlag" name="testDataChild11List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild11List{{idx}}_startArea" name="testDataChild11List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild11List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild11List{{idx}}_endArea" name="testDataChild11List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild11List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild11List{{idx}}_starttime">
		                    <input type='text'  name="testDataChild11List[{{idx}}].starttime" class="form-control required"  value="{{row.starttime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild11List{{idx}}_price" name="testDataChild11List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<textarea id="testDataChild11List{{idx}}_remarks" name="testDataChild11List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild11List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild11RowIdx = 0, testDataChild11Tpl = $("#testDataChild11Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMainDialog.testDataChild11List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild11List', testDataChild11RowIdx, testDataChild11Tpl, data[i]);
						testDataChild11RowIdx = testDataChild11RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild22List', testDataChild22RowIdx, testDataChild22Tpl);testDataChild22RowIdx = testDataChild22RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th><font color="red">*</font>出发时间</th>
						<th><font color="red">*</font>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild22List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild22Tpl">//<!--
				<tr id="testDataChild22List{{idx}}">
					<td class="hide">
						<input id="testDataChild22List{{idx}}_id" name="testDataChild22List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild22List{{idx}}_delFlag" name="testDataChild22List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild22List{{idx}}_startArea" name="testDataChild22List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild22List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild22List{{idx}}_endArea" name="testDataChild22List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild22List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild22List{{idx}}_startTime">
		                    <input type='text'  name="testDataChild22List[{{idx}}].startTime" class="form-control required"  value="{{row.startTime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild22List{{idx}}_price" name="testDataChild22List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<textarea id="testDataChild22List{{idx}}_remarks" name="testDataChild22List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild22List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild22RowIdx = 0, testDataChild22Tpl = $("#testDataChild22Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMainDialog.testDataChild22List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild22List', testDataChild22RowIdx, testDataChild22Tpl, data[i]);
						testDataChild22RowIdx = testDataChild22RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild33List', testDataChild33RowIdx, testDataChild33Tpl);testDataChild33RowIdx = testDataChild33RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th>代理价格</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild33List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild33Tpl">//<!--
				<tr id="testDataChild33List{{idx}}">
					<td class="hide">
						<input id="testDataChild33List{{idx}}_id" name="testDataChild33List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild33List{{idx}}_delFlag" name="testDataChild33List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild33List{{idx}}_startArea" name="testDataChild33List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild33List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild33List{{idx}}_endArea" name="testDataChild33List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild33List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChild33List{{idx}}_price" name="testDataChild33List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control "/>
					</td>
					
					
					<td>
						<textarea id="testDataChild33List{{idx}}_remarks" name="testDataChild33List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild33List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild33RowIdx = 0, testDataChild33Tpl = $("#testDataChild33Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMainDialog.testDataChild33List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild33List', testDataChild33RowIdx, testDataChild33Tpl, data[i]);
						testDataChild33RowIdx = testDataChild33RowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>