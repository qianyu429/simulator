$(function () {
    var $form = $('#modal-form');

    $form.validate({
        rules: {
            bankNo: {
                required: true
            },
            transType: {
                required: function () {
                    return $("#bankNo").val() == "999" || $("#bankNo").val() == "T14"
                }
            },
            workDay: {
                required: true
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
    });

    $("#workDay").datetimepicker({
        language: 'zh-CN',
        autoclose: 1,
        todayBtn: 1,
        pickerPosition: "bottom-left",
        format: 'yyyy-mm-dd',
        minView: 2
    });

    var today = new Date();

    $("#workDay").val(today.getFullYear() + "-" + p(today.getMonth() + 1) + "-" + p(today.getDate()));

    function p(num) {
        return num < 10 ? "0" + num : num;
    }

    $("#bankNo").change(function () {
        var $this = $(this);
        //添加全部对账的select option标签
        if ($this.val() != "999" && $this.val() != "T14" || $this.val() == "all") {
            $("#transTypeGroup").hide();
            $("#workDayGroup").show();
            if ($this.val() == "X08") {
                $("#workDayGroup").hide();
                $("#transTypeGroup").hide();
            }
        } else {
            $("#transTypeGroup").show();
            $("#workDayGroup").show();
        }
    });

});