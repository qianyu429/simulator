$(function () {
    $(".my-tag").each(function () {
        var $this = $(this);
        $this.tag({placeholder: $this.attr('placeholder')});
    });

});

$.ajaxSetup({
    cache: false,
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    complete: function (XMLHttpRequest, textStatus) {
        var session_status = XMLHttpRequest.getResponseHeader("session_status");
        if (session_status == "timeout") {
            window.location = ctx;
        }
    }
});

$(document).on('hidden.bs.modal', '.modal', function () {
    $(this).removeData('bs.modal');
});

$(document).on('click', 'button[data-loading-text]', function(e) {
    var btn = $(this).button('loading');
    setTimeout(function () {
        btn.button('reset');
    }, 5000);
});

$('.modal').on('click', '[data-toggle=form-submit]', function (e) {
    e.preventDefault();
    $($(this).data('target')).submit();
});

var showMessage = function (type, message) {
    $.gritter.add({
        title: '通知',
        text: message,
        class_name: type
    });
};

var Notify = {
    success: function (message) {
        showMessage('gritter-success', message);
    },

    warning: function (message) {
        showMessage('gritter-warning', message);
    },

    error: function (message) {
        showMessage('gritter-error', message);
    },

    info: function (message) {
        showMessage('gritter-info', message);
    }
};

$.messager.model = {
    cancel: {text: "取消", classed: 'btn-default'},
    ok: {text: "确定", classed: 'btn-success'}
};

var callback = function (data, status) {
    if (status == "success") {
        if (data && data.status == "success") {
            Notify.success("操作成功!");
        } else {
            if (data && data.message) {
                Notify.error(data.message);
            } else {
                Notify.error("操作失败!");
            }
        }
    } else {
        Notify.error("网络异常, 请联系管理员!");
    }
};
