let ws, host;

function connect() {
    host = document.location.host;
    ws = new WebSocket("ws://" + host + "/socket/player");
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

function showGreeting(data) {
    $(".card").removeClass("select");
    let CardSelected = JSON.parse(data);
    let listOfIndex = CardSelected.listOfIndex;
    let colors = CardSelected.colors;
    for (let i = 0; i < listOfIndex.length; i++) {
        let id = "#w" + listOfIndex[i];
        $(id).addClass("select");
        $(id).addClass(colors[listOfIndex[i]]);
    }

}

$(function () {
    connect();
});
