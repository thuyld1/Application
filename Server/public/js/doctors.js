$(document).ready(function () {
    $('#province').on('change', function (e) {
        // Remove old options
        $("#district").empty();

        // Get list district
        var province = $('#province option:selected').attr('value');
        var info = $.get("/api/getDistricts", {
            province: province
        });
        info.done(function (data) {
            console.log(data);
            resetSelection($("#district"), data);
        });

        info.fail(function () {
            console.log('Load district fail');
        });
    });
});

/**
 * Reset
 * @param data
 */
function resetSelection(selector, data) {
    // Remove old options
    selector.empty();
    $.each(data, function (value, key) {
        selector.append($("<option></option>").attr("value", value).text(key));
    });
}