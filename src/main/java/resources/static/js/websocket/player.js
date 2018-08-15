let ws, host;

function connect() {
    host = document.location.host;
    ws = new WebSocket("ws://" + host + "/socket/player");
    sendSelect(44);
    ws.onmessage = function (data) {
        try {
            showGreeting(data.data);
        } catch (error) {
            console.error(error);
        }
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
    let isGameOver = CardSelected.isGameOver;
    let winnerTeam = CardSelected.winnerTeam;
    if (isGameOver){
        alert("Game Over! The winner is " + winnerTeam);
        removeClick();
    }
    isYourTurn = CardSelected.isYourTurn;
    let listOfIndex = CardSelected.selected;
    let currentTeam = CardSelected.currentTeam;
    let color = CardSelected.color;
    $("#teamName").html(currentTeam);
    for (let i = 0; i < listOfIndex.length; i++) {
        let id = "#w" + listOfIndex[i];
        if (color !== "") {
            $(id).addClass(color);
            $(id).html("");
        } else {
            $(id).addClass("select");
        }
    }

}

$(function () {
    connect();
});
