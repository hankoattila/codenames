let ws, host;

function connect() {
    host = document.location.host;
    ws = new WebSocket("ws://" + host + "/socket/queue");
    ws.onmessage = function (data) {
        showGreeting(data.data);
    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    console.log("Disconnected");
}

function showGreeting(message) {
    $("#player").append("<li>" + message + " </li>");
}

$(function () {
    connect();
});