<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Org Chart Editor</title>
    <meta name="description" content="" />
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/layui/css/layui.css">
    <script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/common/js/gojs/go.js" type="text/javascript"></script>
<%--
    <script src="${ctxStatic}/plugin/layui/layui.js" type="text/javascript"></script>
--%>
    <script src="${ctxStatic}/plugin/layui/layer/layer.js" type="text/javascript"></script>

    <script>
        var dex = "${ctxStatic}/";
        var ctx = "${ctx}";

    </script>
    <script id="code">
        var json = {};
        function addTopo(){
            layer.open({
                type: 1,
                area: ["420px", "240px"],
                title: "添加",
                content: $('#editRowContent'), //这里content是一个普通的String
                btn: ['确定', '关闭'],
                yes: function(index, layero){
                    editSure(index);
                    var topoName = $("#topo_name").val();
                    var parentViewId = $("#parent_topo").val();
                    $.ajax({
                        type : 'POST',
                        url :  ctx+"/topoview/topoView/topoResouceSave",
                        data:{name:topoName,parentViewId:parentViewId,type:"1"},
                        timeout:10*1000,    //超时时间 10s
                        success : function(result) {
                            // console.log(result);
                            if(editSure(index) != 1){
                                window.location.reload();
                                layer.close(index);
                            }

                        },
                        error:function(){
                            layer.msg('请求失败');
                        },
                        complete:function(XMLHttpRequest,status){
                            if(status=='timeout'){
                                layer.msg('请求超时');
                            }
                        }
                    });
                },
                cancel: function(index1){

                }

            });
        }
        function editSure(index) {
            if($("#topo_name").val() == ''){
                layer.msg("请填写名称");
                return 1;
            }

            if($("#parent_topo").val() == ''){
                layer.msg("请选择父级拓扑图");
                return 1;
            }
        }
        function loadData(){
            jQuery.ajax({
                type : 'GET',
                async : false,
                dataType:"json",
                url     : ctx+"/topoview/topoView/topoViewList",
                data    : {},
                success : function(result) {
                    //console.log(result);
                    if(result.length == 0){
                        $("#topo_none").show();
                    }else {
                        $("#topo_none").hide();
                    }
                    var arr = new Array();
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html += "<option value='"+ result[i].id +"'>"+ result[i].name +"</option>";
                        arr.push({
                            "key": result[i].id,
                            "name": result[i].name,
                            "type": result[i].type,
                            "hasException": result[i].hasException,
                            "topothumb":"${ctxStatic}/images/thumbnail/"+ result[i].id +".png",
                            "parent":result[i].parentViewId
                        })
                    }
                    $("#parent_topo").html(html).prepend("<option value='0'>无父级</option>"); ;
                    json ={
                        "class": "go.TreeModel",
                        "nodeDataArray":arr
                    };
                }
            })
        }

        function init() {
            if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
            var $$ = go.GraphObject.make;  // for conciseness in defining templates

            myDiagram =
                $$(go.Diagram, "myDiagramDiv", // must be the ID or reference to div
                    {
                        initialContentAlignment: go.Spot.Center,
                        maxSelectionCount: 1, // users can select only one part at a time
                        validCycle: go.Diagram.CycleDestinationTree, // make sure users can only create trees
                        // "clickCreatingTool.archetypeNodeData": {}, // allow double-click in background to create a new node
                        //  "clickCreatingTool.insertPart": function(loc) {  // customize the data for the new node
                        //    this.archetypeNodeData = {
                        //      key: getNextKey(), // assign the key based on the number of nodes
                        //      name: "(new person)",
                        //      title: ""
                        //    };
                        //    return go.ClickCreatingTool.prototype.insertPart.call(this, loc);
                        //  },
                        layout:
                            $$(go.TreeLayout,
                                {
                                    treeStyle: go.TreeLayout.StyleLastParents,
                                    arrangement: go.TreeLayout.ArrangementHorizontal,
                                    // properties for most of the tree:
                                    angle: 90,
                                    layerSpacing: 35,
                                    // properties for the "last parents":
                                    alternateAngle: 90,
                                    alternateLayerSpacing: 35,
                                    alternateAlignment: go.TreeLayout.AlignmentBus,
                                    alternateNodeSpacing: 20
                                }),
                        "undoManager.isEnabled": true // enable undo & redo
                    });

            // when the document is modified, add a "*" to the title and enable the "Save" button
           /* myDiagram.addDiagramListener("Modified", function(e) {
                var button = document.getElementById("SaveButton");
                if (button) button.disabled = !myDiagram.isModified;
                var idx = document.title.indexOf("*");
                if (myDiagram.isModified) {
                    if (idx < 0) document.title += "*";
                } else {
                    if (idx >= 0) document.title = document.title.substr(0, idx);
                }
            });
*/
            // manage boss info manually when a node or link is deleted from the diagram
            /*myDiagram.addDiagramListener("SelectionDeleting", function(e) {
                var part = e.subject.first(); // e.subject is the myDiagram.selection collection,
                                              // so we'll get the first since we know we only have one selection
                myDiagram.startTransaction("clear boss");
                if (part instanceof go.Node) {
                    var it = part.findTreeChildrenNodes(); // find all child nodes
                    while(it.next()) { // now iterate through them and clear out the boss information
                        var child = it.value;
                        var bossText = child.findObject("boss"); // since the boss TextBlock is named, we can access it by name
                        if (bossText === null) return;
                        bossText.text = "";
                    }
                } else if (part instanceof go.Link) {
                    var child = part.toNode;
                    var bossText = child.findObject("boss"); // since the boss TextBlock is named, we can access it by name
                    if (bossText === null) return;
                    bossText.text = "";
                }
                myDiagram.commitTransaction("clear boss");
            });
*/
            var levelColors = ["#f0f0f0"];

            // override TreeLayout.commitNodes to also modify the background brush based on the tree depth level
            myDiagram.layout.commitNodes = function() {
                go.TreeLayout.prototype.commitNodes.call(myDiagram.layout);  // do the standard behavior
                // then go through all of the vertexes and set their corresponding node's Shape.fill
                // to a brush dependent on the TreeVertex.level value
                myDiagram.layout.network.vertexes.each(function(v) {
                    if (v.node) {
                        var level = v.level % (levelColors.length);
                        var color = levelColors[level];
                        var shape = v.node.findObject("SHAPE");
                        if (shape) shape.fill = $$(go.Brush, "Linear", { 0: color, 1: go.Brush.lightenBy(color, 1), start: go.Spot.Left, end: go.Spot.Right });
                    }
                });
            };
            //阻止键盘事件
            myDiagram.commandHandler.doKeyDown = function() {
                var e = myDiagram.lastInput;
                // Meta（Command）键代替Mac命令的“控制”
                var control = e.control || e.meta;
                var key = e.key;
                //退出任何撤销/重做组合键/删除，具体键值根据需求而定
                if(control &&(key === 'Z' || key === 'Y' || 'Delete'))return ;

                //调用没有参数的基础方法（默认功能）
                // go.CommandHandler.prototype.doKeyDown.call(this);
            };

            // This function is used to find a suitable ID when modifying/creating nodes.
            // We used the counter combined with findNodeDataForKey to ensure uniqueness.
            /*function getNextKey() {
                var key = nodeIdCounter;
                while (myDiagram.model.findNodeDataForKey(key) !== null) {
                    key = nodeIdCounter--;
                }
                return key;
            }*/

            var nodeIdCounter = -1; // use a sequence to guarantee key uniqueness as we add/remove/modify nodes

            // when a node is double-clicked, add a child to it
            /*function nodeDoubleClick(e, obj) {
              var clicked = obj.part;
              if (clicked !== null) {
                var thisemp = clicked.data;
                myDiagram.startTransaction("add employee");
                var newemp = { key: getNextKey(), name: "(new person)", title: "", parent: thisemp.key };
                myDiagram.model.addNodeData(newemp);
                myDiagram.commitTransaction("add employee");
              }
            }*/
            //双击进入拓扑详情页
            function nodeDoubleClick(e, obj) {
                var clicked = obj.part;
                var thisemp = clicked.data;
                window.location.href="${ctx}/topo?id="+thisemp.key;
            }

            // this is used to determine feedback during drags
            /*function mayWorkFor(node1, node2) {
                if (!(node1 instanceof go.Node)) return false;  // must be a Node
                if (node1 === node2) return false;  // cannot work for yourself
                if (node2.isInTreeOf(node1)) return false;  // cannot work for someone who works for you
                return true;
            }*/

            // This function provides a common style for most of the TextBlocks.
            // Some of these values may be overridden in a particular TextBlock.
            function textStyle() {
                return { font: "9pt  Segoe UI,sans-serif", stroke: "#666" };
            }

            // This converter is used by the Picture.
            function findHeadShot(topothumb) {
                if (topothumb == '') return ""; // There are only 16 images on the server
                return topothumb;
            }
            //warning
            function findWarning(key) {
                if (key) return "${ctxStatic}/images/warning.png"; // There are only 16 images on the server
                return ""
            }
            // define the Node template
            myDiagram.nodeTemplate =
                $$(go.Node, "Auto",
                    { doubleClick: nodeDoubleClick },
                    { // handle dragging a Node onto a Node to (maybe) change the reporting relationship
                        /*mouseDragEnter: function (e, node, prev) {
                            var diagram = node.diagram;
                            var selnode = diagram.selection.first();
                            if (!mayWorkFor(selnode, node)) return;
                            var shape = node.findObject("SHAPE");
                            if (shape) {
                                shape._prevFill = shape.fill;  // remember the original brush
                                shape.fill = "" +
                                    "";
                            }
                        },
                        mouseDragLeave: function (e, node, next) {
                            var shape = node.findObject("SHAPE");
                            if (shape && shape._prevFill) {
                                shape.fill = shape._prevFill;  // restore the original brush
                            }
                        },
                        mouseDrop: function (e, node) {
                            var diagram = node.diagram;
                            var selnode = diagram.selection.first();  // assume just one Node in selection
                            if (mayWorkFor(selnode, node)) {
                                // find any existing link into the selected node
                                var link = selnode.findTreeParentLink();
                                if (link !== null) {  // reconnect any existing link
                                    link.fromNode = node;
                                } else {  // else create a new link
                                    diagram.toolManager.linkingTool.insertLink(node, node.port, selnode, selnode.port);
                                }
                            }
                        }*/
                    },
                    // for sorting, have the Node.text be the data.name
                    new go.Binding("text", "name"),
                    // bind the Part.layerName to control the Node's layer depending on whether it isSelected
                    new go.Binding("layerName", "isSelected", function(sel) { return sel ? "Foreground" : ""; }).ofObject(),
                    // define the node's outer shape
                    $$(go.Shape, "Rectangle",
                        {
                            name: "SHAPE", fill: "white", stroke: null,
                            // set the port properties:
                            portId: "", fromLinkable: false, toLinkable: false, cursor: "pointer"
                        }),
                    $$(go.Panel, "Horizontal",
                        $$(go.Picture,
                            {
                                name: "Picture",
                                desiredSize: new go.Size(50, 50),
                                margin: new go.Margin(6, 8, 6, 10),
                                background: "white",
                            },
                            new go.Binding("source", "topothumb", findHeadShot)),

                        // define the panel where the text will appear
                        $$(go.Panel, "Table",
                            {
                                maxSize: new go.Size(150, 999),
                                margin: new go.Margin(6, 10, 0, 3),
                                defaultAlignment: go.Spot.Left
                            },
                            $$(go.RowColumnDefinition, { column: 2, width: 4 }),
                            $$(go.TextBlock, textStyle(),  // the name
                                {
                                    row: 0, column: 0, columnSpan: 5,
                                    font: "12pt Segoe UI,sans-serif",
                                    editable: false, isMultiline: false,
                                    minSize: new go.Size(10, 16)
                                },
                                new go.Binding("text", "name")),
                            /*$$(go.TextBlock, "Title: ", textStyle(),
                                { row: 1, column: 0 }),
                            $$(go.TextBlock, textStyle(),
                                {
                                    row: 1, column: 1, columnSpan: 4,
                                    editable: false, isMultiline: false,
                                    minSize: new go.Size(10, 14),
                                    margin: new go.Margin(0, 0, 0, 3)
                                },
                                new go.Binding("text", "title")),*/
                            /*$$(go.TextBlock, textStyle(),
                                { row: 2, column: 0 },
                                new go.Binding("text", "key", function(v) {return "ID: " + v;})),
                            $$(go.TextBlock, textStyle(),
                                { name: "boss", row: 2, column: 3, }, // we include a name so we can access this TextBlock when deleting Nodes/Links
                                new go.Binding("text", "parent", function(v) {return "Boss: " + v;})),*/
                            $$(go.TextBlock, textStyle(),  // the comments
                                {
                                    row: 3, column: 0, columnSpan: 5,
                                    font: "italic 9pt sans-serif",
                                    wrap: go.TextBlock.WrapFit,
                                    editable: false,  // by default newlines are allowed
                                    minSize: new go.Size(10, 14)
                                },
                                new go.Binding("text", "comments").makeTwoWay())
                        ),  // end Table Panel
                        $$(go.Picture,
                            {
                                name: "Picture",
                                desiredSize: new go.Size(24, 24),
                                margin: new go.Margin(6),
                            },
                            new go.Binding("source", "hasException", findWarning))
                    ) // end Horizontal Panel
                );  // end Node



            // define the Link template
            myDiagram.linkTemplate =
                $$(go.Link, go.Link.Orthogonal,
                    { corner: 5, relinkableFrom: true, relinkableTo: true },
                    $$(go.Shape, { strokeWidth: 2, stroke: "#ddd" }));  // the link shape

            // read in the JSON-format data from the "mySavedModel" element
            load();


            // support editing the properties of the selected person in HTML
            if (window.Inspector) myInspector = new Inspector("myInspector", myDiagram,
                {
                    properties: {
                        "key": { readOnly: true },
                        "comments": {}
                    }
                });


        }
        

        // Show the diagram's model in JSON format

        function load() {
            loadData();
            myDiagram.model = go.Model.fromJson(json);
        }



    </script>
    <style>
        .operation{
            width:90%;
            margin-left:0;
            padding-top: 20px;
        }
        .add_topo{
            margin:15px 30px;
            background: #41A0DC;
            padding: 8px 16px;
            color: #fff;
            font-size: 14px;
            display: inline-block;
            cursor: pointer;
        }
    </style>
</head>
<body onload="init()" >

<div id="sample">
    <div class="add_topo"><div class="btn btn-primary" onclick="addTopo()">添加</div></div>
    <div id="myDiagramDiv" style="background-color: #fff; height: 90%"></div>

</div>

<div class="topo_content">
    <div id="topo_none">暂时还没有任何拓扑图</div>
</div>

<div class="form-horizontal pad" id="editRowContent" hidden>
    <div class="operation">
        <div class="layui-form-item">
            <label class="layui-form-label">名称</label>
            <div class="layui-input-block">
                <input type="text" name="title" id="topo_name"  required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">父级</label>
            <div class="layui-input-block">
                <select name="" lay-verify="required" id="parent_topo" class="layui-input">
                </select>
            </div>
        </div>
    </div>
</div>
</body>
</html>
