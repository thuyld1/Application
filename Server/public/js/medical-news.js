$(document).ready(function () {
    $('#detect_btn').click(function () {
        var query = 'select * from html where url="' + $('#detect_link').val() + '" and xpath="*"';
        var url = 'https://query.yahooapis.com/v1/public/yql?q=' + encodeURIComponent(query);

        $.get(url, function (data) {
            $('#thumb').val($(data).find('meta[property="og:image"]').attr('content') || '');
            $('#title').val($(data).find('meta[property="og:title"]').attr('content') || '');
            $('#des').val($(data).find('meta[property="og:description"]').attr('content') || '');
            $('#url').val($(data).find('meta[property="og:url"]').attr('content') || '');
        });
    });
});

