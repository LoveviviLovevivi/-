<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                var array = [5,8,12]
                var requestBody = JSON.stringify(array)
                $.ajax({
                    'url': 'send/array.json',
                    'type': 'post',
                    'data': requestBody,
                    'contentType': "application/json;charset=UTF-8",
                    'dataType': "json",
                    'success': function (response) {
                        console.log(response);
                    },
                    'error': function (response) {
                        console.log(response);
                    }
                })
            })
        })
        $(function () {
            $("#btn5").click(function () {
                layer.msg("xixixi")
            })
        })
    </script>
</head>
<body>
<a href="test/ssm.html">测试</a>
<br>
<button id="btn1">send [5 8 12] one</button>
<button id="btn5">点我弹框</button>
</body>
</html>
