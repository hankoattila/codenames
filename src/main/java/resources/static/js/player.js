var app = app || {};

$(function () {
    app.player = {
        table: document.getElementById('table')
    };
    getCards();
});


function getCards() {
    $.post('http://localhost:44444/playerEdit', function (data) {
        let table = app.player.table;
        let newTable = '<tr>';
        let cards = data;
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
        $('#table').on('click', 'td.card', function () {
            console.log(this);
            console.log(this.value);

        });
        addClick();
    });
}

function addClick() {
    $(app.player.table).on('click', '.card', function () {
        $("#table>tbody>tr>td>div.select").removeClass("select");
        $(this).addClass("select");
        var idOfselected = this.id.replace("w", "");
        sendSelect(idOfselected);
    });
}

function sendSelect(id) {
    $.post('http://localhost:44444/selectCard', {"id": id}, function (data) {
        console.log("ok");
    }).fail(function () {
        console.log("error");
    });
}