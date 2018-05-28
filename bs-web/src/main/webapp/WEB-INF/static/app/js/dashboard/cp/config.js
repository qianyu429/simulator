$(function () {
    $("#dashboard-cp").addClass("active open");
    $("#dashboard-cp-config").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        rules: {
            val: {
                required: true
            }
        }
    });

});