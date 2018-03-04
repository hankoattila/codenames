var app = app || {};


$(function () {
    app.queue = {
        players: getPlayers()
    };
    doPoll();
});


function doPoll() {
    $.post("http://localhost:44444/queueEdit", function (data) {
        let team1 = data[0];
        let team2 = data[1];
        editTable(0,team1);
        editTable(1,team2);
        setTimeout(doPoll, 2000);
    });
}

function getPlayers() {
    return document.getElementsByTagName("ul");
}

function editTable(teamNumber,newTeam) {
    let team = app.queue.players[teamNumber];
    team.innerHTML = "";
    for(let i = 0; i < newTeam.length; i++){
        team.innerHTML += "<li>" + newTeam[i] + "</li>";
    }

}

