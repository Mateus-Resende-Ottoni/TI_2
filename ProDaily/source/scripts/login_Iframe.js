
function hide_iframe() {
    $("#the_login_iframe").css("display", "none");
}

function show_iframe() {
    $("#the_login_iframe").css("display", "block");
}



$(document).ready(function () {
    $("#login_button").click(function () {
        if ($("#the_login_iframe").css("display") == "block") {
            hide_iframe();
        }
        else {
            $("#login_iframe").attr("src", "login.html");
            show_iframe();
        }
    })

    $("#the_login_iframe button").click(function () {
        hide_iframe();
    })

})