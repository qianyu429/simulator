$(function(){

    var $form = $('#modal-form');
    var $modal = $form.parents('.modal');

    var showNotify = function(response) {
        if(response.status == 'fail'){
            Notify.error("用户角色保存失败。");
        }
        else
        {
            $modal.modal('hide');
            Notify.success("用户角色保存成功。");
        }
    };

    $form.validate({
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