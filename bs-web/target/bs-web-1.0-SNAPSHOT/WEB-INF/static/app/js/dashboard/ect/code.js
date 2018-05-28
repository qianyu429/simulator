$(function () {
    $("#dashboard-ect").addClass("active open");
    $("#dashboard-ect-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=sms]').popover({
        html: true,
        content: '<p>50050000:交易成功<br/>50050001:处理中<br/>其他均为交易失败<br/></p>'
    });

    $('[data-rel=verify]').popover({
        html: true,
        content: '<p>50050000:交易成功<br/>50050001:处理中<br/>其他均为交易失败<br/></p>'
    });

    $('[data-rel=sign]').popover({
        html: true,
        content: '<p>50050000:交易成功<br/>50050001:处理中<br/>其他均为交易失败<br/></p>'
    });

    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>50050000:交易成功<br/>50050001:处理中<br/>其他均为交易失败<br/></p>'
    });

    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>50050000:交易成功<br/>50050001:处理中<br/>其他均为交易失败<br/></p>'
    });
});