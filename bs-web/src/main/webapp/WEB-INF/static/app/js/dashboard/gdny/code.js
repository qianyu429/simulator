$(function () {
    $("#dashboard-gdny").addClass("active open");
    $("#dashboard-gdny-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=verify]').popover({
        html: true,
        content: '<p>00:交易成功<br/>01:无效商户<br/>99:其他错误</p>'
    });

    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>00:交易成功<br/>01:无效商户<br/>99:其他错误</p>'
    });

    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>00:交易成功<br/>01:无效商户<br/>99:其他错误</p>'
    });

});