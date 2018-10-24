<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>进出记录</title>
    <meta name="decorator" content="ani"/>
    <script type="text/javascript">
        var validateForm;
        function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                $("#inputForm").submit();
                return true;
            }

            return false;
        }
        $(document).ready(function() {
            //$("#name").focus();
            validateForm = $("#inputForm").validate({
                submitHandler: function(form){

                    form.submit();
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
            $('#entryTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });
            $('#departureTime').datetimepicker({
                format: "YYYY-MM-DD HH:mm:ss"
            });

        });
        //        $.validator.addMethod("compareDate",function(value,element){
        //            var entryTime = $("#entryTime").val();
        //            var departureTime = $("#departureTime").val();
        //            var reg = new RegExp('-','g');
        //            entryTime = entryTime.replace(reg,'/');//正则替换
        //            departureTime = departureTime.replace(reg,'/');
        //            entryTime = new Date(parseInt(Date.parse(entryTime),10));
        //            departureTime = new Date(parseInt(Date.parse(departureTime),10));
        //            if(entryTime>departureTime){
        //                return false;
        //            }else{
        //                return true;
        //            }
        //        },"<font color='#E47068'>离开日期必须大于进入日期</font>");

    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/oa/turnover"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <form:form id="inputForm" modelAttribute="turnover" action="${ctx}/oa/turnover/save" method="post" class="form-horizontal">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>单位名称：</label>
                            <div class="col-sm-10">
                                <form:input path="units" htmlEscape="false" maxlength="200" class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>人员名称：</label>
                            <div class="col-sm-10">
                                <form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>进入事由：</label>
                            <div class="col-sm-10">
                                <form:textarea path="reason" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>进入时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='entryTime'>
                                    <input type='text'  name="entryTime" class="form-control required"  value="<fmt:formatDate value="${turnover.entryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                                </div>
                                </p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><font color="red">*</font>离开时间：</label>
                            <div class="col-sm-10">
                                <p class="input-group">
                                <div class='input-group form_datetime' id='departureTime'>

                                    <input type='text'  name="departureTime" class="form-control equalTo  value="<fmt:formatDate value="${turnover.departureTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                                    <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                                </div>
                                </p>
                            </div>
                        </div>
                        <shiro:hasPermission name="oa:oaOutRecords:edit">
                            <div class="col-lg-3"></div>
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <label></label>

                                    <div>
                                        <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
                                    </div>
                                </div>
                            </div>
                        </shiro:hasPermission>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>