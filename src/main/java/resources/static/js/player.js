var app = app || {};

$(function () {
    app.player = {
        table: document.getElementById("table")
    };
    doPoll();
});


function doPoll() {
    $.post("http://localhost:44444/playerEdit", function (data) {
        let table = app.player.table;
        let newTable = "<tr>";
        let cards = data;
        let length = cards.length;
        console.log(cards);
        for (let i = 0; i < length; i++) {
            newTable += `<td class="card ${cards[i].value}">${cards[i].value}</td>`;
            if (i === cards.length) {
                newTable += "<tr>";
            } else if ((i + 1) % 5 === 0) {
                newTable += "</tr>";
            }
        }

        table.innerHTML = newTable;
        $("#table").on('click','td.card',function () {
            console.log(this);
            console.log(this.value);

        });
    });
}