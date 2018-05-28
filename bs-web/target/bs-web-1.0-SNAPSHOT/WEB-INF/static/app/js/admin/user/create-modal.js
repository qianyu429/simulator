$(function () {
    var $form = $('#modal-form');

    $form.validate({
        rules: {
            username: {
                required: true,
                rangelength: [5, 30],
                remote: {
                    url: ctx + "admin/user/verify-username",
                    type: 'post',
                    data: {
                        'username': function () {
                            return $('#username').val();
                        },
                        'vaild': function() {
                            return $("#username").attr("readonly") == undefined;
                        }
                    }
                }
            },
            password: {
                required: true,
                rangelength: [5, 30]
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            var $modal = $(form).parents('.modal');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.status == 'fail') {
                        Notify.error("操作失败。");
                    } else {
                        $modal.modal('hide');
                        //Notify.success("操作成功。");
                        window.location.reload();
                    }
                },
                error: function (data, textstatus) {
                    $modal.modal('hide');
                    Notify.error("服务器内部错误，请稍后再试。");
                }
            });
        }
    })
});