var app = app || {};

$(function () {
    app.boss = {
        table: document.getElementById("table")
    };
    setTable()
});


function setTable() {
    $.post("http://localhost:44444/bossEdit", function (data) {
        let table = app.boss.table;
        let newTable = "<tr>";
        console.log(data);
        for (let i = 0; i < data.length; i++) {
            newTable += `<td class="card ${data[i]}"></td>`;
            if (i === data.length) {
                newTable += "<tr>";
            } else if ((i + 1) % 5 === 0) {
                newTable += "</tr>";
            }
        }

        table.innerHTML = newTable;
    });
}