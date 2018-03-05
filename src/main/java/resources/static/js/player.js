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
        console.log(data);
        for (let i = 0; i < data.cards.length; i++) {
            newTable += `<td class="card ${data.cards[i][1]}">${data.cards[i][0]}</td>`;
            if (i === data.cards.length) {
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