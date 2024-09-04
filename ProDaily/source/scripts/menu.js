

function menu() {

    if ($('#side_menu').css("display") == "grid") {
        $('#side_menu').css("display", "none");
    }
    else {
        $('#side_menu').css("display", "grid");
    }
}


$(document).ready(function () {

    $('#side_menu').css("display", "none");

    $('.side_menu_button').click(menu);

    $("#login_button").click(menu);

})