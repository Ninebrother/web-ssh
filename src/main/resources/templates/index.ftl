<!DOCTYPE html>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
</head>

<body>
<div class="x-nav">
    <span class="layui-breadcrumb">
        <a href="javascript:;">首页</a>
        <a href="javascript:;">服务器管理</a>
        <a>
          <cite>主机管理</cite>
        </a>
    </span>
    <span class="x-right">
        <a class="layui-btn layui-btn-small" href="javascript:location.replace(location.href);" title="刷新">
            <i class="layui-icon" style="line-height:30px">ဂ</i>
        </a>
    </span>

</div>
<div class="x-body">
    <table class="layui-table x-admin">
        <thead>
        <tr>
            <th width="30">
                <div class="layui-unselect header layui-form-checkbox" lay-skin="primary">
                    <i class="layui-icon">&#xe605;</i>
                </div>
            </th>
            <th>编号</th>
            <th>主机名</th>
            <th>主机地址</th>
            <th>端口</th>
            <th width="140">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list listData as row>
        <tr>
            <td>
                <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='${row.id}'>
                    <i class="layui-icon">&#xe605;</i>
                </div>
            </td>
            <td>${row.id}</td>
            <td>${row.name}</td>
            <td>${row.hostAddress}</td>
            <td>${row.portNumber}</td>

            <td class="td-manage">
                <a title="打开终端" href="javascript:;"
                   onclick="openTerminal('${row.name}',${row.id})"
                   style="color: red">
                    <i class="iconfont">&#xe6ee;</i>
                    打开终端
                </a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
<script>
    var openTerminal = function (name, hostId) {
        var url = "/terminal/" + hostId + ".htm?_=" + Math.random();
        window.open(url);
        // parent.x_admin_add_to_tab(name, '/terminal/' + hostId + '.htm?_=' + Math.random(), false);
    };
</script>
</body>

</html>