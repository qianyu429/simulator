$(function () {
    var $form = $('#modal-form');

    $form.validate({
        rules: {
            beSer: {
                required: true,
                rangelength: [1, 32],
                remote: {
                    url: ctx + "dashboard/transaction/verify-beSer",
                    type: 'post',
                    data: {
                        'beSer': function () {
                            return $('#beSer').val();
                        },
                        'vaild': function() {
                            return $("#beSer").attr("readonly") == undefined;
                        }
                    }
                }
            },
            merId: {
                required: true,
                rangelength: [1, 64]
            },
            bsSer: {
                required: true,
                rangelength: [1, 32]
            },
            accoNo: {
                required: true,
                rangelength: [1, 64]
            },
            bankNo: {
                required: true,
                rangelength: [1, 10]
            },
            respCode: {
                required: true,
                rangelength: [1, 20]
            },
            stat: {
                required: true,
                rangelength: [1, 20]
            },
            workDay: {
                required: true,
                rangelength: [8, 8]
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