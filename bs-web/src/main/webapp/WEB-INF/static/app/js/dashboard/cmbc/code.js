$(function () {
    $("#dashboard-cmbc").addClass("active open");
    $("#dashboard-cmbc-code").addClass("active");
    $("#bankmock").addClass("active open");

    var $form = $('#form');

    $form.validate({
        ignore: '',
        rules: {}
    });

    $('[data-rel=sms]').popover({
        html: true,
        content: ''
    });

    $('[data-rel=verify]').popover({
        html: true,
        content: ''
    });

    $('[data-rel=pay]').popover({
        html: true,
        content: '<p>0001:成功<br/>0000:失败<br/>0002:银行处理中</p>0003:银行查无此订单</p>'
    });

    $('[data-rel=redeem]').popover({
        html: true,
        content: '<p>0:交易成功<br/>2:交易失败<br/>其他:交易处理中</p>'
    });

    $('[data-rel=import]').popover({
        container: 'body',
        html: true
    });
});