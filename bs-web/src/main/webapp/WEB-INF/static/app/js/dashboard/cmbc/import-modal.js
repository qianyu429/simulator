$(function () {
    var $form = $('#modal-form');
    var $modal = $form.parents('.modal');
    var file_input = $form.find('input[type=file]');

    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加Excel文件',
        btn_change: null,
        no_icon: 'ace-icon fa fa-file-o',
        droppable: false,
        allowExt: ["xls", "xlsx"],
        allowMime: ["application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"],
        maxSize: 2097152,//bytes, = 2MB
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Notify.warning('超出最大上传限制(2MB)。');
        if (info.error_count['ext'] || info.error_count['mime']) Notify.warning('不合法的文件类型。');
        event.preventDefault();
    });

    var showNotify = function (response) {
        if (response.status == 'fail') {
            Notify.error("导入错误码失败，" + response.errorMessage);
        }
        else {
            $modal.modal('hide');
            Notify.success("成功导入" + response.count + "个错误码。");
            window.location.reload();
        }
    };

    $form.validate({
        submitHandler: function (form, event) {
            var files = file_input.data('ace_input_files');
            if (!files || files.length == 0) {
                Notify.warning('请先选择要导入的文件');
                return;
            }
            event.preventDefault();
            $(form).ajaxSubmit({
                dataType: 'json',
                success: showNotify,
                error: function (data, textstatus) {
                    $modal.modal('hide');
                    Notify.error("服务器内部错误，请稍后再试。");
                }
            });
        }
    });

});