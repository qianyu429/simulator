$(function () {
    $("#dashboard-icbc1").addClass("active open");
    $("#dashboard-icbc1-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=sms]').popover({
        html: true,
        content: '<p>00000:交易成功</p>'
    });
    $('[data-rel=verify]').popover({
        html: true,
        content: '<p>00000:交易成功</p>'
    });

    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>00000:交易成功</p>'
    });

    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>7:交易成功</p>'
    });


});