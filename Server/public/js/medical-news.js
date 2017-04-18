$(document).ready(function () {
    // For insert view
    $('#detect_link').keypress(function (e) {
        if (e.which == '13') {
            detectMetaData();
        }
    });
    $('#detect_btn').click(function () {
        detectMetaData();
    });


    // For edit view
    $('#thumb').keypress(function (e) {
        if (e.which == '13') {
            $('#thumb-img').attr('src', $('#thumb').val());
            e.preventDefault();
        }
    });

});

function detectMetaData() {
    var query = 'select * from html where url="' + $('#detect_link').val() + '" and xpath="*"';
    var url = 'https://query.yahooapis.com/v1/public/yql?q=' + encodeURIComponent(query);

    $.get(url, function (data) {
        var image = $(data).find('meta[property="og:image"]').attr('content');
        $('#thumb').val(image || '');
        $('#title').val($(data).find('meta[property="og:title"]').attr('content') || '');
        $('#des').val($(data).find('meta[property="og:description"]').attr('content') || '');
        $('#url').val($(data).find('meta[property="og:url"]').attr('content') || '');

        if (jQuery.isEmptyObject(image)) {
            $('#thumb-img').attr('src', '/img/common_placeholder.png');
        } else {
            $('#thumb-img').attr('src', image);
        }

    }).fail(function () {
        alert('Link not found!!!')
    });
}