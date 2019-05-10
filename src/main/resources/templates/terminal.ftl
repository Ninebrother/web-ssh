<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${hostName}-${hostIP}</title>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/xterm/3.12.2/xterm.css"/>
    <script type="text/javascript" src="/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/xterm/addons/fit/fit.js?_=1"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/xterm/3.12.2/xterm.min.js"></script>
    <script type="text/javascript" src="/js/webssh.js?_=3"></script>
    <script>
        $(document).ready(function () {
            ShellClient.init(${hostId},${userId},${logId});
        });
    </script>
</head>
<body style="padding: 0; margin: 0;">
<div id="terminal" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
</body>
</html>