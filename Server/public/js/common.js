$(document).ready(function () {
    // For edit view
    $('#thumb').keypress(function (e) {
        if (e.which == '13') {
            $('#thumb-img').attr('src', $('#thumb').val());
            e.preventDefault();
        }
    });

});