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

function showGreeting(message) {
    let playerDTO = JSON.parse(message);
    let isYourTurn = playerDTO.isYourTurn;
    let cards = playerDTO.cards;
    console.log(cards);
    let table = app.player.table;
    let newTable = '<tr>';
    let length = cards.length;
    for (let i = 0; i < length; i++) {
        newTable += `<td><div id='w${i}' class='card `;
        if (cards[i].selected) {
            newTable += ' select';
        }
        newTable += `'><span>${cards[i].value}</span></div></td>`;
        if (i === cards.length) {
            newTable += '<tr>';
        } else if ((i + 1) % 5 === 0) {
            newTable += '</tr>';
        }
    }
    table.innerHTML = newTable;
}

$(function () {
    connect();
});