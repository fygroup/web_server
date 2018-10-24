<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>模板管理</title>
    <meta name="decorator" content="ani"/>
    <link rel="stylesheet" href="${ctxStatic}/common/css/main.css">
</head>
<body class="bg-white">
    <div class="templates-content">
        <div class="template-list">
            <img class="template-img" src="${ctxStatic}/common/images/topn.png">
            <h3 class="panel-title template-title">TopN</h3>
            <div class="addContent">
                <img class="addImg" src="${ctxStatic}/common/images/add-template.png">
            </div>
        </div>
        <div class="template-list">
            <img class="template-img" src="${ctxStatic}/common/images/abnormal.png">
            <h3 class="panel-title template-title">异常</h3>
            <div class="addContent">
                <img class="addImg" src="${ctxStatic}/common/images/add-template.png">
            </div>
        </div>
        <div class="template-list">
            <img class="template-img" src="${ctxStatic}/common/images/services.png">
            <h3 class="panel-title template-title">指标一览</h3>
            <div class="addContent">
                <img class="addImg" src="${ctxStatic}/common/images/add-template.png">
            </div>
        </div>
        <div class="template-list">
            <img class="template-img" src="${ctxStatic}/common/images/singleService.png">
            <h3 class="panel-title template-title">单个资源一览</h3>
            <div class="addContent">
                <img class="addImg" src="${ctxStatic}/common/images/add-template.png">
            </div>
        </div>
        <div class="template-list">
            <img class="template-img" src="${ctxStatic}/common/images/topo.png">
            <h3 class="panel-title template-title">拓扑图</h3>
            <div class="addContent">
                <img class="addImg" src="${ctxStatic}/common/images/add-template.png">
            </div>
        </div>
    </div>
</body>
</html>