$(function () {
    $('#dashboard-transaction').addClass('active open');
    $('#dashboard-dz-manage').addClass('active');
    $("#bankmock").addClass("active open");

    var showNotify = function (response) {
        Notify.success("删除对账文件成功");
    };

    var $table = $('#dz-table');
    $table.on('click', 'a[data-role=delete-dz]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");
        $.messager.confirm("警告", "确定删除" + title + "吗?", function () {
            $.get(url, function () {
                    $trigger.parents('tr').remove();
                })
                .success(showNotify)
        });
    });

    $table.on('click', 'a[data-role=status-dz]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            var $tr = $(html);
            $('#' + $tr.attr('id')).replaceWith($tr);
        })
    });

    $table.on('click', 'a[data-role=push-dz]', function () {
        var $this = $(this);
        var url = $this.data('url');
        $.get(url, function (html) {
            if (html == "success") {
                Notify.success("推送成功!");
            } else {
                Notify.error("推送失败, 请检查银行回调地址是否正确!");
            }
        })
    });

    $("#startDate").datetimepicker({
        language: 'zh-CN',
        autoclose: 1,
        todayBtn: 1,
        pickerPosition: "bottom-left",
        format: 'yyyy-mm-dd',
        minView: 2
    });
    $("#endDate").datetimepicker({
        language: 'zh-CN',
        autoclose: 1,
        todayBtn: 1,
        pickerPosition: "bottom-left",
        format: 'yyyy-mm-dd',
        minView: 2
    });
});