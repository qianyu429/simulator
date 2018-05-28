$(function(){

    var $form = $('#modal-form');
    var $modal = $form.parents('.modal');

    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    $.fn.zTree.init($("#tree"), setting, zNodes);

    var showNotify = function(response) {
        if(!response){
            Notify.error("权限设置失败。");
        }
        else
        {
            $modal.modal('hide');
            Notify.success("权限设置成功。");
        }
    };

    var str = "";
    $form.submit(function(){
        $(this).ajaxSubmit({
            beforeSubmit: function(arr, $form, options) {
                var zTree = $.fn.zTree.getZTreeObj("tree");
                var nodes = zTree.getCheckedNodes(true);
                for (var i = 0; i < nodes.length; i++) {
                    if (str != "") {
                        str += ",";
                    }
                    str += nodes[i].id;
                }
                arr[0].value = str;
            },
            dataType : 'json',
            success: showNotify,
            error: function(data,textstatus){
                $modal.modal('hide');
                Notify.error("服务器内部错误，请稍后再试。");
            }
        });
        return false;
    });
});