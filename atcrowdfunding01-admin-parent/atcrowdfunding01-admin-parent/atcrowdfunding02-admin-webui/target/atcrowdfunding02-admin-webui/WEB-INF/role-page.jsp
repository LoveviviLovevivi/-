<%--
  Created by IntelliJ IDEA.
  User: lovevivi
  Date: 2020/7/2
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function(){
        // 1,为分页操作准备初始化操作
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";
        // 2.调用执行分页的函数，执行分页的效果
        generatePage();
        // 3.给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {
            // 1.获取关键词数据赋值给全局变量
            window.keyword = $("#keywordInput").val();
            // 2,调用分页函数刷新页面
            generatePage();
        });
        // 点击新增按钮打开模态框
        $("#showAddModelBtn").click(function () {
            $("#addModal").modal("show");
        });
        $("#saveRoleBtn").click(function () {
            // 获取文本框的角色名称
            var roleName = $("#addModal [name=roleName]").val();
            if(roleName == null || roleName == ""){
                layer.msg("不能为空")
            }else{
                $.ajax({
                    'url': "role/save.json",
                    'type': "post",
                    'data': {
                        'name': roleName
                    },
                    "dataType": "json",
                    'success': function (response) {
                        var result = response.result;
                        if(result == "SUCCESS"){
                            layer.msg("操作成功")
                            window.pageNum = 1;
                            generatePage();
                        }
                        if(result == "FAILED"){
                            layer.msg("操作失败"+response.message);
                        }
                    },
                    'error': function (response) {
                        layer.msg(response.status+" "+response.statusText);
                    }
                });
            }
            // 关闭模态框
            $("#addModal").modal("hide");
            // 清理模态框
            $("#addModal [name=roleName]").val("");
        });
        // 使用jquery对象的on函数
        // on第一个参数是事件类型
        // 第二个参数是找到要绑定的选择器
        // 第三个是响应函数
        $("#rolePageBody").on("click",".pencilBtn",function () {
            // 打开模态框
            $("#editModal").modal("show");
            var roleName = $(this).parent().prev().text();
            // 获取当前角色的id
            // 依据是role.id设置给了button的id属性
            // 为了让执行跟新的按钮获取到，放在全局变量
            window.roleId = this.id
            // 使用roleName的值设置回显
            $("#editModal [name=roleName]").val(roleName);
        });

        // 给更新模态框的按钮绑定响应函数
        $("#updateRoleBtn").click(function () {
            // 从文本框中获取新的名称
            var roleName = $("#editModal [name=roleName]").val();
            // 发送ajax请求执行跟新
            if(roleName == null || roleName == ""){
                layer.msg("不能为空")
            }else {
                $.ajax({
                    'url': "role/update.json",
                    'type': "post",
                    'data': {
                        'id': window.roleId,
                        'name': roleName
                    },
                    'dataType': 'json',
                    'success': function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            layer.msg("操作成功")
                            generatePage();
                        }
                        if (result == "FAILED") {
                            layer.msg("操作失败" + response.message);
                        }
                    },
                    'error': function (response) {
                        layer.msg(response.status + " " + response.statusText);
                    }
                });
            }
        // 关闭模态框
        $("#editModal").modal("hide");
            });
        // 给单条删除按钮绑定响应函数
        $("#rolePageBody").on("click", ".removeBtn", function () {
            //从当前按钮出发获取角色名称
            var roleName = $(this).parent().prev().text();

            // 创建role数组对象
            var roleArray = [{
                roleId: this.id,
                roleName: roleName
            }];
            // 调用函数打开模态框
            showConfirmModel(roleArray);
        });

    // 执行删除
    $("#removeRoleBtn").click(function () {
        var requestbody = JSON.stringify(window.roleIdArray);
        $.ajax({
            'url': "role/delete/by/role/id/array.json",
            'type': "post",
            'data': requestbody,
            'contentType': "application/json;charset=UTF-8",
            'dataType': "json",
            'success': function (response) {
                var result = response.result;
                if (result == "SUCCESS") {
                    layer.msg("操作成功")
                    generatePage();
                }
                if (result == "FAILED") {
                    layer.msg("操作失败" + response.message);
                }
            },
            'error': function (response) {
                layer.msg(response.status + " " + response.statusText);
            }
        });
        $("#confirmModal").modal("hide");
    });
    // 给总的checkbox绑定单击响应函数
        $("#summaryBox").click(function () {
            // 获取多选框自身的状态
            var currentStatus = this.checked;
            // 用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked", currentStatus)
        });
        // 全选，全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {
            // 获取当前已经选中的.itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;
            // 设置全部.itemBox的数量
            var totalBoxCount = $(".itemBox").length;
            // 使用二者比较的结果设置
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount)
        });

        // 多选删除响应函数
        $("#batchRemoveBtn").click(function () {
            // 创建数组
            var roleArray = [];
            $(".itemBox:checked").each(function () {
                // 通过this引用当前遍历的多选框
                var roleId = this.id;
                // 获取角色名称
                var roleName = this.parent().next().text()
                roleArray.push({
                    'roleId': roleId,
                    'roleName': roleName
                })
            })
            // 判断roleArray是否为空
            if (roleArray.length == 0){
                layer.msg("请至少选择一个删除")
                return;
            }
            showConfirmModel(roleArray);
        });
        // 打开分配权限的模态框
        $("#rolePageBody").on("click",".checkBtn",function () {
            window.roleId = this.id;
            // 打开模态框
            $("#assignModal").modal("show");
            fillAuthTree();
        });
        $("#assignBtn").click(function () {
            // 存储authId的数组
            var authIdArray = [];
            // 获取ztree对像
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo")
            // 获取全部被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes();
            // 遍历checkedNodes
            for (var i = 0; i < checkedNodes.length; i++){
                var checkNode = checkedNodes[i];
                var authId = checkNode.id;
                authIdArray.push(authId);
            }
            // 发送请求执行分配
            var requestBody = {
                'authIdArray': authIdArray,
                // 在handler中统一用List<Integer> 接收
                'roleId': [window.roleId]
            }
            requestBody = JSON.stringify(requestBody)
            $.ajax({
                'url': "assign/do/role/assign/auth.json",
                'type': "post",
                'data': requestBody,
                'contentType': "application/json;charset=UTF-8",
                'dataType': "json",
                'success': function (response) {
                    var result = response.result;
                    if (result == 'SUCCESS'){
                        layer.msg("操作成功")
                    }
                    if (result == 'FAILED'){
                        layer.msg("操作失败")
                    }

                },
                'error': function (response) {
                    layer.msg(response.status+" "+response.statusText)
                }
            });
            $("#assignModal").modal("hide");
        });
    });

</script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" id="showAddModelBtn" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/model-role-add.jsp" %>
<%@include file="/WEB-INF/model-role-edit.jsp" %>
<%@include file="/WEB-INF/model-role-confirm.jsp" %>
<%@include file="/WEB-INF/modal-role-assign-auth.jsp" %>
</body>
</html>
