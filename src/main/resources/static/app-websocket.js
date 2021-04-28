var stompClient = null;
var SOCKET_ENDPOINT = "/demo";
var SUBSCRIBE_PREFIX = "/topic";
var SUBSCRIBE = "";
var SEND_ENDPOINT = "/app/testWebSocket";

function connect() {
    var socket = new SockJS(SOCKET_ENDPOINT);
    stompClient = Stomp.over(socket);
    stompClient.connect({},function (frame) {
        alert("链接成功");
    });
}

function subscribeSocket() {
    SUBSCRIBE = SUBSCRIBE_PREFIX + $("#subscribe").val();
    alert("设置订阅地址为：" + SUBSCRIBE);
    stompClient.subscribe(SUBSCRIBE, function (responseBody) {
        var receiveMessage = JSON.parse(responseBody.body);
        $("#information").append("<tr><td>" + receiveMessage.content + "</td></tr>");
    });
}

function disconnect() {
    stompClient.disconnect(function () {
        alert("断开啦")
    });
}

function sendMessageNoParameter() {
    var sendContent = $("#content").val();
    var message = '{"destination": "' + SUBSCRIBE + '", "content": "' + sendContent + '"}';
    stompClient.send(SEND_ENDPOINT, {}, message);
}