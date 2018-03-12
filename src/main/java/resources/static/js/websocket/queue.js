let ws, host;

function connect() {
    host = document.location.host;
    ws = new WebSocket('ws://' + host + '/socket/queue');
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
    let data = JSON.parse(message);
    let teams = data.teams;
    let isGameActive = data.isGameActive;
    if (isGameActive) {
        location.reload();
    }
    setBoss(teams[0], 0);
    setBoss(teams[1], 1);
    setPlayers(teams[0], 0);
    setPlayers(teams[1], 1);
}

function setBoss(team, index) {
    $('.boss')[index].innerText = 'Boss: ' + team.boss + ' 🦄';
}

function setPlayers(team, index) {
    let player = $('.player')[index];
    let players = '';
    let length = team.player.length;
    for (let i = 0; i < length; i++) {
        players += '<li>' + team.player[i] + '</li>';
    }
    player.innerHTML = players;

}

$(function () {
    connect();
});