

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

    //localStorage.clear();
    $('.side_menu_button').click(menu);

    
})