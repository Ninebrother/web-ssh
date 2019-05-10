Terminal.applyAddon(fit);
var ShellClient = {
    socket: null,
    hostId: 0,
    userId: 0,
    logId: 0,
    terminal: new Terminal({
        rows: 20,
        cols: 140,
        screenKeys: false,
        useStyle: true,
        cursorBlink: true,
        convertEol: true
    }),
    clearTerminal: function () {
        this.terminal.write(String.fromCharCode(27, 91, 72, 27, 91, 50, 74, 27, 93, 48, 59, 114, 111, 111, 116, 64, 99, 105, 110, 101, 109, 97, 58, 126, 7));
    },
    connect: function () {
        var _this = this;
        _this.clearTerminal();

        var queryString = [];
        queryString.push("hostId=" + _this.hostId);
        queryString.push("userId=" + _this.userId);
        queryString.push("logId=" + _this.logId);
        // queryString.push("cols=" + this.terminal.cols);
        // queryString.push("rows=" + this.terminal.rows);

        // var sockUrl = "ws://" + window.location.host + "/terminal/socket?" + queryString.join("&");
        // if (window.location.protocol === "https:") {
        //     sockUrl = "wss://" + window.location.host + "/terminal/socket?" + queryString.join("&");
        // }
        var sockUrl = "ws://127.0.0.1:80/terminal/socket?" + queryString.join("&");
        if (window.location.protocol === "https:") {
            sockUrl = "wss://localhost:80/terminal/socket?" + queryString.join("&");
        }

        console.log("socketUrl:"+sockUrl);
        _this.socket = new WebSocket(sockUrl);

        _this.socket.onerror = () => {
            _this.socket = null;
        };

        _this.socket.onopen = function () {
            console.log("创建socket链接");
        };

        _this.socket.onmessage = function (event) {
            console.log("onmessage："+event.data);
            _this.terminal.write(event.data);

        };

        _this.socket.onclose = function () {
            _this.terminal.write("\n\n\u001b[31m您已经与服务器断开链接");
            _this.socket = null;
        };
    },

    resize: function () {
        this.terminal.fit();
    },
    init: function (hostId, userId, logId) {
        var _this = this;
        _this.hostId = hostId;
        _this.userId = userId;
        _this.logId = logId;
        _this.terminal.open(document.getElementById("terminal"), true);
        _this.terminal.focus();
        _this.terminal.fit();

        _this.connect();

        _this.terminal.on('data', function (data) {

            if (_this.socket == null) {
                _this.connect();
            } else {
                _this.socket.send(data);
            }
        });
    }
};