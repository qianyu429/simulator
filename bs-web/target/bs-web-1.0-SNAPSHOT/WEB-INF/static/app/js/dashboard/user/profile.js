$(function () {
    $('#dashboard-user').addClass('active open');
    $('#dashboard-user-profile').addClass('active');
    $("#bankmock").addClass("active open");

    var $form = $('#user-profile-form');

    $form.validate({
        rules: {
            username: {
                required: true,
                rangelength: [5, 30]
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            //form.submit();
            $.post($form.attr("action"), $form.serialize(), callback);
        }
    });
});