$(function () {
    $("#dashboard-spdb").addClass("active open");
    $("#dashboard-spdb-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=sms]').popover({
        html: true,
        content: '<p>00:交易成功</p>'
    });
    $('[data-rel=verify]').popover({
        html: true,
        content: '<p>00:交易成功</p>'
    });

    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>00:交易成功</p>'
    });

    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>4:交易成功</p>'
    });

    $('[data-rel=interRedeem]').popover({
        html: true,
        content: '<p>1:交易成功</p>'
    });

});