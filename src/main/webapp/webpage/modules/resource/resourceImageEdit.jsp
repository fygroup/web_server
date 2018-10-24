<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ include file="/webpage/include/bootstraptable.jsp"%>
<html>
<head>
	<meta />
	<title>选择图片</title>
	<link href="${ctxStatic}/uploadify/bootstrap.min.css" rel="stylesheet">
	<link href="${ctxStatic}/uploadify/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	<script src="${ctxStatic}/jquery/jquery-2.0.3.min.js"></script>
	<script src="${ctxStatic}/jquery/fileinput.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<div class="container kv-main">
	<br>
	<form enctype="multipart/form-data">
		<div class="form-group">
			<input id="uploadfile" type="file" multiple class="file"  data-min-file-count="1" data-max-file-count="1">
		</div>
	</form>
	<input value="${resourceId}" id="resourceId" type="hidden">
	<input value="${num}" id="num" type="hidden">
</div>
</body>
<script>

    $("#uploadfile").fileinput({
        uploadUrl: '${ctx}/resource/resource/imageEditSave',
        allowedFileExtensions : ['jpg', 'png','jpeg'],
        overwriteInitial: false,
        maxFileSize: 1000,
        allowedFileTypes: ['image'],
        msgFilesTooMany: "选择上传的文件数量超过允许的最大数值(1)",
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        },
        uploadExtraData: function(previewId, index) {   //额外参数的关键点
            var obj = {};
            obj.fodder = $("#resourceId").val()+","+$("#num").val();
            return obj;
        }
    }).on("fileuploaded", function (event, data, previewId, index) {
        var obj = data.response;
        if(obj.success!=null&&obj.success.toString().length>0){
                window.opener.setPic(obj.success.toString(),$("#num").val());
                window.close();
        }else{
            alert("上传失败");
            window.close();
        }
    }).on('fileerror', function(event, data, msg) {
        alert("上传失败");
        window.close();
    }).on('filepreupload', function(event, data, previewId, index) {
        var  files = data.files;

    });
</script>
</html>