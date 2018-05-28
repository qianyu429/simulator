$(function(){

    var $form = $('#modal-form');
    var $modal = $form.parents('.modal');

    var showNotify = function(response) {
        if(response.status == 'fail'){
            Notify.error("添加角色失败。");
        }
        else
        {
            $modal.modal('hide');
            window.location.reload();
        }
    };

    $form.validate({
        rules: {
            name: {
                required: true,
                remote: {
                    url: ctx + "admin/role/verify-name",
                    type: 'post',
                    data: {
                        'name': function () { return $('#name').val(); }
                    }
                }
            },
            description: {
                required: true
            }
        },
        submitHandler: function(form, event) {
            event.preventDefault();
            $(form).ajaxSubmit({
                dataType : 'json',
                success: showNotify,
                error: function(data,textstatus){
                    $modal.modal('hide');
                    Notify.error("服务器内部错误，请稍后再试。");
                }
            });
        }
    });

});