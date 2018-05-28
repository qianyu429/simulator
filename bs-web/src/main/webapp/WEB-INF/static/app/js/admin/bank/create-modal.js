$(function () {
    var $form = $('#modal-form');

    $form.validate({
        rules: {
            bankNo: {
                required: true,
                rangelength: [1, 30],
                remote: {
                    url: ctx + "admin/bank/verify-bankNo",
                    type: 'post',
                    data: {
                        'bankNo': function () {
                            return $('#bankNo').val();
                        },
                        'vaild': function() {
                            return $("#bankNo").attr("readonly") == undefined;
                        }
                    }
                }
            },
            bankName: {
                required: true,
                rangelength: [1, 30]
            },
            merId: {
                required: true,
                rangelength: [1, 64]
            },
            code: {
                required: true,
                rangelength: [1, 10]
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