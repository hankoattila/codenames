var app = app || {};
var sending = false;
var isYourTurn = true;

$(function () {
    app.player = {
        table: document.getElementById('table')
    };
    let url = 'http://localhost:44444/playerEdit';
    getCards(url);
});


function getCards(url) {
    $.post(url, function (data) {
        let table = app.player.table;
        let PlayerDTO = JSON.parse(data);
        let cards = PlayerDTO.cards;
        let colors = PlayerDTO.colors;
        let length = cards.length;
        let newTable = '<tr>';
        for (let i = 0; i < length; i++) {
            newTable += `<td><div id='w${i}' class='card `;
            if (colors[i] !== "") {
                newTable += `${colors[i]}'></div></td>`;
            } else {
                newTable += `'><span>${cards[i].value}</span></div></td>`;
            }
            if (i === cards.length) {
                newTable += '<tr>';
            } else if ((i + 1) % 5 === 0) {
                newTable += '</tr>';
            }
        }

        table.innerHTML = newTable;
        addClick();
    });
}

function addClick() {
    $(app.player.table).on('click', '.card', sendData);

}

function sendData() {
    if (sending) {
        return;
    }
    if ($(this).hasClass("red") ||
        $(this).hasClass("blue") ||
        $(this).hasClass("grey") ||
        $(this).hasClass("black") ||
        !isYourTurn) {
        return;
    }
    sending = true;
    $("#table>tbody>tr>td>div.select").removeClass("select");
    $(this).addClass("select");
    var idOfselected = this.id.replace("w", "");
    sendSelect(idOfselected);
}

function sendSelect(id) {
    $.post('http://localhost:44444/selectCard', {"id": id}, function (data) {
        sending = false;
    }).fail(function () {
        sending = false;
        console.log("error");
    });
}